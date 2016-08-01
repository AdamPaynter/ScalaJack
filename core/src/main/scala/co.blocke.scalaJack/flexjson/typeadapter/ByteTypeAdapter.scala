package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object ByteTypeAdapter extends TypeAdapter[Byte] with TypeAdapterFactory {

  override def toString = "ByteTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Byte]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Byte = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toByte
    }
  }

  override def write(value: Byte, writer: JsonWriter): Unit = ???

}
