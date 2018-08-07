package serio.tim.android.com.pianohelper.home

interface HomeActivityContract {
    interface HomeView {
        fun initView()
        fun launchChordActivity()
        fun launchScaleActivity()
    }
    interface HomePresenter {
        fun chordsOnClick(view: android.view.View)
        fun scalesOnClick(view: android.view.View)
    }
}