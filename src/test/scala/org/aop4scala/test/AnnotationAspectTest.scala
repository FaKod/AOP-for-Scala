package org.aop4scala.test

import org.specs.SpecificationWithJUnit

/**
 * User: FaKod
 * Date: 14.07.2010
 * Time: 07:00:09
 */

class AnnotationAspectTest extends SpecificationWithJUnit {
  "Annotated AspectJ Aspects" should {

    "enable Around interception" in {

      val lolliPop = new LolliPop

      lolliPop.lolli("pop")
    }
  }

}