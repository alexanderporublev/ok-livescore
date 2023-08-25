package ru.otus.livescore.biz

import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsCorSettings

import ru.otus.livescore.biz.workers.*
import ru.otus.livescore.biz.groups.*
import ru.otus.livescore.biz.validation.*
import ru.otus.livescore.biz.general.*

import ru.otus.livescore.cor.rootChain
import ru.otus.livescore.cor.worker
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.livescore.cor.chain
import ru.otus.livescore.biz.repo.*

class LSMatchProcessor(val settings: LsCorSettings) {

    suspend fun exec(ctx: LsContext) = BusinessChain.exec( ctx.apply {
        this.settings =  this@LSMatchProcessor.settings } )

    companion object {
        private val BusinessChain = rootChain<LsContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")
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
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                }
                prepareResult("Подготовка ответа")
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
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объявления из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == LsState.RUNNING }
                        handle { matchRepoDone = matchRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")

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
                    worker ( "Очистка идентификатора события" ) {
                        println("valid " + matchRequest.eventId.asString())
                        matchValidating.eventId = LsEventId(matchValidating.eventId.asString().trim())
                    }
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
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение объявления из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление объявления в БД")
                }
                prepareResult("Подготовка ответа")

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
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение объявления из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление объявления из БД")
                }
                prepareResult("Подготовка ответа")
            }

        }.build()
    }
}