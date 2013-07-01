package bootstrap.liftweb

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

  LiftRules.stripComments.default.set(() => false)

  LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

  // Use this block to send XHTML 1.1
  LiftRules.docType.default.set((r: Req) => Full(DocType.xhtml11))
  LiftRules.htmlProperties.default.set((r: Req) =>
    new OldHtmlProperties(r.userAgent)
  )

/*
  // Use this block to send HTML5
  LiftRules.htmlProperties.default.set((r: Req) =>
    XHtmlInHtml5OutProperties(r.userAgent)
  )
*/
}
