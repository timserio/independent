package serio.tim.android.com.teatimer.timer

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import serio.tim.android.com.teatimer.data.TimerRepository

import org.mockito.Mockito.verify
import serio.tim.android.com.teatimer.util.TimerInfo

class TimerPresenterTest {
    @Mock
    private lateinit var view: TimerContract.View

    @Mock
    private lateinit var repository: TimerRepository

    private lateinit var presenter: TimerPresenter

    @Before
    fun setUpTimerPresenter() {
        MockitoAnnotations.initMocks(this)
        presenter = TimerPresenter(repository, view)
    }

    @Test
    fun clickOnStart() {
        presenter.onStartButtonClicked()

        verify(view).startTimer()
    }

    @Test
    fun clickOnPause() {
        presenter.onPauseButtonClicked()

        verify(view).cancelTimer()
    }

    @Test
    fun clickOnStop() {
        presenter.onStopButtonClicked()

        verify(view).cancelTimer()
        verify(view).updateTimerProgress(0)
        verify(view).updateTimerText()
    }

    @Test
    fun onTimerTickedTest() {
        val p0 = 60000L
        TimerInfo.timerLength = 180L
        TimerInfo.currTimerLength = p0 / 1000
        presenter.onTimerTick(p0)

        verify(view).updateTimerText()
        verify(view).updateTimerProgress(120)
    }

    @Test
    fun onTimerFinishedTest() {
        presenter.onTimerFinished()
        verify(view).updateTimerText()
        verify(view).updateTimerProgress(0)
        verify(view).playSound()
    }

    @Test
    fun updateTimerTextTest() {
        TimerInfo.currTimerLength = 180L
        Assert.assertEquals("3:00", presenter.updateTimerText())
    }

    @Test
    fun updateWaterTempTextTest() {
        TimerInfo.waterTemp = 175L
        Assert.assertEquals("175", presenter.updateWaterTempText())
    }

    @Test
    fun getCurrProgressTest() {
        TimerInfo.timerLength = 180L
        TimerInfo.currTimerLength = 120L
        Assert.assertEquals(60, presenter.getCurrProgress())
    }

    @Test
    fun startTimerOnResumeTest() {
        TimerInfo.state = TimerInfo.TimerState.Running
        presenter.startTimerOnResume()
        verify(view).startTimer()
    }

    @Test
    fun onPauseStartServiceTest() {
        TimerInfo.state = TimerInfo.TimerState.Running
        presenter.onPauseStartService()
        verify(view).cancelTimer()
        verify(view).startService()
    }
}