# Reflection on implementing regular expressions of a DSL

I tried to complete this assignment without my notes from the Complex number tutorial, and had the hardest time remembering all the nuances necessary when implementing an internal DSL. After fishing out my notes from that class, the assignment went by very quickly as it more or less layed out a framework for what needed to be done. It also saved me a lot of time by reminding me of several Scala-specifics such as putting the implicit conversions inside the companion object, importing the class when using implicit methods, importing postfixOps when using postfix operations, among other details.

## Which operators were easiest to implement and why?

In particular, ||, ~, *, and + were extremely straight forward to implement considering they were all one liners. It helped a lot that the functionality was already implemented such that we only had to figure out the syntactical sugar. Even then, most of the tricks that were necessary to make the syntactical changes were already covered when we did the Complex number tutorial. 

## Which operators were most difficult to implement and why?

The operator that proved to be most difficult was probably the {n} repetition, but even then, after realizing to use an apply method, the actual implementation was not very difficult. I think conceptually I struggled most with implicit conversions as there are very Scala-specific details that need to addressed. For instance, I completely forgot that I had to import RegularExpression._ in order to use the implicit conversions. I can't even begin to imagine how difficult this assignment would have been had we not done the Complex number tutorial in class, as the tutorial covered companion objects, implicit conversion, postfix operators, when to use apply, among several other things. I think it also helped that this isn't my first time seeing Regular Expressions (CS81), so I could focus more on the implementation details rather than trying to understand the design and the motivations behind the DSL.

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

This design seems to be geared more towards mathemeticians not so well versed in computer science that want to use regular expressions. Almost immediately, the first application that  comes to mind is CS81, which is a Logic and Computability course offered at Harvey Mudd. It seems like this DSL would be helpful for students in that class as the syntax is more intuitive in that it reflects exactly how regular expressions look when they are taught or when they are introduced in the textbook. However, at the same time, to someone who isn't familiar with regular expressions but is familiar with computer science, perhaps calling each method would make more sense than the DSL we provided. For instance, Concat(Star(hello), world) may make more sense than hello* + world as the ordering of operations is not as explicit in our DSL. 

The DSL makes writing regular expressions both easy and intuitive, as it should. As such, the DSL makes writing any pattern-oriented data pretty easy (by nature of regular expressions). On the same note, the DSL is extremely domain specific in that trying to do anything else besides writing regular expressions would be quite cumbersome. Things that cannot be easily described with regular expressions prove to be difficult. For instance, checking a string to see if it's a palindrome would be difficult because the string is not regular. 

One syntactic change that I actually made was implementing both the Kleene star <*> and the concatanation operator <+> as just * and +. I felt like this change was not particularly difficult to make (as it involved simply leveraging this <*> and this <+>). It makes the DSL look pretty much exactly like how a regular expression would be written. I think although it would be pretty bad to overload + and * as these are already existing operators that are very commonly used, given how specific this DSL is, I don't imagine the users would explain a lot. 


