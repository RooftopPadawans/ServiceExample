package com.flknlabs.serviceexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

const val INTENT_FILTER_NAME = "ClockService"
const val CURRENT_TIME = "CURRENT_TIME"
const val STOP_TIMER = "STOP_TIMER"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val clockIntent = Intent(this, ClockService::class.java)
        startService(clockIntent)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                mMessageReceiver,
                IntentFilter(INTENT_FILTER_NAME)
            )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()

        val clockIntent = Intent(this, ClockService::class.java)
        stopService(clockIntent)
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val currentTime = intent.getLongExtra(CURRENT_TIME, 0)

            val txtTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(currentTime)),
                TimeUnit.MILLISECONDS.toSeconds(currentTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentTime)))

            tvClock.text = txtTime
        }
    }
}