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
//
//
//
//object TumblingProcessingTimeWindow {
//  def main(args : Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
//
//    val data = env.socketTextStream("localhost", 9090)
//
//    val mapped: DataStream[Tuple5[String,String,String,Integer,Integer]] = data.map(new Splitter())
//
//    val reduced: DataStream[Tuple5[String,String,String,Integer,Integer]] = mapped
//      .keyBy(0)
//      .window(TumblingProcessingTimeWindows.of(Time.seconds(2)))
//      .reduce(new Reduce1())
//
//    val profitPerMonth:DataStream[Tuple2[String,Double]] = reduced
//      .map(new MapFunction[Tuple5[String,String,String,Integer,Integer],Tuple2[String,Double]]() {
//        def map(input:Tuple5[String,String,String,Integer,Integer]):Tuple2[String,Double] ={
//          new Tuple2[String,Double](input.f0 , new java.lang.Double((input.f3*1.0) / input.f4))
//        }
//      })
//
//    profitPerMonth.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/TumblingProcessingTimeWindow.txt")
//
//    env.execute("avg profit per month")
//
//  }
//
//
//  class Splitter extends MapFunction[String , Tuple5[String, String, String, Integer, Integer]]{
//    def map(value:String):Tuple5[String,String,String,Integer,Integer] = { //input > "01-06-2018,june,category,bat,12"
//
//      val words:Array[String] = value.split(",")
//
//      new Tuple5[String,String,String,Integer,Integer](
//        words(1),
//        words(2),
//        words(3),
//        java.lang.Integer.parseInt(words(4)),
//        1)
//    }
//  }
//
//  class Reduce1 extends ReduceFunction[Tuple5[String,String,String,Integer,Integer]]{
//    def reduce(current:Tuple5[String,String,String,Integer,Integer],
//               pre_result:Tuple5[String,String,String,Integer,Integer]):Tuple5[String,String,String,Integer,Integer] = {
//
//      new Tuple5[String,String,String,Integer,Integer](
//        current.f0,
//        current.f1,
//        current.f2,
//        current.f3 + pre_result.f3,
//        current.f4 + pre_result.f4
//      )
//    }
//  }
//}
