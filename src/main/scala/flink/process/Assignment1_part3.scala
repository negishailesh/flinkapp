package flink.process

import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment



object Assignment1_part3 {
  def main(args:Array[String]):Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/cab-flink.txt")
    val mapped:DataStream[Tuple3[String,Integer,Integer]] = data.map(new Splitter())
    val reduced:DataStream[Tuple3[String,Integer,Integer]] = mapped.keyBy(0).reduce(new Reduce1())
    val avgNoOfPassenger:DataStream[Tuple2[String,Double]] = reduced.map(x => new Tuple2[String,Double](
      x.f0,
      new java.lang.Double((x.f1*1.0) / x.f2)
    ))
    avgNoOfPassenger.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/assignment_result3")
    env.execute("Assignment_1")
  }

  class Splitter extends MapFunction[String , Tuple3[String ,Integer, Integer]]{
    def map(value : String):Tuple3[String,Integer,Integer] = {
      val words: Array[String] = value.split(",")
      new Tuple3[String,Integer,Integer](
        if (words(3) != "'null'") words(3) else "Unknown",
        if (words(7) != "'null'") words(7).toInt else 0,
        1
      )
    }
  }

  class Reduce1 extends ReduceFunction[Tuple3[String,Integer,Integer]]{
    def reduce(current:Tuple3[String,Integer,Integer],previous:Tuple3[String,Integer,Integer]):Tuple3[String,Integer,Integer]={
      new Tuple3[String,Integer,Integer](
        current.f0,
        current.f1 + previous.f1,
        current.f2 + previous.f2
      )
    }
  }
}

