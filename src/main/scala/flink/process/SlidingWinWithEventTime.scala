package flink.process
import java.sql.Timestamp

import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor


object SlidingWinWithEventTime {
  def main(args:Array[String]):Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    val data:DataStream[String] = env.socketTextStream("localhost",9090)
    val sum:DataStream[Tuple2[Long,String]] = data
      .map(new Splitter())
        .assignTimestampsAndWatermarks(new AssignTimeStAndWatermark())
        .windowAll(SlidingEventTimeWindows.of(Time.seconds(4),Time.seconds(2)))
        .reduce(new Reduce1())
    sum.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/SlidingWinWithEventTime")
    env.execute("run sliding window with event time")
  }


  class AssignTimeStAndWatermark extends AscendingTimestampExtractor[Tuple2[Long,String]]{
    def extractAscendingTimestamp(t:Tuple2[Long,String]):Long = {
      t.f0
    }
  }


  class Reduce1 extends ReduceFunction[Tuple2[Long,String]]{
    def reduce(t1:Tuple2[Long,String],
               t2:Tuple2[Long,String]):Tuple2[Long,String]= {
      val num1: Int = java.lang.Integer.parseInt(t1.f1)
      val num2: Int = java.lang.Integer.parseInt(t2.f1)
      val sum : Int = num1 + num2
      val t:Timestamp = new Timestamp(System.currentTimeMillis())
      new Tuple2[Long,String](t.getTime,"" + sum)
      }
  }


  class Splitter extends MapFunction[String,Tuple2[Long,String]]{
    def map(value:String):Tuple2[Long,String] = {
      val words:Array[String] = value.split(",")
      new Tuple2[Long,String](
        java.lang.Long.parseLong(words(0)),
        words(1)
      )
    }
  }


}

