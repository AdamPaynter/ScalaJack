package co.blocke.scalajack.flexjson

trait TokenHandler {

  def reset(): Unit = {}

  def onBeginObject(source: Array[Char], offset: Int, length: Int): Unit = {}
  def onEndObject(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onBeginArray(source: Array[Char], offset: Int, length: Int): Unit = {}
  def onEndArray(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onBeginString(source: Array[Char], offset: Int, length: Int): Unit = {}
  def onEscapedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}
  def onUnescapedCharacters(source: Array[Char], offset: Int, length: Int): Unit = {}
  def onEndString(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onNumber(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onNameSeparator(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onValueSeparator(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onInsignificantWhitespace(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEndLine(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEnd(): Unit = {}

}
