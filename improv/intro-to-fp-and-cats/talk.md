# Functional - what and why

- Pure functions are awesome
- Everything else is about getting more pure functionsand making pure functions more ergonomic

    def add(a: Int, b: Int): Int = {
      a + b
    }

# Cats

Gives us tools to work with pure functions

## Functional data types


## Typeclasses

Like interfaces but you can have them for types you didn't define.

- Functor
- Foldable
- Applicative
- Monad
- Monoid

class List extends Iterable {
  def iterate() = {
    ...
  }
}

class List {
...
}

Iterable[List] {
  def iterate() = {
  }
}

## Syntax extension

Adds useful utilities to existing types like Either and Option

Either[String, Int]

Also makes it easier to access Typeclass methods

-- Functional data types
-- Typeclasses
-- Syntax extension

# Examples

## Foldable, Monoid

List(1, 2, 3, 4) == 10

Imagine a `count` function

    val l = List(1, 2, 3)
    var count = 0
    foreach(val item in l) {
      count += item
    }
    count

    def count(ints: List[Int]): Int = {
      a.foldLeft(0)((acc, a) => acc + a)
    }













We actually don't care about the fact the numbers are in a `List` we just need something that has a `fold` method:

    def count(ints: SomeFoldable[Int]): Int = {
      ints.fold(0)(a => a + a)
    }


















Turns out this is a Typeclass provided by cats called `Foldable` which can be used like this:

    count(List[Int](1, 2))
    count(Set[Int](1, 2))

    def count[F[_]: Foldable](ints: F[Int]): Int = {
      ints.foldLeft(0)((acc, a) => acc + a)
    }




















Now we can use `count` on anything has can be folded over.

There's actually another functional pattern here: Currently this only works on `Int` but really we should be able to count anything we can accumulate. This is called a `Monoid`.

A `Monoid` definition gives you two things:

- A function to combine two things into the same thing. For example "+" combines two `Int` into one `Int`
- A value when combined with something else doesn't change the something else. For example `0` when used with `+` doesn't change the other number.

0, 1, 2, 3

a + 0 = a
a * 1 = a

This is a great fit for our `count` function. Since we need an initial value and a way of combining two values! It would look something like this:

    count(List(1, 2, 3))

    def count[A: Monoid, F[A]: Foldable](values: F[A]): A = {
      values.foldLeft(Monoid[A].empty)((acc, a) => Monoid[A].combine(acc, a))
    }

    def count(values: F[A])(implicit aMonoid: Monoid[A], implicit foldable: Foldable[F[A]])

Now this function works for a large number of types. Let's try the basic cases:

    count(List(1, 2, 3)) == 6
    count(Set(5, 5)) == 5
    count(List(List(5, 5, 5), List(2, 2, 2))) == List(5, 5, 5, 2, 2, 2)

But it also works for other combinable things:

    count(List("ab", "cd")) == "abcd"
    count(List(Option(1), Option(2))) == Option(3)

What if we didn't want to count things. Maybe instead we want to multiply the numbers, we can do that by changing the Monoid to combine `Ints` differently:

    val defaultListFoldable = implicitly[Foldable[List]]

    val intPlusMonoid = new Monoid[Int] {
      def empty: Int = 0
      def combine(a: Int, b: Int) = a + b
    }

    val intMultiplyMonoid = new Monoid[Int] {
      def empty: Int = 1
      def combine(a: Int, b: Int) = a * b
    }

    count(List(1, 2, 3))(intMultiplyMonoid, defaultListFoldable)

At this point we could rename the function to `combine` as it actually does more then counting.


    val list = List(1,2,3,4)
    list.foldLeft("")((s, number) => s + number.toString())

    list.map(n => n.toString()).combine()

    import cats._
    import cats.implciits._


    sealed class Foobar {
      implicit val intMonoid = new Monoid[Int] {
        def empty: Int = 1
        def combine(a: Int, b: Int) = a * b
      }
    }
    class Foobarbar extends Foobar

    def myMethodUsingCount(): Unit = {
      val foo = new Foobar

      implicit val m = foo.intMonoid

      count(List(Option(1), Option(2)))
    }



sealed trait ListingId
case class ResidentialId(id: String) extends ListingId
case class DeveloperId(id: String) extends ListingId

   trait Monoid[A] {
     def empty: A
     def combine(x: A, y: A): A
   }

    implicit val intPlusMonoid: Monoid[Int] = new Monoid[Int] {
      def empty: Int = 0
      def combine(a: Int, b: Int) = a + b
    }

    def count[A: Monoid, F[A]: Foldable](values: F[A]): A = {
      values.foldLeft(Monoid[A].empty)((acc, a) => Monoid[A].combine(acc, a))
    }

   def count[F, A](values: F[A])(implicit ma: Monoid[A], implicit fa: Foldable[F[A]]): A = {
      values.foldLeft(Monoid[A].empty)((acc, a) => Monoid[A].combine(acc, a))
   }

    count(List(1, 2, 3))

    count[List, Int](values: List[Int])(implicit ma: Monoid[Int], implicit fa: Foldable[List[Int]])

    implicit def optionMonoid[A: Monoid](o: Option[A]): Monoid[Option[A]] = new Monoid[Option[A]] = {
      def empty: Option[A] = Some(Monoid[A].empty)
      def combine(a: Option[A], b: Option[A]): Option[A] = for {
        aValue <- a
        bValue <- b
      } yield Monoid[A].combine(aValue, bValue)
    }

    implicit val optionMonoid: Monoid[Option] = new Monoid[Option]


    {
      implicit val x: Int = 5

      def implicitAdd(a: Int)(implicit b: Int) = {
      a + b
      }

      implicitAdd(10)
    }
