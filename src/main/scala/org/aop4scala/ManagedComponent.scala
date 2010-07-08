package org.aop4scala

import java.lang.reflect.{Method, InvocationHandler, Proxy}

object ManagedComponentFactory {
  def createComponent[T](intf: Class[T] forSome {type T}, proxy: ManagedComponentProxy): T =
    Proxy.newProxyInstance(
      proxy.target.getClass.getClassLoader,
      Array(intf),
      proxy).asInstanceOf[T]
}

class ManagedComponentProxy(val target: AnyRef) extends InvocationHandler {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = invoke(Invocation(m, args, target))

  def invoke(invocation: Invocation): AnyRef = invocation.invoke
}