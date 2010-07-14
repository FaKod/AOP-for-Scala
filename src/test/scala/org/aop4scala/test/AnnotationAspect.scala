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

  /**
   * simple pointcut for execution of method lolli
   */
  @Pointcut("execution(* *.lolli(..))")
  def call2Lolli() = {}

  /**
   * intercepts before execution of lolli
   */
  @Before("call2Lolli()")
  def callFromFoo() = println("before executing lolli")

  /**
   * around execution of lolli
   */
  @Around("call2Lolli()")
  def doNothing(thisJoinPoint: ProceedingJoinPoint): Object = {
    println("Around Enter executing lolli")
    val result = thisJoinPoint.proceed
    println("Around Exit executing lolli")
    result
  }


  /**
   * more complex pointcut to get instance of LolliPop
   * and get String parameter of method lolli 
   */
  @Pointcut("call(* *.lolli(String)) && args(s) && target(callee)")
  def someCall(s: String, callee: LolliPop): Unit = {}

  @Before("someCall(str, callee)")
  def callFromSomeCall(str:String, callee: LolliPop) = println("before call with: " + str)
}