package org.aop4scala

/**
 * Created by IntelliJ IDEA.
 * User: christopherschmidt
 * Date: 30.10.2009
 * Time: 14:34:40
 * To change this template use File | Settings | File Templates.
 */

object App extends Application {
  var foo = ManagedComponentFactory.createComponent[Foo](
    classOf[Foo],
    new ManagedComponentProxy(new FooImpl)
            with LoggingInterceptor
            with TransactionInterceptor)

  foo.foo("foo")
  foo.bar("bar")

}