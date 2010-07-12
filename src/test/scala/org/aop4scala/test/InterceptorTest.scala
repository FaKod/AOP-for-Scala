package org.aop4scala.test

import org.specs.SpecificationWithJUnit
import org.aop4scala.{Invocation, Interceptor, ManagedComponentFactory, ManagedComponentProxy}
import java.lang.reflect.{Proxy, Method, InvocationHandler}

/**
 * User: FaKod
 * Date: 09.07.2010
 * Time: 08:43:08
 */

class Aspect(val pointcut: String) extends Interceptor {

  def create[I: ClassManifest](target: AnyRef): I = {

    val interface = implicitly[ClassManifest[I]]
    
    val proxy = new TargetProxy(this, target)

    Proxy.newProxyInstance(
      target.getClass.getClassLoader,
      Array(interface.erasure),
       proxy).asInstanceOf[I]
  }

  def invoke(invocation: Invocation): AnyRef = invocation.invoke
}



class TargetProxy(val aspect: Aspect, val target: AnyRef) extends InvocationHandler with Interceptor {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = invoke(Invocation(m, args, target))

  def invoke(invocation: Invocation): AnyRef = aspect.invoke(invocation)
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

    "enable logging intercept" in {

      val aspect = new Aspect("execution(* *.foo(..))") with LoggingInterceptor with TransactionInterceptor

      val foo = aspect.create[Foo](new FooImpl)

      foo.foo("foo")
      foo.bar("bar")
    }

  }

}