# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The easiest operators to implement were ||, ~, and <*>. These operators were
literally just wrappers around the Union(), Concat(), and Star() case classes
respectively. The only real difference is these operators were implemented
within the Regular Expression class so they could be infix operators (for ||
and ~) and a postfix operator (for <*>). Although <+> operator was still pretty
easy, unlike the previous examples, it was defined in terms of other 
operatators (i.e. ~ and <*>) rather than just being a wrapper around a case
class.

Overall, these operators were so easy to implement since all the behavior
was already defined by RegexMatcher. This means the operators were really
only responsible for properly nesting input inside predefined 
RegularExpression case classes.

## Which operators were most difficult to implement and why?

Although none of the operators were particularly difficult, apply (i.e. the {}
operator) was certainly the most difficult of the 5 operators. This is because
unlike the other operators, it required more than just nesting case classes
or using our other operators. Rather, the {} operator needed some type of
repetitive logical structure. Although I chose to use recursion (i.e. the {}
operator recursively uses itself until it hits the base case) a _for loop_
could have provided this same functionality.

In other words, although most of the behavior of the {} operator is defined
in RegexMatcher, this operator required a logical construction of the
case class nesting rather than simply relying on a case class to embody all
of this information. Although there could be a case class object to embody
this information, by defining the {} operator in terms of other operators and
case classes, we can simplify the behavior code in RegexMatcher.

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)
   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")

This design works very well for a simple implementation of a Scala regex
internal DSL. That is, this implementation allows Scala users to natively use
the most common regex features within Scala. For instance, if Java was going to
attempt to implement a similar structure for regex (i.e. using classes rather
than packing it within the String class, like it actually does), you would
not be able to be this expressive. Java code, by not allowing implicit
conversions, operator overloads, and () omission in function calls, would be
forced to look similar to the initial Scala code we were provided. Although the
line between internal DSL and API is not clearly defined, the feel of this
final Scala code is much more DSL-like than anything Java would be able to
produce.

That being said, there are parts of this DSL that are a little clunky.
Java's implementation of regex, built into the String class, comes by default
with some very useful predefined regular expressions that match common
"character sets". Although we managed to implement digits in our code (e.g.
the set of digits '\d' in Java), we needed to do it by manually unioning all
the numeric characters. Although this was not too bad thanks to the ||
operator, this quickly become unrealistic for "character sets" that
include more than 10 characters (e.g. the set of non-digits '\D'). Although
not having to implement this saves the implementer work, it is very
undesirable for users.

Additionally, since this internal DSL only tackles the most common regex
operations there are many features that are simply not available currently.
For instance, the '[^...]' subexpression, '\A', \z' in Java cannot be
implemented with the existing set of operations without further expanding
this DSL and the RegexMatcher. ('[^...]' matches any single character not
included in the brackets; '\A' matches the beginning of the string; '\z'
matches the end of a string). Again, although not having to implement these
operations saves the implementer work, it is very undesirable for users.

That being said, nothing about the Scala environment prevents there from
being ways to define these operations and character sets. Just like we
internally defined the objects EMPTY and EPSILON which match nothing
and the empty string respectively, we could define objects like DIGIT, 
NONDIGIT, BEGIN, and END which match to digits, non-digits, string starts, and
string ends respectively. Although this would require the implementer to
convolute this implementation, the additional features would be necessary for
this internal DSL to match up against most common modern regex libraries.

Similarly, although we would probably need to define it as a postfix operator,
we could make something that matches the functionality of the '[^...]'. Although
this change would certainly make the DSL better, it is debatable whether this
can be incorperated into the syntax (this is a very important point). Although
things like NONDIGIT could be incorperated into our internal DSL so it is
functionally and almost syntaxtically iterchangable with the Java regex
implementation (i.e. at least in terms of ordering), there are certain
restrictions Scala puts that we cannot change. That is, as long as our regex
implementation remains an internal DSL, there is a set of features we cannot
change. For instance, Scala limits us to a small subset of unary prefix
operators. It is for this reason we would probably want to implement '[^...]'
as a postfix operator of some sort. Similarly, + and * are normally binary
operators in Scala, thus we use <+> and <*> as our unary postfix operators.
The only way to overcome these shortcomings would be to implement an
external DSL which adds a lot more work for the developer. Furthermore, forcing
users to relearn a standard (i.e. regex) just because of a shortcoming in your
implementation is a little unreasonable.
