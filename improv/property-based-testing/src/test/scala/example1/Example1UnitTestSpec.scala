package com.reagroup.resi.propertybasedtesttalk.example1

import org.specs2.mutable.Specification

object Example1UnitTestSpec extends Specification {
  "unit test examples" >> {
    "CsvRow.isValidRow should" >> {
      "return true if all rules are satisfied" >> {
        val input = CsvRow(
          status = "Booked",
          bookingPeriod = "14",
          listingId = "1000",
          postcode = "3121"
        )

        CsvRow.isValidRow(input) must beTrue
      }

      "return false if status is not 'Booked' or 'On Hold'" >> {
        val input = CsvRow(
          status = "bad input",
          bookingPeriod = "14",
          listingId = "1000",
          postcode = "3121"
        )

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if booking period is not '7' or '14'" >> {
        val input = CsvRow(
          status = "Booked",
          bookingPeriod = "500",
          listingId = "1000",
          postcode = "3121"
        )

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if listing id is not a number" >> {
        val input = CsvRow(
          status = "Booked",
          bookingPeriod = "14",
          listingId = "not a number",
          postcode = "3121"
        )

        CsvRow.isValidRow(input) must beFalse
      }

      "return false if postcode is not a number" >> {
        val input = CsvRow(
          status = "Booked",
          bookingPeriod = "14",
          listingId = "10000",
          postcode = "not a number"
        )

        CsvRow.isValidRow(input) must beFalse
      }
    }
  }
}
