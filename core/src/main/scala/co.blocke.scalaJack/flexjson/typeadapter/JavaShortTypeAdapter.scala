package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object JavaShortTypeAdapter extends TypeAdapter[java.lang.Short] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[java.lang.Short]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): java.lang.Short = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Short.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Short, writer: JsonWriter): Unit = ???

}
