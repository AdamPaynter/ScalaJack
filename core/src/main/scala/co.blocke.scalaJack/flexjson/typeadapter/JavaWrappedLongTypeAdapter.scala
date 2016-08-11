package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedLongTypeAdapter extends SimpleTypeAdapter[java.lang.Long] {

  override def read(reader: JsonReader): java.lang.Long = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Long.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Long, writer: JsonWriter): Unit = ???

}
