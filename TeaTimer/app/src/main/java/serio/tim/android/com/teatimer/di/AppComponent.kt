package serio.tim.android.com.teatimer.di

import dagger.Component
import serio.tim.android.com.teatimer.addtimer.AddTimerActivity
import serio.tim.android.com.teatimer.edittimer.EditTimerActivity
import serio.tim.android.com.teatimer.timer.TimerActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(activity: TimerActivity)
    fun inject(activity: AddTimerActivity)
    fun inject(activity: EditTimerActivity)
}