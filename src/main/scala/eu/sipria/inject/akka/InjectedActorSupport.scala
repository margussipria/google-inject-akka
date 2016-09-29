package eu.sipria.inject.akka

import akka.actor.{Actor, ActorContext, ActorRef, Props}

trait InjectedActorSupport {

  def injectedChild(create: => Actor, name: String, props: Props => Props = identity)(implicit context: ActorContext): ActorRef = {
    context.actorOf(props(Props(create)), name)
  }
}
