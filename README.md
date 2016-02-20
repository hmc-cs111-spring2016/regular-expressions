[EmailRegex]: http://emailregex.com/
[Unicode]: https://en.wikipedia.org/wiki/Plane_(Unicode)#Basic_Multilingual_Plane
[Kleene]: https://en.wikipedia.org/wiki/Kleene_star
[Teams]: https://github.com/hmc-cs111-spring2016/regular-expressions/wiki/Peer-review


# Implementing an internal DSL: regular expressions

## Overview

In this assignment, you'll use the techniques we've learned in class to
implement an internal DSL. This internal DSL allows users to easily check
whether a given string matches a pattern. For example, imagine a user wants to
check whether a string `s` takes the form of a letter, followed by a number
(e.g., `c3`). In your DSL, the user might write:

```
val pattern = letter ~ number
pattern matches s
```

Patterns like this are called _regular expressions_. Before you start
implementing a DSL for the domain of regular expressions, you should first read
the next section, which describes everything you'll need to know about this
domain.

## Background: regular expressions

A regular expression is a pattern—a shorthand for describing sets of strings.
Every string that matches a regular expression is said to be a member of the set
described by that language. As an informal example, the pattern `watch(es)`
describes a set of two strings: `{watch, watches}`. Similarly, the pattern
`colo(u)r` also contains two strings: `{color, colour}`. Neither of these
informal patterns is a regular expression in the formal sense, but they give a
sense of how a pattern can stand in for a set of strings.

The most precise definition of regular expressions is a formal one, i.e., one
that's based on math. That's the definition we're most interested in because
it's easy to translate it into code. Here we go...

We start with an **alphabet**, which is a set of characters. For this
assignment, our alphabet will be any character that can appear in a
Scala string, i.e., anything that the Scala compiler will let us put between two
quotes like so: `'a'`.

A **string** _s_ is a sequence of characters `c1⋅⋅⋅cn`; this string has length
n. There is a special string called the **null** string, spelled ε; this word
has length 0. 

A **language** _L_ is a set of strings. 

Given an alphabet, the **regular languages** “over that alphabet” can be 
defined as follows:

   + The empty language ∅ is regular. This language contains 0 strings.
   + The language {ε} that contains one string—the null string—is regular.
   + If c is a character, then the language {c} that contains one string is
   regular.
   + If L1 and L2 are regular languages (i.e., they're each sets of strings),
   then the language L1 ∪ L2 (i.e., the union of the two languages) is regular.
   + If L1 and L2 are regular, then the language L1 ⋅ L2  = {s1 ⋅ s2 | s1 ∈ L1 ∧
   s2 ∈ L2} is regular. Here, the symbol ⋅ means "concatenation". So s1 ⋅ s2 is
   all the characters in s1, followed by all the characters in s2.
   + If L is regular, then L* is regular. Here, the symbol * means "0 or more
   repetitions of any string in L". So, if L is the language that contains the
   single string `ab`, then L* is {ε, ab, abab, ababab, …}.

A **regular expression** is a short-hand description for a regular language. For
example, `(ab)*` is a regular expression that describes the infinitely large set
of strings `{ab}*`. The regular expression `42` describes the language `{42}`,
which contains a single string. The regular expression `4 || 2` describes the
language `{4, 2}`, which contains two strings. 

Given a regular expression and a candidate string, there are many ways to
determine whether the string **matches** the expression, i.e., whether the
string is in the set described by the expression. In this assignment, you don't
have to worry about implementing a matching algorithm—it's been provided for
you. Your job is to implement the _syntax_ of regular expressions: an easy way
for users to describe them.

## Implementing a DSL for regular expressions

**Note:** Be sure to read the entire assignment before you start implementing!

This repository contains some starter code. The most important pieces are:

**`RegularExpression.scala`** This file defines data structures that correspond
to the different kinds of regular languages described above. You'll modify this
file to write your DSL.

**`Program.scala`** A program that uses the data structures in
`RegularExpresion.scala` to build descriptions of regular expressions, then
match strings against those expressions. This program compiles and runs
correctly as-is, but it's clunky: it uses the data structures from the
regular expression library but it's not very DSL-y. Your job is to modify this
file to use your DSL instead.

There are a couple of other pieces of code provided:

**`RegexMatcher.scala`** This file defines an algorithm for matching a string
against a regular expression. You won't need to modify this file.

**`src/test/scala/dsls/regex`** Some test files for the matching algorithm. You
won't need to modify these files.

### The syntax for your DSL

The user of your DSL eventually should be able to write the following pattern:

```
"42" || ( ('a'*) ~ ('b'+) ~ ('c'{3}))
```

which matches either:
   + the literal string `"42"`   _or_
   + any string that has zero or more 'a's, followed by one or more 'b's,
   followed by exactly three 'c's

To do so, you'll need to:
   + implement literal extension, so that Scala strings can be treated as
   regular expressions
   + implement literal extension, so that Scala characters can be treated as
   regular expressions
   + implement the binary operator `||`, which corresponds to the union operation
   + implement the binary operator `~`, which corresponds to the concatenation
   operation
   + implement the postfix operator `*`, which corresponds to the Kleene star operation
   + implement the postfix operator `+`, which means "one or more repetitions of the
   preceding pattern"
   + implement the repetition operator `{n}` which means "`n` repetitions of the
   preceding pattern"

## Reflecting on design / implementation

In the file `reflection.md`, you'll write about:
   + Your process of implementing the DSL
   + Your thoughts about the design of this DSL

I highly recommend that you **work on this part as you go.** In other words,
treat it as a running diary. Take a moment now to look at the questions in that
file, then update the file as you work on the assignment. Although you might
treat the file as a running diary, the version you submit should _not_ be a
stream of consciousness. Make your final submission clear and concise.

## Grading
Good responses (i.e., responses that receive a 3) will:

   + modify `RegularExpression.scala` to implement the internal DSL
   + transform `Program.scala` to work with the DSL
   + respond to the questions in `reflection.md`

Great responses (i.e., responses that receive a 4) will additionally:

   + provide very well-documented code in `RegularExpression.scala`.
   + deeply engage in the reflection process, e.g., by discussing the benefits
   and drawbacks of every operation in the language, from both the implementer's
   and user's perspective

# Critiques

After the submission deadline, read over and comment on your 
[critique partners'][Teams] work. Be sure to comment on their code _and_ on
their reflection.

Here are some suggestions for critiques:

   + What do you like about their implementation?
   + Is there anything that could be done to improve their implementation?
   + Did they implement things in the same or in different ways as you?
   + With which aspects of their reflection do you agree? With which aspects do
   you disagree?

## Tasks
- [ ] Read the assignment.
- [ ] Implement literal extension for characters.
- [ ] Implement literal extension for strings.
- [ ] Implement the binary operator `||`.
- [ ] Implement the binary operator `~`.
- [ ] Implement the postfix operator `*`.
- [ ] Implement the postfix operator `+`.
- [ ] Implement the repetition operator `{n}`.
- [ ] Write responses in `reflection.md`.
- [ ] Submit your work.
- [ ] Comment on your [critique partners'][Teams] work.
