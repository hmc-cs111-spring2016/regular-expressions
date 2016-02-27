package dsls.regex

import scala.language.postfixOps
import scala.language.implicitConversions

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
  
  // union operator, use as "expr1 || expr2"
  def ||(reg: RegularExpression) = Union(this, reg)
  
  // concatentation operator, use as "expr1 ~ expr2"
  def ~(reg: RegularExpression) = Concat(this, reg)
  
  // star operator, use as "expr <*>"
  def <*> = Star(this)
  
  // "plus" operator
  // like star, but there must be at least one copy of the expression
  // use as "expr <+>"
  def <+> = Concat(this, this <*>)
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

object RegularExpression {
  // converts Strings to RegularExpressions
  // tries to avoid EPSILONs in Concat
  implicit def fromString(str: String): RegularExpression = {
    if (str.length < 1) EPSILON
    else if (str.length == 1) Literal(str(0))
    else Concat(Literal(str.head), fromString(str.tail))
  }
  
  // converts a Char to a RegularExpression
  implicit def fromChar(chr: Char): RegularExpression = Literal(chr)
  
  // helper function that repeats a RegularExpression a given number of times
  // tries to avoid EPSILONs
  private def repeatNTimes(expr: RegularExpression, n: Int):RegularExpression = {
    if (n < 1) EPSILON
    else if (n == 1) expr
    else Concat(expr, repeatNTimes(expr, n-1))
  }
  
  // 100% pure Scala magic
  // converts a RegularExpression to a function that takes a number and returns the RegularExpression repeated that many times
  // the syntax is weird because we may need to do this conversion after already doing a conversionm like in "'c'{3}"
  // the following is a new form of art that combines comments in stream of consciousness with code to simultaneously express the programmer's thoughts and intentions
  implicit def magic[WhyDoINeedThisType](whyIsThisNotARegularExpression: WhyDoINeedThisType)(implicit whyDoINeedThisFunction: WhyDoINeedThisType => RegularExpression): Int => RegularExpression = 
    {i: Int => repeatNTimes(whyIsThisNotARegularExpression, i)}
}