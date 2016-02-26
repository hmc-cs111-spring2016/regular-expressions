# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The || and ~ operators were the easieast for me to implement. The Union and Concat methods clearly already did exactly what these operators were supposed to do so I only needed to define these operators to call those methods to give them a sort of "wrapper" so that the user could call Union or Concat in a more intuitive manner. Implementing these operators also seemed to directly correspond to the way we implemented methods for our DSL in class so it was easy to pattern match with the + operator for complex numbers to understand the required syntax.

## Which operators were most difficult to implement and why?

The repetition opperator seemed very confusing to me. I wasn't sure how to even start because I couldn't figure out what I was defining. I finally glanced as the sample solution and realized I could use the word "apply", although I am still not entirely sure I understand when, where and how that can and should be used.


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


   This design seems to work very welll. It allows the user to write things like 2 || 4 in a very natural way, similar to how one would write it as a mathematical expression. The repetition opperator also gives a very natural feeling. It is easy to see what both expressions mean even for those who are not computer science experts but are just familiar with regular expressions. 

   The other operators feel fairly natural, but it is a little difficult, at least for me, to remember what each one does. They all look very similar. While I can guess what 2 || 4 means, guessing the difference between 2<+> and 2<*> would be difficult. Because they are so similar, it may be easy for users of our DSL to confuse them and therefore write code that doesn't work like they think it does. Because they are actually very similar functions, they might not even immediantly realize that their code isn't working correctly, especially if they aren't strong programmers using good test cases for all their functions. This could lead to code which is very difficult to debug and very frusterating for our users.

   The angular brackets around + and * seem a little annoying and unnatural. The first time I ran my code after implementing these methods, it didn't work correctly because I had actually left of < > by accident. However, it seems very dangerous to override such commonly used expressions as + and *, because our users may want to use them in the normal way to add or multiply some values in their code. The code would be extremely difficult to debug, because it would look perfectly natural and correct and even if they found the bug it would be difficult and annoying for them to find another way to do normal addition or multiplication since we overroad this method. The angular brackets may therefore be the best solution, just requiring a little getting used to .