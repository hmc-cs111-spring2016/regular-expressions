package dsls.regex

import org.scalatest.FunSuite
import org.scalatest.Matchers

class RegexSuite extends FunSuite with Matchers {
   test("epsilon") {
     (EPSILON matches "") should be (true)
   }
   
   test("epsilon*") {
     (Star(EPSILON) matches "") should be (true)
   }
   
   test("union") {
     (Union(Literal('0'), EMPTY) matches "0") should be (true)
     (Union(Literal('1'), EMPTY) matches "0") should be (false)
   }
}