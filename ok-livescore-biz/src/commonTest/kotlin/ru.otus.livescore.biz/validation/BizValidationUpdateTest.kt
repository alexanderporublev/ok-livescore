package ru.otus.livescore.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.LsCorSettings
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {
    private val command = LsCommand.UPDATE
    private val processor = LSMatchProcessor(LsCorSettings())

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
    @Test fun correctParticipant() = validationParticipantCorrect(command, processor)
    @Test fun trimParticipant() = validationParticipantTrim(command, processor)
    @Test fun emptyParticipant() = validationParticipantEmpty(command, processor)

}