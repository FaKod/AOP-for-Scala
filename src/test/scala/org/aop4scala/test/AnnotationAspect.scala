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
@Aspect
class ConditionalPointcut extends AspectBaseForConditionalPointcut {

  @After("AspectBaseForConditionalPointcut.conditionalPointcut(s)") //,aa)")
  def afterConditionalPointcut(s: String): Unit = { //, AspectBaseForConditionalPointcut aa) {
    println ("After Conditional Pointcut. Called with: " + s)
  }
}
