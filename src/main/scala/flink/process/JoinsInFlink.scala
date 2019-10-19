package flink.process


import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.DataSet
import org.apache.flink.api.java._
import org.apache.flink.api.java.io.CsvReader
import org.apache.flink.api.java.operators.DataSource
import org.apache.flink.api.java.tuple._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.operators.base.JoinOperatorBase.JoinHint







class JoinsInFlink {
  val env = ExecutionEnvironment.getExecutionEnvironment
  val person_path = "/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/person.csv"
  val location_path = "/home/shaileshnegi/Documents/scala/flinkApp/src/main/scala/flink/data/location.csv"
  val person_dataset: scala.DataSet[String] = env.readTextFile(person_path)
  val person: scala.DataSet[(String, String)] = person_dataset.map(x=>(x.split(",")(0),x.split(",")(1)))
  val location_dataset = env.readTextFile(location_path)
  val location = location_dataset.map(x=>(x.split(",")(0),x.split(",")(1)))


//  def main(args:Array[String]): Unit ={
  def innerJoin()={
    val person_join_location = person.join(location).where(0).equalTo(0)
    //JOIN WITH DATASET SIZE HINT//
    val join_with_tiny = person.joinWithTiny(location).where(0).equalTo(0)
    val join_with_huge = person.joinWithHuge(location).where(0).equalTo(0)
    println(join_with_huge.print())
    println(join_with_tiny.print())
    println(person_join_location.print())

  }

  def leftOuterAndRightOuterJoin()={
    val left_outer_join = person.leftOuterJoin(location).where(0).equalTo(0){
      (person,location) => (person._1, if (location == null) -1 else location._1)
    }

    val right_outer_join = person.rightOuterJoin(location).where(0).equalTo(0){
      (person,location) => (person._1, if(location == null) -1 else location._1)
    }
    println(left_outer_join.print())
    println(right_outer_join.print())
  }


  def joinHintInFlink()={
    /*
    OPTIMIZER_CHOSES = equivalent to not providing any join hint. flink optimises itself.
    BROADCAST_HASH_FIRST(for small first dataset) = if first dataset is small we can broadcast this data set in the cluster and then perform join.
    BROADCAST_HASH_SECOND(for small second dataset) = broadcast the second dataset and then perform join in that.
    REPARTITION_HASH_FIRST = motive is to create a hash table from the input dataset.beacuse join is basically a look up opeartion and hash table is the best data structure in looking up operation.it builds the hash table from the first input.THIS stratergy is good when both the table are large and first input is smaller then the second input.
    REPARTITION_HASH_SECOND = exact opposite to REPARTITION_HASH_FIRST
    REPARTITION_SORT_MERGE = first flink will do the partition of each dataset.then sorts each dataset.we only pass this hint if we know that one of the dataset is already sorted.
     */
  }
  // following line is must in every class and object.
//  env.execute("join in flink")
}
