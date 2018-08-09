# Property Based Testing

Structure:

- Introduction to Property Based Testing
  - What is it: A tool to help you write clear and robust tests
  - Major Concept: Generators
    A descripton of how to generate some data. Can be combined into bigger generators.

    Example:

    scala> Gen.alphaStr.sample.get
    "abc"

    scala> Gen.numStr.sample.get
    "50125"

    scala> Gen.numStr.sample.get
    ""

    scala> val generator = for {
    scala>   a <- Gen.numStr
    scala>   b <- Gen.alphaStr
    scala> } a + b
    scala> generator.sample.get
    "11513516kjasdnaksjdn"

  - Major Concept: Properties

    - Test harness to run tests with generated data
    - Takes a generator and some test code and executes them using the generator to produce test data
    - Runs the same test multiple times
    - Can look up the generator automatically for some types

  Example:

     if I have string a and string b length(a) + length(b) should always equal length(a + b)

     Prop.forAll { (a: String, b: String) =>
       a.length + b.length == (a + b).length
     }

Ideas:

- What is Property Based Testing
- Why should you care
  - Better, shorter, more effective unit tests.
  - Tests are more robust and won't break as easily if you change something unrelated

- Scenario 1: Read in some CSV data, it has a bunch of verification rules, filter out the rows that don't pass the rules
  - CsvRow(status: String, bookingPeriod: String, listingId: String)
  - Rule: status must be "Booked" or "On Hold"
  - Rule: listingId must be a number
  - Rule: listingId must start with "1" or "6"
  - Rule: booking period must be "7" or "14"
  - Rule: suburb, state and postcode must be provided
  - Bonus Rule: If status is "On Hold" no "startDate" should be given?

  Let's write some unit tests:
  *Example1UnitTestSpec code here*

  There's a lot of repetition here and the intent of the tests is a bit harder to read. Let's factor it out

  These tests are really nice. But something still bothers me: The magic inputs that are supposed to represent "bad"
  input. I've basically just picked them at random and provided a few counter examples.


- Scenario 1: Basic example, something with a subtle error

- Scenario 1 (Fixed): Fixed example, same test

- Scenario 2: Validate a listing ID

    // ResidentialListingId.scala
    // Constraint: Residential Listing IDs _must_ start with a "1"
    case class ResidentialListingId(id: String)

    object ResidentialListingId {
      def fromString(s: String): Either[String, ResidentialListingId] = {
        if(s.startsWith("1")) {
          Right(ResidentialListingId(s))
        } else {
          Left(s"Residential Listing IDs must start with a '1'. Given: ${s}")
        }
      }
    }

    // ResidentialListingIdSpec.scala
    // This is specs2
    object ResidentialListingIdSpec extends Specification with ScalaCheck {
      "ResidentialListingId.fromString should" >> {
        "accept strings that start with '1'" >> {
          val inputs = Gen.numStr.map(s => "1" + s)

          Prop.forAll(inputs) { input =>
            ResidentialListingId.fromString(input) must beRight
          }
        }

        "reject strings that start with any digit other then '1'" >> {
          val inputs = Gen.numStr
        }
      }
    }

Scenario 3: CsvRow => ValidListing

  // Imagine a CSV formatted like so:
  //
  //     activeDate, numActiveDays, listingId
  //     20/10/2017,             7, 133333333
  //     21/10/2017,             7, 144444444
  //     22/10/2017,             7, 222222222
  //     28/10/2017,             1, 155555555
  //
  // We want to return a List of ResidentialListingIds
  // that are "active", that is we have passed their `activeDate` and are still within their
  // `numActiveDays`. If the current date is `23/10/2017` then our expected result would be:
  //
  //     133333333
  //     144444444
  //
  // We filter out `222222222` because it is an invalid residential ID and we filter `155555555`
  // because it hasn't started yet.

  // Goal: Take a List[CsvRow] and find only the listings that should be active today.
  Convert a List[CsvRow] to a List[ResidentialListingId] where the list of residential ids only contains
  // ids that are active for the current time.
  //
  // Example3.scala
  case class CsvRow(activeDate: LocalDate, numActiveDays: Int, listingId: String)
  case class ResidentialListingId(id: String)

  def toActiveListings(rows: List[CsvRow]): List[ResidentialListingId] = {
  }

  def toActiveListing(row: CsvRow): Option[ResidentialListingId] = {
  }

  // Example3Spec.scala
  object Example3Spec extends Specification with ScalaCheck {
    "Example3Spec.toActiveListing should" >> {
      "accept listings that are residential and active" >> {
        val inputs = for {
          now <- arbitrary[LocalDate]

        }
      }

      "reject listings that are not active" >> {
      }

      "reject listings that do not have residential listing ids" >> {
      }
    }
  }

- Caveats (ScalaCheck):
  - Try to avoid `suchThat`, rewrite things ike so:

- Can I use property based testing in language X?
  Good summary: http://hypothesis.works/articles/quickcheck-in-every-language/
  Highlights:
    Scala: Definitely yes, use ScalaCheck
    Haskell: 100% Yes.
    Ruby: Maybe?
