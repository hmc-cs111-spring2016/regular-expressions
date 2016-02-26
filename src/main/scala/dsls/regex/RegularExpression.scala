package dsls.regex

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

	def || (that: RegularExpression) = Union(this,that)

	def ~ (that: RegularExpression) = Concat(this,that)

	def <+> () = this ~ (this<*>)

	def <*> () = Star(this)

	def apply (num: Int): RegularExpression = {
		if (num > 0) {
			this ~ this{num-1}
		} else {
			EPSILON
		}
	}


}

object RegularExpression {
	implicit def fromChar(c: Char) = Literal(c)

	implicit def fromString(word: String): RegularExpression = {
		if (word.equals("")) {
			EPSILON
		} else
		fromChar(word.head) ~ fromString(word.tail)

	}
}

/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** a regular expression that matches a literal string */
//case class Literal(val literal: String) extends RegularExpression

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





