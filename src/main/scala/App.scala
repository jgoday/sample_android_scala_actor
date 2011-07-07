package jgoday.sample

import scala.actors.Actor._
import scala.actors.Actor

import android.app.Application

class App extends Application {
    val actor = new SampleActor

    override def onCreate(): Unit = {
        actor.start
    }
}


case class Pong(count: Int)
case class Ping(a: Actor)

class SampleActor extends Actor with L {
    private var count = 0
    def act() {
        loop {
            receive {
                case Ping(a) => {
                    _d("Receive a ping !")

                    count = count + 1
                    a ! Pong(count)
                }
            }
        }
    }
}

