package com.pako.png.gui

import com.pako.png._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._


class DrawPng(gc: GraphicsContext) {

  lazy val content = loadPng()

  def loadPng(): Seq[Seq[Int]]= {
    PngReaderUtil.readPngContent
  }

  def drawPng() = {
    for{x <- 0 to 300
      y <- 0 to 300 }
    {
      drawPoint(x, y, content(y*300 + x))
    }
  }

  def drawPoint(x: Int, y: Int, rgb: Seq[Int]): Unit = {
    val color = Color.rgb(rgb(0), rgb(1), rgb(2))
    println(s"paint ($x, $y) - $color")
    gc.setFill(color)
    gc.fillRect(x, y, 1, 1)
  }


}

object ScalaFXHelloWorld extends JFXApp {

  def drawCanvas(canvas: Canvas): Unit = {
    val dpng = new DrawPng(canvas.graphicsContext2D)
    dpng.drawPng()
  }

  stage = new PrimaryStage {
    title.value = "Hello Stage"
    width = 300
    height = 300
    scene = new Scene {
      fill = LightGreen
      content = new Canvas(
        width = 300,
        height = 300
      ) {
        drawCanvas(this)
      }
    }
  }
}
