# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
Union was _very_ easy to implement once I was able to understand that it needed to be in the abstract class and not the companion object (I don't know why this is the case but this solved the issue). It gave me a feel of how removing Scala-ness actually works, and (in this case) was fairly straightforward.

Concat was very similar to Union which made it quite simple!

## Which operators were most difficult to implement and why?
I may be conflating char/string literals with the basic work of getting all the imports right, but these to actually took me a long time. This was difficult for me also because my functional programming isn't great, so I spent a while on string literals trying to use foldRight instead of reduce, and forgot a few other small Scala things. But once I got the idea of how to turn a string into a list of char literals (which were interpreted by Scala, I think, as RegEx's), the rest became easier.

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