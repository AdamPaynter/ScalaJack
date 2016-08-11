package co.blocke.scalajack.flexjson

import co.blocke.scalajack.flexjson.handler.Logging
import co.blocke.scalajack.flexjson.typeadapter.{AnyValTypeAdapter, BooleanTypeAdapter, ByteTypeAdapter, CaseClassTypeAdapter, CharTypeAdapter, DateTimeTypeAdapter, DoubleTypeAdapter, FloatTypeAdapter, IntTypeAdapter, JavaWrappedByteTypeAdapter, JavaWrappedDoubleTypeAdapter, JavaWrappedFloatTypeAdapter, JavaWrappedIntegerTypeAdapter, JavaWrappedLongTypeAdapter, JavaWrappedShortTypeAdapter, ListTypeAdapter, LongTypeAdapter, MapTypeAdapter, OptionTypeAdapter, SetTypeAdapter, ShortTypeAdapter, StringTypeAdapter, TraitTypeAdapterFactory, TryTypeAdapter, TypeHintBinding, UUIDTypeAdapter}
import co.blocke.scalajack.{FlavorKind, JackFlavor, ScalaJack, VisitorContext}

import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.{Type, TypeTag}

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

        type MemberName = String
        type TypeHint = String

        val typeHintMemberNamesByDeclaredType: Map[Type, MemberName] = vc.hintMap filter { _._1 != "default" } map {
          case (traitTypeName, typeHintMemberName) ⇒ {
            val traitType: Type = currentMirror.staticClass(traitTypeName).info
            traitType → typeHintMemberName
          }
        }

        val defaultTypeHintMemberName: Option[MemberName] = vc.hintMap.get("default")

        val typeHintToConcreteTypeMappingsByTraitType: Map[Type, TypeHint ⇒ Type] = vc.hintValueRead map {
          case (traitTypeName, fn) ⇒
            val traitType: Type = currentMirror.staticClass(traitTypeName).info.dealias
            traitType → ((typeHint: TypeHint) ⇒ {
              val concreteClassName = fn(typeHint)
              currentMirror.staticClass(concreteClassName).info
            })
        }

        val defaultTypeHintToConcreteTypeMapping: (TypeHint ⇒ Type) =
          hint ⇒ currentMirror.staticClass(hint).info

        val concreteTypeToTypeHintMappingsByTraitType: Map[Type, Type ⇒ TypeHint] = vc.hintValueRender map {
          case (traitTypeName, fn) ⇒
            val traitType: Type = currentMirror.staticClass(traitTypeName).info
            traitType → ((concreteTypeName: Type) ⇒ fn(concreteTypeName.typeSymbol.asClass.name.encodedName.toString))
        }

        val defaultConcreteTypeToTypeHintMapping: (Type ⇒ TypeHint) =
          tpe ⇒ tpe.typeSymbol.fullName

        context.registerFactory(UUIDTypeAdapter)
        context.registerFactory(CharTypeAdapter)
        context.registerFactory(ByteTypeAdapter)
        context.registerFactory(JavaWrappedByteTypeAdapter)
        context.registerFactory(ShortTypeAdapter)
        context.registerFactory(JavaWrappedShortTypeAdapter)
        context.registerFactory(IntTypeAdapter)
        context.registerFactory(JavaWrappedIntegerTypeAdapter)
        context.registerFactory(LongTypeAdapter)
        context.registerFactory(JavaWrappedLongTypeAdapter)
        context.registerFactory(FloatTypeAdapter)
        context.registerFactory(JavaWrappedFloatTypeAdapter)
        context.registerFactory(DoubleTypeAdapter)
        context.registerFactory(JavaWrappedDoubleTypeAdapter)
        context.registerFactory(BooleanTypeAdapter)
        context.registerFactory(StringTypeAdapter)
        context.registerFactory(DateTimeTypeAdapter)
        context.registerFactory(AnyValTypeAdapter)
        context.registerFactory(CaseClassTypeAdapter)
        context.registerFactory(TraitTypeAdapterFactory({ (tpe: Type) ⇒
          val memberName = typeHintMemberNamesByDeclaredType.get(tpe).orElse(defaultTypeHintMemberName)

          println(vc)

          val typeHintToConcreteTypeMapping: TypeHint ⇒ Type =
            typeHintToConcreteTypeMappingsByTraitType.getOrElse(tpe, defaultTypeHintToConcreteTypeMapping)

          val concreteTypeToTypeHintMapping: Type ⇒ TypeHint =
            concreteTypeToTypeHintMappingsByTraitType.getOrElse(tpe, defaultConcreteTypeToTypeHintMapping)

          (memberName.get, new TypeHintBinding {
            override def hintToType(hint: String): Type = {
              typeHintToConcreteTypeMapping(hint)
            }

            override def typeToHint(tpe: Type): String = {
              concreteTypeToTypeHintMapping(tpe)
            }
          })
        }))
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
