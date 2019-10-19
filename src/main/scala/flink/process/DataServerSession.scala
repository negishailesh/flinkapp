package flink.process

import java.io.IOException
import java.util.Random
import java.io.PrintWriter
import java.net.Socket
import java.net.ServerSocket
import java.util.Date
import java.sql.Timestamp

import scala.collection.JavaConversions._


object DataServerSession {
  def main(args:Array[String]):Unit = {
    val listener : ServerSocket = new ServerSocket(9090)
    try{
      val socket:Socket = listener.accept()
      println("got new connection : " + socket.toString)
      try{
        val out:PrintWriter = new PrintWriter(socket.getOutputStream,true)
        val rand:Random = new Random()
        val d:Date = new Date()
        var count:Int = 0
        while(true){
          {count+=1; count-1}
          val i:Int = rand.nextInt(100)
          val s:String = "" + System.currentTimeMillis() + "," + i
          println(s)
          /*<timestamp>,<random-number> */

          out.println(s)
          if(count>=10){
            println("********************")
            Thread.sleep(1000)
            count = 0
          }else Thread.sleep(50)
        }
      }finally socket.close()
    }catch{
      case e:Exception => e.printStackTrace()
    }finally listener.close()
  }

}
