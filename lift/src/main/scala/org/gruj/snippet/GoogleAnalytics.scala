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

  def injectTrackingCode(scripts: NodeSeq) =
   for(name <- S.attr("name"); value <- Props.get("ga.ptc"))
      yield scripts.bind("ga", "ptc" -> Script(JsCrVar(name, value)))
}
