package eu.sipria.inject.akka

import java.util.UUID

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.google.inject.name.Names
import com.google.inject.{Guice, Key}
import eu.sipria.inject.akka.pong._
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.ExecutionContext

object TestString {
  val secret = UUID.randomUUID().toString
  val public = UUID.randomUUID().toString
}

class GuiceAkkaSpec extends TestKit(ActorSystem("TestActorSystem")) with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "Guice" should "create all actors correctly" in {
    val injector = Guice.createInjector(new AkkaModule)
    val ping = injector.getInstance(Key.get(classOf[ActorRef], Names.named("ping")))

    ping ! PingMessage

    expectMsg(PongMessage(TestString.secret, TestString.public))
  }
}

class AkkaModule(implicit system: ActorSystem) extends ScalaModule with AkkaModuleSupport {

  override def configure(): Unit = {
    bind[ActorSystem].toInstance(system)
    bind[ActorRefFactory].toInstance(system)
    bind[ExecutionContext].toInstance(system.dispatcher)

    bind[String].annotatedWithName("named.secret").toInstance(TestString.secret)

    bindActor[Ping]("ping")
    bindActorFactory[Pong, PongFactory]()
  }
}
