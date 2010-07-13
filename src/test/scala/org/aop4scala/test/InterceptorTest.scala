package org.aop4scala.test

import org.specs.SpecificationWithJUnit
import org.aop4scala.Aspect

/**
 * User: FaKod
 * Date: 09.07.2010
 * Time: 08:43:08
 */
class InterceptorTest extends SpecificationWithJUnit {
  "Interceptor" should {

    "enable Around interception" in {

      val aspect = new Aspect("execution(* *.bar(..))")
              with LoggingInterceptor
              with TransactionInterceptor

      val foo = aspect.create[Foo](new FooImpl)

      foo.foo("foo")
      foo.bar("bar")

    }

    "enable Before interception" in {

      val aspect = new Aspect("execution(* *.bar(..))")
              with InterceptBefore

      val foo = aspect.create[Foo](new FooImpl)

      foo.foo("foo")
      foo.bar("bar")
    }

    "enable After interception" in {
      val aspect = new Aspect("execution(* *.bar(..))")
              with InterceptAfter

      val foo = aspect.create[Foo](new FooImpl)

      foo.foo("foo")
      foo.bar("bar")
    }
  }

}