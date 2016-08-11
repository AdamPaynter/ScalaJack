package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object FloatTypeAdapter extends SimpleTypeAdapter[Float] {

  override def read(reader: JsonReader): Float = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toFloat
    }
  }

  override def write(value: Float, writer: JsonWriter): Unit = ???

}
