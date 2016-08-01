package co.blocke.scalajack.flexjson

import co.blocke.scalajack.flexjson.TokenType.TokenType

object NothingJsonReader extends JsonReader {

  override def nextTokenType: TokenType = TokenType.Nothing

  override def moveNext(): TokenType = TokenType.Nothing

  override def nextString(): String = ???

  override def hasMoreMembers: Boolean = ???

  override def beginObject(): Unit = ???

  override def endObject(): Unit = ???

  override def skipNextValue(): Unit = ???

  override def markPosition(): Unit = ???

  override def resetPosition(): Unit = ???

  override def unmarkPosition(): Unit = ???

  override def tokenSource: Array[Char] = ???

  override def tokenOffset: Int = ???

  override def tokenLength: Int = ???

  override def hasMoreElements: Boolean = ???

  override def beginArray(): Unit = ???

  override def endArray(): Unit = ???
}
