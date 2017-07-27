package com.reagroup.resi.propertybasedtesttalk.example2

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.{Gen, Prop}

/*object Example2Spec extends Specification with ScalaCheck {
  "ListingId.fromCsvRow unit test example" >> {
    "return a ResidentialListingId given a CsvRow with listing id starting with 1" >> {
      val input = CsvRow(LocalDate.now, "7", "10000000")

      ListingId.fromCsvRow(input) must_== ResidentialListingId("10000000")
    }
  }

  "ListingId.fromString property based test example" >> {
    "return a ResidentialListingId given a CsvRow with listing id starting with 1" >> {
      val inputs = for {
        listingId <- Gen.numStr.map(s => "1" + s)
        row <- arbitrary[CsvRow]
      } yield row.copy(listingId = listingId)

      Prop.forAll()
    }
  }

  "ListingId.fromString should" >> {
    "return a ListingId given a valid id" >> {
      Prop.forAll(Generators.validListingIdString) { (listingId: String) =>
        ListingId.fromString(listingId) must beRight[ListingId]
      }
    }

    "return a ResidentialListingId given a valid id starting with a 1" >> {
      val inputs = Generators.validListingIdString.map(s => "1" + s)

      Prop.forAll(inputs) { (listingId: String) =>
        ListingId.fromString(listingId) must beLike { case Right(ResidentialListingId(_)) => ok }
      }
    }

    "return a DeveloperPropertyProfileId given a valid id starting with a 6" >> {
      val inputs = Generators.validListingIdString.map(s => "6" + s)

      Prop.forAll(inputs) { (listingId: String) =>
        ListingId.fromString(listingId) must beLike { case Right(DeveloperPropertyProfileId(_)) => ok }
      }
    }

    "fail if given an id starting with an unsupported digit" >> {
      val validStartDigits = List(1, 6)
      val invalidStartDigits = (0 to 9).filter(i => !validStartDigits.contains(i)).map(i => i.toString)

      val inputs = for {
        startDigit <- Gen.oneOf(invalidStartDigits)
        rest <- Gen.numStr
      } yield startDigit + rest

      Prop.forAll(inputs) { (s: String) =>
        ListingId.fromString(s) must beLike { case Left(UnsupportedListingIdSegment(_)) => ok }
      }
    }

    "fail with ListingIdMustBeNumeric if given non-digit strings" >> {
      Prop.forAll(Gen.alphaStr) { (s: String) =>
        ListingId.fromString(s) must beLike { case Left(ListingIdMustBeNumeric(_)) => ok }
      }
    }
  }
}*/
