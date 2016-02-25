# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
Union was _very_ easy to implement once I was able to understand that it needed to be in the abstract class and not the companion object (I don't know why this is the case but this solved the issue). It gave me a feel of how removing Scala-ness actually works, and (in this case) was fairly straightforward.

Concat was very similar to Union which made it quite simple! And, in fact, once I got through the basic concept of adding functionality through new operators, this made everything else easier -- Star, +, and so on.

## Which operators were most difficult to implement and why?

I may be conflating char/string literals with the basic work of getting all the imports right, but these to actually took me a long time. This was difficult for me also because my functional programming isn't great, so I spent a while on string literals trying to use foldRight instead of reduce, and forgot a few other small Scala things. But once I got the idea of how to turn a string into a list of char literals (which were interpreted by Scala, I think, as RegEx's), the rest became easier.

Another thing that took a short while was the apply function, but a quick perusal of the code led me to Scala's factory methods (using the apply function) which completely solved the issue.

That said, now that I've finished the assignment, string literals were by far the most difficult part of the assignment. Looking at the sample solution, the code I wrote is substantially shorter than that code -- I think this is primarily because Prof. Ben added all sorts of conveniences/syntactic sugar that would have made the code nicer and more extensible should the assignment have been taken further, but not necessarily anything that was required in order to complete the assignments in the task.

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

I like this design a lot because it allows you to define new terms very easily. For example, adding "Concat" and plus required a total of five or so lines of code (including the given "case class" line written by Ben), plus some logic code in RegexMatcher which seemed to not be super substantial as well. Thus it seems that the design is _extensible_.

And really, in terms of the design of decribing regular expressions, I genuinely feel this design does quite well. I suppose non regular expressions wouldn't do well here, such as, say, a string with an odd or prime number of a certain pattern, but then again, that's not the intended functionality of the system.

I'm also struggling to find major syntatic issues. For example, I think <+> and <*> could be + and *, and then some sort of implicit pattern matching would be necessary perhaps, but this is trule a minor thing. I think maybe EMPTY and EPSILON can become unicode values, as is the case in the sample solution, but again this is a small and insignificant change in the long run. I think maybe one thing that would be nice is a way for a user to define a pattern and then use it repeatedly, or have regex's in regex's be a non-issue. I'm not actually sure if that works in the current design, but I could imagine for "larger scale regex problems" (whatever that means) it could be useful.
