# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

Everything except for strings and repetition was pretty much trivial to implement because they could be done with just one short line of code, after figuring out the tricks. In particular, I figured out *, +, || and ~ pretty quickly.

## Which operators were most difficult to implement and why?

Implicit conversion from chars was a bit tricky to figure out, because I had to realize that it went in the companion object. It was also tricky to realize that I had to import it directly (which I figured out with Amit's help). Strings were also slightly tricky just because it was recursive but really it wasn't hard. The hardest was probably repetition but even that was just a little bit of thinking until I realized that I could use apply.

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

It's a fine design for regexes. It makes describing a regex easy. Because of that anything that you want to describe as a regex is easy to say, so things that are patterned data are easy to describe. It would be useful if you need to check a list of strings to see which are phone numbers, which are license plate numbers, which consist solely of numbers and lowercase letters, etc.

It's not useful for things that can't be described as regular expressions or are difficult to describe. It's bad if you want to check if a string matches a specific set of strings (or at least, not especially useful). It's bad if you want to describe a language that isn't regular (like the set of all palindromic strings or of prime-length strings). I don't think these are problems with the design. The design of regular expressions is strictly supposed to capture regular languages. If it captured other things, they wouldn't be implementable as efficiently. 

There are other regular expression 'helper functions' that make things easier to express, that might be nice to implement. For instance, _ for a regular expression that matches any string of length one. I believe this is acceptable in scala, but it might break some other things. Underscores are used for singletons (like in anonymous functions) but I don't think it would ever be prolematic, because singleton use wouldn't overlap with where we would use the name. The best way to implement it would be in the same way as EMPTY and EPSILON, because it's a special case like them. This would also require modifying the matcher to include this case in the matching. I don't think _ could be implemented using any combination of the existing methods, at least not in any way I can think of.

In terms of the existing syntax it's all pretty fine. <+> and <*> could potentially be turned into + and *. I think this would be allowed unless there's some special case with those names. It might have conflicts, however. If + is string concatenation, then if we wrote "x" + "y" the compiler wouldn't look for an implicit conversion so we wouldn't get the regex we want, we would get the string "xy" (which could then function as the same regex with another implicit conversion). I'm not sure if there are any cases where this wouldn't work, but it's an interesting thought experiment.

||, ~ and {n} are also fine if those are the operators people are familiar with. If you wanted to make a better language than this for regexes I think the only real way to do so other than adding other features would be to just ask people who use regular expressions which operators they're used to, or possibly give them multiple options. 
