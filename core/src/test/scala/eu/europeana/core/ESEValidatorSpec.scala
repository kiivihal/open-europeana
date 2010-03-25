package eu.europeana.core

import collection.mutable.Stack
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{WordSpec, Spec}

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 2, 2010 4:19:35 PM
 */

class ESEValidatorSpec extends Spec with ShouldMatchers {

  // normal spec
  describe("A Stack") {

    describe("(when non-empty)") {
      val stack = new Stack[Int]
      stack.push(1)
      stack.push(2)
      val oldSize = stack.size
      val result = stack.pop()

      it("should contain one less object ofter being popped"){
        stack.size should equal(oldSize - 1)
      }

      it("should contain 2 objects when the Stack is popped") {
        result should equal(2)
      }

    }

    describe("(when empty)") {

      val stack = new Stack[Int]
                                                
      it("should be empty") {
        stack should be('empty)
      }

      it("should complain when popped") {
        evaluating {stack.pop()} should produce[NoSuchElementException]
      }
    }
  }



  //  "A Stack" when {
  //
  //    "non-empty" should {
  //      val stack = new Stack[Int]
  //      stack.push(1)
  //      stack.push(2)
  //      val oldSize = stack.size
  //      val result = stack.pop()
  //      result should equal(2)
  //      stack.size should equal(oldSize - 1)
  //    }
  //
  //    "empty" should {
  //
  //      val stack = new Stack[Int]
  //
  //      "be empty" in {
  //        stack should be('empty)
  //      }
  //
  //      "complain when popped" in {
  //        evaluating {stack.pop()} should produce[NoSuchElementException]
  //      }
  //    }
  //  }


}