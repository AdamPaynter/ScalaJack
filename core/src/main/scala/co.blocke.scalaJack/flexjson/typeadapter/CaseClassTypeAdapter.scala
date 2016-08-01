package co.blocke.scalajack.flexjson.typeadapter

import co.blocke.scalajack.flexjson.{Context, JsonReader, JsonWriter, NothingJsonReader, TokenType, TypeAdapter, TypeAdapterFactory}

import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.{MethodMirror, Type}

object CaseClassTypeAdapter extends TypeAdapterFactory {

  override def apply(tpe: Type, context: Context) = {
    val classSymbol = tpe.typeSymbol.asClass
    if (classSymbol.isCaseClass) {

      tpe.typeArgs
      tpe.typeParams

      println(s"$classSymbol is a case class")
      //            val instance: Any = currentMirror.reflectClass(classSymbol).reflectConstructor(classSymbol.primaryConstructor.asMethod).apply("a", "b", "c")

      // Support lots of parameters
      val primaryConstructor = classSymbol.primaryConstructor.asMethod

      println("case class")

      val parameters: List[(String, TypeAdapter[_])] = primaryConstructor.paramLists flatMap { parameterList ⇒
        parameterList map { parameter ⇒
          (parameter.name.decodedName.toString, context.adapter(parameter.infoIn(tpe).substituteTypes(tpe.typeConstructor.typeParams, tpe.typeArgs)))
        }
      }
      println(s"Parameters: $parameters")

      Some(new CaseClassTypeAdapter(currentMirror.reflectClass(classSymbol).reflectConstructor(primaryConstructor), parameters))
    } else {
      None
    }
  }

}

case class CaseClassTypeAdapter(constructorMirror: MethodMirror,
                                parameters: List[(String, TypeAdapter[_])]) extends TypeAdapter[Any] {

  override def read(reader: JsonReader): Any = {
    reader.nextTokenType match {
      case TokenType.BeginObject ⇒
        reader.beginObject()

        val parameterValues = parameters map {
          case (name, valueTypeAdapter) ⇒
            var value: Any = null

            println(s"TRYING TO FIND A VALUE FOR PARAMETER $name...")

            var foundMember = false

            reader.markPosition()
            while (reader.hasMoreMembers && !foundMember) {
              val memberName = reader.nextString()
              println(s"Encountered $memberName")
              if (memberName == name) {
                value = valueTypeAdapter.read(reader).asInstanceOf[AnyRef]
                foundMember = true
              } else {
                reader.skipNextValue()
              }
            }

            if (!foundMember) {
              value = valueTypeAdapter.read(NothingJsonReader)
            }

            reader.resetPosition()

            println(s"Parameter $name value: $value")
            println()

            value
        }

        val value = constructorMirror.apply(parameterValues: _*)

//        reader.skipNextValue()
//        reader.endObject()

        // end current object
        var depth = 1
        while (depth > 0) {
          reader.moveNext() match {
            case TokenType.BeginObject ⇒
              depth += 1
            case TokenType.EndObject ⇒
              depth -= 1

            case TokenType.BeginArray ⇒
              depth += 1
            case TokenType.EndArray ⇒
              depth -= 1

            case _ ⇒
              // no change in depth
          }
        }

        value
    }
  }

  override def write(value: Any, writer: JsonWriter): Unit = ???

}
