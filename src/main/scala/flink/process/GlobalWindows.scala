//package flink.process
//
//import java.sql.Timestamp
//
//import org.apache.flink.api.java.utils._
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.api.common.functions._
//import org.apache.flink.api.java.tuple._
//import org.apache.flink.streaming.api.scala.DataStream
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.windowing.assigners._
//import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
//import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
//import org.apache.flink.streaming.api.windowing.triggers.CountTrigger
//import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows
//
//
//import scala.collection.JavaConversions._
//
///*
//> doesn't have an end.
//> we don't perform subdivision of elements
//> there will be only one window per key creator
//> computation in this one is done by using triggers.
//
// */
//
//
//object GlobalWindows {
//  def main(args:Array[String]):Unit = {
//    val env:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
//    val data:DataStream[String] = env.socketTextStream("localhost",9090)
//    val mapped:DataStream[Tuple5[String,String,String,Integer,Integer]] = data.map(new Splitter())
//    val reduced: DataStream[Tuple5[String, String, String, Integer, Integer]] = mapped
//        .keyBy(0)
//        .window(GlobalWindows())
//        .trigger(CountTrigger.of(5))
//        .reduce(new Reduce1())
//    reduced.writeAsText("path")
//    env.execute("GLOBAL WINDOW")
//
//  }
//
//  class Splitter extends MapFunction[String,Tuple5[String,String,String,Integer,Integer]]{
//    def map(value:String):Tuple5[String,String,String,Integer,Integer] = {
//      val words = value.split(",")
//      new Tuple5[String,String,String,Integer,Integer](
//        words(1),
//        words(2),
//        words(3),
//        java.lang.Integer.parseInt(words(4)),
//        1
//      )
//    }
//  }
//
//  class Reduce1 extends ReduceFunction[Tuple5[String,String,String,Integer,Integer]]{
//    def reduce(current:Tuple5[String,String,String,Integer,Integer],
//               pre_result:Tuple5[String,String,String,Integer,Integer]):Tuple5[String,String,String,Integer,Integer]={
//      new Tuple5[String,String,String,Integer,Integer](
//        current.f0,
//        current.f1,
//        current.f2,
//        current.f3 + pre_result.f3,
//        current.f4 + pre_result.f4
//      )
//    }
//  }
//
//}
