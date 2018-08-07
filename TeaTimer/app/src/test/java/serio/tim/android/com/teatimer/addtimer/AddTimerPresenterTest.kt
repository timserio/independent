package serio.tim.android.com.teatimer.addtimer

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import serio.tim.android.com.teatimer.data.Timer
import serio.tim.android.com.teatimer.data.TimerRepository
import java.lang.reflect.Field

class AddTimerPresenterTest {
    @Mock
    private lateinit var view: AddTimerContract.View

    @Mock
    private lateinit var repository: TimerRepository

    private lateinit var presenter: AddTimerPresenter

    @Before
    fun setUpAddTimerPresenter() {
        MockitoAnnotations.initMocks(this)
        presenter = AddTimerPresenter(repository, view)
    }

    @Test
    fun saveTimerTest() {
        presenter.onNumPickerMinutesValueChanged(0)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        presenter.onNumPickerDegreeHundredsValueChanged(1)
        presenter.onNumPickerDegreeTensValueChanged(8)
        presenter.onNumPickerDegreeOnesValueChanged(0)
        presenter.saveTimer("Green")
        verify(view).showZeroTimerLengthError()

        presenter.onNumPickerMinutesValueChanged(1)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        presenter.onNumPickerDegreeHundredsValueChanged(0)
        presenter.onNumPickerDegreeTensValueChanged(0)
        presenter.onNumPickerDegreeOnesValueChanged(0)
        presenter.saveTimer("Green")
        verify(view).showZeroWaterTempError()

        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        presenter.onNumPickerDegreeHundredsValueChanged(1)
        presenter.onNumPickerDegreeTensValueChanged(7)
        presenter.onNumPickerDegreeOnesValueChanged(5)
        presenter.saveTimer("Green Tea")
        val timer: Timer = Timer("Green Tea", 180L, 175L)
        verify(repository).saveTimer(timer)
        verify(view).finishAdd()
    }

    @Test
    fun checkForEmptyTeaNameErrorTest() {
        presenter.checkForEmptyTeaNameError("")
        verify(view).showEmptyTeaNameError()
        verify(view).disableButton()

        presenter.checkForEmptyTeaNameError("Purple Tea")
        verify(view).enableButton()
    }

    @Test
    fun checkForMaxTeaNameLengthErrorTest() {
        presenter.checkForMaxTeaNameLengthError(30)
        verify(view).disableButton()

        presenter.checkForMaxTeaNameLengthError(5)
    }

    @Test
    fun onNumPickerMinutesValueChangedTest() {
        presenter.onNumPickerMinutesValueChanged(2)
        val field: Field = presenter.javaClass.getDeclaredField("numbrMins")
        field.isAccessible = true
        Assert.assertEquals(2, field.get(presenter))
    }

    @Test
    fun onNumPickerSecondsValueChangedTest() {
        presenter.onNumPickerSecondsValueChanged(3)
        val field: Field = presenter.javaClass.getDeclaredField("numbrSecs")
        field.isAccessible = true
        Assert.assertEquals(3, field.get(presenter))

    }

    @Test
    fun onNumPickerSecondsOnesPlaceValueChangedTest() {
        presenter.onNumPickerSecondsOnesPlaceValueChanged(4)
        val field: Field = presenter.javaClass.getDeclaredField("numbrSecsOnes")
        field.isAccessible = true
        Assert.assertEquals(4, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeHundredsValueChangedTest() {
        presenter.onNumPickerDegreeHundredsValueChanged(1)
        val field: Field = presenter.javaClass.getDeclaredField("degreeHundreds")
        field.isAccessible = true
        Assert.assertEquals(1, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeTensValueChangedTest() {
        presenter.onNumPickerDegreeTensValueChanged(2)
        val field: Field = presenter.javaClass.getDeclaredField("degreeTens")
        field.isAccessible = true
        Assert.assertEquals(2, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeOnesValueChangedTest() {
        presenter.onNumPickerDegreeOnesValueChanged(3)
        val field: Field = presenter.javaClass.getDeclaredField("degreeOnes")
        field.isAccessible = true
        Assert.assertEquals(3, field.get(presenter))
    }

    @Test
    fun initializeViews() {
        presenter.initializeViews("Purple")
        verify(view).showTimerLengthInput()
        verify(view).showWaterTempInput()
        verify(view).enableButton()

        presenter.initializeViews("")

        verify(view).showEmptyTeaNameError()
        verify(view).disableButton()
    }

    @Test
    fun afterTextChanged() {
        // calls view.showEmptyTeaNameError() and  view.disableButton()
        presenter.afterTextChanged("", 0)

        // calls view.enableButton() in checkForEmptyTeaNameError(text) and  view.disableButton() in view.checkForMaxTeaNameLengthError(length)
        presenter.afterTextChanged("abcdefghijklmnopqrstuvwxyzabcd", 30)

        // calls view.enableButton()
        presenter.afterTextChanged("Purple", 6)

        verify(view).showEmptyTeaNameError()
        verify(view, times(2)).disableButton()
        verify(view, times(2)).enableButton()
    }
}