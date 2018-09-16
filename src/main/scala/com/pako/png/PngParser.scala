package com.pako.png

import scala.io.Source

object ChunkTypes {
  sealed class ChunkType
}

case class Chunk(
  length: Int,
  chunkType: ChunkTypes.ChunkType,
  data: Stream[Byte],
  crc: Array[Byte]
)

case class PngFile(chunks: Seq[Chunk]) {
}

object PngReader {
  val MagicNumber = Seq(137, 80, 78, 71, 13, 10, 26, 10)

}

class PngReader(val start: Int = 0, val data: Seq[Int]) {
  def take(slice: Seq[Int]): PngReader = {
    if( slice.length > data.length) this
    val toCheck : Seq[Int] = data.take(slice.length)
    assert( toCheck == slice )
    new PngReader(start + slice.size,
      data.slice(start + slice.length, data.length))

  }
  def skipHeader : PngReader = {
   take( PngReader.MagicNumber )
  }

}

object PngParser {
  def read(file: Seq[Int]) = {
    val pngReader =
      new PngReader(data = file)
      .skipHeader

    println( pngReader )
  }

}

class PngParser {

}
