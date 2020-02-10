ThisBuild / organization := "com.github.ancane"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "1.3.1"

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

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := {
    <url>https://github.com/ancane/hashids.scala</url>
      <licenses>
        <license>
          <name>MIT</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <connection>scm:git:github.com:ancane/hashids.scala</connection>
        <developerConnection>scm:git:git@github.com:ancane/hashids.scala</developerConnection>
        <url>https://github.com/ancane/hashids.scala</url>
      </scm>
      <developers>
        <developer>
          <id>ancane</id>
          <email>igor.shimko@gmail.com</email>
          <name>Igor Shymko</name>
          <url>https://github.com/ancane</url>
        </developer>
      </developers>
  }
)

lazy val root = (project in file("."))
  .settings(
    name := "hashids-scala",
    scalaVersion := "2.13.1",
    crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1"),
    sonatypeProfileName := "ancane",
    description := "Hashids scala port"
  )
  .settings(publishSettings: _*)
  .settings(libraryDependencies ++= Deps.specs2)
