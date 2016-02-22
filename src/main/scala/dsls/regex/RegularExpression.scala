package dsls.regex

/**
 * Modify this file to implement an internal DSL for regular expressions. 
 * 
 * You're allowed to add anything you want to this file, but you're not allowed
 * to *remove* anything that currently appears in the file.
 */

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)
// Handles matches for a character input, not necessary if we have implicit conversion
//  def matches(char: Char) = RegexMatcher.matches(char.toString, this)
  /** allows users to use || as union operator */
  def ||(rest: RegularExpression) = Union(this, rest)
  
  /** allows users to use ~ as concatenation operator */
  def ~(rest: RegularExpression) = Concat(this, rest)
}

/** A companion object for RegularExpression to handle implicit conversions*/
object RegularExpression {
  /** implicit conversion from Char to valid RegularExpression */
  implicit def charToRegex(char: Char): RegularExpression = Literal(char)
  
  /** implicit conversion from String to valid RegularExpression */
  implicit def stringToRegex(string: String): RegularExpression = {
    // Convert string to a list of literals such that they are valid RegularExpressions
    val listLiterals: List[RegularExpression] = string.toList map Literal
    
    // Method that uses pattern matching to reconstruct the literals 
    def literalsCombine(list: List[RegularExpression]): RegularExpression = {
      list match {
        case (x::xs) => Concat(x, literalsCombine(xs))
        case _ => EPSILON
      }
    }
    // Call literalsCombine to concatenate literals to make a RegularExpression
    literalsCombine(listLiterals)
  }
}

/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** a regular expression that matches either one expression or another */
case class Union(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression

/** a regular expression that matches one expression followed by another */
case class Concat(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression
  
/** a regular expression that matches zero or more repetitions of another 
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression
