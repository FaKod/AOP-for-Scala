package org.aop4scala


trait LoggingInterceptor extends Interceptor {
  val loggingPointcut = parser.parsePointcutExpression("execution(* *.foo(..))")

  abstract override def invoke(invocation: Invocation): AnyRef =
    if (matches(loggingPointcut, invocation)) {
      println("=====> Enter: " + invocation.method.getName + " @ " + invocation.target.getClass.getName)
      val result = super.invoke(invocation)
      println("=====> Exit: " + invocation.method.getName + " @ " + invocation.target.getClass.getName)
      result
    } else super.invoke(invocation)
}

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
