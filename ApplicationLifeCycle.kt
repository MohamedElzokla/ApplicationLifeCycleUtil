
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import kotlin.properties.Delegates

class ApplicationLifeCycle:Application(), Application.ActivityLifecycleCallbacks {
    private var numStarted = 0

    var isBackground = false
        set(value: Boolean) {
            Toast.makeText(this, "isBackground $value", Toast.LENGTH_SHORT).show()
            field = value
        }
    var lastActivity : Activity? = null

    companion object{
        var app : Application? = null
    }
    override fun onCreate() {
        super.onCreate()
        app = this
        registerActivityLifecycleCallbacks(this)

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        this.lastActivity = activity

    }

    override fun onActivityStarted(activity: Activity) {
        if (numStarted == 0) {
            // app went to foreground
            appDidEnterForeground()

        }
        numStarted++
    }

    override fun onActivityResumed(activity: Activity) {
        this.lastActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        numStarted--
        if (numStarted == 0) {
                appDidEnterBackground()
        }

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        print("onactivitysaveinsat")
    }

    override fun onActivityDestroyed(activity: Activity) {

    }
    fun appDidEnterForeground(){
        isBackground = false
    }
    fun appDidEnterBackground(){
        isBackground = true
    }
}

var foo: Boolean by Delegates.observable(false) { property, old, new ->
    println("$property has changed from $old to $new")
}
