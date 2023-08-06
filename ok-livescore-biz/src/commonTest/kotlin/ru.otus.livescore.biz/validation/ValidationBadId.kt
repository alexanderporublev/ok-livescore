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
fun validationIdCorrect(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.get()
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LsState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            id = LsMatchId(" \n\t 765 \n\t ")
        },
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LsState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            id = LsMatchId("")
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")

}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: LsCommand, processor: LSMatchProcessor) = runTest {
    val ctx = LsContext(
        command = command,
        state = LsState.NONE,
        workMode = LsWorkMode.TEST,
        matchRequest = LsMatchStub.prepareResult {
            id = LsMatchId("!@#\$%^&*(),.{}")
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LsState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")

}