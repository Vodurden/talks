organization := "com.reagroup.resi"

name := "intro-to-fp-and-cats"
version := "1.0"
scalaVersion := "2.12.1"
scalaVersion in ThisBuild := "2.12.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.8.1"
)

scalacOptions ++= Seq("-language:higherKinds", "-deprecation", "-unchecked", "-feature", "-Ywarn-unused", "-Ywarn-unused-import")

// Kind projection
resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
