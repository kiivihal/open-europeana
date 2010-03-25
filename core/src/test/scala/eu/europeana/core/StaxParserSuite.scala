package eu.europeana.core

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{PrivateMethodTester, FunSuite}

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 11, 2010 9:02:06 PM
 */

class StaxParserSuite extends FunSuite with ShouldMatchers with PrivateMethodTester {

  test("run StaxParser") {
    val parser: StaxParser = new StaxParser
    parser.runParser
    val randomString = "bla"
    randomString should equal (randomString)
  }

  ignore("run RecordParser") {
    val parser: StaxParser = new StaxParser
    val randomString = "bla"
    randomString should equal (randomString)
  }

  test("test isStartRecord") {
  }

}