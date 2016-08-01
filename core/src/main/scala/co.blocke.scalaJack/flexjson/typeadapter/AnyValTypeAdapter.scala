package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, TypeAdapter, TypeAdapterFactory, JsonWriter}

import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.{MethodMirror, Type}

object AnyValTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) = {
    val classSymbol = tpe.typeSymbol.asClass
    if (classSymbol.isDerivedValueClass) {
      println(s"$classSymbol is a value class")
      val primaryConstructor = classSymbol.primaryConstructor.asMethod

      val parameterType = primaryConstructor.paramLists.head.head.infoIn(tpe).substituteTypes(tpe.typeConstructor.typeParams, tpe.typeArgs)
      val parameterTypeAdapter = context.adapter(parameterType)

      println(s"parameter type adapter: $parameterTypeAdapter")

      Some(new AnyValTypeAdapter(currentMirror.reflectClass(classSymbol).reflectConstructor(primaryConstructor), parameterTypeAdapter))
    } else {
      None
    }
  }

}

class AnyValTypeAdapter(constructorMirror: MethodMirror, valueTypeAdapter: TypeAdapter[_]) extends TypeAdapter[Any] {

  override def read(reader: JsonReader) = {
    val value = valueTypeAdapter.read(reader)
    constructorMirror.apply(value)
  }

  override def write(value: Any, writer: JsonWriter): Unit = ???

}
