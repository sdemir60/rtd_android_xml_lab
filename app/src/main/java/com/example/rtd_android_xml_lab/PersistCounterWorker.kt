package com.example.rtd_android_xml_lab

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Calendar

class PersistCounterWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            Log.d("CounterWorker", "doWork started.")

            val sharedPreferences = applicationContext.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE)

            val currentCount = sharedPreferences.getInt("count", 0)

            val newCount = currentCount + 1

            val currentTime = Calendar.getInstance().timeInMillis

            sharedPreferences.edit()
                .putInt("count", newCount)
                .putLong("last_update_time", currentTime)
                .apply()

            Log.d("CounterWorker", "The counter is increased: $newCount")
            Log.d("CounterWorker", "doWork complated.")

            return Result.success()
        } catch (e: Exception) {
            Log.e("CounterWorker", "doWork error: ${e.message}")
            return Result.failure()
        }
    }
}