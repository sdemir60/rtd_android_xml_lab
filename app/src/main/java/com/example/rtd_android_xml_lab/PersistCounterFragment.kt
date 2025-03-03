package com.example.rtd_android_xml_lab

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class PersistCounterFragment : Fragment() {

    private lateinit var tvCountdownTimer: TextView
    private lateinit var tvCounterValue: TextView
    private lateinit var btnStartWorker: Button
    private lateinit var workManager: WorkManager

    private var countDownTimer: CountDownTimer? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateRunnable: Runnable

    private val WORKER_INTERVAL_MS = 15 * 60 * 1000L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.persist_counter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        tvCountdownTimer = view.findViewById(R.id.countdown_timer_text_view)
        tvCounterValue = view.findViewById(R.id.counter_value_text_view)
        btnStartWorker = view.findViewById(R.id.start_worker_button)
        workManager = WorkManager.getInstance(requireContext())

        btnStartWorker.setOnClickListener {
            startWorker()
        }

        updateUI()

        setupPeriodicUpdates()
    }

    private fun startWorker() {

        val startTime = Calendar.getInstance().timeInMillis

        val sharedPreferences = requireContext().getSharedPreferences("counter_prefs", Context.MODE_PRIVATE)

        sharedPreferences.edit()
            .putLong("start_time", startTime)
            .putInt("count", 0)
            .putLong("last_update_time", startTime)
            .apply()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<PersistCounterWorker>(
            15, TimeUnit.MINUTES,
            5, TimeUnit.MINUTES
        ).build()

        workManager.enqueueUniquePeriodicWork(
            "COUNTER_WORK",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )

        Log.d("CounterFragment", "Worker started: $startTime")

        updateUI()
    }

    private fun updateUI() {

        val sharedPreferences = requireContext().getSharedPreferences("counter_prefs", Context.MODE_PRIVATE)
        val count = sharedPreferences.getInt("count", 0)
        val startTime = sharedPreferences.getLong("start_time", 0)

        tvCounterValue.text = count.toString()

        countDownTimer?.cancel()

        if (startTime > 0) {
            startCountdownTimer(startTime)
        } else {
            tvCountdownTimer.text = "15:00"
        }
    }

    private fun startCountdownTimer(startTime: Long) {

        val currentTime = Calendar.getInstance().timeInMillis

        val timeElapsed = currentTime - startTime

        val timeUntilNextExecution = WORKER_INTERVAL_MS - (timeElapsed % WORKER_INTERVAL_MS)

        countDownTimer = object : CountDownTimer(timeUntilNextExecution, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                tvCountdownTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                updateUI()
            }

        }.start()
    }

    private fun setupPeriodicUpdates() {

        updateRunnable = object : Runnable {
            override fun run() {
                updateUI()
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(updateRunnable)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        handler.removeCallbacks(updateRunnable)
    }
}