package com.pako.png.util

import org.scalatest.{FlatSpec, Matchers}

class ZipSpec
  extends FlatSpec with Matchers {

  "A zip file" should "be decompress" in {
    val data = "Hola, como estas"
    val dataComp = Zip.compress(data.getBytes())
    val dataAgain = Zip.decompress(dataComp)
    println(dataAgain)
  }

}
