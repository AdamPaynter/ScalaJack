package co.blocke.scalajack.flexjson.handler

import co.blocke.scalajack.flexjson.TokenHandler

trait LineNumbers extends TokenHandler {

  private var $lineNumber = 1

  override def reset(): Unit = {
    $lineNumber = 1
    super.reset()
  }

  override def onEndLine(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEndLine(source, offset, length)
    $lineNumber += 1
  }

}
