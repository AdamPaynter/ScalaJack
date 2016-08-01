package co.blocke.scalajack.flexjson.typeadapter

import java.util.UUID

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object UUIDTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[UUID]) {
      Some(new UUIDTypeAdapter(context.adaptorOf[String]))
    } else {
      None
    }

}

case class UUIDTypeAdapter(stringTypeAdapter: TypeAdapter[String]) extends TypeAdapter[UUID] {

  override def read(reader: JsonReader): UUID =
    UUID.fromString(stringTypeAdapter.read(reader))

  override def write(value: UUID, writer: JsonWriter): Unit = ???

}
