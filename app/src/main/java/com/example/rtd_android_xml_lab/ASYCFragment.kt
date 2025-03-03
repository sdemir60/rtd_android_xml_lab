package com.example.rtd_android_xml_lab

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class ASYCFragment : Fragment() {

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView

    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null
    private var job4: Job? = null

    private val colors = arrayOf(
        Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA,
        Color.CYAN, Color.YELLOW, Color.DKGRAY
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.asyc_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView1 = view.findViewById(R.id.textView1)
        textView2 = view.findViewById(R.id.textView2)
        textView3 = view.findViewById(R.id.textView3)
        textView4 = view.findViewById(R.id.textView4)

        startCoroutines()
    }

    private fun startCoroutines() {

        job1 = lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                updateTextView(
                    textView1,
                    "Main Thread\n${getCurrentTime()}\nSayaç: ${Random.nextInt(100)}",
                    getRandomColor()
                )
                delay(1000)
            }
        }

        job2 = lifecycleScope.launch(Dispatchers.IO) {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    updateTextView(
                        textView2,
                        "IO Thread\n${getCurrentTime()}\nDosya İşlemi: ${Random.nextInt(1000)} KB",
                        getRandomColor()
                    )
                }
                delay(1500)
            }
        }

        job3 = lifecycleScope.launch(Dispatchers.Default) {
            while (isActive) {

                var result = 0L
                for (i in 1..1000000) {
                    result += i
                }

                withContext(Dispatchers.Main) {
                    updateTextView(
                        textView3,
                        "Default Thread\n${getCurrentTime()}\nHesaplama: $result",
                        getRandomColor()
                    )
                }
                delay(2000)
            }
        }

        job4 = lifecycleScope.launch(Dispatchers.Unconfined) {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    updateTextView(
                        textView4,
                        "Unconfined Thread\n${getCurrentTime()}\nOlay: ${UUID.randomUUID().toString().substring(0, 8)}",
                        getRandomColor()
                    )
                }
                delay(2500)
            }
        }
    }

    private fun updateTextView(textView: TextView, message: String, color: Int) {
        textView.text = message
        textView.setBackgroundColor(color)
    }

    private fun getRandomColor(): Int {
        return colors[Random.nextInt(colors.size)]
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
        job4?.cancel()
    }
}