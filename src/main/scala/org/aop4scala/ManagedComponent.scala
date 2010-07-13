package org.aop4scala

import java.lang.reflect.{Method, InvocationHandler, Proxy}

//object ManagedComponentFactory {
//
//  def createComponent[T: ClassManifest](proxy: ManagedComponentProxy): T = {
//    val intf = implicitly[ClassManifest[T]]
//    Proxy.newProxyInstance(
//      proxy.target.getClass.getClassLoader,
//      Array(intf.erasure),
//      proxy).asInstanceOf[T]
//  }
//}
//
//class ManagedComponentProxy(val target: AnyRef) extends InvocationHandler with Interceptor {
//  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = invoke(Invocation(m, args, target))
//
//  def invoke(invocation: Invocation): AnyRef = invocation.invoke
//}