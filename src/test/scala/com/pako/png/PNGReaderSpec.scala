package com.pako.png

import org.scalatest.{FlatSpec, Matchers}

class PNGReaderSpec
  extends FlatSpec
  with Matchers {

  "PNG" should "parse" in {
    val data =
      ChunkReader.read( TestReaderUtils.readSample() )

    val hls = data
      .map(ch => HLChunk(ch) )

    println(hls)

  }

}
