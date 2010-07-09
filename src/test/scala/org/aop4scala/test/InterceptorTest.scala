package org.aop4scala.test

import org.specs.SpecificationWithJUnit
import org.aop4scala.{ManagedComponentFactory, ManagedComponentProxy}

/**
 * User: FaKod
 * Date: 09.07.2010
 * Time: 08:43:08
 */

class InterceptorTest extends SpecificationWithJUnit {
  "Interceptor" should {

    "enable logging intercept" in {
      var foo = ManagedComponentFactory.createComponent[Foo](
        new ManagedComponentProxy(new FooImpl)
                with LoggingInterceptor
                with TransactionInterceptor
        )

      foo.foo("foo")
      foo.bar("bar")
    }

  }

}