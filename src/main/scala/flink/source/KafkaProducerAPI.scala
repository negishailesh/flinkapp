package flink.source
import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka._
import org.apache.kafka.clients.producer.ProducerConfig

class KafkaProducerAPI() {

    def buildProducer(topic:  String): FlinkKafkaProducer011[String] = {

      new FlinkKafkaProducer011[String](
        topic, // target topic
        new SimpleStringSchema(),
        getDefaultProducerConfig
      )
    }

    def getDefaultProducerConfig: Properties = {
      val producerProperties = new Properties()
      producerProperties.setProperty(
        "bootstrap.servers",
        "localhost:9092"
      )
      producerProperties
    }
}


