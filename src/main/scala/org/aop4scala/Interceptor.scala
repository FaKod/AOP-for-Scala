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
  protected def doInvoke(f: => AnyRef): AnyRef

  abstract override def invoke(invocation: Invocation): AnyRef =
    if (matches(pointcut, invocation)) {
      doInvoke(super.invoke(invocation))
    } else
      super.invoke(invocation)
}

/**
 *
 */
trait BeforeInterceptor extends InterceptorInvoker {
  def before: AnyRef

  protected def doInvoke(f: => AnyRef) = {
    before
    f
  }

}

/**
 *
 */
trait AfterInterceptor extends InterceptorInvoker {
  def after(result: AnyRef): AnyRef

  protected def doInvoke(f: => AnyRef) = {
    after(f)
  }
}

/**
 *
 */
trait AroundInterceptor extends InterceptorInvoker {
  def around(invoke: => AnyRef): AnyRef

  protected def doInvoke(f: => AnyRef) = {
    around(f)
  }
}
