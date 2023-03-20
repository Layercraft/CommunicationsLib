package io.layercraft

import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.KeySharedPolicy
import org.apache.pulsar.client.api.MessageListener
import org.apache.pulsar.client.api.MessageRouter
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.apache.pulsar.client.api.SubscriptionType
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.TimeUnit


fun main() {
    val client = PulsarClient.builder()
        .serviceUrl("pulsar://127.0.0.1:6650")
        .build()

    val messageListener: MessageListener<String> = MessageListener { consumer, msg ->
        try {
            println("Message received -> ${consumer.consumerName}: ${String(msg.data)} | ${msg.messageId}")

            consumer.acknowledge(msg)
        } catch (e: Exception) {
            println("Failed to process message")
            consumer.negativeAcknowledge(msg)
        }
    }

    MessageRouter

    val consumer = client.newConsumer(Schema.STRING)
        .topic(Topics.API.topic)
        .subscriptionName("bedwars")
        .subscriptionType(SubscriptionType.Key_Shared)
        .messageListener(messageListener)
        .negativeAckRedeliveryDelay(1, TimeUnit.MILLISECONDS)
        .subscribeAsync().get()

    println("Consumer: $consumer")
}
