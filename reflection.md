# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The Union and Concat operators were super easy for me to implement. Their implementation just seemed straightforward and they took practically no code to write.

The star and plus operators were also fairly easy to write, but I wasn't sure what was up with the angle brackets on each side of the operators, which threw me off. Once I figured out I could just use the brackes in the method name, the rest was largely straightforward.

## Which operators were most difficult to implement and why?
Implicit conversion and the repetition operators were difficult to get working. Implicit conversion is just complicated and (at least as of writing this) I had trouble getting Program.scala to see and/or use the methods I had created without using an import statement.* I also didn't really know how to approach the repetition operator as it makes it look like the RegularExpression is a function or something. There was no method name I could just use like in many of the other functions. While I did get it working, it's not the prettiest implementation.

\* The import statement is probably necessary according to the code from class.

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

Being able to use Strings and Chars and have them automatically convert to regular expressions is really nice for users. I think it really helps them focus on regular expressions and helps the code look really nice. On the same note, users don't even have to know about RegularExpressions (except for the import statement required for implicit conversions), which helps abstract away how the DSL works and is implemented. I think this was really good design.

The union and concatentation operators are fairly easy to use, but they don't really make sense. It would be nice to use 'U' for union, for example, and concatentation could probably be improved as well. In general, it would be nice if the syntax matched what people would normally see or work with.

While the star and plus operators should be familiar to people who've used regular expressions before, the angle brackets around them are unnecessary and just bad. I even got the DSL working without them (so you could say "'c'\*" or at least "'c' \*" instead of "'c' <*>"); it should be as easy as removing the brackets from the method name to get it working (you may also want to import postfixOps to get rid of warnings, but this isn't absolutely necessary).

The syntax for the repeat operator is ok and compact, but it doesn't seem completely intuitive (I don't remember how to do repeats in regular expressions, but I feel like repetition is normally expressed differently).