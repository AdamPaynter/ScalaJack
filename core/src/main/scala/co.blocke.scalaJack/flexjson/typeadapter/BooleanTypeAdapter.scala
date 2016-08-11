package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object BooleanTypeAdapter extends SimpleTypeAdapter[Boolean] {

  override def toString = "BooleanTypeAdapter"

  override def read(reader: JsonReader): Boolean =
    reader.moveNext() match {
      case TokenType.True ⇒
        true

      case TokenType.False ⇒
        false
    }

  override def write(value: Boolean, writer: JsonWriter): Unit = {
    if (value) {

    } else {

    }

    ???
  }

}
