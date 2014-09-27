import sbt._
import Keys._
import aether.Aether._
import sbtrelease.ReleasePlugin._

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
    organization := "org.hashids",
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
    )
  )

  import Deps._
  lazy val root = Project("hashids-scala", file("."))
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= Seq(
      specs2
    ))
}
