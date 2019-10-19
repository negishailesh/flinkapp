package flink.process

import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.tuple._


object EducationVertical {
  def main(args:Array[String]):Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val institute_data:DataStream[String] = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/institute_data.csv")
    val location_data :DataStream[String] = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/location_data.csv")
    val new_institue_data = institute_data.map(new Spliter1())
    val new_location_data = location_data.map(new Spliter2())
    val final_join = new_location_data.join(new_institue_data)
      .where()
      .equalTo(true)

  }



  class Spliter1  extends MapFunction[String,Tuple9[Integer, String, String, Integer, Double, Double, Integer, Integer, Double]]{
    def map(value:String):Tuple9[Integer ,String,String,Integer,Double,Double,Integer,Integer,Double] ={
      val words = value.split(",")
      new Tuple9[Integer,String,String,Integer,Double,Double,Integer,Integer,Double](
        java.lang.Integer.parseInt(words(0)),
        words(1),
        words(2),
        java.lang.Integer.parseInt(words(3)),
        java.lang.Long.parseLong(words(4)),
        java.lang.Long.parseLong(words(5)),
        java.lang.Integer.parseInt(words(6)),
        java.lang.Integer.parseInt(words(7)),
        Math.sqrt((java.lang.Integer.parseInt(words(8)) * 4046.86) / (22/7))
      )
    }
  }


  class Spliter2  extends MapFunction[String,Tuple4[Integer,Double, Double, String]]{
    def map(value:String):Tuple4[Integer ,Double,Double,String] ={
      val words = value.split(",")
      new Tuple4[Integer,Double,Double,String](
        java.lang.Integer.parseInt(words(0)),
        java.lang.Long.parseLong(words(1)),
        java.lang.Long.parseLong(words(2)),
        words(3)
      )
    }
  }



  case class Location(lat: Double, lon: Double)

  trait DistanceCalcular {
    def calculateDistanceInKilometer(userLocation: Location, warehouseLocation: Location): Int
  }

  class DistanceCalculatorImpl extends DistanceCalcular {

    private val AVERAGE_RADIUS_OF_EARTH_KM = 6371

    override def calculateDistanceInKilometer(userLocation: Location, warehouseLocation: Location): Int = {
      val latDistance = Math.toRadians(userLocation.lat - warehouseLocation.lat)
      val lngDistance = Math.toRadians(userLocation.lon - warehouseLocation.lon)
      val sinLat = Math.sin(latDistance / 2)
      val sinLng = Math.sin(lngDistance / 2)
      val a = sinLat * sinLat +
        (Math.cos(Math.toRadians(userLocation.lat))
          * Math.cos(Math.toRadians(warehouseLocation.lat))
          * sinLng * sinLng)
      val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      (AVERAGE_RADIUS_OF_EARTH_KM * c).toInt
    }
  }
  //  new DistanceCalculatorImpl().calculateDistanceInKilometer(Location(10,20),Location(40,20))
}
