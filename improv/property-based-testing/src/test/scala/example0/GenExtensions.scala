package com.reagroup.resi.propertybasedtesttalk

import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary

import scala.language.implicitConversions

object GenExtensions {
  object RichGen {
    def stringOfLength(n: Int): Gen[String] = for {
      characters <- Gen.listOfN(n, arbitrary[Char])
    } yield characters.mkString

    def stringLongerThen(n: Int): Gen[String] = Gen.sized { maxSize =>
      for {
        stringLength <- Gen.choose(n, n + maxSize)
        string <- stringOfLength(stringLength)
      } yield string
    }

    def stringShorterThen(n: Int): Gen[String] = for {
      stringLength <- Gen.choose(0, n-1)
      string <- stringOfLength(stringLength)
    } yield string

  }

  // There's probably a better way to add other generators (like using your own object)
  // but for this example I'm going to enrich `Gen`.
  implicit def enrichGen(gen: Gen.type): RichGen.type = RichGen
}
