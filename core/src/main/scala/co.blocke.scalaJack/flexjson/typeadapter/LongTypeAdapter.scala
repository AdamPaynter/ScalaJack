package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object LongTypeAdapter extends SimpleTypeAdapter[Long] {

  override def toString = "LongTypeAdapter"

  override def read(reader: JsonReader): Long = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toLong
    }
  }

  override def write(value: Long, writer: JsonWriter): Unit = ???

}
