package org.aop4scala.test

import org.specs.SpecificationWithJUnit
import org.aop4scala.{Invocation, Interceptor}
import java.lang.reflect.{Proxy, Method, InvocationHandler}

/**
 * User: FaKod
 * Date: 09.07.2010
 * Time: 08:43:08
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



class TargetProxy(val aspect: Aspect, val target: AnyRef) extends InvocationHandler {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = aspect.invoke(Invocation(m, args, target))
}



class InterceptorTest extends SpecificationWithJUnit {
  "Interceptor" should {

//    "enable logging intercept" in {
//      var foo = ManagedComponentFactory.createComponent[Foo](
//        new ManagedComponentProxy(new FooImpl)
//                with LoggingInterceptor
//                with TransactionInterceptor
//        )
//
//      foo.foo("foo")
//      foo.bar("bar")
//    }

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