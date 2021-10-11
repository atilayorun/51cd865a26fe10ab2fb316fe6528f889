package com.example.a51cd865a26fe10ab2fb316fe6528f889.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentCreatingSpacecraftBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.CreatingSpacecraftViewModel

class CreatingSpacecraftFragment : Fragment() {
    private var _binding: FragmentCreatingSpacecraftBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreatingSpacecraftViewModel
    private var totalPoint = 0
    private var durabilityPoint: Int = 0
    private var speedPoint: Int = 0
    private var capacityPoint: Int = 0
    private lateinit var progressDialog: ProgressDialog
    private lateinit var station: Station

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setProgressDialog()
        _binding = FragmentCreatingSpacecraftBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(CreatingSpacecraftViewModel()::class.java)
        viewModel.setDb(context?.let { SpaceStationDatabase.getStationDatabase(it) }!!)

        return view
    }

    override fun onStart() {
        viewModelSetObserver()
        listeners()
        viewModel.getFavSpaceStation()
        viewModel.getAllStationFromAPI()
        super.onStart()
    }

    private fun viewModelSetObserver() {
        viewModel.spaceCraftLiveData.observe(viewLifecycleOwner, {
            it.let {
                viewModel.removeSpacecraft()
            }
        })

        viewModel.favSpaceStationListLiveData.observe(viewLifecycleOwner, {
            it.let {
                viewModel.removeFavSpaceStation()
            }
        })

        viewModel.spaceStationListLiveData.observe(viewLifecycleOwner, {
            this.station = it[0]
            viewModel.addAllStation()
            progressDialog.dismiss()
        })

        viewModel.apiOnFailure.observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            Toast.makeText(
                context,
                "Bağlantıda sorun yaşandı. Lütfen tekrardan bağlanın.",
                Toast.LENGTH_SHORT
            )
                .show()
            binding.btnContinue.text = "Tekrardan Bağlan"
        })
    }

    private fun listeners() {
        fun updateValue() {
            totalPoint = durabilityPoint + speedPoint + capacityPoint
            binding.tvPoint.text = "$totalPoint"
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

        binding.btnContinue.setOnClickListener {
            if (binding.btnContinue.text.toString().trim() == "Tekrardan Bağlan") {
                progressDialog.show()
                viewModel.getAllStationFromAPI()
            } else {
                if (speedPoint == 0 || durabilityPoint == 0 || capacityPoint == 0)
                    Toast.makeText(context, "Yetenekler 0'dan büyük olmalıdır.", Toast.LENGTH_SHORT)
                        .show()
                else if (totalPoint != 15) {
                    Toast.makeText(
                        context,
                        "Yeteneklerin toplamı 15 olmalıdır.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else if (binding.edtStationName.text.isEmpty()) {
                    Toast.makeText(context, "İstasyon adı boş olamaz.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val spaceCraft = Spacecraft(
                        binding.edtStationName.text.toString(),
                        binding.sbSpeed.progress,
                        binding.sbCapacity.progress,
                        binding.sbDurability.progress,
                        binding.sbCapacity.progress * 10000,
                        binding.sbSpeed.progress * 20,
                        binding.sbDurability.progress * 10000,
                        currentPositionName = station.name,
                        coordinateX = station.coordinateX,
                        coordinateY = station.coordinateY
                    )
                    viewModel.addSpacecraft(spaceCraft)
                    findNavController().navigate(R.id.action_creatingSpacecraftFragment_to_homeScreenFragment)
                }
            }
        }

    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Yükleniyor")
        progressDialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
