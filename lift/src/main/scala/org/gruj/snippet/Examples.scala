package org.gruj.snippet

import scala.xml._

import net.liftweb.http.SessionVar
import net.liftweb.http.LiftRules

import net.liftweb.sitemap._
import org.gruj.lib.ExampleTab

import net.liftweb.util.Helpers._
import net.liftweb.util.BindPlus._

object LastExampleTab extends SessionVar[ExampleTab](ExampleTab.BootstrappingSBT)

object Examples {
  def links(n: NodeSeq): NodeSeq =
    ExampleTab.tabs.flatMap(t =>
      n.bind("et",
        "name" -> t.title,
        AttrBindParam("href", t.path, "href")
      )
    )

  def tabs(n: NodeSeq): NodeSeq =
    ExampleTab.tabs.flatMap(t =>
      <div class="hidden">
        <lift:embed what={t.path}/>
      </div>
    )
}