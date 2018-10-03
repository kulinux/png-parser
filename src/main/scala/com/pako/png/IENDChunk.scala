package com.pako.png

case class IENDChunk ()
  extends HLChunk {
  val ct = "IEND"
}

object IENDChunk {

  def read(ch: Chunk) = {
    assert(ch.chunkType.ascii() == "IEND")
    IENDChunk()
  }

}


