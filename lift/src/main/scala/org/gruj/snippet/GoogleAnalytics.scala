package org.gruj.snippet

import scala.xml.NodeSeq

import net.liftweb._
import http._
import js.JsCmds._
import util._
import Helpers._
import BindPlus._

object GoogleAnalytics {
  val Disabled = <!-- Google Analytics is disabled -->
  val Undefined = <!-- Google Analytics page tracking code is not defined -->

  def render(scripts: NodeSeq) = Props.productionMode match {
    case true => injectTrackingCode(scripts) openOr Undefined
    case _ => Disabled
  }

/** If lift is being run in production mode, try to read the
  * ga.ptc (Google Analytics Page Tracking Code) property.
  * Then create a javascript variable with the said code,
  * and name it with the value which was hard-coded in the
  * google-analytics.html snippet */

  def injectTrackingCode(scripts: NodeSeq) =
   for(name <- S.attr("name"); value <- Props.get("ga.ptc"))
      yield scripts.bind("ga", "ptc" -> Script(JsCrVar(name, value)))
}
