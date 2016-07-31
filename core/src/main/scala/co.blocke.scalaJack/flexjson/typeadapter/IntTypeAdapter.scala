package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object IntTypeAdapter extends TypeAdapter[Int] with TypeAdapterFactory {

  override def toString = "IntTypeAdapter"

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Int]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Int = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toInt
    }
  }

  override def write(value: Int, writer: JsonWriter): Unit = ???

}
