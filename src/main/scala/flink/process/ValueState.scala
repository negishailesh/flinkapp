//package flink.process
//
//import java.sql.Timestamp
//
//import org.apache.flink.api.java.utils._
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.api.common.functions.MapFunction
//import org.apache.flink.api.common.functions.ReduceFunction
//import org.apache.flink.api.common.functions.RichFlatMapFunction
//import org.apache.flink.api.java.tuple._
//import org.apache.flink.streaming.api.scala.DataStream
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
//import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor
//import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
//import scala.collection.JavaConversions._
//import org.apache.flink.util.Collector
//import org.apache.flink.api.common.state.ValueState
//import org.apache.flink.api.common.state.ValueStateDescriptor
//import org.apache.flink.api.common.typeinfo.TypeHint
//import org.apache.flink.api.common.typeinfo.TypeInformation
//import org.apache.flink.configuration.Configuration
//import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
//import org.apache.flink.streaming.api.watermark.Watermark
//import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
//import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
//import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
//import org.apache.flink.streaming.api.windowing.time.Time
//
//
//
//
//object ValueState {
//  def main(args:Array[String]):Unit={
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    val data:DataStream[String] = env.socketTextStream("localhost",9090)
//    val sum:DataStream[Long] = data
//      .map(new Splitter())
//      .keyBy(0)
//      .flatMap(new StatefulMaps())
//    sum.writeAsText("path")
//    env.execute("value state")
//  }
//
//  class Splitter extends MapFunction[String,Tuple2[Long,String]]{
//    def map(value:String):Tuple2[Long,String]={
//      val words : Array[String] = value.split(",")
//      new Tuple2[Long,String](
//        java.lang.Long.parseLong(words(0)),
//        words(1)
//      )
//    }
//  }
//
//
//  class StatefulMaps extends RichFlatMapFunction[Tuple[Long,String] ,Long]{
//    //2
//    @transient private var sum: ValueState[Long] = _
//    //  4
//    @transient private var count: ValueState[Long] = _
//
//    def flatMap(input: Tuple[Long, String], out: Collector[Long]): Unit = {
//      var currCount:java.lang.Long = count.value()
//      var currSum:java.lang.Long = sum.value()
//      currCount+=1
//      currSum = currSum + java.lang.Long.parseLong(input.f1)
//      count.update(currCount)
//      sum.update(currSum)
//      if (currCount >= 10){
//        /* emit sum of last 10 elements */
//        out.collect(sum.value())
//        /* clear value */
//      }
//      count.clear()
//      sum.clear()
//    }
//
//    def open(conf:Configuration):Unit = {
//      val descriptor:ValueStateDescriptor[Long] = new ValueStateDescriptor[Long](
//        "sum",
//        TypeInformation.of(new TypeHint[Long]() {}),
//        0L)
//      sum = getRuntimeContext.getState(descriptor)
//      val descriptor2:ValueStateDescriptor[Long] = new ValueStateDescriptor[Long](
//        "count",
//        TypeInformation.of(new TypeHint[Long]() {}),
//        0L)
//      count = getRuntimeContext.getState(descriptor2)
//
//    }
//
//  }
//
//
//}
//
//
