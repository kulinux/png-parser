package com.pako.png.util

import java.util.zip.{Deflater, Inflater}

object Zip {
  def decompress(dataIn: Array[Byte]) =  {
    val inflater = new Inflater()
    val dataOut : Array[Byte] = new Array[Byte](dataIn.size * 2)
    inflater.setInput(dataIn)

    var count = inflater.inflate(dataOut)
    var finalData = dataOut.take(count)
    while (count > 0) {
      count = inflater.inflate(dataOut)
      finalData = finalData ++ dataOut.take(count)
    }
    finalData
  }

  def compress(inData: Array[Byte]): Array[Byte] = {
    var deflater: Deflater = new Deflater()
    deflater.setInput(inData)
    deflater.finish
    val compressedData = new Array[Byte](inData.size * 2) // compressed data can be larger than original data
    val count: Int = deflater.deflate(compressedData)
    return compressedData.take(count)
  }


}
