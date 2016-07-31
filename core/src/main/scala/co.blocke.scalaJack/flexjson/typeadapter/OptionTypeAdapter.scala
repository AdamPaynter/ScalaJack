package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, TypeAdapter, TypeAdapterFactory, JsonWriter}

import scala.reflect.runtime.universe.{Type, typeOf}

object OptionTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe <:< typeOf[Option[Any]]) {
      val valueType = tpe.typeArgs.head
      val valueTypeAdapter = context.adapter(valueType)

      Some(new OptionTypeAdapter(valueTypeAdapter))
    } else {
      None
    }

}

case class OptionTypeAdapter[T](valueTypeAdapter: TypeAdapter[T]) extends TypeAdapter[Option[T]] {

  override def read(reader: JsonReader): Option[T] = {
    valueTypeAdapter.read(reader)
    None
  }

  override def write(value: Option[T], writer: JsonWriter): Unit = ???

}
