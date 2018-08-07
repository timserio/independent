package serio.tim.android.com.teatimer.data

class TimerRepositoryProvider private constructor(){
    companion object {
        private var repository: TimerRepository? = null

        @Synchronized fun getRepoInstance(timerServiceApi: TimerServiceApi): TimerRepository {
            checkNotNull(timerServiceApi)
            if (null == repository) {
                repository = SqlLiteTimerRepository(timerServiceApi)
            }
            return repository as TimerRepository
        }
    }
}