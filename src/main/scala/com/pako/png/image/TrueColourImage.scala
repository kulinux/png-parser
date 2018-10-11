package com.pako.png.image

import scala.annotation.tailrec

object TrueColourImage {

  def takeThree(data: Seq[Byte])= data match {
    case chunk: Seq[Int] if(data.length < 3) => None
    case _ => Some(data.take(3))
  }

  @tailrec
  def takeOneThree(data: Seq[Byte], res: Seq[Seq[Byte]]): Seq[Seq[Byte]] = {
    takeThree(data) match {
      case Some(chunk) => {
        val newRes : Seq[Seq[Byte]] = chunk +: res
        val dataAfterToken = data.drop(3)

        takeOneThree(dataAfterToken, newRes)
      }
      case None => res
    }
  }

  def takeInThree(data: Seq[Byte]) = takeOneThree(data, Seq(Seq()))

  def takeInThree(data: Seq[Byte], f: Seq[Int] => Unit) =  {
  }

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
      y <- 0 to height } {
        val color = rgb(data, width, x, y)
        println(color)
      }
  }
    /*
    val inThree = takeInThree(data)
    for( rgb <- inThree ) {
      var r = inThree(0)
      var g = inThree(0)
      var b = inThree(0)

      println(s"$r $g $b")
    }
    */


}
