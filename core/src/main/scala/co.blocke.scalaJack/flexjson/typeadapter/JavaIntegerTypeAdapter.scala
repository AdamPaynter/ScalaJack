package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object JavaIntegerTypeAdapter extends TypeAdapter[java.lang.Integer] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[java.lang.Integer]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): java.lang.Integer = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Integer.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Integer, writer: JsonWriter): Unit = ???

}
