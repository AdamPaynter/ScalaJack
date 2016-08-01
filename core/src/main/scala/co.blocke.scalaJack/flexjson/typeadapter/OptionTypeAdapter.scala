package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object OptionTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe <:< typeOf[Option[_]]) {
      val valueType = tpe.typeArgs.head
      val valueTypeAdapter = context.adapter(valueType)

      Some(new OptionTypeAdapter(valueTypeAdapter))
    } else {
      None
    }

}

case class OptionTypeAdapter[T](valueTypeAdapter: TypeAdapter[T]) extends TypeAdapter[Option[T]] {

  override def read(reader: JsonReader): Option[T] = {
    if (reader.nextTokenType == TokenType.Nothing) {
      None
    } else {
      Option(valueTypeAdapter.read(reader))
    }
  }

  override def write(value: Option[T], writer: JsonWriter): Unit = ???

}
