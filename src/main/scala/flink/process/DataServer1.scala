//package flink.process
//
//import java.io.BufferedReader
//import java.io.FileReader
//import java.io.IOException
//import java.io.PrintWriter
//import java.net.Socket
//import java.net.ServerSocket
//
//import org.apache.flink.api.java.utils._
//
//
//object DataServer1 {
//  def main(args:Array[String]):Unit={
//    val listener:ServerSocket = new ServerSocket(9090)
//    try{
//      val socket:Socket = listener.accept()
//      println("Got new connection: " +  socket.toString)
//      val br:BufferedReader = new BufferedReader(
//        new FileReader("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/avg"))
//      try{
//        val out:PrintWriter = new PrintWriter(socket.getOutputStream , true)
//        var line:String = null
//        while((line = br.readLine())  != null){
//          out.println(line)
//          Thread.sleep(50)
//        }
//      }finally  socket.close()
//    }catch{
//      case e:Exception => e.printStackTrace()
//    }finally  listener.close()
//  }
//}
