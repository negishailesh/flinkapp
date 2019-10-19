package flink.process

import flink.source.KafkaConsumerAPI
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._



object KafkaToAvro {

  def main(args:Array[String]): Unit ={
    //checking input parameter
    val params = ParameterTool.fromArgs(args)
    //setup execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //make parameter available in web interface
    env.getConfig.setGlobalJobParameters(params)

    val stream = env.addSource(new KafkaConsumerAPI().buildConsumer("test"))
    stream.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/new_result.txt")
    env.execute("Kafka To Avro")
  }

}
