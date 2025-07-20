import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun main() {
    val props = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"aghighi-K1:9094")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
        put(ProducerConfig.ACKS_CONFIG,"all")
        put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true")
        put(ProducerConfig.RETRIES_CONFIG,"3")
        put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SASL_SSL")
        put(SaslConfigs.SASL_MECHANISM,"PLAIN")
        put(SaslConfigs.SASL_JAAS_CONFIG,"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"admin-secret\";")
        put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,"/opt/certs/kafka.truststore.jks")
        put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,"mirror123")
        put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,"/opt/certs/kafka.keystore.jks")
        put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG,"mirror123")
        put(SslConfigs.SSL_KEY_PASSWORD_CONFIG,"mirror123")
    }
    val producer = KafkaProducer<String,String>(props)
    fixedRateTimer(initialDelay=0,period=5000){
        val m="timestamp:${System.currentTimeMillis()}"
        producer.send(ProducerRecord("aghighi-topic",m)){r,e->
            if(e==null) println("${r.topic()}@${r.offset()}:$m") else println(e.message)
        }
    }
}
