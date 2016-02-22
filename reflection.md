# Reflection on implementing regular expressions of a DSL

My first attempt at implicitly casting from Char to Literal was to create a companion Literal object and define the implicit cast there.  I realize now that this didn’t work because, even though I ultimately wanted to end up with a Literal, the method “matches” was looking specifically for a RegularExpression.  This (I think) means that Scala would only look for casts in the Char companion object (which obviously doesn’t exist / I don’t have access to it) and the RegularExpression companion object.
Once I had figured that out, there weren’t any major roadblocks.  The code from last Wednesday’s lecture helped immensely.  One question did come up, though, that I still haven’t been able to answer.  Do the names of our implicit methods matter?  If so, why?  My “charToLiteral” method didn’t work, but my “charToLit” method did (all I changed was the name).  Very confused.

## Which operators were easiest to implement and why?

||, ~, *, + were all very straightforward to implement.  They were all one-liners that followed the same format as the operators we had implemented in our Complex number DSL.  The fact that the test were already written for us (we just had to update the syntax) was very helpful in streamlining the process.

## Which operators were most difficult to implement and why?

The basic idea of {n} (the repetition operator) didn’t take too long to figure out.  However, my first implementation wasn’t working and it took me a while to debug.  It turned out that I was supposed to return EPSILON, and not EMPTY (I had chosen EMPTY randomly as I assumed the two were interchangeable) in my base case, and that was causing the error.
The writeup states that the empty language is one that contains zero strings, while epsilon is itself a null string.  So I guess the issue was that by returning EPSILON as my base case, my regular expression was one with an EPSILON at the end, which was causing it to match improperly.

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