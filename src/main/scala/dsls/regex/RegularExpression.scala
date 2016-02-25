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
  def ||(that: RegularExpression): RegularExpression = Union(this, that)
  def ~(that: RegularExpression): RegularExpression = Concat(this, that)
  def <*> = Star(this)
  def <+> = Concat(this, Star(this))
  def apply(num:Int): RegularExpression = if (num == 0) EPSILON else Concat(this, this.apply(num-1))
}

object RegularExpression {
  implicit def charToRegex(c:Char) = Literal(c)
  implicit def strToRegex(s:String): RegularExpression = {
    if (s.length() ==1) Literal(s.head) else Concat(Literal(s.head), strToRegex(s.tail))
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
