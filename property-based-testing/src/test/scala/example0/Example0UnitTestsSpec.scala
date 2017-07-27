package com.reagroup.resi.propertybasedtesttalk.example0

import org.specs2.mutable.Specification

object Example0UnitTestSpec extends Specification {
  "unit test examples" >> {
    "Example0.lengthGreaterThen10 should" >> {
      "return true if the length of the string is greater then 10" >> {
        Example0.lengthGreaterThen10("abcdefabcdef") must beTrue
      }

      "return false if the length of the string is exactly 10" >> {
        Example0.lengthGreaterThen10("aaaaabbbbb")
      }

      "return false if the length of the string is less than 10" >> {
        Example0.lengthGreaterThen10("ccccdddd") must beFalse
      }
    }
  }
}
