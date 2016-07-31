package co.blocke.scalajack.flexjson.handler

import co.blocke.scalajack.flexjson.TokenHandler

trait Identifiers extends TokenHandler {

  def onTrueIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onFalseIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onNullIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {}

  def onUnknownIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {}

  override def onIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {
    super.onIdentifier(source, offset, length)

    if (length == 4 &&
      source(offset + 0) == 't' &&
      source(offset + 1) == 'r' &&
      source(offset + 2) == 'u' &&
      source(offset + 3) == 'e') {
      onTrueIdentifier(source, offset, length)
      return
    }

    if (length == 5 &&
      source(offset + 0) == 'f' &&
      source(offset + 1) == 'a' &&
      source(offset + 2) == 'l' &&
      source(offset + 3) == 's' &&
      source(offset + 4) == 'e') {
      onFalseIdentifier(source, offset, length)
      return
    }

    if (length == 4 &&
      source(offset + 0) == 'n' &&
      source(offset + 1) == 'u' &&
      source(offset + 2) == 'l' &&
      source(offset + 3) == 'l') {
      onNullIdentifier(source, offset, length)
      return
    }

    onUnknownIdentifier(source, offset, length)
  }

}
