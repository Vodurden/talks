# Why bother with anything

Programming: Solve problems, have fun

Imperative: Solve problems more easily?

Object Oriented Programming: Solve problems more easily?

Algorithms: Solve problems more easily, solve problems you couldn't solve before?

Design patterns: Solve problems more easily?

Functional Programming: Solve problems more easily?


What's common?

- Solve problems more easily
- Solve new problems
- Have fun

# Learning FP:
## Part 1

Examples of code encountered in beginner tutorials:

    // Purity
    def add(a: Int, b: Int): Int = {
      a + b
    }

    // Recursion
    def sum(n: Int): Int = {
      if(n == 0) {
        0
      } else {
        n + sum(n - 1)
      }
    }

    sum(4) // Returns 10!


    // Currying!
    val add = (a: Int) => {
      (b: Int) => {
        a + b
      }
    }

    add(1)(2) // Returns 3! Cool!


    // List processing:
    val numbers = [1, 1, 1]

    numbers.map(i => i + 1).sum // Returns 6! Cool!

## Part 2

Back to reality:

    // Making decisions & causing side effects:
    def makeLotsOfMoney(database: DbConnection): Unit = {
      val monies: Array[Float] = database.getMoney()

      val isThereEnoughMoney = true
      monies.foreach { money =>
        if(money < 1000000) {
          isThereEnoughMoney = false
        }
      }

      if(!isThereEnoughMoney && someBusinessThing() && !globalVariable) {
        doSomethingWithAWS()
      } else {
        globalVariable = true

        log.error("Something bad happened but it's been happening for years so don't worry!")

        doSomethingElseWithAWS()
      }
    }


They look completely different!

# Why bother:

All the stuff we talk about in FP is because we think pure functions are awesome.

A brief and not 100% correct definition of pure functions:

- Behavior should only depend on it's parameters. (No referencing variables other then it's arguments)
- Output should only be provided by returning. (No mutating your inputs)
- Should not perform any visible effect (writing to logs, writings to the user, drawing things, talking to the network, talking to the file system)
- If you were watching the programs activity with a tracing tool the state of the program shouldn't change because of a pure function


Impure example:

    // Games in the datbase: ["Dwarf Fortress", "Duke Nukem Forever", "Prey", "E.T."]
    var gameDatabase = gameServer.getGameDatabase

    // Impure
    def filterBadGames(): Unit = {
      gameDatabase.remove("Duke Nukem Forever")
      gameDatabase.remove("E.T.")
    }

    filterBadGames()
    println(gameDatabase) // ["Dwarf Fortress", "Prey"]!

Pure example:

    // Games in the datbase: ["Dwarf Fortress", "Duke Nukem Forever", "Prey", "E.T."]
    val gameDatabase = gameServer.getGameDatabase

    // Pure
    def filterBadGames(games: Array[String]): Array[String] = {
      games
        .filter(_ ==  "Duke Nukem Forever")
        .filter(_ == "E.T.")
    }

    val goodGames = filterBadGames(gameDatabase)
    println(goodGames) // ["Dwarf Fortress", "Prey"]


Benifits:

- Super easy to test
- Super portable
- Everything you need to know in one place

Drawbacks:

- Can't do anything!


Functional programming is about making as much of your program as possible out of pure functions


Things of note if you accept pure functions as your lord and saviour:

- Immutability becomes an obvious choice
- Objects start to look a lot less useful
- Since you've got lots of pure functions you need new techniques to manage them

# Getting stuff done

If pure functions are so great how do you actually write real _pragmatic_ programs with them!

Interpreter Pattern!

Separate the "What to do" from the "How to do it"

    def decideHowToMakeLotsOfMoney: Script[(GetMoney, LogError, SetGlobalVariable, DoSomething, DoSomethingElse)] = {
      val monies: Array[Float] = GetMoney()

      val isThereEnoughMoney = true
      monies.foreach { money =>
        if(money < 1000000) {
          isThereEnoughMoney = false
        }
      }

      if(!isThereEnoughMoney && someBusinessThing() && !globalVariable) {
        DoSomething()
      } else {
        SetGlobalVariable(true)

        LogError("Something bad happened but it's been happening for years so don't worry!")

        DoSomethingElse()
      }
    }


    def makeLotsOfMoney = {
      val script = decideHowToMakeLotsOfMoney()

      script.withGetMoney(() => database.getMoney)
        .withLogError(text => log.error(text))
        .withSetGlobalVariable(value => globalVariable = value)
        .withDoSomething(() => doSomethingWithAws())
        .withDoSomethingElse(() => doSomethingElseWithAws())
        .run
    }

# But what about the real world!
## Part 1
- React!

React example:

    // index.html:
    <h1>React application:</h1>
    <div id="root">
     <h2>Hello, jake</h2>
    </div>

    // index.js
    class MyCoolComponent extends React.Component {
      render() {
        return <h2>Hello, {this.props.name}</h1>;
      }
    }

    const reactApp = React.createElement(MyCoolComponent, { name: 'jake' }, null)

    ReactDOM.render(reactApp, document.getElementById('root'));

## Part 2

- The above is actually secretly a functional program!
- Yep: The most popular framework in the most popular language in the world is secretly a functional programming ninja

Check it:


    // index.html:
    <h1>React application:</h1>
    <div id="root"></div>

    // index.js
    const MyCoolComponent = (props) => {
      return <h2>Hello, {this.props.name}</h2>;
    }

    const reactApp = React.createElement(MyCoolComponent, { name: 'jake' }, null)

    ReactDOM.render(reactApp, document.getElementById('root'));


React components are just a function from props to DOM!


What does this have to do with the interpreter pattern we were talking about before?

## Part 3

The DOM is our script! We construct a description of how to render the page, and the browser acts as our interpreter and makes it all work.

That's right, if you've done any React you were secretly a functional programmer all along!

# Wrapping it all up:

Why do we bother with fp?

- Make as much of our program out of pure functions as possible. Because we think they're simpler

I.e.:

Functors: Makes it easier to apply functions to different types of containers

Applicatives: Makes it easier to apply functions in paralell

Monads: Makes it easier to apply functions in sequence

Typeclasses: Makes it easier to pick which function to apply to some data

Free/Eff: Makes it easier to implement the interpreter pattern

Fold: Makes it easier to use functions for sequence transformation

Traverse: Makes it easier to use functions on nested data structures

Immutability: Makes it possible to write pure functions


- Talk about my own Challenges

- Final word: You don't need to start with monads/functors/applicatives. Just look for opportunities to write pure functions and see how they make you feel :)

# Bonus section:

Functors

    // Function
    val plusOne = (i: Int) => i + 1

    // Things that can be treated as a Functor
    val list: List[Int] = List(1, 2, 3, 4, 5)
    val option: Option[Int] = Some(5)
    val future: Future[Int] = Future.successful(2000)

    // Using things as a functor: Applying `plusOne` to different things
    Functor.map(list, plusOne)
    list.map(plusOne)
    option.map(plusOne)
    future.map(plusOne)

Traverse

    // Function that adds " is great" to a string
    val isGreat = (s: String) => s + " is great"

    val maybeGames: List[Option[String]] = List(None, Some("Dwarf Fortress"), Some("Void Expanse"), None, None)
    val maybeGamesTraversed: Option[List[String]] = maybeGames.traverse(isGreat) // Applying a function to nested data types (Option nested in List)

    println(maybeGamesTraversed) // None

    val getGameAsync: Future[String] = ...
    val moreGames: List[Future[String]] = List(getGameAsync("Dwarf Fortress"), getGameAsync("Void Expanse"), getGameAsync("Black Desert Online"))
    val moreGamesTraversed: Future[List[String]] = maybeGames.traverse(isGreat) // Applying a function to nested data types (Future nested in List)

    println(moreGamesTraversed) // Future(List("Dwarf Fortress is great", "Void Expanse is great", "Black Desert Online is great"))

Typeclasses

    typeclass Stringify {
      def toString()
    }

    def myFunctionUsingStringify(s: Stringify) = {
      s.toString()
    }


    case class NameHolder(name: String)

    NameHolder implements Stringify {
      def toString(nameHolder: NameHolder) = {
        nameHolder.name
      }
    }

    val nameHolder = NameHolder("jake")
    val anotherNameHolder = NameHolder("someone else")

    myFunctionUsingStringify(nameHolder)
