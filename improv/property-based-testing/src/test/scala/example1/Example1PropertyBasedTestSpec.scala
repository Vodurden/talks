package com.reagroup.resi.propertybasedtesttalk.example1

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.{Gen, Prop}
import org.scalacheck.Arbitrary.arbitrary

object Example1PropertyBasedTestSpec extends Specification with ScalaCheck {
  "property based test examples" >> {
    "CsvRow.isValidRow should" >> {
      val validResidentialId = Gen.numStr.map(s => "1" + s)
      val validPropertyId = Gen.numStr.map(s => "6" + s)
      val validListingId = Gen.oneOf(validResidentialId, validPropertyId)

      val validInputs: Gen[CsvRow] = for {
        status <- Gen.oneOf("Booked", "On Hold")
        bookingPeriod <- Gen.oneOf("7", "14")
        listingId <- validListingId
        postcode <- Gen.numStr
      } yield CsvRow(
        status = status,
        bookingPeriod = bookingPeriod,
        listingId = listingId,
        postcode = postcode
      )

      "return true if all rules are satisfied" >> {
        Prop.forAll(validInputs) { input =>
          CsvRow.isValidRow(input) must beTrue
        }
      }

      "return false if status is not 'Booked' or 'On Hold'" >> {
        val inputs = for {
          invalidStatus <- arbitrary[String].suchThat(s => s != "Booked" && s != "On Hold")
          validInput <- validInputs
        } yield validInput.copy(status = invalidStatus)

        Prop.forAll(inputs) { input =>
          CsvRow.isValidRow(input) must beFalse
        }
      }

      "return false if booking period is not '7' or '14'" >> {
        val inputs = for {
          invalidBookingPeriod <- Gen.numStr.suchThat(s => s != "7" && s != "14")
          validInput <- validInputs
        } yield validInput.copy(bookingPeriod = invalidBookingPeriod)

        Prop.forAll(inputs) { input =>
          CsvRow.isValidRow(input) must beFalse
        }
      }

      "return false if listing id is not a number" >> {
        val inputs = for {
          invalidListingId <- Gen.alphaStr
          validInput <- validInputs
        } yield validInput.copy(listingId = invalidListingId)

        Prop.forAll(inputs) { input =>
          CsvRow.isValidRow(input) must beFalse
        }
      }

      "return false if postcode is not a number" >> {
        val inputs = for {
          invalidPostcode <- Gen.alphaStr
          validInput <- validInputs
        } yield validInput.copy(postcode = invalidPostcode)

        Prop.forAll(inputs) { input =>
          CsvRow.isValidRow(input) must beFalse
        }
      }
    }
  }
}
