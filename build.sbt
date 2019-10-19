import sbt.Keys.libraryDependencies

name := "flinkApp"

version := "0.1"

//scalaVersion := "2.12.6"
scalaVersion := "2.11.8"
val flinkVersion = "1.8.1"

ThisBuild / resolvers ++= Seq(
  "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
  "HortonWorks" at "http://repo.hortonworks.com/content/repositories/releases/",
  "HortonWorks Public" at "http://repo.hortonworks.com/content/repositories/public/",
  Resolver.mavenLocal
)
resolvers += "confluent" at "http://packages.confluent.io/maven"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
  "org.scala-js" %% "scalajs-test-interface" % "0.6.14",
  "org.apache.flink" %% "flink-scala" % "1.8.1",
  "org.apache.flink" %% "flink-streaming-scala" % "1.8.1",
  "org.scalatest" %% "scalatest" % "3.0.1", //version changed as these the only versions supported by 2.12
  "com.novocode" % "junit-interface" % "0.11",
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "com.typesafe" % "config" % "1.3.3",
  "org.apache.flink" %% "flink-connector-kafka-0.11" % flinkVersion,
  "org.apache.flink" %% "flink-connector-filesystem" % flinkVersion,
  "org.json4s" %% "json4s-core" % "3.6.2",
  "org.json4s" %% "json4s-native" % "3.6.2",
  "org.json4s" %% "json4s-jackson" % "3.6.2",
  "com.softwaremill.sttp" %% "core" % "1.5.4",
  "com.github.wnameless" % "json-flattener" % "0.6.0",
  "org.apache.flink" % "flink-avro-confluent-registry" % flinkVersion exclude ("com.fasterxml.jackson.core", "jackson-databind"),
  "io.confluent" % "kafka-avro-serializer" % "4.1.1" exclude ("com.fasterxml.jackson.core", "jackson-databind"),
  "org.apache.flink" % "flink-json" % "1.7.1",

)

// https://mvnrepository.com/artifact/org.apache.flink/flink-core
libraryDependencies += "org.apache.flink" % "flink-core" % "1.8.1"


mainClass in (Compile, run) := Some("flink.process.ReduceFunction")

assemblyMergeStrategy in assembly := {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.first
    }
  case _ => MergeStrategy.first}