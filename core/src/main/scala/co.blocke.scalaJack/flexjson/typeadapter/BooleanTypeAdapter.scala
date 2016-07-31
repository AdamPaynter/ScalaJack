package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object BooleanTypeAdapter extends TypeAdapter[Boolean] with TypeAdapterFactory {

  override def toString = "BooleanTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Boolean]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Boolean =
    reader.moveNext() match {
      case TokenType.True ⇒
        true

      case TokenType.False ⇒
        false
    }

  override def write(value: Boolean, writer: JsonWriter): Unit = {
    if (value) {

    } else {

    }

    ???
  }

}
