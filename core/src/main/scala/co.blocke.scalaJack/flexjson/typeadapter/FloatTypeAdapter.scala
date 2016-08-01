package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, typeOf}

object FloatTypeAdapter extends TypeAdapter[Float] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= typeOf[Float]) {
      Some(this)
    } else {
      None
    }

  override def read(reader: JsonReader): Float = {
    reader.moveNext() match {
      case TokenType.Number ⇒
        new String(reader.tokenSource, reader.tokenOffset, reader.tokenLength).toFloat
    }
  }

  override def write(value: Float, writer: JsonWriter): Unit = ???

}
