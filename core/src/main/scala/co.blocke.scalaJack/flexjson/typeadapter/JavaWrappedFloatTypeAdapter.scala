package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedFloatTypeAdapter extends SimpleTypeAdapter[java.lang.Float] {

  override def read(reader: JsonReader): java.lang.Float = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Float.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Float, writer: JsonWriter): Unit = ???

}
