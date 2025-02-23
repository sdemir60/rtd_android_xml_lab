package com.example.rtd_android_xml_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CounterFragment : Fragment() {

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_counter, container, false)

        initCounter(view)

        return view
    }

    private fun initCounter(view: View) {

        val counterTextView = view.findViewById<TextView>(R.id.counterTextView)
        val incrementButton = view.findViewById<Button>(R.id.incrementButton)

        incrementButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

    }

}