package jgoday.sample

import _root_.android.util.{Log}

object LogUtil {
    var enabled: () => Boolean = () => true
}

/**
 * Wrapper arround android.util.Log
 */
trait L {
    private var _enabled: Option[Boolean] = None

    def logEnabled: Boolean = _enabled match {
        case Some(value) => value
        case _ => LogUtil.enabled()
    }

    def logEnabled_=(value: Boolean) = _enabled = Some(value)

    def tagName: String = this.getClass.getSimpleName

    def _d(msg: String) = if (logEnabled) Log.d(tagName, msg)
    def _e(msg: String) = if (logEnabled) Log.e(tagName, msg)
    def _i(msg: String) = if (logEnabled) Log.i(tagName, msg)
}
