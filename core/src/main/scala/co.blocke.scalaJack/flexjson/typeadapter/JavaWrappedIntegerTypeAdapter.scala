package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedIntegerTypeAdapter extends SimpleTypeAdapter[java.lang.Integer] {

  override def read(reader: JsonReader): java.lang.Integer = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Integer.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Integer, writer: JsonWriter): Unit = ???

}
