package co.blocke.scalajack.flexjson

import TokenType.TokenType

trait JsonReader {

  def position: Int = ???

  def position_=(newPosition: Int): Unit = ???

  def nextTokenType: TokenType

  def moveNext(): TokenType

  def nextString(): String

  def hasMoreMembers: Boolean

  def beginObject(): Unit

  def endObject(): Unit

  def hasMoreElements: Boolean

  def beginArray(): Unit

  def endArray(): Unit

  def skipNextValue(): Unit

  def markPosition(): Unit

  def resetPosition(): Unit

  def unmarkPosition(): Unit

  def tokenSource: Array[Char]

  def tokenOffset: Int

  def tokenLength: Int

}
