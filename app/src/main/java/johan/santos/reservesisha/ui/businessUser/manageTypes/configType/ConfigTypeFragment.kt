package johan.santos.reservesisha.ui.businessUser.manageTypes.configType

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigTypeFragmentBinding
import johan.santos.reservesisha.databinding.ConfigUsersFragmentBinding
import johan.santos.reservesisha.ui.access.models.*
import johan.santos.reservesisha.ui.adminUser.manageUsers.configUser.ConfigUsersFragment
import johan.santos.reservesisha.ui.adminUser.manageUsers.configUser.ConfigUsersFragmentArgs
import johan.santos.reservesisha.ui.adminUser.manageUsers.configUser.ConfigUsersViewModel

class ConfigTypeFragment : Fragment() {

    private lateinit var binding: ConfigTypeFragmentBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var database2 : DatabaseReference
    val args    : ConfigTypeFragmentArgs by navArgs()
    var cif: String = ""
    private var oldRate                : DataType = DataType("","")

    companion object {
        fun newInstance() = ConfigTypeFragment()
        private const val TAG = "RegistreFragment"
    }

    private lateinit var viewModel: ConfigTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.config_type_fragment,
            container,
            false
        )

        cif = (activity as MainActivity).getPersonalID()

        viewModel = ViewModelProvider(this).get(ConfigTypeViewModel::class.java)

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        if (!args.newType) {
            loadType()
        }

        binding.btnUpdateType.setOnClickListener {
            saveDatesTypeViewModel()
            if(args.newType){
                createType()
            }else{
                deleteType()
            }

        }

        return binding.root
    }

    private fun loadType(){
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/$cif/types")
        database2.child(args.type).get().addOnSuccessListener {
            if (it.exists()){

                binding.tvTypeNameContent.setText(it.child("name").value.toString())
                binding.tvTypeSuplementoContent.setText(it.child("suplemento").value.toString())
                oldRate = DataType(
                    it.child("name").value.toString(),
                    it.child("suplemento").value.toString()
                )

                saveDatesTypeViewModel()

            }else{
                (activity as MainActivity).toastView("Rate! Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }

    }

    private fun saveDatesTypeViewModel(){
        viewModel.setName(binding.tvTypeNameContent.text.toString().trim())
        viewModel.setSuplemento(binding.tvTypeSuplementoContent.text.toString().trim())

        viewModel.setEstadoRegistro(1)
    }

    private fun deleteType(){

            val myRefDadesType = database.getReference("AllBusiness/$cif/types/${viewModel.name.value.toString()}")
            myRefDadesType.removeValue().addOnSuccessListener {
                createType()
            }
        }


    private fun createType(){

        var user = DataType(
            viewModel.name.value.toString(),
            viewModel.suplemento.value.toString()
        )

        val myRefDadesUser = database.getReference("AllBusiness/$cif/types/${viewModel.name.value.toString()}")
        myRefDadesUser.setValue(user)

        Toast.makeText(activity,"Action Succes", Toast.LENGTH_SHORT).show()

    }
}