package org.aop4scala

import java.lang.annotation.Annotation
import org.aspectj.weaver.tools.{PointcutParser, PointcutExpression}

trait Interceptor {
  protected val parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution

  protected def matches(pointcut: PointcutExpression, invocation: Invocation): Boolean = {
    pointcut.matchesMethodExecution(invocation.method).alwaysMatches ||
            invocation.target.getClass.getDeclaredMethods.exists(pointcut.matchesMethodExecution(_).alwaysMatches) ||
            false
  }

  protected def matches(annotationClass: Class[T] forSome {type T <: Annotation}, invocation: Invocation): Boolean = {
    invocation.method.isAnnotationPresent(annotationClass) ||
            invocation.target.getClass.isAnnotationPresent(annotationClass) ||
            false
  }

  def invoke(invocation: Invocation): AnyRef
}
