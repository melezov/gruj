import sbt._
import Keys._

object BuildSettings {
  val bsDefault = Defaults.defaultSettings ++ Seq(
    organization  := "Element d.o.o.",
    scalaVersion  := "2.9.1",
    scalacOptions := Seq("-unchecked", "-deprecation", "-Yrepl-sync", "-encoding", "UTF-8", "-optimise")
  )

  val bsCore = bsDefault ++ Seq(
    name          := "Gruj-Core",
    version       := "0.2.0"
  )

  val bsLift = bsDefault ++ Seq(
    name          := "Gruj-Lift",
    version       := "0.1.5"
  )
}

object Dependencies {
  val jetty  = "org.eclipse.jetty" % "jetty-webapp" % "8.1.0.RC4" % "container"

  val liftVersion = "2.4"
  val liftweb = Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile"
  )

  val logback = "ch.qos.logback" % "logback-classic" % "1.0.0" % "compile->default"

  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
}

object GrujBuild extends Build {
  import BuildSettings._
  import Dependencies._

  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

  // Coffeescript plugin
  import coffeescript.Plugin._
  import CoffeeKeys._

  // Less plugin
  import less.Plugin._
  import LessKeys._

  val depsCore = Seq(
    scalatest
  )

  val depsLift = liftweb ++ Seq(
    jetty,
    logback
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
      port in container.Configuration := 8071,
      scanDirectories in Compile := Nil
    ) ++ coffeeSettings ++ Seq(
      resourceManaged in (Compile, coffee) <<= (webappResources in Compile)(_.get.head / "static" / "coffee")
    ) ++ lessSettings ++ Seq(
      mini in (Compile, less) := true,
      resourceManaged in (Compile, less) <<= (webappResources in Compile)(_.get.head / "static" / "less")
    )
  ) dependsOn(core)
}
