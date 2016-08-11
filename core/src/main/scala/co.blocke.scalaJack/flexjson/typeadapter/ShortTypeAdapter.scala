package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object ShortTypeAdapter extends SimpleTypeAdapter[Short] {

  override def toString = "ShortTypeAdapter"

  override def read(reader: JsonReader): Short = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toShort
    }
  }

  override def write(value: Short, writer: JsonWriter): Unit = ???

}
