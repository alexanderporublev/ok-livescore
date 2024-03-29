package ru.otus.otuskotlin.livescore.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class LsLoggerProvider(
    private val provider: (String) -> ILsLogWrapper = { ILsLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
    fun logger(function: KFunction<*>) = provider(function.name)
}
