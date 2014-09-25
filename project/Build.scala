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

object Hashids extends Build {

  lazy val buildSettings = Seq(
    organization := "com.github.ancane",
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

  lazy val root = Project("hashids-scala", file("."))
    .settings(buildSettings: _*)
}
