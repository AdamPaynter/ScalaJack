package co.blocke.scalajack.flexjson.handler

import co.blocke.scalajack.flexjson.TokenHandler

trait EscapedCharacters extends TokenHandler {

  self: Strings ⇒

  def onEscapedQuotationMarkCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedReverseSolidusCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedSolidusCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedBackspaceCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedFormFeedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedLineFeedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedCarriageReturnCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedTabCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onEscapedUnicodeCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onUnknownEscapedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {}

  override def onEscapedCharacter(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onEscapedCharacter(source, offset, length)

    source(offset + 1) match {
      case '"' ⇒
        onEscapedQuotationMarkCharacter(source, offset, length)

      case '\\' ⇒
        onEscapedReverseSolidusCharacter(source, offset, length)

      case '/' ⇒
        onEscapedSolidusCharacter(source, offset, length)

      case 'b' ⇒
        onEscapedBackspaceCharacter(source, offset, length)

      case 'f' ⇒
        onEscapedFormFeedCharacter(source, offset, length)

      case 'n' ⇒
        onEscapedLineFeedCharacter(source, offset, length)

      case 'r' ⇒
        onEscapedCarriageReturnCharacter(source, offset, length)

      case 't' ⇒
        onEscapedTabCharacter(source, offset, length)

      case 'u' ⇒
        onEscapedUnicodeCharacter(source, offset, length)

      case _ ⇒
        onUnknownEscapedCharacter(source, offset, length)
    }
  }

}
