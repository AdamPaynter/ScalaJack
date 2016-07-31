package co.blocke.scalajack.flexjson

class JsonReader(table: TokenTable) {

  private var position = -1
  private var markedPosition: Int = -1

  def nextTokenType: TokenType.Value = {
    table.tokenTypes(position + 1)
  }

  def moveNext(): TokenType.Value = {
    position += 1
    table.tokenTypes(position)
  }

  def nextString(): String = {
    moveNext()
    if (table.tokenTypes(position) != TokenType.String) {
      throw new AssertionError(s"Expected a String token, not a ${table.tokenTypes(position)} token")
    }
    table.tokenStrings(position)
  }

  def hasMoreMembers: Boolean =
    nextTokenType != TokenType.EndObject

  def beginObject(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.BeginObject)
  }

  def endObject(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.EndObject)
  }

  def skipNextValue(): Unit = {
    // FIXME this is utterly incorrect. The next value may be complex and require multiple calls to [[moveNext]].
    moveNext()
  }

  def markPosition(): Unit = {
    markedPosition = position
  }

  def resetPosition(): Unit = {
    position = markedPosition
  }

  def unmarkPosition(): Unit = {
    markedPosition = -1
  }

  def tokenSource: Array[Char] = table.tokenSources(position)

  def tokenOffset: Int = table.tokenOffsets(position)

  def tokenLength: Int = table.tokenLengths(position)

}
