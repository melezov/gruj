package bootstrap.liftweb

import net.liftweb._
import sitemap._
import http._
import common._
import js.jquery._

class Boot {
  LiftRules.addToPackages("org.gruj")

  LiftRules.setSiteMap(SiteMap(
    Menu.i("About") / "index",
    Menu.i("Examples") / "examples"
  ))

  LiftRules.jsArtifacts = JQuery14Artifacts
  LiftRules.stripComments.default.set(() => false)

  LiftRules.htmlProperties.default.set((r: Req) =>
    new XHtmlInHtml5OutProperties(r.userAgent)
  )

  LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
}
