package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object LongTypeAdapter extends TypeAdapter[Long] with TypeAdapterFactory {

  override def toString = "LongTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Long]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Long = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toLong
    }
  }

  override def write(value: Long, writer: JsonWriter): Unit = ???

}
