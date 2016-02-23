package dsls.regex
import scala.language.implicitConversions

// Not necessary, but still a nice thing to do, I think.
// If I recall correctly this just tells scala that we're going to
// be using postfix operators.
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
  
  // Scala automatically allows us to use these in infix notation.
  def ||(other: RegularExpression) = Union(this, other)
  def ~(other: RegularExpression) = Concat(this, other)
  
  // Scala also automaticaly allows us to use this in postfix notation.
  // So calling it is simply a matter of putting it after a regex.
  def <*>() = Star(this)
  
  // Traditionally + isn't defined as a basic operation, it's defined as
  // the concatenation of one copy with a starred copy, so this operation
  // is also very simple.
  def <+>() = Concat(this, Star(this))
  
  // The syntax R{n} for a regular expression R and an integer n is exactly
  // the syntax that allows us to use apply, because syntactically
  // it looks like we're applying R to n (since {} and ()
  // can both be used for function application). 
  // This could be done recursively or with a mapreduce. 
  def apply(x:Int):RegularExpression =
    if (x==0) 
      EPSILON 
    else 
      // We can get n copies by taking one copy and concatenating with n-1 more
      Concat(this, this.apply(x-1));
}

// We only need this for the implicit conversions (because as implicit 
// conversions, they are called without an instance.)
object RegularExpression {
  // Simple.
  implicit def fromChar(c: Char) = Literal(c)
  
  // Again, this could be done with a mapreduce as well.
  implicit def fromString(s: String):RegularExpression = 
    if (s == "") 
      EPSILON 
    else 
      // Pull off one char, and recurse on the rest.
      Concat(Literal(s.head), fromString(s.tail))
  
}


/*
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*/


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
