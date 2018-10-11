package com.pako.png

object PNGFile {

  sealed trait PNGChunkType
  case object IDAT extends PNGChunkType
  case object IHDR extends PNGChunkType
  case object IEND extends PNGChunkType
  case object Unknown extends PNGChunkType

  def translate(str: String) = {
    str match {
      case "IDAT" => IDAT
      case "IHDR" => IHDR
      case "IEND" => IEND
      case _ => Unknown
    }
  }
}

class PNGFile (chs: Seq[HLChunk]){


  val chunks: Map[PNGFile.PNGChunkType, HLChunk] =
    chs.map(ch => PNGFile.translate(ch.ct) -> ch)
    .toMap

}
