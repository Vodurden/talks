package com.reagroup.resi.propertybasedtesttalk.example1

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.{Gen, Prop}

object Example1UnitTestFactored extends Specification with ScalaCheck {
  "unit test refactored examples" >> {
    "CsvRow.isValidRow should" >> {
      val validInput = CsvRow(
        status = "Booked",
        bookingPeriod = "14",
        listingId = "1000",
        postcode = "3121"
      )

      "return true if all rules are satisfied" >> {
        CsvRow.isValidRow(validInput) must beTrue
      }

      "return false if status is not 'Booked' or 'On Hold'" >> {
        val input = validInput.copy(status = "bad input")

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if booking period is not '7' or '14'" >> {
        val input = validInput.copy(bookingPeriod = "500")

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if listing id is not a number" >> {
        val input = validInput.copy(listingId = "not a number")

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if postcode is not a number" >> {
        val input = validInput.copy(postcode = "not a number")

        CsvRow.isValidRow(input) must beFalse
      }
    }
  }
}
