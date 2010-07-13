package org.aop4scala

import java.lang.annotation.Annotation
import org.aspectj.weaver.tools.{PointcutParser, PointcutExpression}

/**
 * base trait for interceptors
 * defines val for pointcut and invoke method whch can be overwritten by
 * interception traits
 * @author Christopher Schmidt
 */
trait Interceptor {
  protected val parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution

  protected def matches(pointcut: PointcutExpression, invocation: Invocation): Boolean = {
    pointcut.matchesMethodExecution(invocation.method).alwaysMatches ||
            //invocation.target.getClass.getDeclaredMethods.exists(pointcut.matchesMethodExecution(_).alwaysMatches) ||
            false
  }

  protected def matches[T <: Annotation](annotationClass: Class[T], invocation: Invocation): Boolean = {

    invocation.method.isAnnotationPresent(annotationClass) ||
            invocation.target.getClass.isAnnotationPresent(annotationClass) ||
            false
  }

  def invoke(invocation: Invocation): AnyRef

  val pointcut: PointcutExpression
}

/**
 *
 */
abstract trait InterceptorInvoker extends Interceptor {

  def before: AnyRef = null
  def after(result: AnyRef): AnyRef = result
  def around(invoke: => AnyRef): AnyRef = invoke

  abstract override def invoke(invocation: Invocation): AnyRef =
    if (matches(pointcut, invocation)) {
      before
      val result = around(super.invoke(invocation))
      after(result)
    } else
      super.invoke(invocation)
}

/**
 *
 */
trait BeforeInterceptor extends InterceptorInvoker {
  def before: AnyRef
}

/**
 *
 */
trait AfterInterceptor extends InterceptorInvoker {
  def after(result: AnyRef): AnyRef
}

/**
 *
 */
trait AroundInterceptor extends InterceptorInvoker {
  def around(invoke: => AnyRef): AnyRef
}
