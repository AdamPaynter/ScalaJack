package co.blocke.scalajack.flexjson.handler

trait Strings extends EscapedCharacters {

  private val stringBuilder = new StringBuilder

  override def reset(): Unit = {
    super.reset()
    stringBuilder.clear()
  }

  def onString(string: String): Unit = {}

  override def onBeginString(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onBeginString(source, offset, length)
    stringBuilder.clear()
  }

  override def onEndString(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEndString(source, offset, length)
    onString(stringBuilder.toString)
  }

  override def onUnescapedCharacters(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onUnescapedCharacters(source, offset, length)
    stringBuilder.appendAll(source, offset, length)
  }

  override def onEscapedQuotationMarkCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedQuotationMarkCharacter(source, offset, length)
    stringBuilder.append('"')
  }

  override def onEscapedReverseSolidusCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedReverseSolidusCharacter(source, offset, length)
    stringBuilder.append('\\')
  }

  override def onEscapedSolidusCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedSolidusCharacter(source, offset, length)
    stringBuilder.append('/')
  }

  override def onEscapedBackspaceCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedBackspaceCharacter(source, offset, length)
    stringBuilder.append('\b')
  }

  override def onEscapedFormFeedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedFormFeedCharacter(source, offset, length)
    stringBuilder.append('\f')
  }

  override def onEscapedLineFeedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedLineFeedCharacter(source, offset, length)
    stringBuilder.append('\n')
  }

  override def onEscapedCarriageReturnCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedCarriageReturnCharacter(source, offset, length)
    stringBuilder.append('\r')
  }

  override def onEscapedTabCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedTabCharacter(source, offset, length)
    stringBuilder.append('\t')
  }

  override def onEscapedUnicodeCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedUnicodeCharacter(source, offset, length)
    val unicodeCodePoint = Integer.parseInt(new String(source, offset + 2, length - 2), 16)
    stringBuilder.append(unicodeCodePoint.asInstanceOf[Char])
  }

  override def onUnknownEscapedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onUnknownEscapedCharacter(source, offset, length)
    throw new Exception(s"Unknown escaped character: ${new String(source, offset, length)}")
  }

}
