package com.pako.png

trait HLChunk {
  val ct: String
}

case class UnknownChunk(ch: Chunk)
  extends HLChunk {
  val ct = ch.chunkType.ascii()
}

object HLChunk {
  def apply(ch: Chunk): HLChunk =  {
    ch.chunkType.ascii() match {
      case "IHDR" => IHDRChunk.read(ch)
      case "IDAT" => IDATChunk.read(ch)
      case "IEND" => IENDChunk.read(ch)
      case _ => UnknownChunk(ch)
    }
  }

}
