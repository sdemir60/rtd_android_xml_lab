package com.example.rtd_android_xml_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class CounterFragment : Fragment() {

    private var counter = 0
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.counter_fragment, container, false)

        initCounter(view)
        initHilt(view)

        return view
    }

    private fun initCounter(view: View) {

        val counterTextView = view.findViewById<TextView>(R.id.counter_text_view)
        val incrementButton = view.findViewById<Button>(R.id.increment_button)

        incrementButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

    }

    private fun initHilt(view: View) {

        val textView = view.findViewById<TextView>(R.id.user_name_text_view)
        val getUserNameWithHiltButton = view.findViewById<Button>(R.id.get_user_name_with_hilt_button)

        getUserNameWithHiltButton.setOnClickListener {
            textView.text = viewModel.getUserName()
        }

    }

}