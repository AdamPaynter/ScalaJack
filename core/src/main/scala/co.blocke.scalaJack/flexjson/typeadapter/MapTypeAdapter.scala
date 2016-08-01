package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.collection.mutable
import scala.reflect.runtime.universe.{Type, typeOf}

object MapTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context): Option[TypeAdapter[_]] =
    if (tpe <:< typeOf[Map[_, _]]) {
      val keyType = tpe.typeArgs(0)
      val keyTypeAdapter = context.adapter(keyType)

      val valueType = tpe.typeArgs(1)
      val valueTypeAdapter = context.adapter(valueType)

      Some(new MapTypeAdapter[Any, Any](keyTypeAdapter.asInstanceOf[TypeAdapter[Any]], valueTypeAdapter.asInstanceOf[TypeAdapter[Any]]))
    } else {
      None
    }

}

case class MapTypeAdapter[K, V](keyTypeAdapter: TypeAdapter[K],
                                valueTypeAdapter: TypeAdapter[V]) extends TypeAdapter[Map[K, V]] {

  override def read(reader: JsonReader): Map[K, V] = {
    if (reader.nextTokenType == TokenType.Nothing) {
      return Map()
    }

    val map = new mutable.HashMap[K, V]

    reader.beginObject()

    while (reader.hasMoreMembers) {
      val key = keyTypeAdapter.read(reader)
      val value = valueTypeAdapter.read(reader)

      map += key -> value
    }

    reader.endObject()

    map.toMap
  }

  override def write(value: Map[K, V], writer: JsonWriter): Unit = ???

}
