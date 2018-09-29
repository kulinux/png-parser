package com.pako.png

case class IENDChunk ()
  extends HLChunk

object IENDChunk {

  def read(ch: Chunk) = {
    assert(ch.chunkType.ascii() == "IEND")
    IENDChunk()
  }

}


