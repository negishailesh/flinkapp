package flink.source

import java.util.Properties

import org.apache.flink.api.common.serialization._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011



class KafkaConsumerAPI {

  def buildConsumer(topic:  String): FlinkKafkaConsumer011[String] = {

    new FlinkKafkaConsumer011[String](
      topic,
      new SimpleStringSchema(),
      getDefaultConsumerConfig
    )
  }

  def getDefaultConsumerConfig: Properties = {
    val consumerProperties = new Properties()
    consumerProperties.setProperty(
      "bootstrap.servers",
      "localhost:9092"
    )
    consumerProperties
  }
}
