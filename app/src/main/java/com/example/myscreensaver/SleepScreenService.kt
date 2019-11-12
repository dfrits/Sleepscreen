package com.example.myscreensaver

import android.os.Handler
import android.os.Looper
import android.service.dreams.DreamService
import android.view.View
import android.widget.TextView
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import java.util.*

class SleepScreenService : DreamService(), KoinComponent {

    private val updateTimer = Timer(TIMER_TAG)

    override fun onDreamingStarted() {
        super.onDreamingStarted()

        setTheme(R.style.AppTheme)

        setContentView(R.layout.sleepscreen)

        findViewById<View>(R.id.sleepscreen_root).setOnClickListener {
            finish()
            updateTimer.cancel()
        }

        setupScreen()

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isFullscreen = true
        isInteractive = true
    }

    private fun setupScreen() {
        setTimeUpdate()
    }

    private fun setTimeUpdate() {
        val timeView = findViewById<TextView>(R.id.dayDream_time)
        val dateText = findViewById<TextView>(R.id.dateText)

        timeView.text = getFormattedTime()
        dateText.text = getFormattedDate()

        val updateTimerTask = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    timeView.text = getFormattedTime()
                    dateText.text = getFormattedDate()
                }
            }
        }
        val seconds = Calendar.getInstance().get(Calendar.SECOND)
        updateTimer.scheduleAtFixedRate(updateTimerTask, 60000 - (seconds.toLong() * 1000), 60000)
    }

    private fun getFormattedTime() = SimpleDateFormat(
        getString(R.string.sleepscreen_time_pattern),
        Locale.getDefault()
    ).format(Date())

    private fun getFormattedDate() = SimpleDateFormat(
        getString(R.string.sleepscreen_date_pattern),
        Locale.getDefault()
    ).format(Date())

    private companion object {
        private const val TIMER_TAG = "Timer Updater"
    }
}
