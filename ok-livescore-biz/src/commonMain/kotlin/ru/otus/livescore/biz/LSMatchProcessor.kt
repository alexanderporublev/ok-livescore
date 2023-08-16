package ru.otus.livescore.biz

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsCorSettings

import ru.otus.livescore.biz.workers.*
import ru.otus.livescore.biz.groups.*
import ru.otus.livescore.biz.validation.*


import ru.otus.livescore.cor.rootChain
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.models.*

class LSMatchProcessor(val settings: LsCorSettings) {

    suspend fun exec(ctx: LsContext) = BusinessChain.exec( ctx.apply { this.settings =  this@LSMatchProcessor.settings } )

    companion object {
        private val BusinessChain = rootChain<LsContext> {
            initStatus("Инициализация статуса")
            operation("Создание матча", LsCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadParticipant("Имитация ошибки валидации участника")
                    stubValidationBadScore("Имитация ошибки валидации очков")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в matchValidating") { matchValidating = matchRequest.deepCopy() }
                    worker("Очистка id") { matchValidating.id = LsMatchId.NONE }
                    worker ( "Очистка идентификатора события" ) { matchValidating.eventId = LsEventId(matchValidating.eventId.asString().trim()) }
                    worker("Очистка участника 1") { matchValidating.participant1 = matchValidating.participant1.trim() }
                    worker("Очистка участника 2") { matchValidating.participant2 = matchValidating.participant2.trim() }

                    worker("Очистка корта") { matchValidating.court = matchValidating.court.trim() }
                    validateParticipant1NotEmpty("Проверка, что участник 1 не пуст")
                    validateParticipant2NotEmpty("Проверка, что участник 2 не пуст")

                    validateParticipant1HasContent("Проверка символов")
                    validateParticipant2HasContent("Проверка символов")

                    validateEventIdNotEmpty("Проверка, что идентификатор события не пусто")
                    validateEventIdProperFormat("Проверка что идентификатор события имеет правильный формат")

                    finishMatchValidation("Завершение проверок")
                }
            }

            operation("Получение матча", LsCommand.READ){
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в matchValidating") { matchValidating = matchRequest.deepCopy() }
                    worker("Очистка id") { matchValidating.id = LsMatchId(matchValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishMatchValidation("Успешное завершение процедуры валидации")
                }
            }

            operation("Изменение матча", LsCommand.UPDATE){
                stubs("Обратобк сатабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadParticipant("Имитация ошибки валидации участника")
                    stubValidationBadScore("Имитация ошибки валидации очков")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    worker("Копируем поля в matchValidating") { matchValidating = matchRequest.deepCopy() }
                    worker("Очистка id") { matchValidating.id = LsMatchId(matchValidating.id.asString().trim()) }
                    worker ( "Очистка идентификатора события" ) { matchValidating.eventId = LsEventId(matchValidating.eventId.asString().trim()) }
                    worker("Очистка участника 1") { matchValidating.participant1 = matchValidating.participant1.trim() }
                    worker("Очистка участника 2") { matchValidating.participant2 = matchValidating.participant2.trim() }
                    worker("Очистка корта") { matchValidating.court = matchValidating.court.trim() }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    validateParticipant1NotEmpty("Проверка, что участник 1 не пуст")
                    validateParticipant2NotEmpty("Проверка, что участник 2 не пуст")

                    validateParticipant1HasContent("Проверка символов")
                    validateParticipant2HasContent("Проверка символов")

                    validateEventIdNotEmpty("Проверка, что идентификатор события не пусто")
                    validateEventIdProperFormat("Проверка что идентификатор события имеет правильный формат")

                    finishMatchValidation("Успешное завершение процедуры валидации")

                }
            }

            operation("Удалить объявление", LsCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation{
                    worker("Копируем поля в matchValidating") { matchValidating = matchRequest.deepCopy() }
                    worker("Очистка id") { matchValidating.id = LsMatchId(matchValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishMatchValidation("Успешное завершение процедуры валидации")
                }
            }

        }.build()
    }
}