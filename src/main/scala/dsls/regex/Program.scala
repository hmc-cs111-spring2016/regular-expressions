package dsls.regex

object Program extends App {
  
  /****************************************************************************
   * Allows us to use our DSL in this Program object
   ***************************************************************************/
  import RegularExpression._
  
  /****************************************************************************
   * Implicitly casts characters to a RegularExpression
   ***************************************************************************/
  val zero = '0'
  val one = '1'
  val two = '2'
  val three = '3'
  val four = '4'
  val five = '5'
  val six = '6'
  val seven = '7'
  val eight = '8'
  val nine = '9'
  
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
  println("Passed individual digit requires!")
  
  /****************************************************************************
   * Implicitly casts strings to a RegularExpression
   ****************************************************************************/
  val answer = "42"
  
  require(answer matches "42")
  println("Passed implicit string conversion require!")
              
  /****************************************************************************
   * Implicitly defines a RegularExpression with characters and the || 
   * operator that matches to any digit 
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
  println("Passed digit requires!")

  /****************************************************************************
   * Uses the ~ operator for concatenation 
   ***************************************************************************/
  val pi = '3' ~ '1' ~ '4'

  require(pi matches "314")
  println("Passed pi requires!")
  
  /****************************************************************************
   * Uses the <*> operator
   ***************************************************************************/
  val zeroOrMoreDigits = digit<*>
  
  require(zeroOrMoreDigits matches "")
  require(zeroOrMoreDigits matches "0")
  require(zeroOrMoreDigits matches "9")
  require(zeroOrMoreDigits matches "09")
  require(zeroOrMoreDigits matches "987651234")
  println("Passed digit requires!")
  
  /****************************************************************************
   * Uses the <+> operator
   ***************************************************************************/
  val number = digit<+>
  
  require(!(number matches ""))
  require(number matches "0")
  require(number matches "9")
  require(number matches "09")
  require(number matches "987651234")
  println("Passed number requires!")

  /****************************************************************************
   * Uses the {} operator for repetition
   ***************************************************************************/
  val cThree = 'c'{3}
  
  require(cThree matches "ccc")
  println("Passed repetition requires!")
  
  /****************************************************************************
   * Uses multiple operators together
   ***************************************************************************/
  val pattern = "42" || (('a'<*>) ~ ('b'<+>) ~ ('c'{3}))
  
  require(pattern matches "42")
  require(pattern matches "bccc")
  require(pattern matches "abccc")
  require(pattern matches "aabccc")
  require(pattern matches "aabbccc")
  require(pattern matches "aabbbbccc")
  println("Passed pattern requires!")
 
   /****************************************************************************
   * Uses multiple operators together (again)
   ***************************************************************************/
  val helloworld = ("hello"<*>) ~ "world"
  
  require(helloworld matches "helloworld")
  require(helloworld matches "world")
  require(helloworld matches "hellohelloworld")
  println("Passed hello world requires!")
  
   /****************************************************************************
   * Uses multiple operators together (again, again)
   ***************************************************************************/
  val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}
  
  require(telNumber matches "(202)456-1111")
  println("Passed telephone requires!")
}
