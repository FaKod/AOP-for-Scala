package org.aop4scala.test

import org.aop4scala._

trait TransactionInterceptor extends Interceptor {
  val matchingDeprecatedAnnotation = classOf[java.lang.Deprecated]

  abstract override def invoke(invocation: Invocation): AnyRef =
    if (matches(matchingDeprecatedAnnotation, invocation)) {
      println("=====> interceptor begin")
      try {
        // val result = super.doStuff
        val result = super.invoke(invocation)
        println("=====> interceptor commit")
        result
      } catch {
        case e: Exception =>
          println("=====> interceptor rollback since we got an exception")
          e
      }
    } else super.invoke(invocation)
}

/**
 *
 */
trait InterceptorAround extends AroundInterceptor {
  override def around(invoke: => AnyRef): AnyRef = {
    println("=====> Enter Around Aspect")
    val result = invoke
    println("=====> Exit Around Aspect")
    result
  }
}

/**
 *
 */
trait InterceptBefore extends BeforeInterceptor {
  override def before: AnyRef = {
    println("=====> Enter Before Aspect")
    null.asInstanceOf[AnyRef]
  }
}

/**
 *
 */
trait InterceptAfter extends AfterInterceptor {
  override def after(result: AnyRef): AnyRef = {
    println("=====> Enter After Aspect with result: " + result)
    null.asInstanceOf[AnyRef]
  }
}
