package com.example.a51cd865a26fe10ab2fb316fe6528f889

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity.apply
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.GravityCompat.apply
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentCreatingSpacecraftBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.CreatingSpacecraftViewModel

class CreatingSpacecraftFragment : Fragment() {
    private var _binding: FragmentCreatingSpacecraftBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreatingSpacecraftViewModel
    private var totalPoint = 0
    private var durabilityPoint: Int = 0
    private var speedPoint: Int = 0
    private var capacityPoint: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CreatingSpacecraftViewModel()::class.java)
        viewModel.setDb(context?.let { SpaceStationDatabase.getStationDatabase(it) }!!)
        val spacecraft = viewModel.getSpacecraft()


        spacecraft?.let {
            findNavController().navigate(R.id.action_creatingSpacecraftFragment_to_homeScreenFragment)
        }

        _binding = FragmentCreatingSpacecraftBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnContinue.setOnClickListener {
            if (speedPoint == 0 || durabilityPoint == 0 || capacityPoint == 0)
                Toast.makeText(context, "Yetenekler 0'dan büyük olmalıdır.", Toast.LENGTH_SHORT)
                    .show()
            else if(totalPoint != 15){
                Toast.makeText(context, "Yeteneklerin toplamı 15 olmalıdır.", Toast.LENGTH_SHORT)
                    .show()
            }
            else if(binding.edtStationName.text.isEmpty()){
                Toast.makeText(context, "İstasyon adı boş olamaz.", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                val spaceCraft = Spacecraft(
                    binding.edtStationName.text.toString(),
                    binding.sbSpeed.progress,
                    binding.sbCapacity.progress,
                    binding.sbDurability.progress,
                    binding.sbCapacity.progress * 10000,
                    binding.sbSpeed.progress * 20,
                    binding.sbDurability.progress * 10000
                )
                viewModel.addSpacecraft(spaceCraft)
                findNavController().navigate(R.id.action_creatingSpacecraftFragment_to_homeScreenFragment)
            }
        }


        fun updateValue(){
            totalPoint = durabilityPoint + speedPoint + capacityPoint
            binding.tvPoint.text = "Dağıtılacak Puan : ${totalPoint}"
        }

        binding.sbDurability.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                durabilityPoint = p1
                updateValue()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.sbSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                speedPoint = p1
                updateValue()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        binding.sbCapacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                capacityPoint = p1
                updateValue()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
