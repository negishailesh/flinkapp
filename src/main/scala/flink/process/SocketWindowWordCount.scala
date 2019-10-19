package flink.process

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.serialization.{SerializationSchema, SimpleStringSchema}



object SocketWindowWordCount {

  def main(args:Array[String]):Unit={

    var hostname: String = "localhost"
    var port: Int = 0;
    try{
      val params = ParameterTool.fromArgs(args)
      println(params)
      //hostname = if(params.has(hostname)) params.get("hostname") else "localhost"
      hostname = "localhost"
      //port = params.getInt("port")
      port = 1234
    }catch{
      case e: Exception => {
        System.err.println("No port specified. Please run 'SocketWindowWordCount " +
          "--hostname <hostname> --port <port>', where hostname (localhost by default) and port " +
          "is the address of the text server")
        System.err.println("To start a simple text server, run 'netcat -l <port>' " +
          "and type the input text into the command line")
        return
      }
    }

    //get execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //get input data by connecting to sockets
    var text: DataStream[String] = env.socketTextStream(hostname , port , '\n')

    //parse the data group it ,window it and aggregate the data
    val windowCounts = text
        .filter(x  => x.startsWith("n"))
        .flatMap{w => w.split("\\s")}
        .map{w => WordWithCount(w,1)}
        .keyBy("word")
      .sum("count")
      .map(tuple => tuple.toString() + "\n")

    windowCounts.writeToSocket("localhost", 9000, new SimpleStringSchema())
    env.execute("Socket Word Program")
  }
  /** Data type for word with count **/
  case class WordWithCount(word: String, count: Long)

}
