package org.gruj.lib

import net.liftweb.sitemap._

object ExampleTab {
  val BootstrappingSBT =
    ExampleTab("Bootstrapping SBT", "bootstrapping-sbt")

  val ExecutingJars =
    ExampleTab("Executing Jars", "executing-jars")

  val OtherUses =
    ExampleTab("Other Uses", "other-uses")

  val tabs = List(
    BootstrappingSBT,
    ExecutingJars,
    OtherUses
  )
}

case class ExampleTab private(title: String, tab: String) {
 val menu = Menu.i(title) / "examples" / tab
 val path = menu.path.map(_.pathItem).foldLeft(""){_+"/"+_}
}