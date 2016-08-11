package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object CharTypeAdapter extends SimpleTypeAdapter[Char] {

  override def toString = "CharTypeAdapter"

  override def read(reader: JsonReader): Char = {
    reader.moveNext() match {
      case TokenType.String ⇒
        reader.tokenSource(reader.tokenOffset)
    }
  }

  override def write(value: Char, writer: JsonWriter): Unit = ???

}
