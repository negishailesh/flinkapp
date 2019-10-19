package flink.process

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import java.net.ServerSocket
import java.util.Random
import java.util.Date
import java.sql.Timestamp

import org.apache.flink.api.java.utils._

object DataServer2 {
  def main(args:Array[String]):Unit={
    val listener: ServerSocket = new ServerSocket(9090)
    try {
      val socket: Socket = listener.accept()
      println("Got new connection: " + socket.toString)
      try {
        val out: PrintWriter = new PrintWriter(socket.getOutputStream, true)
        val rand: Random = new Random()
        val d: Date = new Date()
        while (true) {
          val i: Int = rand.nextInt(100)
          val s: String = "" + System.currentTimeMillis() + "," + i
          println(s)
          /* <timestamp>,<random-number> */

          out.println(s)
          Thread.sleep(50)
        }
      } finally socket.close()
    } catch {
      case e: Exception => e.printStackTrace()

    } finally listener.close()
  }
}
