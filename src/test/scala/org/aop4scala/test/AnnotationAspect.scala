package org.aop4scala.test

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.{Aspect, Around, Before, Pointcut}

/**
 * User: FaKod
 * Date: 14.07.2010
 * Time: 06:34:54
 */

@Aspect
class AnnotationAspect {
  
  @Pointcut("execution(* *.lolli(..))")
  def call2Lolli() = {}

  @Before("call2Lolli()")
  def callFromFoo() = println("before executing lolli")

  @Around("call2Lolli()")
  def doNothing(thisJoinPoint:ProceedingJoinPoint):Object = {
    println("Around Enter executing lolli")
    val result = thisJoinPoint.proceed
    println("Around Exit executing lolli")
    result
  }
}