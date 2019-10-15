package eu.sipria.inject.akka

import javax.inject.{Inject, Provider}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.google.inject.Injector

import scala.reflect.ClassTag

protected class ActorRefProvider[T <: Actor: ClassTag](name: String, props: Props => Props) extends Provider[ActorRef] {

  @Inject private var actorSystem: ActorSystem = _
  @Inject private var injector: Injector = _

  lazy val get: ActorRef = {
    val creation = Props(injector.getInstance(implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]))
    actorSystem.actorOf(props(creation), name)
  }
}
