package com.pako.png

import com.pako.png.ChunkTypes.ChunkType


case class PngFile(chunks: Seq[Chunk]) {
}


object ChunkTypes {
  sealed trait ChunkType
  case object MyChunkType extends ChunkType
}

object Chunk {
  def getLength  = skip(8)_ andThen PngReader.getBytes(4) andThen PngReader.getInt
  def getChunkType  = skip(12)_ andThen PngReader.getBytes(4) _ andThen getChunkTypeFromBytes

  def getData(length: Int) = skip(16)_ andThen PngReader.getBytes(length)
  def getCrc(length: Int) = skip(16)_ andThen skip(length) andThen PngReader.getBytes(4)

  def getChunkTypeFromBytes(d: Seq[Int]): ChunkTypes.ChunkType = ChunkTypes.MyChunkType

  def skip(i: Int)(data: Seq[Int]): Seq[Int] = {
    assert(i < data.length)
    data.slice(i, data.length - 1)
  }

  def getChunk(data: Seq[Int]) = {
    val length = getLength(data)
    val ct = getChunkType(data)
    val dat = getData(length)(data)
    val crc = getCrc(length)(data)
    Chunk(
      length,
      ct,
      dat,
      crc
    )
  }
}

case class Chunk(
                  length: Int,
                  chunkType: ChunkTypes.ChunkType,
                  data: Seq[Int],
                  crc: Seq[Int]
                )

object PngReader {
  val MagicNumber = Seq(-119, 80, 78, 71, 13, 10, 26, 10)

  def take(data: Seq[Int], slice: Seq[Int]): Seq[Int] = {
    if( slice.length > data.length) this
    val toCheck : Seq[Int] = data.take(slice.length)
    assert( toCheck == slice )
    data.slice(slice.length, data.length)
  }
  def skipHeader(data: Seq[Int]) : Seq[Int] = {
    take( data, PngReader.MagicNumber )
  }

  def getBytes(n: Int)(data: Seq[Int]) = data.slice(0, n)

  def getInt(d: Seq[Int]): Int = {
    assert(d.length == 4)
    var res = 0
    res += d(0) << 24
    res += d(1) << 16
    res += d(2) << 8
    res += d(3)
    res
  }


}


object PngParser {
  def read(file: Seq[Int]) = {
    val pngReader =
      PngReader.skipHeader(file)

    val ch = Chunk.getChunk( pngReader )

    println( pngReader )
  }

}

class PngParser {

}
