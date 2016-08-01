package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object JavaFloatTypeAdapter extends TypeAdapter[java.lang.Float] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[java.lang.Float]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): java.lang.Float = {
    reader.moveNext() match {
      case TokenType.Null ⇒
        null

      case TokenType.Number ⇒
        java.lang.Float.valueOf(new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength))
    }
  }

  override def write(value: java.lang.Float, writer: JsonWriter): Unit = ???

}
