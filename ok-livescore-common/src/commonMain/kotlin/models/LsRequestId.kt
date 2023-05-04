package ru.otus.otuskotlin.livescore.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class LsRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = LsRequestId("")
    }
}
