package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object DoubleTypeAdapter extends TypeAdapter[Double] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Double]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Double = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toDouble
    }
  }

  override def write(value: Double, writer: JsonWriter): Unit = ???

}
