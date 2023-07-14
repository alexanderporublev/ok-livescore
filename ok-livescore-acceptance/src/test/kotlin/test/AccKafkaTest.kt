package ru.otus.livescore.blackbox.test

import ru.otus.livescore.blackbox.fixture.BaseFunSpec
import ru.otus.livescore.blackbox.docker.KafkaDockerCompose
import ru.otus.livescore.blackbox.fixture.client.KafkaClient

class AccKafkaTest : BaseFunSpec(KafkaDockerCompose, {
    val client = KafkaClient(KafkaDockerCompose)

    testApiV1(client)
})
