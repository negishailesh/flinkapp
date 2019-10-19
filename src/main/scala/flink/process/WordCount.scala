package flink.process


import org.apache.flink.api.java.utils._
import org.apache.flink.streaming.api.scala._


object WordCount {

  def main(args:Array[String]): Unit ={
    //checking input parameter
    val params = ParameterTool.fromArgs(args)

    //setup execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //make parameter available in web interface
    env.getConfig.setGlobalJobParameters(params)

    //get input data from environment
//    val text =
//    // read the text file from given input path
//      if (params.has("input")) {
//        env.readTextFile(params.get("input"))
//      } else {
//        println("Executing WordCount example with default inputs data set.")
//        println("Use --input to specify file input.")
//        // get default test text data
//        env.fromElements("file")
//      }

    val text: DataStream[String] = env.readTextFile("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/README.txt")

    /*
    readField method take any file format. it has a argument called PATH which take EMUN value as mentioned below:
        > FileProcessingMode.PROCESS_CONTINUOSLY  - continuously read the file. doesn't create any check point
        > FileProcessingMode.PROCESS_ONCE - take the checkpoint of line read.

    PATH_FILTER_ARGUMENT - suppose in path argument we have provided the directory path so then in pathFilter parameter we can exclude the file extension which we don't want to process.
     */

    println("*********************this console log************************")
    val counts: DataStream[(String, Int)] = text
      // split up the lines in pairs (2-tuples) containing: (word,1)
      .flatMap(_.split("\\W+"))
      .filter(_.nonEmpty)
      .map((_, 1))
      // group by the tuple field "0" and sum up tuple field "1"
      .keyBy(0)
      .sum(1)

    counts.writeAsCsv("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/result.csv")

    /*
    DATA SINK
    > write to sockets.
     */

    env.execute("Streaming WordCount")


    /* DATASET API's
    val myStream :DataStream[(String , Int)]

    readTextfile(path) -  returns dataset/datastream of string

    readcsvfile(path) - returns dataset/datastream of tuples

    readFileOfPrimitives(path , class) - read each line of file in the form of class mentioned in the argument class.

    readHadoopFile(fileInput, key , value,  path) - reads HDFs

    readSequenceFile(key, value, path) - read sequence file



    */




  }

}
