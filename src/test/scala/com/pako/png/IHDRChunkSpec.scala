package com.pako.png

import org.scalatest.{FlatSpec, Matchers}

class IHDRChunkSpec
  extends FlatSpec
  with Matchers {

  "IHDR Chunk" should "parse" in {
    val data = ChunkReader.read( TestReaderUtils.readSample() )

    val ihdr = data
      .filter(p => p.chunkType.ascii() == "IHDR")
      .map( IHDRChunk.read )

    println(ihdr)

  }

}
