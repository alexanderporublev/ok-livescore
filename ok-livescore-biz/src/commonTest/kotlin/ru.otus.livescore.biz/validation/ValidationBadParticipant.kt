package ru.otus.livescore.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.livescore.biz.LSMatchProcessor
import ru.otus.livescore.stubs.LsMatchStub
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.livescore.common.models.*
import ru.otus.otuskotlin.livescore.common.stubs.LsStubs

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationParticipantCorrect(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            participant1 = "Bykov"
        }
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LsState.FAILING, ctx.state)
    assertEquals("Bykov", ctx.matchValidated.participant1)

}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationParticipantTrim(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            participant1 = " \n\t Bykov \t\n "
        }
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LsState.FAILING, ctx.state)
    assertEquals("Bykov", ctx.matchValidated.participant1)

}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationParticipantEmpty(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            participant1 = ""
        }
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals(error?.field, "participant1")
    assertContains(error?.message ?: "", "participant" )
}

