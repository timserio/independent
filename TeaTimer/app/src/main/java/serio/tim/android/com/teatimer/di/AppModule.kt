package serio.tim.android.com.teatimer.di

import dagger.Module
import dagger.Provides
import serio.tim.android.com.teatimer.data.TimerRepositoryProvider
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.data.TimerServiceApiImpl
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(): TimerRepository {
        return TimerRepositoryProvider.getRepoInstance(TimerServiceApiImpl())
    }
}