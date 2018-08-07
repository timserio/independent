package serio.tim.android.com.teatimer.edittimer

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import serio.tim.android.com.teatimer.data.Timer
import serio.tim.android.com.teatimer.data.TimerRepository
import java.lang.reflect.Field

class EditTimerPresenterTest {
    @Mock
    private lateinit var view: EditTimerContract.View

    @Mock
    private lateinit var repository: TimerRepository

    private lateinit var presenter: EditTimerPresenter

    @Before
    fun setUpEditTimerPresenter() {
        MockitoAnnotations.initMocks(this)
        presenter = EditTimerPresenter(repository, view)
    }

    @Test
    fun setTeaNameTest() {
        presenter.setNameOfTea("Yellow Tea")
        val field: Field = presenter.javaClass.getDeclaredField("nameOfTea")
        field.isAccessible = true
        Assert.assertEquals("Yellow Tea", field.get(presenter))
    }

    @Test
    fun getNameOfTeaTest() {
        presenter.setNameOfTea("Chamomile Tea")
        val field: Field = presenter.javaClass.getDeclaredField("nameOfTea")
        field.isAccessible = true
        Assert.assertEquals("Chamomile Tea", field.get(presenter))
    }

    @Test
    fun setTeaKeysSizeTest() {
        presenter.setTeaKeysSize(10)
        val field: Field = presenter.javaClass.getDeclaredField("teaKeysSize")
        field.isAccessible = true
        Assert.assertEquals(10, field.get(presenter))
    }

    @Test
    fun setTotalSecondsTest() {
        presenter.setTotalSeconds(60L)
        val field: Field = presenter.javaClass.getDeclaredField("totalSeconds")
        field.isAccessible = true
        Assert.assertEquals(60L, field.get(presenter))
    }

    @Test
    fun setWaterTempTest() {
        presenter.setWaterTemp(180L)
        val field: Field = presenter.javaClass.getDeclaredField("waterTemp")
        field.isAccessible = true
        Assert.assertEquals(180L, field.get(presenter))
    }

    @Test
    fun setUpTimerInputTest() {
        presenter.setTotalSeconds(120)

        presenter.setTimerInputValues()

        Assert.assertEquals(2, presenter.getNumbrMins())

        Assert.assertEquals(0, presenter.getSecsTens())

        Assert.assertEquals(0, presenter.getSecsOnes())

        Assert.assertEquals(120L, presenter.getTotalSecs())
    }

    @Test
    fun setUpWaterTempInputTest() {
        presenter.setWaterTemp(200)

        presenter.setWaterTempInputValues()

        Assert.assertEquals(2, presenter.getDegreeHundreds())

        Assert.assertEquals(0, presenter.getDegreeTens())

        Assert.assertEquals(0, presenter.getDegreeOnes())

        Assert.assertEquals(200L, presenter.getTotalDegrees())
    }

    @Test
    fun getNumbrMinsTest() {
        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)

        Assert.assertEquals(3, presenter.getNumbrMins())
    }

    @Test
    fun getSecsTensTest() {
        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)

        Assert.assertEquals(0, presenter.getSecsTens())
    }

    @Test
    fun getSecsOnesTest() {
        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)

        Assert.assertEquals(0, presenter.getSecsOnes())
    }

    @Test
    fun onEditButtonTest() {
        presenter.onNumPickerMinutesValueChanged(0)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)
        presenter.onEditButtonClicked()
        verify(view).showZeroTimerLengthError()

        presenter.setNameOfTea("Yellow Tea")
        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)
        presenter.onEditButtonClicked()
        val timer = Timer("Yellow Tea", 180L, 180L)
        verify(repository).updateTimer(timer)
        verify(view).finishEdit()
    }

    @Test
    fun getTotalSecsTest() {
        presenter.onNumPickerMinutesValueChanged(3)
        presenter.onNumPickerSecondsValueChanged(0)
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)

        Assert.assertEquals(180L, presenter.getTotalSecs())
    }

    @Test
    fun getDegreeHundredsTest() {
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)

        Assert.assertEquals(1, presenter.getDegreeHundreds())
    }

    @Test
    fun getDegreeTensTest() {
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)

        Assert.assertEquals(8, presenter.getDegreeTens())
    }

    @Test
    fun getDegreeOnesTest() {
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)

        Assert.assertEquals(0, presenter.getDegreeOnes())
    }

    @Test
    fun getTotalDegreesTest() {
        presenter.onNumPickerDegreeHundredsChanged(1)
        presenter.onNumPickerDegreeTensChanged(8)
        presenter.onNumPickerDegreeOnesChanged(0)

        Assert.assertEquals(180L, presenter.getTotalDegrees())
    }

    @Test
    fun onNumPickerMinutesValueChangedTest() {
        presenter.onNumPickerMinutesValueChanged(3)
        val field: Field = presenter.javaClass.getDeclaredField("numbrMins")
        field.isAccessible = true
        Assert.assertEquals(3, field.get(presenter))
    }

    @Test
    fun onNumPickerSecondsValueChangedTest() {
        presenter.onNumPickerSecondsValueChanged(0)
        val field: Field = presenter.javaClass.getDeclaredField("secsTens")
        field.isAccessible = true
        Assert.assertEquals(0, field.get(presenter))
    }

    @Test
    fun onNumPickerSecondsOnesValueChangedTest() {
        presenter.onNumPickerSecondsOnesPlaceValueChanged(0)
        val field: Field = presenter.javaClass.getDeclaredField("secsOnes")
        field.isAccessible = true
        Assert.assertEquals(0, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeHundredsChangedTest() {
        presenter.onNumPickerDegreeHundredsChanged(1)
        val field: Field = presenter.javaClass.getDeclaredField("degreesHundreds")
        field.isAccessible = true
        Assert.assertEquals(1, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeTensChangedTest() {
        presenter.onNumPickerDegreeTensChanged(8)
        val field: Field = presenter.javaClass.getDeclaredField("degreesTens")
        field.isAccessible = true
        Assert.assertEquals(8, field.get(presenter))
    }

    @Test
    fun onNumPickerDegreeOnesChangedTest() {
        presenter.onNumPickerDegreeOnesChanged(0)
        val field: Field = presenter.javaClass.getDeclaredField("degreesOnes")
        field.isAccessible = true
        Assert.assertEquals(0, field.get(presenter))
    }

    @Test
    fun onCreateTest() {
        presenter.onCreate()
        verify(view).showTeaName(presenter.getNameOfTea())

        verify(view).showTimerLengthInput()

        verify(view).showMinsInput(presenter.getNumbrMins())

        verify(view).showSecsTensInput(presenter.getSecsTens())

        verify(view).showSecsOnesInput(presenter.getSecsOnes())

        verify(view).showWaterTempInput()

        verify(view).showDegreeHundredsInput(presenter.getDegreeHundreds())

        verify(view).showDegreeTensInput(presenter.getDegreeTens())

        verify(view).showDegreeOnesInput(presenter.getDegreeOnes())
    }
}