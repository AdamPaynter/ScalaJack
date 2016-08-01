package co.blocke.scalajack.flexjson

import scala.reflect.api.JavaUniverse
import scala.reflect.runtime.universe.{TypeSymbol, Type, TypeTag}

class Context {

  private var factories = Seq[TypeAdapterFactory]()
  private var adapters = List[(Type, TypeAdapter[_])]()

  def registerFactory(factory: TypeAdapterFactory): Unit = {
    factories = factories :+ factory
  }

  def adaptorOf[T](implicit typeTag: TypeTag[T]): TypeAdapter[T] =
    adapter(typeTag.tpe).asInstanceOf[TypeAdapter[T]]

  def adapter(tpe: Type): TypeAdapter[_] = {
    val map: Option[TypeAdapter[_]] = adapters.find(_._1 =:= tpe).map(pair ⇒ pair._2.asInstanceOf[TypeAdapter[_]])

    map.getOrElse({
      var optionalAdapter: Option[TypeAdapter[_]] = None

      for (factory ← factories if optionalAdapter.isEmpty) {
        optionalAdapter = factory(tpe, this)
      }

      val adapter = optionalAdapter.getOrElse(throw new Exception(s"Could not find any type adapter capable of serializing/deserializing values of type $tpe"))
      adapters = Tuple2[Type, TypeAdapter[_]](tpe, adapter.asInstanceOf[TypeAdapter[_]]) :: adapters
      adapter
    })
  }

  def read[T: TypeTag](reader: JsonReader): T =
    adaptorOf[T].read(reader)

  def write[T: TypeTag](value: T, writer: JsonWriter): Unit =
    adaptorOf[T].write(value, writer)

}
