package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object CharTypeAdapter extends TypeAdapter[Char] with TypeAdapterFactory {

  override def toString = "CharTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Char]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Char = {
    reader.moveNext() match {
      case TokenType.String ⇒
        reader.tokenSource(reader.tokenOffset)
    }
  }

  override def write(value: Char, writer: JsonWriter): Unit = ???

}
