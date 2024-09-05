package com.example.servicewhole

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.math.nextTowards

/**
 * Created by Himanshu Verma on 11/06/24.
 **/
class MyService : Service() {
    /**
     * Step 6 making random number
     *
     */
    private var randomNumberGeneratorOn: Boolean = false
    private var randomNumber: Double = 0.0
    private val maxValue: Int = 100

    private fun startRandomGenerator() {
        while (randomNumberGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (randomNumberGeneratorOn) {
                    randomNumber = Math.random().nextTowards(maxValue.toDouble())
                    Log.d(
                        "Service",
                        "Started and Thread id :- ${Thread.currentThread().id} random number is : $randomNumber"
                    )
                }

            } catch (e: Exception) {
                Log.d(
                    "Service",
                    "Thread interrupted"
                )
            }
        }
    }

    private fun stopRandomGenerator() {
        randomNumberGeneratorOn = false

    }

    fun getRandomNumber(): Double {
        return randomNumber
    }

    /**
     *
     * Step 7 need to implement Ibinder
     *
     *
     */
    inner class MyServiceBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }

    private val IBinder = MyServiceBinder()

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("Service", "On Bind")
        return IBinder
    }

    override fun onRebind(intent: Intent?) {
        Log.d("Service", "On reBind")
        super.onRebind(intent)
    }

    override fun unbindService(conn: ServiceConnection) {
        Log.d("Service", "On unbind service")
        super.unbindService(conn)
    }

    override fun bindService(service: Intent, conn: ServiceConnection, flags: Int): Boolean {
        Log.d("Service", "On bind service")
        return super.bindService(service, conn, flags)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Started and Thread id :- ${Thread.currentThread().id}")
        /**
         *
         * Step 6 random number
         *
         */
        randomNumberGeneratorOn = true
        Thread(
            object : Runnable {
                override fun run() {
                    startRandomGenerator()
                }

            }
        ).start()

        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("Service", "Destroyed")
        stopRandomGenerator()
        super.onDestroy()
    }
}