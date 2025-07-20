import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*

fun main() {
    val props = Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "aghighi-K1:1992")
        put(ConsumerConfig.GROUP_ID_CONFIG, "aghighi-consumer-group")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    }

    val consumer = KafkaConsumer<String, String>(props)
    consumer.subscribe(listOf("aghighi-topic"))

    println("🔍 Consumer started. Waiting for messages...")

    while (true) {
        val records = consumer.poll(Duration.ofMillis(500))
        for (record in records) {
            println("📥 Received: ${record.value()} from partition ${record.partition()} at offset ${record.offset()}")
        }
    }
}
