package com.pako.png


case class PngFile(chunks: Seq[Chunk]) {
}


object ChunkTypes {
  sealed class ChunkType
}

object Chunk {
  def getLength  = skip(4) andThen PngReader.getBytes(4) andThen PngReader.getInt
  def getChunkType  = PngReader.getBytes(4) andThen getChunkTypeFromBytes
  def getData(length: Int, data: Seq[Int]) = ???
  def getCrc(length: Int, data: Seq[Int]) = ???

  def getChunkTypeFromBytes = ???

  def skip(i: Int)(data: Seq[Int]) = ???



  def getChunk(data: Seq[Int]) = {
    val length = getLength(data)
    val ct = getChunkType(data)
    val dat = getData(length, data)
    val crc = getCrc(length, data)
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
                  data: Stream[Byte],
                  crc: Array[Byte]
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
  def getInt(data: Seq[Int]): Int = ???


}


object PngParser {
  def read(file: Seq[Int]) = {
    val pngReader =
      PngReader.skipHeader(file)

    println( pngReader )
  }

}

class PngParser {

}
