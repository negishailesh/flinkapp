package flink.process

import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object ReduceFunction {

  def main(args : Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg")
    //tuple = [june,category,bat,12,1]
    val mapped: DataStream[Tuple5[String,String,String,Integer,Integer]] = data.map(new Splitter())
    //groupby by month
    val reduced: DataStream[Tuple5[String,String,String,Integer,Integer]] = mapped.keyBy(0).reduce(new Reduce1())
    //month,avg.profit
    val profitPerMonth:DataStream[Tuple2[String,Double]] = reduced
            .map(new ProfitPerMonth())
    profitPerMonth.print()
    env.execute("avg profit per month")

    /*
    month,product,category,profit,count = [june,category4,perfume,10,1]
    june{[category4,bat,12,1],vcategory4,perfume,10,1} //rolling reduce
    reduced = {[categroy4,perfume,22,2],.....}
     */
  }

  class ProfitPerMonth extends MapFunction[Tuple5[String,String,String,Integer,Integer],Tuple2[String,Double]]{
    def map(input:Tuple5[String,String,String,Integer,Integer]):Tuple2[String,Double]={
      new Tuple2[String,Double](input.f0 ,new java.lang.Double((input.f3 * 1.0) / input.f4))
    }
  }

  
  class Splitter extends MapFunction[String , Tuple5[String, String, String, Integer, Integer]]{
    def map(value:String):Tuple5[String,String,String,Integer,Integer] = { //input > "01-06-2018,june,category,bat,12"
      val words:Array[String] = value.split(",")
      //words = [{01-06-2018},{june},{category},{bat},{12}]
      new Tuple5[String,String,String,Integer,Integer](
        words(1),
        words(2),
        words(3),
        java.lang.Integer.parseInt(words(4)),
        1)
    }
  }

  class Reduce1 extends ReduceFunction[Tuple5[String,String,String,Integer,Integer]]{
    def reduce(current:Tuple5[String,String,String,Integer,Integer],
               pre_result:Tuple5[String,String,String,Integer,Integer]):Tuple5[String,String,String,Integer,Integer] = {
      //in this reduce method input and output will be of same type
      //also it take parameter as current stream data and previously aggregated streaming data
      // it is rolling up the count and the quantity of purchase
      new Tuple5[String,String,String,Integer,Integer](
        current.f0,
        current.f1,
        current.f2,
        current.f3 + pre_result.f3,
        current.f4 + pre_result.f4
      )
    }
  }


}
