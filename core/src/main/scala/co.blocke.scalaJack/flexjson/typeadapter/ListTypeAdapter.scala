package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}
import scala.collection.mutable

object ListTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe <:< typeOf[List[_]]) {
      val elementType = tpe.typeArgs.head
      val elementTypeAdapter = context.adapter(elementType)
      Some(new ListTypeAdapter(elementTypeAdapter))
    } else {
      None
    }

}

case class ListTypeAdapter[T](elementTypeAdapter: TypeAdapter[T]) extends TypeAdapter[List[T]] {

  override def read(reader: JsonReader): List[T] = {
    if (reader.nextTokenType == TokenType.Nothing) {
      return List()
    }

    reader.beginArray()

    val listBuilder = new mutable.ListBuffer[T]

    while (reader.hasMoreElements) {
      val element = elementTypeAdapter.read(reader)
      listBuilder += element
    }

    reader.endArray()

    listBuilder.toList
  }

  override def write(value: List[T], writer: JsonWriter): Unit = ???

}
