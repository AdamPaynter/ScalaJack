package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}
import scala.collection.mutable

object SetTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe <:< typeOf[Set[_]]) {
      val elementType = tpe.typeArgs.head
      val elementTypeAdapter = context.adapter(elementType)
      Some(new SetTypeAdapter(elementTypeAdapter))
    } else {
      None
    }

}

case class SetTypeAdapter[T](elementTypeAdapter: TypeAdapter[T]) extends TypeAdapter[Set[T]] {

  override def read(reader: JsonReader): Set[T] = {
    if (reader.nextTokenType == TokenType.Nothing) {
      return Set()
    }

    reader.beginArray()

    val listBuilder = new mutable.HashSet[T]

    while (reader.hasMoreElements) {
      val element = elementTypeAdapter.read(reader)
      listBuilder += element
    }

    reader.endArray()

    listBuilder.toSet
  }

  override def write(value: Set[T], writer: JsonWriter): Unit = ???

}
