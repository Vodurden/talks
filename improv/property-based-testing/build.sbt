organization := "com.reagroup.resi"

name := "property-based-testing-talk"
version := "1.0"
scalaVersion := "2.12.1"
scalaVersion in ThisBuild := "2.12.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.8.1",

  "org.specs2" %% "specs2-core" % "3.8.6" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.8.6" % "test",
  "org.specs2" %% "specs2-matcher-extra" % "3.8.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.fortysevendeg" %% "scalacheck-datetime" % "0.2.0" % "test"
)

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Ywarn-unused", "-Ywarn-unused-import")

// Kind projection
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
