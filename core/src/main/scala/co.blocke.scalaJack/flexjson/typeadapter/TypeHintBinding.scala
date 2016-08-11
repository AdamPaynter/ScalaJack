package co.blocke.scalajack.flexjson.typeadapter

import scala.reflect.runtime.universe.Type

trait TypeHintBinding {

  def hintToType(hint: String): Type

  def typeToHint(tpe: Type): String

}