package com.pako.png

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import org.scalatest.prop.Checkers
import org.scalacheck.Arbitrary._


class ByteUtils2Spec extends FlatSpec
  with Checkers
  with Matchers {

  "PngRead.getInt" should "readInt" in {
    ByteUtils.getInt( Seq(0x00, 0x00, 0x00, 0xFF) ) should equal(255)
    ByteUtils.getInt( Seq(0, 0, 0, 13) ) should equal(13)
    ByteUtils.getInt( Seq(0, 0, 3, -105) ) should equal(919)
  }

}

class ByteUtilsSpec extends FlatSpec
  with PropertyChecks
  with Checkers
  with Matchers {

  "Property Scala Check" should "works" in {
    forAll { (n: Int) =>
      whenever (n > 1) { (n / 2) should be > 0 }
    }
  }

}


class ChunkReaderSpec extends FlatSpec with Matchers {
  "ChunkReader" should "read file" in {
    val is = getClass.getResourceAsStream("/pnggrad8rgb.png" )
    val b = new Array[Byte](is.available())
    is.read(b)
    val r = b.map(_.toInt)

    ChunkReader.read( r )
    is.close()
  }

}
