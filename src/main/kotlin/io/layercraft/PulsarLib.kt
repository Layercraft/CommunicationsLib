package io.layercraft

import org.apache.pulsar.client.api.MessageListener
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.apache.pulsar.client.api.SubscriptionType
import java.util.UUID
import java.util.concurrent.TimeUnit

object PulsarLib {

    private val id: UUID = UUID.randomUUID()

    fun createClient(): PulsarClient {
        return PulsarClient.builder()
            .serviceUrl("pulsar://127.0.0.1:6650")
            .build()
    }

    fun createStringProducer(topic: String): Producer<String> {
        val client = createClient()

        return client.newProducer(Schema.STRING)
            .topic(topic)
            .create()
    }

    fun createStringProducer(client: PulsarClient, topic: String): Producer<String> {
        return client.newProducer(Schema.STRING)
            .topic(topic)
            .create()
    }

    inline fun <reified T> createProducer(topic: String): Producer<T> {
        val client = createClient()

        return client.newProducer(Schema.AVRO(T::class.java))
            .topic(topic)
            .create()
    }

    inline fun <reified T> createProducer(client: PulsarClient, topic: String): Producer<T> {
        return client.newProducer(Schema.AVRO(T::class.java))
            .topic(topic)
            .create()
    }


}

fun main() {
    val client = PulsarClient.builder()
        .serviceUrl("pulsar://127.0.0.1:6650")
        .build()

    val producer: Producer<String> = client.newProducer(Schema.STRING)
        .topic(Topics.API.topic)
        .create()

    repeat(100) {
        println("Sending message $it")
        producer.newMessage()
            .key("bedwars")
            .value("Hello World")
    }

    producer.close()
    client.close()
}
