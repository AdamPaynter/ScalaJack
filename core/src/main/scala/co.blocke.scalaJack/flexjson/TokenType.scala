package co.blocke.scalajack.flexjson

object TokenType extends Enumeration {
  type TokenType = Value
  val BeginObject, EndObject, BeginArray, EndArray, String, True, False, Null, Number, Nothing = Value
}
