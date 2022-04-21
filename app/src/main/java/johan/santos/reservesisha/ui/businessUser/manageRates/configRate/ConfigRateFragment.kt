package johan.santos.reservesisha.ui.businessUser.manageRates.configRate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigRateFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.adminUser.manageUsers.configUser.ConfigUsersFragment


class ConfigRateFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigRateFragment()
    }

    private lateinit var viewModel  : ConfigRateViewModel
    private lateinit var binding    : ConfigRateFragmentBinding
    private lateinit var database   : FirebaseDatabase
    private lateinit var database2  : DatabaseReference
    private var cif        : String = ""
    private var oldRate                : DataRates = DataRates("","")
    val args: ConfigRateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConfigRateFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ConfigRateViewModel::class.java)

        getCifBusiness()
        // instance database
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        if(viewModel.estadoRegistro.value == 1) {
            restaurarDatos()
        } else {
            if (!args.newRate){
                loadRate()
            }
        }

        binding.btnUpdateRate.setOnClickListener {
            saveDataViewModel()
            if (args.newRate){
                createRate()
            } else {
                updateRate()
            }

        }


        return binding.root
    }

    private fun updateRate(){
        borraOldRate()
        createRate()
    }

    private fun borraOldRate(){
        val myRefDadesUser = database.getReference("AllBusiness/$cif/rates/${oldRate.name}")
        myRefDadesUser.removeValue()
    }

    private fun createRate(){
        val missatge = controlDadesRegistre()

        if (missatge != null)
            (activity as MainActivity).toastView(missatge)
        else {
            val ratePath = viewModel.name.value!!
            oldRate = DataRates(
                viewModel.name.value!!,
                viewModel.price.value!!
            )
            val myRefDadesUser = database.getReference("AllBusiness/$cif/rates/$ratePath")
            myRefDadesUser.setValue(oldRate)

            back()
        }
    }

    private fun controlDadesRegistre() : String? {
        var missatgeSortida : String? = null

        if (binding.tvRateNameContent.text.isEmpty() ) {
            missatgeSortida = "Falta el nombre!"
        } else if (binding.tvRatePriceContent.text.isEmpty()) {
            missatgeSortida = "Falta el price!"
        }

        return missatgeSortida
    }

    private fun back(){
        val action = ConfigRateFragmentDirections.actionConfigRateFragmentToManageRatesFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun loadRate(){
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/$cif/rates")
        database2.child(args.rate).get().addOnSuccessListener {
            if (it.exists()){

                binding.tvRateNameContent.setText(it.child("name").value.toString())
                binding.tvRatePriceContent.setText(it.child("price").value.toString())
                oldRate = DataRates(
                    it.child("name").value.toString(),
                    it.child("price").value.toString()
                )

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

    private fun getCifBusiness() {

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

    }

    override fun onStop() {
        super.onStop()
        saveDataViewModel()
    }

}