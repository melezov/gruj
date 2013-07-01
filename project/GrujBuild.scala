import sbt._
import Keys._

object BuildSettings {
  val bsDefault = Defaults.defaultSettings ++ Seq(
    organization  := "org.gruj"
  , scalaVersion  := "2.10.2"
  , scalacOptions := Seq("-unchecked", "-deprecation", "-Yrepl-sync", "-encoding", "UTF-8", "-optimise")
  )

  val bsCore = bsDefault ++ Seq(
    name          := "Gruj-Core"
  , version       := "0.2.0"
  )

  val bsLift = bsDefault ++ Seq(
    name          := "Gruj-Lift"
  , version       := "0.1.5"
  )
}

object Dependencies {
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % "8.1.11.v20130520" % "container"
  val orbit = "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container" artifacts Artifact("javax.servlet", "jar", "jar")

  val liftWebKit = "net.liftweb" %% "lift-webkit" % "2.5.1"

  val logback = "ch.qos.logback" % "logback-classic" % "1.0.13"

  val scalaTest = "org.scalatest" %% "scalatest" % "2.0.M5b" % "test"
}

object GrujBuild extends Build {
  import BuildSettings._
  import Dependencies._

  // Web plugin
  import com.earldouglas.xsbtwebplugin.WebPlugin._
  import com.earldouglas.xsbtwebplugin.PluginKeys._

  // Coffeescript plugin
  import coffeescript.Plugin._
  import CoffeeKeys._

  // Less plugin
  import less.Plugin._
  import LessKeys._

  val depsCore = Seq(
    scalaTest
  )

  val depsLift = Seq(
    jetty
  , orbit
  , liftWebKit
  , logback
  )

  lazy val core = Project(
    "Core",
    file("core"),
    settings = bsCore ++ Seq(
      libraryDependencies := depsCore
    )
  )

  lazy val lift = Project(
    "Lift",
    file("lift"),
    settings = bsLift ++ Seq(
      libraryDependencies := depsLift
    ) ++ webSettings  ++ Seq(
      artifactName in packageWar :=
        ((_: ScalaVersion, _: ModuleID, _: Artifact) => "gruj.war")
    , port in container.Configuration := 8071
    , scanDirectories in Compile := Nil
    ) ++ coffeeSettings ++ Seq(
      resourceManaged in (Compile, coffee) <<= (webappResources in Compile)(_.get.head / "static" / "coffee")
    ) ++ lessSettings ++ Seq(
      mini in (Compile, less) := true
    , resourceManaged in (Compile, less) <<= (webappResources in Compile)(_.get.head / "static" / "less")
    )
  ) dependsOn(core)
}
