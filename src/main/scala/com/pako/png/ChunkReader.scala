package com.pako.png


object ChunkTypes {
  case class ChunkType(d: Seq[Int]) {
    assert(d.length == 4)
    def ascii(): String = d.map(_.toChar).foldLeft("")((a, b) => a + b )
  }
}

case class Chunk(
                  length: Int,
                  chunkType: ChunkTypes.ChunkType,
                  data: Seq[Int],
                  crc: Seq[Int]
                ) {
  def size = 4 + 4 + length + 4
}


object Chunk {
  def getLength  = ByteUtils.getBytes(4)_ andThen ByteUtils.getInt
  def getChunkType  = skip(4)_ andThen ByteUtils.getBytes(4) _ andThen getChunkTypeFromBytes

  def getData(length: Int) = skip(8)_ andThen ByteUtils.getBytes(length)
  def getCrc(length: Int) = skip(8)_ andThen skip(length) andThen ByteUtils.getBytes(4)

  def getChunkTypeFromBytes(d: Seq[Int]): ChunkTypes.ChunkType = new ChunkTypes.ChunkType(d)

  def skip(i: Int)(data: Seq[Int]): Seq[Int] = {
    assert(i < data.length)
    data.slice(i, data.length - 1)
  }

  def getChunk(data: Seq[Int]): Option[Chunk] = {
    if(data.length == 0) return None
    val length = getLength(data)
    val ct = getChunkType(data)
    val dat = getData(length)(data)
    val crc = getCrc(length)(data)
    return Some(Chunk(
      length,
      ct,
      dat,
      crc
    ))
  }
}


object ByteUtils {
  val MagicNumber = Seq(-119, 80, 78, 71, 13, 10, 26, 10)

  def take(data: Seq[Int], slice: Seq[Int]): Seq[Int] = {
    assert( slice.length < data.length)
    val toCheck : Seq[Int] = data.take(slice.length)
    assert( toCheck == slice )
    data.slice(slice.length, data.length)
  }
  def skipHeader(data: Seq[Int]) : Seq[Int] = {
    take( data, ByteUtils.MagicNumber )
  }

  def getBytes(n: Int)(data: Seq[Int]) = data.slice(0, n)

  def getInt(d: Seq[Int]): Int = {
    assert(d.length == 4)
    val b : Seq[Byte] = d.map(_.toByte)
    var res = 0
    res += ((d(0) & 0xFF) << 24)
    res += ((d(1) & 0XFF) << 16)
    res += ((d(2) & 0xFF) << 8)
    res += (d(3) & 0xFF)
    res
  }


}


object ChunkReader {

  def getChunk(from: Int, data: Seq[Int]) =  {
      Chunk.getChunk(data)
  }

  def getAllChunks(data: Seq[Int]): Seq[Chunk] = {
    getChunk(0, data) match {
      case None => Nil
      case Some(ch) => ch +: getAllChunks(data.slice(ch.size, data.length - 1))
    }
  }

  def read(file: Seq[Int]) = {
    val data =
      ByteUtils.skipHeader(file)

    val chs = getAllChunks(data)

    println( s"I have parsed ${chs.length} chunks")

    chs.foreach(ch => println("Type: " + ch.chunkType.ascii()))

  }

}
