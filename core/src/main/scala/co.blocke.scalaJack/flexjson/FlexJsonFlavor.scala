package co.blocke.scalajack.flexjson

import co.blocke.scalajack.flexjson.typeadapter.{AnyValTypeAdapter, BooleanTypeAdapter, ByteTypeAdapter, CaseClassTypeAdapter, CharTypeAdapter, DateTimeTypeAdapter, DoubleTypeAdapter, FloatTypeAdapter, IntTypeAdapter, JavaByteTypeAdapter, JavaDoubleTypeAdapter, JavaFloatTypeAdapter, JavaIntegerTypeAdapter, JavaLongTypeAdapter, JavaShortTypeAdapter, ListTypeAdapter, LongTypeAdapter, MapTypeAdapter, OptionTypeAdapter, SetTypeAdapter, ShortTypeAdapter, StringTypeAdapter, TraitTypeAdapter, TraitTypeAdapterFactory, TryTypeAdapter, UUIDTypeAdapter}
import co.blocke.scalajack.flexjson.handler.Logging
import co.blocke.scalajack.{FlavorKind, JackFlavor, ScalaJack, VisitorContext}
import org.joda.time.DateTime

import scala.reflect.api.JavaUniverse
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.{Type, TypeTag, typeOf}

class FlexJsonFlavor extends FlavorKind[String] {

  override def makeScalaJack: ScalaJack[String] = new ScalaJack[String] with JackFlavor[String] {

    override def rr: ReadRenderer = new ReadRenderer {

      override def render[T](value: T)(implicit declaredTypeTag: TypeTag[T], vc: VisitorContext): String = {
        ???
      }

      override def read[T](json: String)(implicit declaredTypeTag: TypeTag[T], vc: VisitorContext): T = {

        val tokenTable = new TokenTable with Logging

        val tokenizer = new Tokenizer(tokenTable)

        val source = json.toCharArray
        tokenizer.update(source, 0, source.length)

        val reader = new TokenTableJsonReader(tokenTable)

        val context = new Context

        val hintMap = vc.hintMap map {
          case (traitTypeName, typeHintMemberName) =>
            currentMirror.staticClass(traitTypeName) -> typeHintMemberName
        }

        context.registerFactory(UUIDTypeAdapter)
        context.registerFactory(CharTypeAdapter)
        context.registerFactory(ByteTypeAdapter)
        context.registerFactory(JavaByteTypeAdapter)
        context.registerFactory(ShortTypeAdapter)
        context.registerFactory(JavaShortTypeAdapter)
        context.registerFactory(IntTypeAdapter)
        context.registerFactory(JavaIntegerTypeAdapter)
        context.registerFactory(LongTypeAdapter)
        context.registerFactory(JavaLongTypeAdapter)
        context.registerFactory(FloatTypeAdapter)
        context.registerFactory(JavaFloatTypeAdapter)
        context.registerFactory(DoubleTypeAdapter)
        context.registerFactory(JavaDoubleTypeAdapter)
        context.registerFactory(BooleanTypeAdapter)
        context.registerFactory(StringTypeAdapter)
        context.registerFactory(DateTimeTypeAdapter)
        context.registerFactory(AnyValTypeAdapter)
        context.registerFactory(CaseClassTypeAdapter)
        context.registerFactory(TraitTypeAdapterFactory())
        context.registerFactory(OptionTypeAdapter)
        context.registerFactory(TryTypeAdapter)
        context.registerFactory(ListTypeAdapter)
        context.registerFactory(MapTypeAdapter)
        context.registerFactory(SetTypeAdapter)

        val value = context.read[T](reader)
        println(s"Deserialized value: $value")
        value
      }

    }

  }

}
