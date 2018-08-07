package serio.tim.android.com.solunar

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.View
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var location: SimpleLocation
    private lateinit var wifiManager: WifiManager
    private lateinit var myPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        location = SimpleLocation(this@MainActivity)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        myPrefs = getSharedPreferences("my_preferred_choices", Activity.MODE_PRIVATE)
        editor = myPrefs.edit()

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        }

        if(!(myPrefs.getString("date", "").equals(""))) {
            text_date.text = myPrefs.getString("date", "")
            text_first_major.text = "${myPrefs.getString("major1Start", "")} to ${myPrefs.getString("major1Stop", "")}"
            text_first_minor.text = "${myPrefs.getString("minor1Start", "")} to ${myPrefs.getString("minor1Stop", "")}"
            text_second_major.text = "${myPrefs.getString("major2Start", "")} to ${myPrefs.getString("major2Stop", "")}"
            text_second_minor.text = "${myPrefs.getString("minor2Start", "")} to ${myPrefs.getString("minor2Stop", "")}"
        }
    }

    private fun getCurrentTimezoneOffset(): String {

        val tz = TimeZone.getDefault()
        val cal = GregorianCalendar.getInstance(tz)
        val offsetInMillis = tz.getOffset(cal.timeInMillis)

        var offset = Math.abs(offsetInMillis / 3600000).toString()
        offset = (if (offsetInMillis >= 0) "+" else "-") + offset

        return offset
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    fun getButtonClicked(view: View) {
        val builder = Retrofit.Builder().baseUrl("https://api.solunar.org").addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()

        val client = retrofit.create<SolunarApiClient>(SolunarApiClient::class.java!!)

        val latitude = location.getLatitude()
        val longitude = location.getLongitude()
        editor.putLong("latitude", latitude.toLong())
        editor.putLong("longitude", longitude.toLong())

        val date = Date()

        val strDateFormat = "yyyyMMdd"

        val sdf = SimpleDateFormat(strDateFormat)

        val currDate = sdf.format(date)

        val response = client.getSolunar("${latitude},${longitude},${currDate},${getCurrentTimezoneOffset()}").enqueue(object : Callback<SolunarData> {
            override fun onResponse(call: Call<SolunarData>?, response: Response<SolunarData>?) {
                val body = response!!.body()

                val contentResolver: ContentResolver = baseContext.contentResolver
                // Find out what the settings say about which providers are enabled
                val mode: Int = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)

                if (mode == Settings.Secure.LOCATION_MODE_OFF) {
                    if(wifiManager.isWifiEnabled) {
                        text_error.text = getString(R.string.text_error_location)
                    } else {
                        text_error.text = getString(R.string.text_error_location_wifi)
                    }
                } else if(mode != Settings.Secure.LOCATION_MODE_OFF) {
                    if(wifiManager.isWifiEnabled) {
                        text_error.text = ""
                        val sdf = SimpleDateFormat("MMMM dd, yyyy")
                        val currentDate = sdf.format(Date())
                        // make sure location and date is different from last request
                        if(!text_date.text.toString().equals(currentDate) && !(longitude.equals(myPrefs.getLong("latitude", 0L))) && !(longitude.equals(myPrefs.getLong("longitude", 0L)))) {
                            text_date.text = currentDate

                            editor.putString("date", currentDate)

                            text_first_major.text = "${timeConverter(body!!.major1Start!!)} to ${timeConverter(body!!.major1Stop!!)}"

                            editor.putString("major1Start", timeConverter(body!!.major1Start!!))
                            editor.putString("major1Stop", timeConverter(body!!.major1Stop!!))

                            text_first_minor.text = "${timeConverter(body!!.minor2Start!!)} to ${timeConverter(body!!.minor2Stop!!)}"

                            editor.putString("minor2Start", timeConverter(body!!.minor2Start!!))
                            editor.putString("minor2Stop", timeConverter(body!!.minor2Stop!!))

                            text_second_major.text = "${timeConverter(body!!.major2Start!!)} to ${timeConverter(body!!.major2Stop!!)}"

                            editor.putString("major2Start", timeConverter(body!!.major2Start!!))
                            editor.putString("major2Stop", timeConverter(body!!.major2Stop!!))

                            text_second_minor.text = "${timeConverter(body!!.minor1Start!!)} to ${timeConverter(body!!.minor1Stop!!)}"

                            editor.putString("minor1Start", timeConverter(body!!.minor1Start!!))
                            editor.putString("minor1Stop", timeConverter(body!!.minor1Stop!!))

                            editor.commit()
                        }
                    } else {
                        text_error.text = getString(R.string.text_error_wifi)
                    }
                }
            }

            override fun onFailure(call: Call<SolunarData>?, t: Throwable?) {

            }
        })
    }

    private fun timeConverter(time: String) : String {
        val cal: Calendar = Calendar.getInstance()

        if(time[0].equals('-')) {
            cal.set(Calendar.HOUR_OF_DAY, time.substring(1,3).toInt())
            cal.set(Calendar.MINUTE, time.substring(4,6).toInt())
        } else {
            cal.set(Calendar.HOUR_OF_DAY, time.substring(0,2).toInt())
            cal.set(Calendar.MINUTE, time.substring(3,5).toInt())
        }

        var hour = 0
        if(cal.get(Calendar.HOUR).equals(0)) {
            hour = 12
        } else {
            hour = cal.get(Calendar.HOUR)
        }

        var amPm = "AM"
        if(cal.get(Calendar.AM_PM).equals(1)) {
            amPm = "PM"
        }

        return "${hour}:${String.format("%02d", cal.get(Calendar.MINUTE))} ${amPm}"
    }

    override fun onResume() {
        super.onResume()

        if(ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        }

        location.beginUpdates()
    }

    override fun onPause() {
        location.endUpdates()

        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)

            when (wifiStateExtra) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiManager.isWifiEnabled = true
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiManager.isWifiEnabled = false
                }
            }
        }
    }

}
