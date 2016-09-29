package eu.sipria.inject.akka.pong

import javax.inject.Inject

import akka.actor.Actor
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import eu.sipria.inject.akka.{InjectedActorSupport, TestString}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext

class Ping @Inject() (pongFactory: PongFactory)(implicit ec: ExecutionContext) extends Actor with InjectedActorSupport {

  implicit val timeout: akka.util.Timeout = Timeout(5 seconds)

  val pong = injectedChild(pongFactory.create(TestString.public), "pong")

  def receive = {
    case PingMessage =>
      pipe(pong ? PingMessage) to sender()
  }
}

case object PingMessage
