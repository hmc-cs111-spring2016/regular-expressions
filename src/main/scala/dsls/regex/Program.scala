package dsls.regex

import RegularExpression._

object Program extends App {
  
  /****************************************************************************
   * Replaced:
   *    val zero = Literal('0')
   *  With:
   *   val zero = '0'
   * etc.
   ***************************************************************************/
  val zero  = '0'
  val one   = '1'
  val two   = '2'
  val three = '3'
  val four  = '4'
  val five  = '5'
  val six   = '6'
  val seven = '7'
  val eight = '8'
  val nine  = '9'
  
  require(zero matches "0")
  require(one matches "1")
  require(two matches "2")
  require(three matches "3")
  require(four matches "4")
  require(five matches "5")
  require(six matches "6")
  require(seven matches "7")
  require(eight matches "8")
  require(nine matches "9")
  
  /****************************************************************************
   * Replaced: 
   *    val answer = Concat(four, two)
   * with:
   *   val answer = "42"
   *Added more test cases
   ***************************************************************************/
  
  val answer = "42"
  require(answer matches "42")


  val answer1 = ""
  val answer2 = "1234"
  
  require(answer1 matches "")
  require(answer2 matches "1234")
              
  /****************************************************************************
   * Replaced:
   *    val digit = Union(zero, Union(one, Union(two, Union(three, Union(four, 
   *          Union(five, Union(six, Union(seven, Union(eight, nine)))))))))
   * with:
   *    val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 
   ***************************************************************************/

  val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 

  require(digit matches "0")
  require(digit matches "1")
  require(digit matches "2")
  require(digit matches "3")
  require(digit matches "4")
  require(digit matches "5")
  require(digit matches "6")
  require(digit matches "7")
  require(digit matches "8")
  require(digit matches "9")      

  /****************************************************************************
   * Replaced:
   *    val pi = Concat(Literal('3'), Concat(Literal('1'), Literal('4')))
   * With:
   *   val pi = '3' ~ '1' ~ '4'
   ***************************************************************************/
  val pi = '3' ~ '1' ~ '4'

  require(pi matches "314")
  
  /****************************************************************************
   * Replaced:
   *    val zeroOrMoreDigits = Star(digit)
   * With:
   *    val zeroOrMoreDigits = digit <*>
   ***************************************************************************/
  
  val zeroOrMoreDigits = digit <*>
  
  require(zeroOrMoreDigits matches "")
  require(zeroOrMoreDigits matches "0")
  require(zeroOrMoreDigits matches "9")
  require(zeroOrMoreDigits matches "09")
  require(zeroOrMoreDigits matches "987651234")
  
  /****************************************************************************
   * Replaced:
   *    val number = Concat(digit, zeroOrMoreDigits)
   * With:
   *    val number = digit <+> 
   ***************************************************************************/
  val number = digit <+> 
  
  require(!(number matches ""))
  require(number matches "0")
  require(number matches "9")
  require(number matches "09")
  require(number matches "987651234")

  /****************************************************************************
   * Replaced:
   *    val cThree = Concat(Literal('c'), Concat(Literal('c'), Literal('c')))
   * With:
   *    val cThree = 'c'{3}
   ***************************************************************************/
  val cThree = 'c'{3}
  
  require(cThree matches "ccc")
  
  /****************************************************************************
   * Replaced:
   *    val aStar = Star(Literal('a'))
   *    val bPlus = Concat(Literal('b'), Star(Literal('b')))
   *    val pattern = Union(answer, Concat(aStar, Concat(bPlus, cThree)))
   * With:
   *    val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
   ***************************************************************************/

  val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
  
  require(pattern matches "42")
  require(pattern matches "bccc")
  require(pattern matches "abccc")
  require(pattern matches "aabccc")
  require(pattern matches "aabbccc")
  require(pattern matches "aabbbbccc")
 
   /****************************************************************************
   * Replaced:
   *    val hello = Concat(Literal('h'), Concat(Literal('e'), Concat(Literal('l'), 
   *          Concat(Literal('l'), Literal('o'))))) 
   *
   *    val world = Concat(Literal('w'), Concat(Literal('o'), Concat(Literal('r'), 
   *           Concat(Literal('l'), Literal('d'))))) 
   *
   *    val helloworld = Concat(Star(hello), world)
   * 
   * With:
   *   val helloworld = ("hello" <*>) ~ "world"
   ***************************************************************************/

  val helloworld = ("hello" <*>) ~ "world"
  
  require(helloworld matches "helloworld")
  require(helloworld matches "world")
  require(helloworld matches "hellohelloworld")
  
   /****************************************************************************
   * Replaced:
   *    val threeDigits = Concat(digit, Concat(digit, digit))
   *    val fourDigits = Concat(threeDigits, digit)
   *    val areaCode = Concat(Literal('('), Concat(threeDigits, Literal(')')))
   *    val telNumber = Concat(areaCode, Concat(threeDigits, Concat(Literal('-'), fourDigits)))
   * With:
   *   val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}
   ***************************************************************************/
  
  val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}

  require(telNumber matches "(202)456-1111")
}
