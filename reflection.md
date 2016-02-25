# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

For me, the union and concatenation operators were the easiest to implement. Once I understood that it would be easy to declare them as methods in the `RegularExpression` abstract class, I knew exactly what to do. It made sense that they would be treated as binary operators and it was easy to take advantage of Scala being left-associative to get both arguments.

The most difficult part of implementing these operators was to realize where I needed to put the code. I knew that I wanted to combine two regular expressions with the binary operators but was uncertain if I should be doing so in `Union` and `Concat` objects or not. I eventually understood that these could just be declared as methods in the `RegularExpression` class since each of the case classes extends this class.

One interesting thing that I discovered while implementing implicit conversions is that the empty string is treated like a single character in Scala. My conversion from string to regular expression involves recursion through each character in the string. The base case was `EPSILON` since I thought that the empty string would be counted as a string. However, I discovered through testing in Program.scala that this base case is unnecessary. The empty string is not processed by this implicit conversion and the base case is never needed. As a result of this discovery, I changed the base case for this conversion to checking for a string of length 1.

## Which operators were most difficult to implement and why?

The repetition operator was the most difficult to implement because I forgot that arguments could be passed into methods through curly braces. Once I started looking at this operator as a `RegularExpression` that took one argument, I understood how to go about solving the problem. However, it took me a while to implement it. I wanted to modify `RegularExpression` so that it could take an optional argument, that being the number of times it should be repeated. 

I first tried to create a companion object with an implicit definition that would convert a `RegularExpression` followed by an integer argument into one `RegularExpression`. However, this was not recognized as a valid command and the program gave errors about some of the operators not taking arguments. I then tried changing up the order in which I called the implicit definitions of the program. Eventually I used a call method in the `RegularExpression` class that would treat the repetition value passed after a regular expression as an argument to an instance of the class. 

## Comment on the design of this internal DSL

_What works about this design? What doesn't work about this design? Think of a syntactic change that might make the language better. How would you implement it __or__ what features of Scala would prevent you from implementing it?_

Using the DSL seems more user friendly, especially to someone more math- and logic-oriented than a computer scientist. I would say that this is one of the things that both works and doesn't work about the DSL. Previously, the expressions were more like function calls and the operations were defined as words. This was more explanatory and perhaps more intuitive to some users. However, the new design is more succinct and the placement of the operators is more intuitive for some users. 

In this new DSL, it is less cumbersome to define union operators and to write expressions involving many types of operations. For example, some of the provided patterns involved many intermediate values to produce a readable regular expression. One of these was a telephone number which required precise placement of symbols and numbers. Whereas the old syntax required four lines declaring new values, the new DSL represented a phone number in one statement (taking previously declared values into account).

One syntactic change that would make more sense to me in this language would involve the postfix operators `<*>` and `<+>`. In normal regular expressions, these operators are not surrounded by angle brackets. They are merely of the form `*` and `+`. However, these are already defined as operators in Scala. The functionality of these operators within the context of `RegularExpression` would be different from that of the standard binary operator. The operators would not take the same parameters as the others and Scala would complain. It would also be terrible style to define `*` and `+` as something other than addition and multiplication, even for a custom class. Thus, the DSL does not have the exact same syntax for some operators as regular expressions, but that is one of the tradeoffs of trying to implement this in Scala. 
