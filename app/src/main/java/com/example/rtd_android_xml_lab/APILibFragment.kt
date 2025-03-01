package com.example.rtd_android_xml_lab

import RetrofitClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class APILibFragment : Fragment() {

    private lateinit var btnRetrofit: Button
    private lateinit var btnVolley: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: JSONPlaceholderPostsAdapter
    private val postList = mutableListOf<JSONPlaceholderPostModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.api_lib_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRetrofit = view.findViewById(R.id.get_with_retrofit_button)
        btnVolley = view.findViewById(R.id.get_with_volley_button)
        progressBar = view.findViewById(R.id.get_placeholder_posts_data_progress_bar)
        recyclerView = view.findViewById(R.id.placeholder_posts_data_recycler_view)

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = JSONPlaceholderPostsAdapter(postList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {

        btnRetrofit.setOnClickListener {
            fetchDataWithRetrofit()
        }

        btnVolley.setOnClickListener {
            Toast.makeText(requireContext(), "Volley işlevi henüz eklenmedi!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDataWithRetrofit() {

        showLoading(true)

        lifecycleScope.launch {
            try {
                val posts = RetrofitClient.api.getPosts()
                updateUI(posts)
                showLoading(false)
            } catch (e: Exception) {
                handleError("Retrofit Error: ${e.message}")
                showLoading(false)
            }
        }
    }

    private fun updateUI(posts: List<JSONPlaceholderPostModel>) {
        postList.clear()
        postList.addAll(posts)
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}


