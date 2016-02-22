# Reflection on implementing regular expressions of a DSL

I tried to complete this assignment without my notes from the Complex number tutorial, and had the hardest time remembering all the nuances necessary when implementing an internal DSL. After fishing out my notes from that class, the assignment went by very quickly as it more or less layed out a framework for what needed to be done. It also saved me a lot of time by reminding me of several Scala-specifics such as putting the implicit conversions inside the companion object, importing the class when using implicit methods, importing postfixOps when using postfix operations, among other details.

## Which operators were easiest to implement and why?

In particular, ||, ~, *, and + were extremely straight forward to implement considering they were all one liners. It helped a lot that the functionality was already implemented such that we only had to figure out the syntactical sugar. Even then, most of the tricks that were necessary to make the syntactical changes were already covered when we did the Complex number tutorial. 

## Which operators were most difficult to implement and why?

The operator that proved to be most difficult was probably the {n} repetition, but even then, after realizing to use an apply method, the actual implementation was not very difficult. I think conceptually I struggled most with implicit conversions as there are very Scala-specific details that need to addressed. For instance, I completely forgot that I had to import RegularExpression._ in order to use the implicit conversions. I can't even begin to imagine how difficult this assignment would have been had we not done the Complex number tutorial in class, as the tutorial covered companion objects, implicit conversion, postfix operators, when to use apply, among several other things. I think it also helped that this isn't my first time seeing Regular Expressions (CS81 woohoo), so I could focus more on the implementation details rather than trying to understand the design and the motivations behind the DSL.

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
