package org.gruj.snippet

import scala.xml._
import net.liftweb.sitemap._
import net.liftweb.util.Helpers._
import net.liftweb.util.BindPlus._
import net.liftweb.http._
import LiftRules._
import net.liftweb.util.PassThru

case class Tab(
    title: String,
    section: String,
    tabId: String,
    active: SessionVar[Tab]) {

  val menu = Menu.i(title) / section / tabId
  val fullPath = "/"+ section +"/"+ tabId
  val hashPath = "/"+ section +"#"+ tabId

  def isActive = active.is eq this
  def getPath = if (isActive) hashPath else fullPath
}

//  -------------------------------------------

object ExampleTabs extends DispatchPF {
  val Section = "examples"

  private def makeTab(title: String, tabId: String) = {
    Tab(title, Section, tabId, Active)
  }

  val list = List(
    makeTab("Bootstrapping SBT", "bootstrapping-sbt"),
    makeTab("Executing Jars", "executing-jars"),
    makeTab("Other Uses", "other-uses")
  )

//  -------------------------------------------

  private object Active extends SessionVar[Tab](list.head)

//  -------------------------------------------

  private def find(r: Req) =
    list.find(t => r.uri startsWith t.fullPath)

  def isDefinedAt(r: Req) =
    find(r).isDefined

  def apply(r: Req) = () =>
    find(r).map{t =>
      Active.set(t)
      if (r.uri == t.fullPath +"/ping"){
        PlainTextResponse("pong")
      }
      else {
        RedirectResponse(t.hashPath)
      }
    }

//  -------------------------------------------

  private def activeFunc(t: Tab) =
    if (t.isActive) {
      (n: NodeSeq) => Text(n.text +" active")
    }
    else {
      PassThru
    }

  def menu(n: NodeSeq): NodeSeq =
    list.flatMap(t =>
      n.bind("et",
          AttrBindParam("id", t.tabId, "id"),
        FuncAttrBindParam("class", activeFunc(t), "class"),
        AttrBindParam("href", t.getPath, "href"),
        "name" -> t.title
      )
    )

//  -------------------------------------------

  private def activeOrHiddenFunc(t: Tab) =
    if (t.isActive) {
      (n: NodeSeq) => Text(n.text +" active")
    }
    else {
      (n: NodeSeq) => Text(n.text +" hidden")
    }


  def render(n: NodeSeq): NodeSeq =
    list.flatMap(t =>
      n.bind("et",
        AttrBindParam("id", t.tabId +"-tab", "id"),
        FuncAttrBindParam("class", activeOrHiddenFunc(t), "class"),
        AttrBindParam("what", t.fullPath, "what")
      )
    )
}
