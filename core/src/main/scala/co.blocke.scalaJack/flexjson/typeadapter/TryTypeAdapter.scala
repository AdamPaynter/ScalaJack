package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}
import scala.util.Try

object TryTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe <:< typeOf[Try[_]]) {
      val valueType = tpe.typeArgs.head
      val valueTypeAdapter = context.adapter(valueType)
      Some(new TryTypeAdapter[Any](valueTypeAdapter.asInstanceOf[TypeAdapter[Any]]))
    } else {
      None
    }

}

class TryTypeAdapter[T](valueTypeAdapter: TypeAdapter[T]) extends TypeAdapter[Try[T]] {

  override def read(reader: JsonReader): Try[T] = {
    reader.markPosition()

    val triedValue = Try { valueTypeAdapter.read(reader) }

    triedValue
  }

  override def write(value: Try[T], writer: JsonWriter): Unit = ???

}
