package org.aop4scala.test

import org.aop4scala._

//trait LoggingInterceptorOld extends Interceptor {
//  //val loggingPointcut = parser.parsePointcutExpression("execution(* *.bar(..))")
//
//  abstract override def invoke(invocation: Invocation): AnyRef =
//    if (matches(pointcut, invocation)) {
//      println("=====> Enter: " + invocation.method.getName + " @ " + invocation.target.getClass.getName)
//      val result = super.invoke(invocation)
//      println("=====> Exit: " + invocation.method.getName + " @ " + invocation.target.getClass.getName)
//      result
//    } else super.invoke(invocation)
//}

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
trait LoggingInterceptor extends AroundInterceptor {
  def around(invoke: => AnyRef): AnyRef = {
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
  def before: AnyRef = {
    println("=====> Enter Before Aspect")
    null.asInstanceOf[AnyRef]
  }
}

/**
 *
 */
trait InterceptAfter extends AfterInterceptor {
  def after(result: AnyRef): AnyRef = {
    println("=====> Enter After Aspect with result: " + result)
    null.asInstanceOf[AnyRef]
  }
}
