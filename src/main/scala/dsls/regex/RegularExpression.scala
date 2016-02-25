package dsls.regex

import scala.language.implicitConversions
import scala.language.postfixOps

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** Returns true if the given string matches this regular expression. */
  def matches(string: String) = RegexMatcher.matches(string, this)
  
  /** Definition of a symbol to represent the union of two regular expressions. */
  def ||(expr: RegularExpression) = Union(this, expr)
  
  /** Definition of a symbol to represent the concatenation of two regular expressions. */
  def ~(expr: RegularExpression) = Concat(this, expr)
  
  /** Implementation of postfix operator for Kleene star operation. */
  def <*> = Star(this)
  
  /** Implementation of postfix operator for one or more pattern repetitions. */
  def <+> = Concat(this, Star(this))
  
  /** Call method dealing with repetition operators that takes in an integer as an 
   *  argument and repeats the given expression that many times
   */
  def apply (repeat: Int): RegularExpression = {
    if (repeat == 0) EPSILON
    else Concat(this, apply(repeat-1))
  }
}

/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** Literal companion object including implicit conversion from char to Literal */
object Literal {
  implicit def charToLiteral(c: Char) = Literal(c)
}

/** a regular expression that matches either one expression or another */
case class Union(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression
  
/** a regular expression that matches one expression followed by another */
case class Concat(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression
  
/** 
 *  Concat companion object implicitly converting strings to RegularExpressions by
 *  using Concat to combine each char Literal in the string
 */
object Concat {
  implicit def stringToRegex(s: String): RegularExpression = {
    if (s.length() == 1) Literal(s.head)
    else Concat(Literal(s.head), stringToRegex(s.tail))
  }
}
  
/** a regular expression that matches zero or more repetitions of another 
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression
