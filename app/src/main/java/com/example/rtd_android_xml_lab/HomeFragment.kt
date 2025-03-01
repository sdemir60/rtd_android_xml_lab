package com.example.rtd_android_xml_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.button_navigate_to_subpage)
        val editText = view.findViewById<EditText>(R.id.user_message_text_input_edit_text)

        button.setOnClickListener {

            val userMessage = editText.text.toString()

            val bundle = Bundle().apply {
                putString("userMessage", userMessage)
            }

            findNavController().navigate(R.id.action_homeNavigation_to_subPageFragment, bundle)
        }
    }
}