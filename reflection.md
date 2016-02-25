# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The easiest operators to implement by far were the ones that literally called methods that existed as case classes without any modification (so basically union, concat, and star). The other operators (besides the repetition, which I'll talk about later) either involved looking something up (like for the implicit conversions, I had to look up the syntax) or some sort of conditional or recursion. These weren't particularly difficult either, but not entirely trivial. I think this is mostly just the result of what case classes we were given. If we were given more (or different) case classes already implemented, different methods would have been easier. 
## Which operators were most difficult to implement and why?

By far, the hardest operator to implement was the last one. I had absolutely no idea how to get a method with this syntax, so I basically spent a long time thinking about what could possibly give me the syntax that I needed for that. Some of the thought processes I had involved thinking about how ScalaTest works (e.g. "scala objects should" {blah}), and thinking about default methods. This led to factory methods, which up until this point, I had only seen used for companion objects. I basically just had to mess around with things until it turned out you can put factory methods in the main class, and that just ended up working. 

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

This design is fundamentally good, because its how regular expressions are conventionally written. People don't write them as methods (concat(union literal(a) literal(b)), etc.), they write them out in one line. Thus, having these methods as infix operators or unary operators lets people write regexes much more naturally, so it should lead to fewer silly mistakes and general ease of use.

However, there are still a few issues with the syntax that are unnatural. The use of angle brackets around the star and plus operator signs is unusual, and could make things with lots of stars and plusses harder to read, and more awkward to write out. Additionally, the concatenation operator is more usually written just as simple concatenation without a symbol. While the syntax that exists is much better than the method syntax, it could be more natural

In terms of fixing these issues, I'm not sure what can be done. I think the biggest thing is to override the * and + somehow, instead of calling them with angle brackets. Then, calling them as postfix operators would make this a lot more natural. Similarly, with concatenation, I'm not sure if its possible to have 2 different factory methods, but if it is, then we can make two different factory methods, one for the repetition operator, and one that takes in another regular expression, and returns the concatenation of the two. This would fix all of the problems I mentioned in the section above. 