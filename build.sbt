import sbt.Keys.libraryDependencies

name := "bitcoin-trending"
version := "1.0"
scalaVersion := "2.11.0"
crossScalaVersions := Seq("2.10.2", "2.10.3", "2.11.8")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.11.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",

//  "org.scala-lang" % "scala-parser-combinators" % "2.11.0-M4",


  "org.quartz-scheduler" % "quartz" % "2.3.0",
  "org.quartz-scheduler" % "quartz-jobs" % "2.3.0",
  "mysql" % "mysql-connector-java" % "5.1.39",


  "org.apache.httpcomponents" % "httpclient" % "4.5",
  "log4j" % "log4j" % "1.2.14",

  "org.skife.com.typesafe.config" % "typesafe-config" % "0.3.0",

  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.5",
//  "com.fasterxml.jackson.module" % "jackson-module-scala" % "2.9.5",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.1",


  "org.apache.commons" % "commons-io" % "1.3.2",

  "com.google.code.gson" % "gson" % "2.3.1"

)