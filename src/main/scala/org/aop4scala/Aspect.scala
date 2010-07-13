package org.aop4scala

import java.lang.reflect.{Proxy, InvocationHandler, Method}

/**
 * User: FaKod
 * Date: 13.07.2010
 * Time: 15:49:37
 */

class Aspect(val pointcutExpression: String) extends Interceptor {
  def create[I: ClassManifest](target: AnyRef): I = {

    val interface = implicitly[ClassManifest[I]]

    val proxy = new TargetProxy(this, target)

    Proxy.newProxyInstance(
      target.getClass.getClassLoader,
      Array(interface.erasure),
      proxy).asInstanceOf[I]
  }

  def invoke(invocation: Invocation): AnyRef = invocation.invoke

  val pointcut = parser.parsePointcutExpression(pointcutExpression)
}


/**
 *
 */
class TargetProxy(val aspect: Aspect, val target: AnyRef) extends InvocationHandler {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = aspect.invoke(Invocation(m, args, target))
}


