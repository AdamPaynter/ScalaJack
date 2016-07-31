package co.blocke.scalajack.flexjson

import co.blocke.scalajack.flexjson.handler.Logging
import co.blocke.scalajack.flexjson.typeadapter.BooleanTypeAdapter
import co.blocke.scalajack.flexjson.typeadapter.{AnyValTypeAdapter, CaseClassTypeAdapter, DateTimeTypeAdapter, IntTypeAdapter, OptionTypeAdapter, StringTypeAdapter, TryTypeAdapter}
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

        val reader = new JsonReader(tokenTable)

        val context = new Context

        context.registerFactory(IntTypeAdapter)
        context.registerFactory(BooleanTypeAdapter)
        context.registerFactory(StringTypeAdapter)
        context.registerFactory(DateTimeTypeAdapter)
        context.registerFactory(AnyValTypeAdapter)
        context.registerFactory(CaseClassTypeAdapter)
        context.registerFactory(OptionTypeAdapter)
        context.registerFactory(TryTypeAdapter)

        val value = context.read[T](reader)
        println(s"Deserialized value: $value")
        value
      }

    }

  }

}
