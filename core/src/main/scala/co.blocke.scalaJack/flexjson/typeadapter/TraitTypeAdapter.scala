package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.Type
import scala.reflect.runtime.currentMirror

case class TraitTypeAdapterFactory(typeHintMemberName: Type ⇒ (String, String ⇒ Type)) extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe.typeSymbol.asClass.isTrait) {
      val (memberName, actualTypeFn) = typeHintMemberName(tpe)
      Some(TraitTypeAdapter[Any](tpe, memberName, actualTypeFn, context))
    } else {
      None
    }

}

case class TraitTypeAdapter[T](traitType: Type, typeHintMemberName: String, actualTypeFn: (String ⇒ Type), context: Context) extends TypeAdapter[T] {

  override def read(reader: JsonReader): T = {
    var actualTypeHint: String = null

    // First pass determines the type
    reader.markPosition()
    reader.beginObject()
    while (reader.hasMoreMembers && actualTypeHint == null) {
      if (reader.nextString() == typeHintMemberName) {
        actualTypeHint = reader.nextString()
      } else {
        reader.skipNextValue()
      }
    }
    reader.resetPosition()

    val actualType = actualTypeFn(actualTypeHint)
    val actualAdapter = context.adapter(actualType)

    actualAdapter.read(reader).asInstanceOf[T]
  }

  override def write(value: T, writer: JsonWriter): Unit = ???

}
