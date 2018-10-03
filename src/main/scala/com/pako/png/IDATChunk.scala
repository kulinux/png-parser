package com.pako.png

case class IDATChunk
(
  data: Seq[Int]
) extends HLChunk {
  val ct = "IDAT"
}

object IDATChunk {
  def read(ch: Chunk) : IDATChunk = {
    assert(ch.chunkType.ascii() == "IDAT")
    IDATChunk(ch.data)
  }
}
