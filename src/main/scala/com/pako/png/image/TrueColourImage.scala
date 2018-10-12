package com.pako.png.image

import scala.annotation.tailrec

object TrueColourImage {

  def numberOfPoints(width: Int, height: Int) = width * height

  def rgb(data: Seq[Byte], width: Int, x: Int, y: Int) = {
    Seq(
      data(y*width + x) & 0xFF,
      data(y*width + x + 1) & 0xFF,
      data(y*width + x + 2) & 0xFF
    )
  }

  def parse(data: Seq[Byte], width: Int, height: Int) = {
    for{x <- 0 to width
      y <- 0 to height }  yield rgb(data, width, x, y)
  }

}
