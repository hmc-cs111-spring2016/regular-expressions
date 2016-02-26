# Reflection on implementing regular expressions of a DSL

My first attempt at implicitly casting from Char to Literal was to create a companion Literal object and define the implicit cast there.  I realize now that this didn’t work because, even though I ultimately wanted to end up with a Literal, the method “matches” was looking specifically for a RegularExpression.  This (I think) means that Scala would only look for casts in the Char companion object (which I don't have access to) and the RegularExpression companion object.
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

Most basic regular expressions are easy/natural to say with this DSL.  All of the example expressions in Program.scala, for example, are pretty concise and easy to read.  There are, however, some changes I would make to the syntax.  I would also extend the DSL's functionality to make certain more complex regular expressions easier to write.
I'll start by evaluating each piece of syntax separately.
- '', "" (chars and strings): Usually when I write regular expressions, I'm not used to putting quotes around all the strings.  Ideally users wouldn't have to with this DSL either, as it adds an extra step and makes the expressions look a little clunky.  However, I really can't picture how this would be implemented within Scala.  We wouldn't be able to implicitly cast from Char or String anymore and there'd be no way to tell if a user is trying to input a string or a variable name.
- || (union): The implementation of this is fine, but a single bar '|' would feel more natural and be more concise.  gray|grey looks better than gray||grey. A benefit of the double bar '||' is that Java/Scala users will be comfortable using it as an "or" operator, but anyone who is familiar with the RegEx would probably still prefer '|' since it's standard.
- ~ (concatenation): I'd like to able to simply write a string next to another and have them automatically be concatenated.  So, ('b' <+>)('c'{3}) instead of ('b' <+>)~('c'{3}).  This might not be possible to accomplish, however, as there'd be no operator to let Scala know that concatenation should be performed.
- * (Kleene Star): Nothing wrong with this.  Should be implemented without '<>', though (In my version I made both * and < * > work).
- + (One or more repitition): This is also pretty standard, so no issues other than the '<>' (same as above).
- {n} (exact number of repititions): This is fine.  Straightforward to implement and familiar to users of the domain.

Some things I'd add:
- A {min, max} operator would allow users to match an expression to an exact range of repetitions, which could be useful.  Implementing this would be similar to implementing {n}.  I'd overload the apply method and take two parameters for this function.
- It would be convenient for the user to be able to input ranges of numbers.  Then, instead of defining "digit" as '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9', we could simply define it as something like [0-9].  This could also work for letters.  [a-c] would match 'a', 'b', or 'c'.  Not actually sure how I'd implement this.
