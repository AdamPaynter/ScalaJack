package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object JavaWrappedDoubleTypeAdapter extends SimpleTypeAdapter[java.lang.Double] {

  override def read(reader: JsonReader): java.lang.Double = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Double.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Double, writer: JsonWriter): Unit = ???

}
