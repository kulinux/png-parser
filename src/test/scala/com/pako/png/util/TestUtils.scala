package com.pako.png.util

import com.pako.png.TestReaderUtils.getClass

object TestUtils {

  def readFromRsc(path: String) = {
    val is = getClass.getResourceAsStream( path )
    val b = new Array[Byte](is.available())
    is.read(b)
    val data = b.map(_.toInt)
    is.close()
    data
  }

}
