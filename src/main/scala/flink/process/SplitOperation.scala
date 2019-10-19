package flink.process


import java.util

import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import java.util.ArrayList

import org.apache.flink.streaming.api.collector.selector._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment



object SplitOperation {
  def main(args:Array[String]):Unit={
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/oddeven")
    val evenOddStream : SplitStream[Integer] = data
      .map(new StringToInt())
      .split(new OutSelector())

    val evenData : DataStream[Integer] = evenOddStream.select("even")
    val oddData  : DataStream[Integer] = evenOddStream.select("odd")
    evenData.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/evenData")
    oddData.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/oddData")

    env.execute("segregation of odd even")
  }

  class OutSelector extends OutputSelector[Integer]{
    def select(value:java.lang.Integer):java.lang.Iterable[String] = {
      val out = new ArrayList[String]
      if (value%2==0){
        out.add("even")
      }
      else{
        out.add("odd")
      }
      out
    }
  }

  class StringToInt extends MapFunction[String,Integer]{
    def map(value:String):java.lang.Integer = {
      java.lang.Integer.parseInt(value)
    }
  }

}
