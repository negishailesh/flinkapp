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
//
//
//
///*
//created based on activity
//don't have fixed start or end time
//start whenever any input comes and ends after a set interval of time or inactivity
//INORDER TO BE MERGABLE SESSION WINDOW NEEDS MERGING TRIGGER AND WINDOW FUNCTION.
//FOLD FUNCTION CAN"T BE MERGED.
//*/
//
//
//object SessionWindows {
//  def main(args:Array[String]):Unit={
//    val env:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    val data:DataStream[String] = env.socketTextStream("localhost",9090)
//    val sum:DataStream[Tuple2[Long,String]] = data
//      .map(new Splitter())
//      .assignTimestampsAndWatermarks(new AssignTimeStamp())
//      .windowAll(EventTimeSessionWindows.withGap(Time.seconds(1)))
//      .reduce(new Reduce())
//    sum.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/sessionwithEventTime")
//    env.execute("Session windowing with event time")
//  }
//
//
//  class Splitter extends MapFunction[String,Tuple2[Long,String]]{
//    def map(value:String):Tuple2[Long,String]={
//      val words:Array[String] = value.split(",")
//      new Tuple2[Long,String](
//        java.lang.Long.parseLong(words(0)),
//        words(1)
//      )
//    }
//  }
//
//  class AssignTimeStamp extends AscendingTimestampExtractor[Tuple2[Long,String]]{
//    def extractAscendingTimestamp(t:Tuple2[Long,String]):Long={
//      t.f0
//    }
//  }
//
//  class Reduce extends ReduceFunction[Tuple2[Long,String]]{
//    def reduce(t1:Tuple[Long,String],
//               t2:Tuple[Long,String]):Tuple2[Long,String]={
//      var num1:Int = java.lang.Integer.parseInt(t1.f1)
//      var num2:Int = java.lang.Integer.parseInt(t2.f1)
//      var sum:Int = num1+num2
//      var t:Timestamp = new Timestamp(System.currentTimeMillis())
//      new Tuple2[Long,String](t.getTime,""+sum)
//    }
//  }
//}
