package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object JavaByteTypeAdapter extends TypeAdapter[java.lang.Byte] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[java.lang.Byte]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): java.lang.Byte = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Byte.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Byte, writer: JsonWriter): Unit = ???

}
