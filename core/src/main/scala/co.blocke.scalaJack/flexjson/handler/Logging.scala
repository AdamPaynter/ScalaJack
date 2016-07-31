package co.blocke.scalajack.flexjson.handler

import co.blocke.scalajack.flexjson.TokenHandler

trait Logging extends TokenHandler {

  override def reset(): Unit = super.reset()

  override def onBeginObject(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onBeginObject: ${new String(source, offset, length)}")
    super.onBeginObject(source, offset, length)
  }

  override def onEndObject(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onEndObject: ${new String(source, offset, length)}")
    super.onEndObject(source, offset, length)
  }

  override def onBeginArray(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onBeginArray: ${new String(source, offset, length)}")
    super.onBeginArray(source, offset, length)
  }

  override def onEndArray(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onEndArray: ${new String(source, offset, length)}")
    super.onEndArray(source, offset, length)
  }

  override def onBeginString(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onBeginString: ${new String(source, offset, length)}")
    super.onBeginString(source, offset, length)
  }

  override def onEscapedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onEscapedCharacter: ${new String(source, offset, length)}")
    super.onEscapedCharacter(source, offset, length)
  }

  override def onUnescapedCharacters(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onUnescapedCharacters: ${new String(source, offset, length)}")
    super.onUnescapedCharacters(source, offset, length)
  }

  override def onEndString(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onEndString: ${new String(source, offset, length)}")
    super.onEndString(source, offset, length)
  }

  override def onIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onIdentifier: ${new String(source, offset, length)}")
    super.onIdentifier(source, offset, length)
  }

  override def onNumber(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onNumber: ${new String(source, offset, length)}")
    super.onNumber(source, offset, length)
  }

  override def onNameSeparator(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onNameSeparator: ${new String(source, offset, length)}")
    super.onNameSeparator(source, offset, length)
  }

  override def onValueSeparator(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onValueSeparator: ${new String(source, offset, length)}")
    super.onValueSeparator(source, offset, length)
  }

  override def onInsignificantWhitespace(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onInsignificantWhitespace: ${new String(source, offset, length)}")
    super.onInsignificantWhitespace(source, offset, length)
  }

  override def onEndLine(source: Array[Char], offset: Int, length: Int): Unit = {
    println(s"onEndLine: ${new String(source, offset, length)}")
    super.onEndLine(source, offset, length)
  }

  override def onEnd(): Unit = {
    println(s"onEnd")
    super.onEnd()
  }

}
