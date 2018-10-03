package com.pako.png

import com.pako.png
import com.pako.png.IHDRChunk.ColorType

case class IHDRChunk
(
  width: Int,
  heigth: Int,
  depth: Int,
  colorType: ColorType,
  compMeth: Int,
  filterMeth: Int,
  interlace: Int
) extends HLChunk {
  val ct = "IHDR"
}


object IHDRChunk {

  sealed trait ColorType
  case object GreyScale extends ColorType
  case object TrueColor extends ColorType
  case object IndexedColor extends ColorType
  case object GreyScaleWithAlpha extends ColorType
  case object TrueColorWithAlpha extends ColorType

  def mapColor(b: Int) = b match {
    case 0 => GreyScale
    case 2 => TrueColor
    case 3 => IndexedColor
    case 4 => GreyScaleWithAlpha
    case 6 => TrueColorWithAlpha
  }


  import png.ByteUtils._
  def width = getNextInt
  def height = skip(4)_ andThen getNextInt
  def bitDepth = skip(8)_ andThen getByte
  def colorType = skip(9)_ andThen getByte andThen mapColor
  def compMethod = skip(10)_ andThen getByte
  def filterMethod = skip(11)_ andThen getByte
  def interlaceMethod = skip(12)_ andThen getByte


  def read(ch: Chunk): IHDRChunk = {
    assert(ch.chunkType.ascii() == "IHDR")
    val d = ch.data
    IHDRChunk(
      width(d),
      height(d),
      bitDepth(d),
      colorType(d),
      compMethod(d),
      filterMethod(d),
      interlaceMethod(d)
    )
  }

}
