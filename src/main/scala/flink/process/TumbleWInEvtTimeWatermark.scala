//package flink.process
//
//import java.sql.Timestamp
//
//import org.apache.flink.api.common.functions._
//import org.apache.flink.api.java.tuple._
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
//import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
//import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
//import org.apache.flink.streaming.api.watermark.Watermark
//
//
///*
//Built-In watermark Generator:
//> BoundedOutOfOrderTimestampExtractor(allowedLateTime is dynamic and can be passed as an argument.)
//> AscendingTimestampExtractor(no concept of lateness and allowed lateness.)
// */
//
//object TumbleWInEvtTimeWatermark {
//  def main(args:Array[String]):Unit={
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//
//    val data:DataStream[String] = env.socketTextStream("localhost",9090)
//
//    val sum:DataStream[Tuple2[Long,String]] = data
//      .map(new Splitter())
//      .assignTimestampsAndWatermarks(new DemoWatermark())
//      .windowAll(TumblingProcessingTimeWindows.of(Time.seconds(10))) // this time of 5 seconds will be event time.
//      .reduce(new Reduce1())
//
//    sum.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/TumblingEventTime_result.txt")
//    env.execute("event time tumbling window")
//  }
//
//
////3.5 seconds
//  class DemoWatermark extends AssignerWithPeriodicWatermarks[Tuple2[Long,String]]{
//    private val allowedlateTime:Long = 3500
//    private var currentmaxTimeStamp:Long = 0
//
//    def extractTimestamp(element: Tuple2[Long, String], previousElementTimestamp: Long): Long = {
//      val timestamp:Long = element.f0
//      currentmaxTimeStamp = Math.max(timestamp,currentmaxTimeStamp)
//      timestamp
//    }
//
//    def getCurrentWatermark: Watermark = {
//      new Watermark(currentmaxTimeStamp - allowedlateTime)
//    }
//  }
//
//
//  class Splitter extends MapFunction[String , Tuple2[Long,String]]{
//    def map(value:String):Tuple2[Long,String] = {
//      val words:Array[String] = value.split(",")
//      new Tuple2[Long,String](
//        java.lang.Long.parseLong(words(0)),
//        words(1))
//    }
//  }
//
//  class Reduce1 extends ReduceFunction[Tuple2[Long,String]]{
//    def reduce(current:Tuple2[Long,String],previous:Tuple2[Long,String]):Tuple2[Long,String]={
//      var num1 : Int = java.lang.Integer.parseInt(current.f1)
//      var num2 : Int = java.lang.Integer.parseInt(previous.f1)
//      var sum  = num1 + num2
//      var t : Timestamp = new Timestamp(System.currentTimeMillis())
//      new Tuple2[Long,String](t.getTime , sum.toString)
//    }
//  }
//
//}
