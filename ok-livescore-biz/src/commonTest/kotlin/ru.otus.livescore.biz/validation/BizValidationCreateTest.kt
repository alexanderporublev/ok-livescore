package ru.otus.livescore.biz.validation

import kotlin.test.Test
import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.LsCorSettings
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs

class BizValidationCreateTest {
    private val command = LsCommand.CREATE
    private val processor = LSMatchProcessor(LsCorSettings())

    @Test fun correctParticipant() = validationParticipantCorrect(command, processor)
    @Test fun trimParticipant() = validationParticipantTrim(command, processor)
    @Test fun emptyParticipant() = validationParticipantEmpty(command, processor)

}