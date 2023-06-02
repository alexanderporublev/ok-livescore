package ru.otus.otuskotlin.livescore.mappers.v1.exceptions

import ru.otus.otuskotlin.livescore.common.models.LsCommand

class UnknownLsCommand(command: LsCommand) : Throwable("Wrong command $command at mapping toTransport stage")
