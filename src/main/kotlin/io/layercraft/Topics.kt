package io.layercraft

/**
 * Topics are used to identify the component where the message goes to.
 *
 * @see <a href="https://info.layercraft.io/software-parts/services.html">Services</a>
 * @see <a href="https://pulsar.apache.org/docs/2.11.x/concepts-messaging/">Pulsar-Messaging</a>
 */
enum class Topics(
    val topic: String,
    val defaultSubscriptionName: String = "default",
) {
    // outgoing
    CONNECTOR("connector"),
    // incoming
    WORLD_SERVICE("world-service"),
    ENTITY_SERVICE("entity-service"),
    PLAYER_SERVICE("player-service"),
    SERVER_SERVICE("server-service"),
    CHAT_SERVICE("chat-service"),
    API("api2"),

}
