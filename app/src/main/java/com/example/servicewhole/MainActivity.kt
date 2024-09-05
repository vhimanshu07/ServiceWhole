package com.example.servicewhole

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.servicewhole.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceIntent: Intent

    /**
     *
     * Step 9
     *
     */
    private lateinit var myService: MyService
    private var isServiceBound: Boolean = false
    private var serviceConnection: ServiceConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Service", "Started and Thread id in Main activity:- ${Thread.currentThread().id}")
        /**
         *
         * Step 1 Make a new MyService class
         *
         */
        /**
         *
         * Step 2 as service starts from intent so need to make it.
         *
         */
        serviceIntent = Intent(applicationContext, MyService::class.java)
        /**
         *
         * Step 3 need to declare service in manifest file
         *
         */

        binding.apply {
            start.setOnClickListener {
                startService(serviceIntent)
            }
            stopBtn.setOnClickListener {
                stopService(serviceIntent)
            }
            /**
             *
             * Step 10
             *
             */
            bindBtn.setOnClickListener {
                bindServicefun()
            }
            unbindBtn.setOnClickListener {
                unbindIt()
            }
            randomBtn.setOnClickListener {
                makeRandom()
            }


        }
        /**
         *
         * Step 4 As we can stop a service till its lifecycle get executed
         * Check onStartCommand.
         *
         */

        /**
         *
         * Step 5 Working on local binding first
         * We will be using Service to make different numbers and give it to UI and it would display it
         *
         * Go to service
         */


        /**
         *
         * Step 8 added more buttons in xml
         *
         */

        /**
         *
         * Step 9 made variable at the top
         *
         */


    }

    private fun makeRandom() {
        binding.randomNumber.text = if (isServiceBound) {
            "Random number :" + myService.getRandomNumber()
        } else {
            "Service is not bound"
        }
    }

    private fun unbindIt() {
        if (isServiceBound) {
            serviceConnection?.let { unbindService(it) }
            isServiceBound = false
        }
    }

    private fun bindServicefun() {
        if (serviceConnection == null) {
            serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
                    /**
                     *
                     * Step 11 this is a way to bind service
                     *
                     */
                    val myServiceBinder = binder as MyService.MyServiceBinder
                    myService = myServiceBinder.getService()
                    isServiceBound = true
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    isServiceBound = false
                }

            }
        }
        /**
         *
         * Step 12 way to bind service, BIND_AUTO_CREATE would create service if it is not created
         *
         */
        serviceConnection?.let {
            bindService(serviceIntent, it, Context.BIND_AUTO_CREATE)
        }

    }

    /**
     *
     * Step 13 Bound service cannot be stopped, it can be unbinded
     *
     */

    /**
     *
     * Step 14 Now working on remote binding implementation
     *
     */
}