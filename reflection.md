# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The union and concatenation operaters went fairly simply. I 
think these operators have less moving parts and dependencies,
so they're easier to work with in general. The string recognizer
was likewise easy to work with, as all it has to do is see the 
exact string written there.

## Which operators were most difficult to implement and why?

For some reason, I couldn't get the char to string conversion working.
I'm not sure why. I eventually even ended up copy and pasting some 
ideas from the sample solution, and these still didn't fix the problem.
I had similar issues with the star operator. Again, I looked into the 
sample solution and tried out ideas from there, but it didn't seem
to make a difference. If you (the critiquer) have any ideas where I 
was going wrong with these, feedback would be greaty appreciated. I'm
assuming it's a simple but stupid bug at this point.

Conceptually, the {n} operator was the hardest for me to wrap my head around.
I was originally thinking of using something similar to the currying in
our loop lab, but couldn't figure that out. The sample solution helped me
think about how to use apply to work it out.

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

One of the things I like about this design is that it uses operators very similar
to those used in mathematical notation. As such, writing out the regular 
expressions is very similar to how I did so in 81, which was satisfying. It also
greatly reduced the amount of writing necessary for the user, and I think in doing
so, made it more readable.

However, this makes this language less accessible to a beginner. Because it 
assumes that the user understands what each operator is and does, it doesn't
easily lend way to learning about regular expressions and the different operators.
I don't know that anything feels cumbersome to me, given how used I am to the 
typical regex espressions, but I suspect it may feel that way to a beginner.

In regards to syntactic changes that would make the language better, I think it
could be interesting to implement a more "natural language" way to putting things
in addition the current symbolic forms. Whether this is in English or another 
language would be up to the demographics of the proposed users. To implement it,
I would use the same practices as in this assignement to give multiple names to
the same operators. 


