package co.blocke.scalajack.flexjson

trait TypeAdapter[T] {

  def read(reader: JsonReader): T

  def write(value: T, writer: JsonWriter): Unit

}
