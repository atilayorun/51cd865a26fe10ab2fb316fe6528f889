package com.example.a51cd865a26fe10ab2fb316fe6528f889

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentHomeScreenBinding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root


        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.fragment3) as? NavHostFragment
        val navController = nestedNavHostFragment?.navController
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        }

        return view
    }
}
