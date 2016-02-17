[EmailRegex]: http://emailregex.com/
[Unicode]: https://en.wikipedia.org/wiki/Plane_(Unicode)#Basic_Multilingual_Plane
[Kleene]: https://en.wikipedia.org/wiki/Kleene_star

# Implementing an internal DSL: regular expressions

## Overview

In this assignment, you'll use the techniques we've learned in class to
implement an internal DSL. This internal DSL allows users to easily check
whether a given string matches a pattern. For example, imagine a user wants to
check whether a string `s` is a letter, followed by a number. 
In your DSL, the user might write:

```
val pattern = letter <~> number
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
assignment, we'll take the alphabet to be any character that can appear in a
Scala string, i.e., [Unicode] characters.

A **word** _w_ is a sequence of characters `c1⋅⋅⋅cn`; this word has length n.
There is a special word called the **null** word, spelled ε; this word has
length 0. We use the terms word and **string** interchangeably.

A **language** _L_ is a set of words. 

Given an alphabet, the set of **regular languages** “over that alphabet” can be 
defined inductively as follows:

   + The empty language ∅ is regular. This language contains 0 words.
   + The language {ε} that contains the one string—the null word—is regular.
   + If c is a character, then the language {c} that contains one string is
   regular.
   + If L1 and L2 are regular languages (i.e., they're each sets of words), then
   the language L1 ∪ L2 is regular.
   + If L1 and L2 are regular, then the language L1 ⋅ L2  = {w1 ⋅ w2 | w1 ∈ L1 ∧
   w2 ∈ L2} is regular. Here, the symbol ⋅ means "concatenation". So w1 ⋅ w2 is
   all the characters in w1, followed by all the characters in w2.
   + If L is regular, then L* is regular. Here, the symbol * means "0 or more
   repetitions of any word in L". So, if L is the language that contains the
   single string `ab`, then L* is {ε, ab, abab, ababa, …}.

A **regular expression** is a short-hand description for a regular language. For
example, `ab*` is a regular expression that describes the infinitely large set
of words defined by the language `{ab}*`. The regular expression `12`
describes the language `{12}`, which contains a single word. The regular
expression `1 | 2` describes the language `{1, 2}`. 

Given a regular expression and a candidate word there are many ways to determine
whether the word **matches** the expression, i.e., whether the word is in the
set of words described by the expression. In this assignment, you don't have to
worry about implementing a matching algorithm—it's been provided for you. Your
job is to implement the _syntax_ of regular expressions: an easy way for users
to describe regular expressions.

## Checklist


## A running diary 

Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nobis eos aliquid sint
quasi aut iste. Numquam, dignissimos provident. Ea qui perferendis delectus et,
dolor voluptates quam cumque iure possimus quas.


## Advice

### Implementation techniques

As a brief summary, here are some things about Scala that may be useful to know.
This information was compiled from Chapter 11 of _Scala for the Impatient_;
check there for much more detailed information

#### Identifiers
A valid _identifier_ (i.e., name) can include the following characters:
   + any ASCII character _except_ `(`, `)`, `[`, `]`, `{`, `}`, `.`, `,`, `;`,
     `'` or `"`.
   + Unicode characters from the 
    [Sm](http://www.fileformat.info/info/unicode/category/Sm/list.htm) and 
    [So](http://www.fileformat.info/info/unicode/category/So/list.htm) categories.


#### Precedence and Associativity

_Precedence_ determines how to decide which of two _different_ operations to
perform first.

_Associativity_ determines how to decide which of two applications of the _same_
operation to perform first.

In Scala, the _first_ character of an operator's name determines its precedence, 
in increasing order as follows:

```
(all letters)
|
^
&
< >
= !
:
+ -
* / %
(all other special characters)
````

So, `*` has higher precedence than `+`, etc. Unsurprisingly, there are a few
caveats:
   1. Assignment has lower precedence than any other operations
   1. Postfix operators have lower precedence than infix operators

In Scala, the _last_ character of an operator's name determines its
associativity, according to these rules:
   1. Operators that end with a colon `:` are right-associative
   1. Assignment is right-associative
   1. Every other operator is left-associative

## Peer-review another person's work

