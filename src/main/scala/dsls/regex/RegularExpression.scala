package dsls.regex

import scala.language.implicitConversions
import scala.language.postfixOps

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)
  
  // operators
  /****************************************************************************
   * Defines the || operator using the Union case class
   ***************************************************************************/
  def ||(other: RegularExpression): RegularExpression = Union(this, other)
  
  /****************************************************************************
   * Defines the ~ operator using the Concat case class
   ***************************************************************************/
  def ~(other: RegularExpression): RegularExpression = Concat(this, other)
  
  /****************************************************************************
   * Defines the <*> operator using the Star case class
   ***************************************************************************/
  def <*> = Star(this)
  
  /****************************************************************************
   * Defines the <+> operator in terms of the ~ and <*> operators. Since the
   * <*> operator matches to EPSILON, we can prevent the <+> operator from
   * matching to EPSILON by ~ an occurrence of this to the this<*>.  
   ***************************************************************************/
  def <+> = this ~ this<*>
  
  /****************************************************************************
   * Recursively defines the {} operator by concatenating itself to itself
   * num times and terminating with an EPSILON.
   ***************************************************************************/
  def apply(num:Int): RegularExpression =
    if (num == 0) EPSILON else this ~ this{num - 1}
}
  
object RegularExpression {
  /****************************************************************************
   * Defines the implicit conversion from char to RegularExpression
   ***************************************************************************/
  implicit def charToRegex(c:Char) = Literal(c)
  
  /****************************************************************************
   * Defines the implicit recursive conversion from string to
   * RegularExpression. Modified to use the implicit character conversion
   * and the ~ operator.
   ***************************************************************************/
  implicit def strToRegex(s:String): RegularExpression =
    if (s.length() == 1) s.head else s.head ~ s.tail 
}

/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** a regular expression that matches either one expression or another */
case class Union(val left: RegularExpression, val right: RegularExpression) extends RegularExpression

/** a regular expression that matches one expression followed by another */
case class Concat(val left: RegularExpression, val right: RegularExpression) extends RegularExpression
  
/** a regular expression that matches zero or more repetitions of another 
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression
