package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedByteTypeAdapter extends SimpleTypeAdapter[java.lang.Byte] {

  override def read(reader: JsonReader): java.lang.Byte = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Byte.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Byte, writer: JsonWriter): Unit = ???

}
