package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, TypeAdapter, TypeAdapterFactory, JsonWriter}
import org.joda.time.DateTime

import scala.reflect.runtime.universe.{Type, typeOf}

object DateTimeTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[DateTime]) {
      Some(DateTimeTypeAdapter(context.adaptorOf[String]))
    } else {
      None
    }

}

case class DateTimeTypeAdapter(stringTypeAdapter: TypeAdapter[String]) extends TypeAdapter[DateTime] {

  override def read(reader: JsonReader): DateTime = {
    val string = stringTypeAdapter.read(reader)
    DateTime.parse(string)
  }

  override def write(value: DateTime, writer: JsonWriter): Unit = ???

}
