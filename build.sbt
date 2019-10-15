name := "guice-akka"

homepage := Some(new URL("https://github.com/margussipria/guice-akka"))
licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

organization := "eu.sipria.inject.akka"

version := "1.2.0"

scalaVersion := "2.11.12"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

scalacOptions := Seq(
  "-deprecation",
  "-language:postfixOps"
)

val AkkaVersion = "2.5.25"
val GuiceVersion = "4.2.2"
val ScalaTestVersion = "3.0.8"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "com.google.inject" % "guice" % GuiceVersion,
  "com.google.inject.extensions" % "guice-assistedinject" % GuiceVersion,
  "com.google.inject.extensions" % "guice-multibindings" % GuiceVersion,

  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,

  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
)

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

developers := List(
  Developer("margussipria", "Margus Sipria", "margus+guice-akka@sipria.fi", url("https://github.com/margussipria"))
)

scmInfo := Some(ScmInfo(
  url("https://github.com/margussipria/guice-akka"),
  "scm:git:https://github.com/margussipria/guice-akka.git",
  Some("scm:git:git@github.com:margussipria/guice-akka.git")
))
