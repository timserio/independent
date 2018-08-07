package serio.tim.android.com.pianohelper.home

import android.view.View

class HomeActivityPresenter(view: HomeActivityContract.HomeView) : HomeActivityContract.HomePresenter {

    private var view: HomeActivityContract.HomeView

    init {
        this.view = view
        view.initView()
    }

    override fun chordsOnClick(v: View) {
        if (view != null){
            view.launchChordActivity()
        }
    }

    override fun scalesOnClick(v: View) {
        if (view != null){
            view.launchScaleActivity()
        }
    }

}