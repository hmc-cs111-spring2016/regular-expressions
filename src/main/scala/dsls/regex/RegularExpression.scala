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
  def matches(string: String): Boolean = RegexMatcher.matches(string, this)

  /** Allows the usage of double bars "||" as a union operator */
  def ||(other: RegularExpression) = Union(other, this)

  /** Allows the usage of the tilda "~" as a concatenation operator */
  def ~(other: RegularExpression) = Concat(this, other) //order of parameters here matters!

  /** Allows the usage of the asterisk "*" as Kleene Star */
  def *() = Star(this)
  def <*>() = Star(this)

  /** Allows the usage of the plus sign "+" to indicate one or more repetitions */
  def +() = Concat(this, this*)
  def <+>() = Concat(this, this*)

  /** Allows us to use {n} to indicate n repetitions
   *
   * @pre: n is non-negative
   */
  def apply(n: Int): RegularExpression = {
    if (n < 0) {
      throw new IllegalArgumentException //yell if the user gives us a negative value
    }
    n match {
      case 0 => EPSILON //base case
      case x => Concat(this, apply(n - 1)) //recursive case
    }
  }
}

/** A companion object to RegularExpression, made for the purpose of implicitly casting to RegularExpression */
object RegularExpression {

  /** Implicit cast from Char to RegularExpression */
  implicit def charToLit(character: Char) = Literal(character) //for some reason this doesn't work when named 'charToLiteral'

  /** Implicit cast from String to RegularExpression */
  implicit def stringToRegEx(string: String): RegularExpression = {

    //inner method since we want to recurse on a Char list, rather than a String
    def myStringToRegEx(chars: List[Char]): RegularExpression = {
      chars match {
        case Nil       => EPSILON
        case (x :: xs) => Concat(x, myStringToRegEx(xs))
      }
    }
    myStringToRegEx(string.toList)
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

/**
 * a regular expression that matches zero or more repetitions of another
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression
