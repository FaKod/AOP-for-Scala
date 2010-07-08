package org.aop4scala


trait Foo {
  @Deprecated
  def foo(msg: String)

  def bar(msg: String)
}

class FooImpl extends Foo {
  val bar: Bar = new BarImpl

  def foo(msg: String) = println("msg: " + msg)

  def bar(msg: String) = bar.bar(msg)
}

trait Bar {
  def bar(msg: String)
}

class BarImpl extends Bar {
  def bar(msg: String) = println("msg: " + msg)
}