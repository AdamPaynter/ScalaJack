package co.blocke.scalajack.flexjson

class Tokenizer(handler: TokenHandler) {

  def reset(): Unit = {

  }

  def update(source: Array[Char], offset: Int, length: Int): Unit = {
    var position = offset
    val maxPosition = offset + length

    def isDigit(ch: Char): Boolean =
      '0' <= ch && ch <= '9'

    def isLetter(ch: Char): Boolean =
      ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z')

    object Digit {
      def unapply(ch: Char): Boolean = isDigit(ch)
    }

    while (position < maxPosition) {
      val ch = source(position)
      ch match {
        case '{' ⇒
          handler.onBeginObject(source, position, 1)
          position += 1

        case '}' ⇒
          handler.onEndObject(source, position, 1)
          position += 1

        case '[' ⇒
          handler.onBeginArray(source, position, 1)
          position += 1

        case ']' ⇒
          handler.onEndArray(source, position, 1)
          position += 1

        case ':' ⇒
          handler.onNameSeparator(source, position, 1)
          position += 1

        case ',' ⇒
          handler.onValueSeparator(source, position, 1)
          position += 1

        case letter if isLetter(letter) ⇒
          val startOfIdentifier = position
          position += 1

          while (isLetter(source(position)) || isDigit(source(position))) {
            position += 1
          }

          val lengthOfIdentifier = position - startOfIdentifier

          handler.onIdentifier(source, startOfIdentifier, lengthOfIdentifier)

        case digit if isDigit(digit) || digit == '.' || digit == '-' ⇒
          val startOfNumber = position
          position += 1

          while (isDigit(source(position))) {
            position += 1
          }

          val lengthOfNumber = position - startOfNumber

          handler.onNumber(source, startOfNumber, lengthOfNumber)

        case '"' ⇒
          handler.onBeginString(source, position, 1)
          position += 1

          val startOfString = position
          var startOfUnescapedCharacters = position

          var readingString = true
          while (readingString && position < maxPosition) {
            source(position) match {
              case '\\' =>
                // Escaped character
                if (position > startOfUnescapedCharacters) {
                  handler.onUnescapedCharacters(source, startOfUnescapedCharacters, position - startOfUnescapedCharacters)
                }

                if (source(position + 1) == 'u') {
                  // four-digit unicode
                  handler.onEscapedCharacter(source, position, 6) // FIXME actually verify that the four characters are, in fact, digits
                  position += 6
                  startOfUnescapedCharacters = position
                } else {
                  handler.onEscapedCharacter(source, position, 2)
                  position += 2
                  startOfUnescapedCharacters = position
                }

              case '"' ⇒
                if (position > startOfUnescapedCharacters) {
                  handler.onUnescapedCharacters(source, startOfUnescapedCharacters, position - startOfUnescapedCharacters)
                }

                handler.onEndString(source, position, 1)
                position += 1

                readingString = false

              case _ ⇒
                position += 1
            }
          }
      }
    }
  }

  def end(): Unit = {

  }

  def close(): Unit = {

  }

}
