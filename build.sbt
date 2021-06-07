
name := "quandl4s"

version := "0.3"

organization := "alepro"

scalaVersion := "2.13.4"

scalacOptions ++= Seq(
  "-Xlog-implicits" // Show more detail on why some implicits are not applicable.
)

// github
githubOwner := "alepro"
githubRepository := "quandl4s"

// setup GitHub token in ~/.gitconfig
// [github]
//  token = TOKEN_DATA
githubTokenSource := TokenSource.GitConfig("github.token")

val http4sVersion = "0.21.15"

val http4sDeps = Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.3.1"
libraryDependencies ++= http4sDeps


libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % Test

