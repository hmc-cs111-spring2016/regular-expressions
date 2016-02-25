# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
I think the conversion from `Char` to `RegularExpression` was pretty easy once I
remembered the syntax for implicit conversions. 

After I knew this, creating and implicit conversion from a string also seemed
pretty easy if I just used the `Concat RegularExpression` object on `Literals`. 
I wanted to use a foldl however on the string and that took me some time
to get the right syntax. I kept getting a type error because my first argument
to `foldl` was an `EPSILON` but overall it returned a `Concat`. I decided to 
look at the sample solutions and found that if I specified both of them as 
`RegularExpressions`, the types would check out.

Creating the new `Union` operator was very easy. Just a single line.

Creating the new `Concat` operator was similarly easy.

Creating the new `Star` operator was similarly easy.

## Which operators were most difficult to implement and why?

Implementing the repitition operator was pretty difficult. I think the 
definition was easy enough, just `Concat` the specified `Literal` n times. 
However, I could not figure out how to make the syntax work for a while. 
`'a'{3}` to me looks like a function called `'a'` is being called with one 
argument, `3`. At first I thought this couldn't be what is happening because 
of course `'a'` is a `Char` or `RegularExpression` and not a function. After
I did some searching around on StackOverflow however, I realized that this is 
exactly what is happening. An object can be used as a function as long as it has 
an apply method. I thought that this was incredibly cool.

## Comment on the design of this internal DSL
It seems pretty easy to define any arbitrary regex with this DSL. One thing that
is difficult however is to define a range such as all digits or all numbers. One
solution is to just define a val digit as is done in the code and then reuse 
that variable throughout, but what if you want to match any number 0-7 or
another variation? That would be a lot of needless definitions. 

I think one thing that most regex parsers have is a range such as [0-5]+ would 
match one or more numbers from 0-5. To implement this, I would probably define
a function `-` that took in two ints or two characters and returned a Union of
all the numbers in that range. 

