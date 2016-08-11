package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object IntTypeAdapter extends SimpleTypeAdapter[Int] {

  override def toString = "IntTypeAdapter"

  override def read(reader: JsonReader): Int = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toInt
    }
  }

  override def write(value: Int, writer: JsonWriter): Unit = ???

}
