package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, TokenType, TypeAdapter, TypeAdapterFactory, JsonWriter}

import scala.reflect.runtime.universe.{Type, typeOf}

object StringTypeAdapter extends TypeAdapter[String] with TypeAdapterFactory {

  override def toString = "StringTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[String]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): String = {
    reader.nextTokenType match {
      case TokenType.String ⇒
        reader.nextString()

      case TokenType.False ⇒
        "false"

      case TokenType.True ⇒
        "true"

      case TokenType.Null ⇒
        null
    }
  }

  override def write(value: String, writer: JsonWriter): Unit = {
    ???
  }

}
