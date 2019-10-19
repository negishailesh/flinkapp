package flink.process

import org.apache.flink.api.scala._

class FirstProgram {

//  def readFile(): Any = {
//    //TO CHECK DATATYPE OF SCALA
//    println("paytm".getClass.getSimpleName)
//    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
//    val movieDataset = env.readCsvFile[(Int, String, String)]("/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/movies.csv")
//    println(movieDataset)
//    movieDataset
//  }

  def filterOperation(dataSet: DataSet[String]): DataSet[String] = {
    val filterOps: DataSet[String] = dataSet.filter(_.contains("man"))
    println(filterOps.first(1).print())
    filterOps
  }

  def mapOperation(dataSet: DataSet[String]) = {
    val mapOps = dataSet.map(x => x.concat("paytm"))
    println(mapOps.first(1).print())
    mapOps
  }

  def flatMapOperation(dataSet: DataSet[String]): DataSet[String] = {
    //The FlatMap transformation applies a user-defined flat-map function on each element of a DataSet.
    // This variant of a map function can return arbitrary many result elements (including none) for each input element
    val flatMapOps = dataSet.flatMap(_.split(","))
    println(flatMapOps.first(1).print())
    flatMapOps
  }

  def mapPartitionOperation(dataSet: DataSet[String]): Any = {
    //MapPartition transforms a parallel partition in a single function call.
    //The map-partition function gets the partition as Iterable and can produce an arbitrary number of result values.
    //The number of elements in each partition depends on the degree-of-parallelism and previous operations.
    //Some is required because the return value must be a Collection.
    //There is an implicit conversion from Option to a Collection.
    val mapPartitionOps = dataSet.rebalance().mapPartition(in => Some(in.size))
    println(dataSet.count())
    println(mapPartitionOps.print())
    mapPartitionOps
  }


}
