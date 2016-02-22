package dsls.regex

import scala.language.implicitConversions
import scala.language.postfixOps

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
  def matches(char: Char) = RegexMatcher.matches(char.toString, this)
  
  def ||(other:RegularExpression): RegularExpression = {
    Union(this, other)
  }
  
  def ~(other:RegularExpression): RegularExpression = {
    Concat(this, other)
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

object RegularExpression {
  
  implicit def charToRegEx(char: Char):RegularExpression = Literal(char)
  
  implicit def stringToRegEx(str: String):RegularExpression = {
    val characters:List[RegularExpression] = str.toList map Literal
    characters.reduce((r, c) => Concat(r, c))
  }
}
