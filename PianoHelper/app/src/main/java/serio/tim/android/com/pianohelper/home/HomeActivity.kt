package serio.tim.android.com.pianohelper.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import serio.tim.android.com.pianohelper.R
import serio.tim.android.com.pianohelper.chord.ChordActivity
import serio.tim.android.com.pianohelper.scale.ScaleActivity

class HomeActivity : AppCompatActivity(), HomeActivityContract.HomeView {

    private lateinit var presenter: HomeActivityContract.HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.title = ""
        presenter = HomeActivityPresenter(this)
    }

    override fun initView() {

        button_home_chords.setOnClickListener {
            presenter.chordsOnClick(it)
        }

        button_home_scales.setOnClickListener {
            presenter.scalesOnClick(it)
        }
    }


    override fun launchChordActivity(){
        val i = Intent(this@HomeActivity, ChordActivity::class.java)
        startActivity(i)
    }

    override fun launchScaleActivity(){
        val i = Intent(this@HomeActivity, ScaleActivity::class.java)
        startActivity(i)
    }
}
