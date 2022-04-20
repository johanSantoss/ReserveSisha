package johan.santos.reservesisha.ui.businessUser.manageRates.configRate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigRateFragmentBinding


class ConfigRateFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigRateFragment()
    }

    private lateinit var viewModel  : ConfigRateViewModel
    private lateinit var binding    : ConfigRateFragmentBinding
    private lateinit var database2 : DatabaseReference
    private lateinit var cif        : String
    val args: ConfigRateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConfigRateFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ConfigRateViewModel::class.java)

        cif = getCifBusiness()

        if(viewModel.estadoRegistro.value == 1) {
            restaurarDatos()
        } else {
            if (!args.newRate){
                loadRate()
            }
        }

        binding.btnUpdateRate.setOnClickListener {
            saveDataViewModel()
            updateRate()
        }


        return binding.root
    }

    private fun updateRate(){
        val rate = mapOf<String,String>(
            "name"    to viewModel.name.value!!,
            "price"   to viewModel.price.value!!
        )

        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/$cif/tarifas")

        database2.child(viewModel.name.value!!).setValue(rate)
    }

    private fun loadRate(){
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/$cif/tipos")
        database2.child(args.rate).get().addOnSuccessListener {
            if (it.exists()){

                binding.tvRateNameContent.setText(it.child("name").value.toString())
                binding.tvRatePriceContent.setText(it.child("price").value.toString())
                saveDataViewModel()

            }else{
                (activity as MainActivity).toastView("Rate! Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }

    }

    private fun restaurarDatos(){
        binding.tvRateNameContent.setText(viewModel.name.value)
        binding.tvRatePriceContent.setText(viewModel.price.value)
    }

    private fun saveDataViewModel(){
        viewModel.setName(binding.tvRateNameContent.text.toString())
        viewModel.setPrice(binding.tvRatePriceContent.text.toString())

        viewModel.setEstadoRegistro(1)
    }

    private fun getCifBusiness(): String {
        var cif = ""
        val auth = (activity as MainActivity).getAuth()
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database2.child("userDates").get().addOnSuccessListener {
            if (it.exists()) {

                cif = it.child("cif").value.toString()

            } else {
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener {
            (activity as MainActivity).toastView("Failed conection")
        }

        return cif
    }

    override fun onStop() {
        super.onStop()
        saveDataViewModel()
    }

}