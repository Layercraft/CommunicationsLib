package io.layercraft

import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import java.util.UUID

object CommunicationsLib {

    val id: UUID = UUID.randomUUID()

    fun createClient(): PulsarClient {
        return PulsarClient.builder()
            .serviceUrl("pulsar://127.0.0.1:6650")
            .build()
    }

    fun createProducer(topic: String): Producer<String> {
        val client = createClient()

        return client.newProducer(Schema.STRING)
            .topic(topic)
            .create()
    }

    fun createProducer(client: PulsarClient, topic: String): Producer<String> {
        return client.newProducer(Schema.STRING)
            .topic(topic)
            .create()
    }

    inline fun <reified T> createProducer(): Producer<T> {
        val client = createClient()

        return client.newProducer(Schema.AVRO(T::class.java))
            .topic("my-topic")
            .create()
    }

    inline fun <reified T> createProducer(client: PulsarClient): Producer<T> {
        return client.newProducer(Schema.AVRO(T::class.java))
            .topic("my-topic")
            .create()
    }
}

fun main() {
    val client = PulsarClient.builder()
        .serviceUrl("pulsar://127.0.0.1:6650")
        .build()

    val producer: Producer<String> = client.newProducer(Schema.STRING)
        .topic("my-topic")
        .create()

    for (i in 0..10_000_000) {
        producer.sendAsync("Hello World $i")
    }

    producer.close()
    client.close()
}
