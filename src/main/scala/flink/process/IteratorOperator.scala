package flink.process



import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.api.common.functions.FilterFunction
import org.apache.flink.streaming.api.collector.selector._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.datastream.IterativeStream
import org.apache.flink.streaming.api.datastream.DataStream

import scala.collection.JavaConversions._



object IteratorOperator {
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    val data = env.generateSequence(0, 5).map(new Splitter())
//    //( 0,0   1,0  2,0  3,0   4,0  5,0 )
//    //special kind of stream which can accept feedback from other stream.
//    val iteration: IterativeStream[Tuple2[Long, Integer]] = data.iterate(5000)
//    // define iteration
//    val plusOne: DataStream[Tuple2[Long, Integer]] = iteration.map(
//      new MapFunction[Tuple2[Long, Integer], Tuple2[Long, Integer]]() {
//        def map(value: Tuple2[Long, Integer]): Tuple2[Long, Integer] =
//          if (value.f0 == 10) value
//          else new Tuple2[Long, Integer](value.f0 + 1, value.f1 + 1)
//      })
//    // part of stream to be used in next iteration (
//    val notEqualtoten: DataStream[Tuple2[Long, Integer]] =
//      plusOne.filter(new FilterFunction[Tuple2[Long, Integer]]() {
//        def filter(value: Tuple2[Long, Integer]): Boolean =
//          if (value.f0 == 10) false else true
//      })
//    // feed data back to next iteration
//    iteration.closeWith(notEqualtoten)
//    // data not feedback to iteration
//    val equaltoten: DataStream[Tuple2[Long, Integer]] =
//      plusOne.filter(new FilterFunction[Tuple2[Long, Integer]]() {
//        def filter(value: Tuple2[Long, Integer]): Boolean =
//          if (value.f0 == 10) true else false
//      })
//    equaltoten.writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/IteratorResult")
//    env.execute("Iteration Demo")
//  }
//  // prepare stream for iteration
//  //   plusone    1,1   2,1  3,1   4,1   5,1   6,1
//  // prepare stream for iteration
//  //   plusone    1,1   2,1  3,1   4,1   5,1   6,1
//
//  class Splitter extends MapFunction[Long , Tuple2[Long, Integer]]{
//    def map(value:Long):Tuple2[Long, Integer] = {
//      new Tuple2[Long, Integer](value, 0)
//    }
//  }
}