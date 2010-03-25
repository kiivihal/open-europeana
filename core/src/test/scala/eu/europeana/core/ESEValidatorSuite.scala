package eu.europeana.core

import org.scalatest.{FunSuite}
import collection.mutable.Stack
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 2, 2010 12:12:00 AM
 */

@RunWith(classOf[JUnitRunner])
class ESEValidatorSuite extends FunSuite with ShouldMatchers{

  test("pop is invoked on a non-empty stack") {

      val stack = new Stack[Int]
      stack.push(1)
      stack.push(2)
      val oldSize = stack.size
      val result = stack.pop()
      result should equal (2)
      stack.size should equal (oldSize - 1)
    }

    test("pop is invoked on an empty stack") {

      val emptyStack = new Stack[String]
      evaluating { emptyStack.pop() } should produce [NoSuchElementException]
      emptyStack should be ('empty)
    }

}