import sbt._
import Keys._
import aether.Aether._
import sbtrelease.ReleasePlugin._
import xerial.sbt.Sonatype._
import SonatypeKeys._

object ShellPrompt {
  def currentBranch = Process(List("git", "rev-parse", "--abbrev-ref", "HEAD"))
    .lines_!.headOption.getOrElse("-")

  val buildShellPrompt = (state: State) => {
    val extracted = Project.extract(state)
    val currentVersion = (version in ThisBuild get extracted.structure.data).getOrElse("-")
    val currentProject = extracted.currentProject.id
    s"[$currentProject:$currentVersion][git:$currentBranch]\n> "
  }
}

object Deps {
  val specs2 = "org.specs2" %% "specs2" % "2.3.12" % "test"
}

object ScalaHashids extends Build {

  lazy val buildSettings = Seq(
    organization := "org.github.ancane",
    profileName  := "org.github.ancane",
    version      := "1.0",
    description  := "Hashids scala port",
    scalaVersion := "2.10.4",
    shellPrompt  := ShellPrompt.buildShellPrompt,
    crossPaths   := false,
    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-unchecked",
      "-deprecation",
      "-feature",
      "-Xlog-reflective-calls"
    ),
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
        <url>github.com/ancane/hashids.scala</url>
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

  import Deps._
  lazy val root = Project("hashids-scala", file("."))
    .settings(buildSettings: _*)
    .settings(sonatypeSettings: _*)
    .settings(libraryDependencies ++= Seq(
      specs2
    ))
}
