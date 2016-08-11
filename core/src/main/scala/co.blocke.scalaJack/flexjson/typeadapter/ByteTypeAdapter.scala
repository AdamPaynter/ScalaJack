package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object ByteTypeAdapter extends SimpleTypeAdapter[Byte] {

  override def toString = "ByteTypeAdapter"

  override def read(reader: JsonReader): Byte = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toByte
    }
  }

  override def write(value: Byte, writer: JsonWriter): Unit = ???

}
