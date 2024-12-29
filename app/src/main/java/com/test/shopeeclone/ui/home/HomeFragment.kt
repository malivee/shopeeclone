package com.test.shopeeclone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.test.shopeeclone.databinding.FragmentHomeBinding
import com.test.shopeeclone.helper.Result
import com.test.shopeeclone.helper.ViewModelFactory
import com.test.shopeeclone.ui.MainActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBackground.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

        setupRecycler()

    }

    private fun setupRecycler() {
        val homeAdapter = HomeAdapter()
        binding.rvItem.adapter = homeAdapter
        binding.rvItem.layoutManager = GridLayoutManager(requireActivity(), 2)

        homeViewModel.getShoppingItem().observe(requireActivity()) {
            if (it != null) {
                when(it) {
                    is Result.Failure -> {
                        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                    Result.Loading -> {

                    }
                    is Result.Success -> {
                        homeAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}