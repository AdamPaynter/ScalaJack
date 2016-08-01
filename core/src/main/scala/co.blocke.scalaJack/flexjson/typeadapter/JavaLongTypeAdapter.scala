package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object JavaLongTypeAdapter extends TypeAdapter[java.lang.Long] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[java.lang.Long]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): java.lang.Long = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Long.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Long, writer: JsonWriter): Unit = ???

}
