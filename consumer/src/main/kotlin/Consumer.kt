import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import java.time.Duration
import java.util.*

fun main() {
    val props=Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"aghighi-K1:9094")
        put(ConsumerConfig.GROUP_ID_CONFIG,"aghighi-consumer-group")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true")
        put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SASL_SSL")
        put(SaslConfigs.SASL_MECHANISM,"PLAIN")
        put(SaslConfigs.SASL_JAAS_CONFIG,"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"admin-secret\";")
        put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,"/opt/certs/kafka.truststore.jks")
        put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,"mirror123")
        put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,"/opt/certs/kafka.keystore.jks")
        put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG,"mirror123")
        put(SslConfigs.SSL_KEY_PASSWORD_CONFIG,"mirror123")
    }
    val consumer=KafkaConsumer<String,String>(props).apply {
        subscribe(listOf("aghighi-topic"))
    }
    while(true){
        consumer.poll(Duration.ofMillis(500)).forEach{r->println(r.value())}
    }
}
