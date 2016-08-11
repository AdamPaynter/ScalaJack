package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedShortTypeAdapter extends SimpleTypeAdapter[java.lang.Short] {

  override def read(reader: JsonReader): java.lang.Short = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Short.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Short, writer: JsonWriter): Unit = ???

}
