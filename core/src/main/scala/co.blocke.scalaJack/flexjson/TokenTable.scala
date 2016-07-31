package co.blocke.scalajack.flexjson

import co.blocke.scalajack.flexjson.handler.{Identifiers, Strings}

class TokenTable extends TokenHandler with Identifiers with Strings {

  private[flexjson] var numberOfTokens = 0
  private[flexjson] var tokenTypes: Array[TokenType.Value] = new Array[TokenType.Value](1024)
  private[flexjson] var tokenSources: Array[Array[Char]] = new Array[Array[Char]](1024)
  private[flexjson] var tokenOffsets: Array[Int] = new Array[Int](1024)
  private[flexjson] var tokenLengths: Array[Int] = new Array[Int](1024)
  private[flexjson] var tokenStrings: Array[String] = new Array[String](1024)

  def ensureCapacity(minCapacity: Int): Unit = {

  }

  def appendToken(tokenType: TokenType.Value, tokenSource: Array[Char], tokenOffset: Int, tokenLength: Int, tokenString: String = null): Unit = {
    val i = numberOfTokens
    numberOfTokens += 1
    ensureCapacity(numberOfTokens)

    tokenTypes(i) = tokenType
    tokenSources(i) = tokenSource
    tokenOffsets(i) = tokenOffset
    tokenLengths(i) = tokenLength
    tokenStrings(i) = tokenString
  }

  override def onBeginObject(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.BeginObject, source, offset, length)
  }

  override def onEndObject(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.EndObject, source, offset, length)
  }

  override def onBeginArray(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.BeginArray, source, offset, length)
  }

  override def onEndArray(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.EndArray, source, offset, length)
  }

  override def onNumber(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.Number, source, offset, length)
  }

  override def onNameSeparator(source: Array[Char], offset: Int, length: Int): Unit = {
//    appendToken(TokenType.NameSeparator, source, offset, length)
  }

  override def onValueSeparator(source: Array[Char], offset: Int, length: Int): Unit = {
//    appendToken(TokenType.ValueSeparator, source, offset, length)
  }

  override def onTrueIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.True, source, offset, length)
  }

  override def onFalseIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.False, source, offset, length)
  }

  override def onNullIdentifier(source: Array[Char], offset: Int, length: Int): Unit = {
    appendToken(TokenType.Null, source, offset, length)
  }

  override def onString(string: String): Unit = {
    appendToken(TokenType.String, null, -1, 0, string)
  }

}
