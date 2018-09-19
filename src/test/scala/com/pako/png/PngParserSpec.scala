package com.pako.png

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import org.scalatest.prop.Checkers
import org.scalacheck.Arbitrary._

class PngReaderSpec extends FlatSpec
  with PropertyChecks
  with Checkers
  with Matchers {

  "Property Scala Check" should "works" in {
    forAll { (n: Int) =>
      whenever (n > 1) { (n / 2) should be > 0 }
    }
  }
  "PngReader.take" should "works" in {

    forAll { (take: Int) =>
      whenever( take > 0 ) {
        val Data: Seq[Int] = List.fill(10000){5}.toSeq

        val takeData = Data.take(take)
        val afterRead = PngReader.take(Data, takeData)
        val toCheck : Seq[Int] = (takeData ++ afterRead)

        toCheck should be (Data)

      }
    }

  }
}


class PngParserSpec extends FlatSpec with Matchers {
  "PngParser" should "read file" in {
    val is = getClass.getResourceAsStream("/pnggrad8rgb.png" )
    val b = new Array[Byte](is.available())
    is.read(b)
    val r = b.map(_.toInt)

    PngParser.read( r )
    is.close()
  }

}
