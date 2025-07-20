import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun main() {
    val props = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "aghighi-K1:1992")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        put(ProducerConfig.ACKS_CONFIG, "all")
        put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
        put(ProducerConfig.RETRIES_CONFIG, 3)
    }

    val producer = KafkaProducer<String, String>(props)
    val topic = "aghighi-topic"

    println("⏳ Producer started. Sending message every 5 seconds...")

    fixedRateTimer(name = "kafka-producer", initialDelay = 0, period = 5000) {
        val message = "timestamp: ${System.currentTimeMillis()}"
        producer.send(ProducerRecord(topic, message)) { metadata, exception ->
            if (exception == null) {
                println("✅ Sent to ${metadata.topic()} @ offset ${metadata.offset()}: $message")
            } else {
                println("❌ Error sending: ${exception.message}")
            }
        }
    }
}
