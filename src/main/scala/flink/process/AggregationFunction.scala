package flink.process

import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment



object AggregationFunction {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1")
    //tuple = [june,category,bat,12,1]
    val mapped: DataStream[Tuple4[String, String, String, Integer]] = data.map(new Splitter())
    mapped.keyBy(0).sum(3).writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1_sum")
    mapped.keyBy(0).min(3).writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1_min")
    mapped.keyBy(0).minBy(3).writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1_minBy")
    mapped.keyBy(0).max(3).writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1_max")
    mapped.keyBy(0).maxBy(3).writeAsText("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg1_maxBy")
    env.execute("aggregation of data")
    /*
    DIFFERENCE BETWEEN (MIN,MAX) AND (MINBY,MAXBY) is that in (min,max) flink only care about the particular field , rest of the tuple
    value may change. whereas in (minby,maxby) flink retains the same tuple values.
     */
  }


  class Splitter extends MapFunction[String, Tuple4[String, String, String, Integer]] {
    def map(value: String): Tuple4[String, String, String, Integer] = { //input > "01-06-2018,june,category,bat,12"
      val words: Array[String] = value.split(",")
      //words = [{01-06-2018},{june},{category},{bat},{12}]
      new Tuple4[String, String, String, Integer](
        words(1),
        words(2),
        words(3),
        java.lang.Integer.parseInt(words(4)))
    }
  }

}