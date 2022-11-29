package com.flknlabs.serviceexample

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager




class ClockService: Service() {
    override fun onBind(p0: Intent?): IBinder? { return null }

    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //Will create the clock
        object : CountDownTimer(300000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val intent = Intent(INTENT_FILTER_NAME)
                intent.putExtra(CURRENT_TIME, millisUntilFinished)
                broadcaster?.sendBroadcast(intent)
            }

            override fun onFinish() {

            }
        }.start()


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        //Will stop the clock
        Log.d("ClockService", "Service stops")
    }
}