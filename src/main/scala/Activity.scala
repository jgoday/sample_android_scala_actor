package jgoday.sample

import _root_.android.app.Activity
import _root_.android.os.Bundle

import jgoday.sample.ViewConversions._

class MainActivity extends Activity
                      with ActivityUtil
                      with Reactive {

    override def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        button(R.id.Button01).onClicked {
            app.actor ! Ping(this)
        }
    }

    onReact {
        case Pong(count) => onUi {
            textView(R.id.Label1).setText("Pong -> " + count)
        }
    }
}


