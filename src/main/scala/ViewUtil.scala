package jgoday.sample

import scala.actors.Actor._
import scala.actors.Actor

import _root_.android.app.Activity
import _root_.android.widget.Button
import _root_.android.widget.TextView
import _root_.android.view._

/**
 * Converts an activity into an actor
 * to receive events from another actors or services
 * using onReact function
 */
trait Reactive extends Actor {
    self: Activity with ActivityUtil =>

    private var reactiveFunctions: PartialFunction[Any, Unit] = _

    def act() {
        loop {
            react {
                reactiveFunctions
            }
        }
    }

    def onReact(pf: PartialFunction[Any, Unit]): Unit = {
        startReacting

        reactiveFunctions = pf
    }

    protected def onUi(block : => Unit) {
        self.runOnUiThread(new Runnable() {
            override def run() {
                block
            }
        })
    }

    private def startReacting = {
        this.start
    }
}

/**
 * Utils to interact with an android Activity
 * Find components with a Type T by id
 * Access to the main application throught app
 */
trait ActivityUtil {
    self: Activity =>

    def app: App = self.getApplication.asInstanceOf[App]

    def button(id: Int) =
        component[Button](id)

    def textView(id: Int) =
        component[TextView](id)

    def component[T](id: Int) =
        self.findViewById(id).asInstanceOf[T]
}

/**
 * Implicit conversions to implement listeners
 * for example, View onClickListener
 */
object ViewConversions {
    implicit def toClickListener(bt: Button) : ButtonViewConversions = {
        new ButtonViewConversions(bt)
    }
}

/**
 * ButtonViewConversions to execute a function with an inner OnClickListener
 */
class ButtonViewConversions(bt: Button) {
    type F = (View) => Unit

    def onClicked(block : => Unit): Unit =
        onClicked( (v: View) => block)

    def onClicked(f: F): Unit = {
        bt.setOnClickListener(
            new View.OnClickListener() {
                def onClick(v: View) {
                    f(v)
                }
            }
        )
    }
}

