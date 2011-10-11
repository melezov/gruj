import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "Element d.o.o."
  val buildScalaVersion = "2.9.1"
  val buildScalacOptions = Seq("-deprecation", "-Yrepl-sync")

  val bsCore = Defaults.defaultSettings ++ Seq(
    organization  := buildOrganization,
    name          := "Gruj - Core",
    version       := "0.1.4",
    scalaVersion  := buildScalaVersion,
    scalacOptions := buildScalacOptions
  )

  val bsLift = Defaults.defaultSettings ++ Seq(
    organization  := buildOrganization,
    name          := "Gruj - Lift",
    version       := "0.1.2",
    scalaVersion  := buildScalaVersion,
    scalacOptions := buildScalacOptions
  )
}

object Resolvers {
  val resCore = Seq()
  val resLift = Seq()
}

object Dependencies {
  val jetty  = "org.eclipse.jetty" % "jetty-webapp" % "8.0.2.v20111006" % "container"

  val liftVersion = "2.4-M4"
  val liftweb = Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile"
  )

  val logback = "ch.qos.logback" % "logback-classic" % "0.9.30" % "compile->default"

  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
}

object TemplaterBuild extends Build {
  import BuildSettings._
  import Resolvers._
  import Dependencies._

  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

  // Coffeescript plugin
  import coffeescript.CoffeeScript._

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
      resolvers := resCore,
      libraryDependencies := depsCore
    )
  )

  lazy val lift = Project(
    "Lift",
    file("lift"),
    settings = bsLift ++ Seq(
      resolvers := resLift,
      libraryDependencies := depsLift
    ) ++ webSettings  ++ Seq(
      port in config("container") := 8071,
      scanDirectories in Compile := Nil
    ) ++ coffeeSettings ++ Seq(
      targetDirectory in Coffee <<= (webappResources in Compile)(_.get.head / "static" / "coffee")
    ) ++ lessSettings ++ Seq(
      mini in (Compile, less) := true,
      resourceManaged in (Compile, less) <<= (webappResources in Compile)(_.get.head / "static" / "less")
    )
  ) dependsOn(core)
}