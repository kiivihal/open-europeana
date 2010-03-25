package eu.europeana.core

import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 3, 2010 4:13:33 PM
 */

@RunWith(classOf[JUnitRunner])
class ESEValidatorFeatureSpec extends FeatureSpec with GivenWhenThen {

  feature("Integer arithmetic") {

    scenario("addition") {

      given("two integers")
      val x = 2
      val y = 3

      when("they are added")
      val sum = x + y

      then("the result is the sum of the two numbers")
      assert(sum === 5)
    }

    scenario("subtraction") {

      given("two integers")
      val x = 7
      val y = 2

      when("one is subtracted from the other")
      val diff = x - y

      then("the result is the difference of the two numbers")
      assert(diff === 5)
    }
  }
}
