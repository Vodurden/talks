package com.reagroup.resi.propertybasedtesttalk.example0

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.{Gen, Prop}
import org.scalacheck.Arbitrary.arbitrary

import com.reagroup.resi.propertybasedtesttalk.GenExtensions._

object Example0PropertyBasedSpec extends Specification with ScalaCheck {
  "property based test examples" >> {
    "Example0.lengthGreaterThen10 should" >> {
      "return true if the length of the string is greater then 10" >> {
        Prop.forAll(Gen.stringLongerThen(10)) { input =>
          Example0.lengthGreaterThen10(input) must beTrue
        }
      }

      "return false if the length of the string is exactly 10" >> {
        Prop.forAll(Gen.stringOfLength(10)) { input =>
          Example0.lengthGreaterThen10(input) must beFalse
        }
      }

      "return false if the length of the string is less than 10" >> {
        Prop.forAll(Gen.stringShorterThen(10)) { input =>
          Example0.lengthGreaterThen10(input) must beFalse
        }
      }
    }
  }
}
