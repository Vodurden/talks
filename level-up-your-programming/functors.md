Power up - Functors

# Why

See `fp-why-bother`: We want to be able to use pure functions on more things

# How

Functors level up your functions so you can use them on more things.

## Consider a normal function:

    val plusFive: (i: Int) => i + 5

## What types of data can you use it on?

Integers!

    val someInteger = 2
    plusFive(someInteger) // Returns 7

## What about other types of data?

### Optional

An Optional represents a value that may or may not exist. You can read `Option[Int]` as "An Int that may or may not exist".

Can we call `plusFive` on an `Option[Int]`?

    val someOptionalInteger: Option[Int] = Some(5)
    plusFive(someOptionalInteger) // Doesn't work! plusFive needs an `Int` not an `Option[Int]`

We can write another version of `plusFive` that works on options:

    def plusFiveOnOption(optionalInt: Option[Int]): Option[Int] = {
      optionalInt match {
        case Some(i: Int) => i + 5
        case None => None
      }

    }
    val someOptionalInteger: Option[Int] = Some(7)
    plusFiveOnOption(someOptionalInteger) // Returns Some(13)

    val someOptionalIntegerThatDoesntExist = None
    plusFiveOnOption(someOptionalIntegerThatDoesntExist) // Returns None

### Future

How about `Future[Int]` which represents a "Int that is computed asynchronously"?

    val someAsyncInteger: Future[Int] = Future.succesful(100)
    plusFive(someOptionalInteger) // Doesn't work! plusFive needs an `Int` not a `Future[Int]`

We can do it again: Write another version of `plusFive` that works on futures:

    val plusFiveOnFuture: (futureInt: Future[Int]) => futureInt match {
      case FinishedFuture(i: Int) => i + 5
      case StillRunningFuture() => SomeAsyncMagic()
      case FailedFuture(error: Error) => FailedFuture(error)
    }

    val someFinishedFuture: Future[Int] = Future.successful(11)
    plusFiveOnFuture(someFinishedFuture) // Returns FinishedFuture(16)

    val someStillRunningFuture: Future[Int] = Future.doSomeAsyncMagicAndReturn(77)
    plusFiveOnFuture(someStillRunningFuture) // Returns StillRunningFuture() that will eventually do the plusFive using the async magic

    val someFailedFuture: Future[Int] = Future.failed("I failed :(")
    plusFiveOnFuture(someFailedFuture)

### List

Maybe a `List[Int]` which represents "a collection of Integers"?

    val someListOfInt: List[Int] = List(5, 10, 15, 20, 25)
    plusFive(someListOfInt) // Doesn't work! plusFive needs an `Int` not a `List[Int]`


But we can do it again!: `plusFive` that works on `List`s:

    def plusFiveOnList(ints: List[Int]): List[Int] = {
      // I'm cheating and using a mutable value! You could do this with
      // recursion but it's not important for the talk
      var newList = List()
      foreach(val int in ints) {
        newList.append(int + 5)
      }
      newList
    }

    val emptyList = List()
    plusFiveOnList(emptyList) // Returns List()

    val someNumbers = List(5, 6, 7, 10)
    plusFiveOnList(someNumbers) // Returns List(10, 11, 12, 15)

### What do they all have in common?

`Option[Int]`, `Future[Int]` and `List[Int]` all have the same shape: `Something[Int]`

`plusFiveOnOption`, `plusFiveOnFuture` and `plusFiveOnList` all had some way of getting an `Int` out of the wrapper type:

`plusFiveOnOption` knows how to get an `Int` from an `Option[Int]`
`plusFiveOnFuture` knows how to get an `Int` from a `Future[Int]`
`plusFiveOnList` knows how to get an `Int` from a `List[Int]`

All of our `plusFive` methods knew how to handle cases where an `Int` wasn't available

All of our `plusFive` methods had the same shape: `Something[Int] => Something[Int]`


## The punchline

`Functor` let's you go from `plusFive` to `plusFiveOnSomething` *automatically*

Check it out:

    val plusFive = (i: Int) => i + 5

    // Option
    val plusFiveOnOption = Functor[Int].lift(plusFive)

    val someOption = Some(7)
    plusFiveOnOption(someOption) // Some(12)

    // Future
    val plusFiveOnFuture = Functor[Future].lift(plusFive)

    val someFuture = Future.successful(20)
    plusFiveOnFuture(someFuture) // FinishedFuture(25)

    // List
    val plusFiveOnList = Functor[List].lift(plusFive)
    val someList = List(6, 7, 8)
    plusFiveOnList(someList) // List(11, 12, 13)

It *levels up your functions*

## The real punchline

Because this is so useful there's even a handy helper method to let you apply `plusFive` directly without calling `lift`.

That helper's name is `map`:

    val plusFive = (i: Int) => i + 5

    // Option
    val someOption = Some(7)
    someOption.map(plusFive) // Some 12

    // Future
    val someFuture = Future.successful(20)
    someFuture.map(plusFive) // FinishedFuture(25)

    // List
    val someList = List(6, 7, 8)
    someList.map(plusFive) // List(11, 12, 13)



# Bringing it all together

- Cool notes about how most function data types will expose a `Functor` interface.
-- "You only need to learn it once"
