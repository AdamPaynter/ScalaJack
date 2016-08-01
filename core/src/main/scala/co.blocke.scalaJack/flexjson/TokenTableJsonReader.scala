package co.blocke.scalajack.flexjson

import scala.collection.mutable

class TokenTableJsonReader(table: TokenTable) extends JsonReader {

  private var position = -1
  private var markedPositions = new mutable.Stack[Int]

  override def nextTokenType: TokenType.Value = {
    table.tokenTypes(position + 1)
  }

  override def moveNext(): TokenType.Value = {
    position += 1
    table.tokenTypes(position)
  }

  override def nextString(): String = {
    moveNext()
    if (table.tokenTypes(position) != TokenType.String) {
      throw new AssertionError(s"Expected a String token, not a ${table.tokenTypes(position)} token")
    }
    table.tokenStrings(position)
  }

  override def hasMoreMembers: Boolean =
    nextTokenType != TokenType.EndObject

  override def beginObject(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.BeginObject)
  }

  override def endObject(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.EndObject)
  }

  override def skipNextValue(): Unit = {
    // FIXME this is utterly incorrect. The next value may be complex and require multiple calls to [[moveNext]].
    moveNext() match {
      case TokenType.BeginArray ⇒
        endCurrentArray()

      case TokenType.BeginObject ⇒
        endCurrentObject()

      case _ ⇒
        // Everything done
    }
  }

  private def endCurrentArray(): Unit = {
    var depth = 1
    while (depth > 0) {
      moveNext() match {
        case TokenType.BeginArray ⇒
          endCurrentArray()

        case TokenType.BeginObject ⇒
          endCurrentObject()

        case TokenType.EndArray ⇒
          depth -= 1

        case _ ⇒
      }
    }
  }

  private def endCurrentObject(): Unit = {
    var depth = 1
    while (depth > 0) {
      moveNext() match {
        case TokenType.BeginArray ⇒
          endCurrentArray()

        case TokenType.BeginObject ⇒
          endCurrentObject()

        case TokenType.EndObject ⇒
          depth -= 1

        case _ ⇒
      }
    }
  }

  override def markPosition(): Unit = {
    markedPositions.push(position)
    println(s"  MARKED POSITION $position")
  }

  override def resetPosition(): Unit = {
    val markedPosition = markedPositions.pop()
    println(s"  RESET POSITION BACK TO $markedPosition FROM $position")
    position = markedPosition
  }

  override def unmarkPosition(): Unit = {
    markedPositions.pop()
  }

  override def tokenSource: Array[Char] = table.tokenSources(position)

  override def tokenOffset: Int = table.tokenOffsets(position)

  override def tokenLength: Int = table.tokenLengths(position)

  override def hasMoreElements: Boolean =
    nextTokenType != TokenType.EndArray

  override def beginArray(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.BeginArray)
  }

  override def endArray(): Unit = {
    moveNext()
    assert(table.tokenTypes(position) == TokenType.EndArray)
  }
}
