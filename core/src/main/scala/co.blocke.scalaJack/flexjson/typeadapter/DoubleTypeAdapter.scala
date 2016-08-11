package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{JsonReader, JsonWriter, TokenType}

object DoubleTypeAdapter extends SimpleTypeAdapter[Double] {

  override def read(reader: JsonReader): Double = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toDouble
    }
  }

  override def write(value: Double, writer: JsonWriter): Unit = ???

}
