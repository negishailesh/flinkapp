//package flink.process
//
//import org.apache.flink.api.java.utils._
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.api.common.functions._
//import org.apache.flink.api.java.tuple._
//import org.apache.flink.streaming.api.scala.DataStream
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
//import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
//
//import java.sql.Timestamp
//
//object TumblingWindowEventTime {
//  def main(args:Array[String]):Unit={
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//
//    val data:DataStream[String] = env.socketTextStream("localhost",9090)
//
//    val sum:DataStream[Tuple2[Long,String]] = data
//      .map(new Splitter())
//      .assignTimestampsAndWatermarks(new AscendingTimeExt())
//      .windowAll(TumblingProcessingTimeWindows.of(Time.seconds(5))) // this time of 5 seconds will be event time.
//      .reduce(new Reduce1())
//
//    sum.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/TumblingEventTime_result.txt")
//    env.execute("event time tumbling window")
//  }
//
//
//  class AscendingTimeExt extends AscendingTimestampExtractor[Tuple2[Long,String]]{
//    //event time stamp is to be extracted  from each record and the same should be intimated to flink.
//    //so to intimate flink which field in timestamp we use this AscendingTimestampExtractor and watermark operations.
//    //inside which we will overwrite the below method to extract the time stamp from the stream and return the same
//    def extractAscendingTimestamp(element: Tuple2[Long, String]): Long = element.f0
//  }
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
