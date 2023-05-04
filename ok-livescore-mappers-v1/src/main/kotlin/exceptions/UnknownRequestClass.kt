package ru.otus.otuskotlin.livescore.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to LsContext")
