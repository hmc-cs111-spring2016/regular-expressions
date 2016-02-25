# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
I found all of the operators pretty easy to implement, but I guess the * and + operators were the easiest. * was of course easy because all it took was calling the Star constructor. + was nearly as easy since its definition wasn't much more complex and was entirely in terms of other operators.

## Which operators were most difficult to implement and why?
The ~ and || were somewhat more difficult, by which I mean that I spent more time figuring out how to make them work and not that I had to write significantly more complex code for them. For reasons I'm not entirely sure of (I guess it's because of the pattern matching?) it was not possible to use pattern matching on a Union or Concat object created with the operator when the definition looked like
```Scala
def ||(other:RegularExpression) = Union(this, other)
```
I looked at the sample solution to see why this might not be working, and found "unapply" methods. After reading about them online, I figured out what they do but wouldn't have thought to use them so I wanted to see if I could allow pattern matching in a different way. For reasons I'm completely not sure of (and this might be super hacky and I shouldn't have done it), specifying the return type of these operators as `RegularExpression` made the pattern matching work without
extractor methods.

## Comment on the design of this internal DSL
Now that I've got the whole thing working, it seems to have a lot of the features you'd want in a DSL. From a strictly theoretical point of view, you can write any regular expression you could want. All the operators are there, and represented by no more than three characters, too (unless you count the number of characters in the number _n_ you supply to the repetition operator). 

However, the formal mathematical definition of regular expressions doesn't have wildcard characters so far as I can recall, and neither does this DSL. It _is_ possible to get a wildcard expression by taking the union of all characters that could possibly be matched against, but this is _really_ not practical for someone using the DSL. 

It might be nice if the language had some kind of wildcard object (just like there's an empty regex object). It probably couldn't be called "." since, I'm pretty sure, that's against the rules for identifiers in Scala. You could still call it something like "WILD", or use a
unicode character, and just that would make it much easier to use wildcards.

