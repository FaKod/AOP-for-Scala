package org.aop4scala.test;

import org.aspectj.lang.annotation.Pointcut;

/**
 * User: FaKod
 * Date: 15.07.2010
 * Time: 12:23:22
 */
public class AspectBaseForConditionalPointcut {

    /**
     * creating public static boolean ... method
     * required for conditional pointcuts
     */
    @Pointcut("call(* *.lolli(String)) && if() && args(s)")// && this(aa)")
    public static boolean conditionalPointcut(String s) {//, AspectBaseForConditionalPointcut aa) {
        return true;
    }
}
