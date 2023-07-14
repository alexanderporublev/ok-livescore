package ru.otus.livescore.app.kafka

import ru.otus.otuskotlin.marketplace.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.marketplace.api.v1.apiV1ResponseSerialize
import ru.otus.api.v1.models.IRequest
import ru.otus.api.v1.models.IResponse
import ru.otus.otuskotlin.livescore.common.LsContext
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportMatch

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicIn, config.kafkaTopicOut)
    }

    override fun serialize(source: LsContext): String {
        val response: IResponse = source.toTransportMatch()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: LsContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}