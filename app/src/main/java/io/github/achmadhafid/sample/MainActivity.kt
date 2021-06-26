package io.github.achmadhafid.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.achmadhafid.datetimeutils.THIS_TIME
import io.github.achmadhafid.datetimeutils.TODAY
import io.github.achmadhafid.datetimeutils.withIndFormat

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.tv_now)?.text =
            "Current Date: ${TODAY withIndFormat "EEEE, dd MMMM yyyy"}\n" +
                    "Current Time: ${THIS_TIME withIndFormat "HH:mm:ss"}"
    }

}
