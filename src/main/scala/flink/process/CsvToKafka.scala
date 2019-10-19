package flink.process

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import flink.pojoClass.JsonMessage
import flink.source.KafkaProducerAPI


object CsvToKafka {

  def main(args:Array[String]): Unit ={
    //checking input parameter
    val params = ParameterTool.fromArgs(args)
    //setup execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //make parameter available in web interface
    env.getConfig.setGlobalJobParameters(params)
    val text: DataStream[String] = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/movies.csv")

//    val new_text: DataStream[JsonMessage] = text.map(x=>{new JsonMessage(x)})
    val sank = new KafkaProducerAPI().buildProducer("test")
    sank.setWriteTimestampToKafka(true)
    text.addSink(sank)

    env.execute("Streaming Kafka")
  }

}
