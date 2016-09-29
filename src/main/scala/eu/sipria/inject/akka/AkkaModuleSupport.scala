package eu.sipria.inject.akka

import java.lang.reflect.Method
import javax.inject.Provider

import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.{AbstractModule, Binder}
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names

import scala.reflect.ClassTag

trait AkkaModuleSupport { _: AbstractModule =>

  private def binder: Binder = {
    val method: Method = classOf[AbstractModule].getDeclaredMethod("binder")
    if (!method.isAccessible) {
      method.setAccessible(true)
    }
    method.invoke(this).asInstanceOf[Binder]
  }

  def bindActor[T <: Actor: ClassTag](name: String, props: Props => Props = identity): Unit = {
    binder.bind(classOf[ActorRef])
      .annotatedWith(Names.named(name))
      .toProvider(providerOf[T](name, props))
      .asEagerSingleton()
  }

  def bindActorFactory[ActorClass <: Actor: ClassTag, FactoryClass: ClassTag](): Unit = {
    binder.install(
      new FactoryModuleBuilder()
        .implement(classOf[Actor], implicitly[ClassTag[ActorClass]].runtimeClass.asInstanceOf[Class[_ <: Actor]])
        .build(implicitly[ClassTag[FactoryClass]].runtimeClass)
    )
  }

  def providerOf[T <: Actor: ClassTag](name: String, props: Props => Props = identity): Provider[ActorRef] = {
    new ActorRefProvider(name, props)
  }
}
