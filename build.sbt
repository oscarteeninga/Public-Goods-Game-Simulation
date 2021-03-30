name := "Public-Goods-Game-Simulation"

version := "0.1"

scalaVersion := "2.11.11"

val AkkaVersion = "2.4.20"

libraryDependencies += "org.nd4j" % "nd4j-native" % "1.0.0-beta7" % Test
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-beta7"
libraryDependencies += "org.nd4j" % "nd4j-tensorflow" % "1.0.0-beta7"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-beta7"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-nlp" % "1.0.0-beta7"
libraryDependencies += "org.bytedeco" % "tensorflow-platform" % "1.15.5-1.5.5"