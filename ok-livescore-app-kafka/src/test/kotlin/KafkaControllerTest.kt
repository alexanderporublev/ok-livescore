package ru.otus.livescore.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.marketplace.api.v1.apiV1ResponseDeserialize
import ru.otus.api.v1.models.MatchCreateObject
import ru.otus.api.v1.models.MatchCreateRequest
import ru.otus.api.v1.models.MatchCreateResponse
import ru.otus.api.v1.models.MatchDebug
import ru.otus.api.v1.models.MatchRequestDebugMode
import ru.otus.api.v1.models.MatchRequestDebugStubs
import ru.otus.api.v1.models.MatchStatus
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicIn
        val outputTopic = config.kafkaTopicOut

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(MatchCreateRequest(
                        requestId = "11111111-1111-1111-1111-111111111111",
                        match = MatchCreateObject(
                            particapant1 = "Ivanov",
                            particapant2 = "Petrov",
                            matchStatus = MatchStatus.INPROGRESS,
                            datetime = "2022-02-02T00:00:00"
                        ),
                        debug = MatchDebug(
                            mode = MatchRequestDebugMode.STUB,
                            stub = MatchRequestDebugStubs.SUCCESS
                        )
                    ))
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<MatchCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals(MatchStatus.INPROGRESS, result.ad?.matchStatus)
    }

    companion object {
        const val PARTITION = 0
    }
}


