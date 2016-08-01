package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object ShortTypeAdapter extends TypeAdapter[Short] with TypeAdapterFactory {

  override def toString = "ShortTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Short]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Short = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toShort
    }
  }

  override def write(value: Short, writer: JsonWriter): Unit = ???

}
