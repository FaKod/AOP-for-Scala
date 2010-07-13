package org.aop4scala

import java.lang.reflect.Method

case class Invocation(val method: Method, val args: Array[AnyRef], val target: AnyRef) {
  def invoke: AnyRef = method.invoke(target, args: _*)

  override def toString: String = "Invocation [method: " + method.getName + ", args: " + args + ", target: " + target + "]"

  override def hashCode(): Int = method.hashCode
  //override def equals (that: Any): Boolean = {...}
}