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

  LiftRules.jsArtifacts = JQuery14Artifacts
  LiftRules.stripComments.default.set(() => false)

  LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

/*
  // Use this block to send HTML5
  LiftRules.htmlProperties.default.set((r: Req) =>
    XHtmlInHtml5OutProperties(r.userAgent)
  )
*/

/*
  ##############################################
  The following does not work, since whatever we
  setup in setDocType will always result in
  XHTML Transitional.
  ##############################################
*/

  // Use this block to send XHTML 1.1
  LiftRules.htmlProperties.default.set((r: Req) => {
    new OldHtmlProperties(r.userAgent).
      setDocType(() => Full(DocType.xhtml11))
  })
}
