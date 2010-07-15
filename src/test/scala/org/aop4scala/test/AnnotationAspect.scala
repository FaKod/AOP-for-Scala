package org.aop4scala.test

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation._

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
  def beforeAdviceCall2Lolli() = println("before executing lolli")

  /**
   * around execution of lolli
   */
  @Around("call2Lolli()")
  def aroundAdviceCall2Lolli(thisJoinPoint: ProceedingJoinPoint): Object = {
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
  def call2LolliWithArgs(s: String, callee: LolliPop): Unit = {}

  @Before("call2LolliWithArgs(str, callee)")
  def beforeAdviceCall2LolliWithArgs(str: String, callee: LolliPop) = println("before call with: " + str + " to " + callee)
}


/**
 * test for conditional pointcuts
 */
//class ConditionalPointcut extends AspectBaseForConditionalPointcuts {
//
//  /**
//   *  conditional pointcut stuff
//   * implementing the java interface to provide a Scala
//   * conditional expression
//   */
//  override def pointcutCondition(s:String):Boolean = {
//    println("Called Condition with string: " + s)
//    true
//  }
//
//  @After("conditionalPointcut(s,aa)")
//  override def afterConditionalPointcut(s:String, aa:AspectBaseForConditionalPointcuts) = {
//    println("After Conditional Pointcut. Called with: " + s)
//  }
//}