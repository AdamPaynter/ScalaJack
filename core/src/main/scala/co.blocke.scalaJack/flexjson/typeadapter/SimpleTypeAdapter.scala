package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.universe.{Type, TypeTag}

abstract class SimpleTypeAdapter[T](implicit valueTypeTag: TypeTag[T]) extends TypeAdapter[T] with TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) =
    if (tpe =:= valueTypeTag.tpe) {
      Some(this)
    } else {
      None
    }

}
