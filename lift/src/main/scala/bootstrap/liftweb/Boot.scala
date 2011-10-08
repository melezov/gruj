package bootstrap.liftweb

import net.liftweb._
import sitemap._
import http._
import common._
import js.jquery._

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import js.jquery._

import org.gruj.snippet._

class Boot {
  LiftRules.addToPackages("org.gruj")

  LiftRules.setSiteMap(SiteMap(
    Menu.i("About") / "index",
    Menu.i("Examples") / "examples" submenus(
      ExampleTabs.list.map(_.menu >> Hidden)
    )
  ))

  LiftRules.dispatch.append(ExampleTabs)

  LiftRules.htmlProperties.default.set((r: Req) =>
    new XHtmlInHtml5OutProperties(r.userAgent)
  )

  LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
}
