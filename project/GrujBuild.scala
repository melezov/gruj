import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "Element d.o.o."
  val buildScalaVersion = "2.9.1"
  val buildScalacOptions = Seq("-deprecation")

  val bsCore = Defaults.defaultSettings ++ Seq(
    organization  := buildOrganization,
    name          := "Gruj - Core",
    version       := "0.2.1",
    scalaVersion  := buildScalaVersion,
    scalacOptions := buildScalacOptions
  )

  val bsLift = Defaults.defaultSettings ++ Seq(
    organization  := buildOrganization,
    name          := "Gruj - Lift",
    version       := "0.1.0",
    scalaVersion  := buildScalaVersion,
    scalacOptions := buildScalacOptions
  )
}

object Resolvers {
  val resCore = Seq()
  val resLift = Seq()
}

object Dependencies {
  val jetty  = "org.eclipse.jetty" % "jetty-webapp" % "7.5.1.v20110908" % "jetty" // "container"

  val liftVersion = "2.4-M4"
  val liftweb = Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile"
  )

  val commons = Seq(
    "commons-io" % "commons-io" % "2.0.1"
  )

  val logback = "ch.qos.logback" % "logback-classic" % "0.9.30" % "compile->default"

  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
}

object TemplaterBuild extends Build {
  import BuildSettings._
  import Resolvers._
  import Dependencies._
  import com.github.siasia.WebPlugin._
  import coffeescript.CoffeeScript._
  import less.Plugin._

  val depsCore = commons ++ Seq(
    scalatest
  )

  val depsLift = commons ++ liftweb ++ Seq(
    jetty,
    logback,
    scalatest
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
    ) ++ webSettings ++ Seq(
      jettyPort     := 8071,
      jettyScanDirs := Nil,
      temporaryWarPath <<= (sourceDirectory in Compile)(_ / "webapp")
    ) ++ coffeeSettings ++ Seq(
      targetDirectory in Coffee <<= webappResources(_.get.head / "static" / "coffee")
    ) ++ lessSettings ++ Seq(
      (LessKeys.mini in (Compile, LessKeys.less)) := true,
      (resourceManaged in (Compile, LessKeys.less)) <<= webappResources(_.get.head / "static" / "less")
    )
  ) dependsOn(core)
}