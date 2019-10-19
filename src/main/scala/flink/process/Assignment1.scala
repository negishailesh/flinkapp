package flink.process

import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object Assignment1 {
  def main(args:Array[String]):Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/cab-flink.txt")
    val mapped:DataStream[Tuple2[String,Integer]] = data.map(new Splitter())
    mapped
      .keyBy(0)
      .sum(1)
      .writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/assignment_result1")
    env.execute("Assignment_1")
  }

  class Splitter extends MapFunction[String , Tuple2[String , Integer]]{
    def map(value : String):Tuple2[String,Integer] = {
      val words: Array[String] = value.split(",")
      new Tuple2[String,Integer](
         if (words(6) != "'null'") words(6) else "Unknown",
        1
      )
    }
  }
}
