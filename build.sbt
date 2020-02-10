ThisBuild / organization := "com.github.ancane"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "1.4"

val Deps = new {
  val specs2 = Seq("org.specs2" %% "specs2-core" % "4.8.3" % "test",
    "org.specs2" %% "specs2-junit" % "4.8.3" % "test",
    "org.specs2" %% "specs2-mock" % "4.8.3" % "test")
}

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xlog-reflective-calls",
  "-Xlint",
  "-Xfatal-warnings"
)

lazy val root = (project in file("."))
  .settings(
    name := "hashids-scala",
    scalaVersion := "2.13.1",
    crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1"),
    description := "Hashids scala port",
    publishTo := sonatypePublishToBundle.value,
    libraryDependencies ++= Deps.specs2
  )
