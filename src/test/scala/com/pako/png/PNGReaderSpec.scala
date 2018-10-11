package com.pako.png

import com.pako.png.image.TrueColourImage
import com.pako.png.util.Zip
import org.scalatest.{FlatSpec, Matchers}

class PNGReaderSpec
  extends FlatSpec
  with Matchers {

  "PNG" should "parse" in {
    val data =
      ChunkReader.read( TestReaderUtils.readSample() )

    val hls = data
      .map(ch => HLChunk(ch) )

    val png = new PNGFile(hls)


    val dataChunk = png.chunks(PNGFile.IDAT).asInstanceOf[IDATChunk]
    val ihdrChunk = png.chunks(PNGFile.IHDR).asInstanceOf[IHDRChunk]

    val imgComp = dataChunk
      .data
        .map(_.toByte).toArray

    val img = Zip.decompress(imgComp)

    TrueColourImage.parse(img,
      ihdrChunk.width,
      ihdrChunk.heigth)

    println(hls)

  }

}
