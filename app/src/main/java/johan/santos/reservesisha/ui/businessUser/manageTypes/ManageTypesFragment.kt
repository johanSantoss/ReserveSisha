package johan.santos.reservesisha.ui.businessUser.manageTypes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ManageRatesFragmentBinding
import johan.santos.reservesisha.databinding.ManageTypesFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.businessUser.manageRates.ManageRatesFragmentDirections
import johan.santos.reservesisha.ui.businessUser.manageRates.ManageRatesViewModel
import johan.santos.reservesisha.ui.businessUser.manageRates.recyclerViewRates.DataRatesAdapter
import johan.santos.reservesisha.ui.businessUser.manageTypes.recyclerViewTypes.DataTypeAdapter

class ManageTypesFragment : Fragment() {

    companion object {
        fun newInstance() = ManageTypesFragment()
    }

    private lateinit var viewModel: ManageTypesViewModel
    private lateinit var binding: ManageTypesFragmentBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManageTypesFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ManageTypesViewModel::class.java)

        // cargar la lista de reservas
        reloadListTypes()

        binding.btnAddNewType.setOnClickListener {
            viewModel.setHayDatosList(true)
            val action =
                ManageTypesFragmentDirections.actionManageTypesFragmentToConfigTypeFragment(
                    "",
                    true
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    //----------------------------------------------------------------------------------------------------------------------------
    private fun reloadListTypes() {
        database =
            FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val cif = getCifBusiness()
        val myRef = database.getReference("AllBusiness/$cif/tipos/")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                    item.children.forEach { valors ->
                        valors.getValue<DataType>()?.let {
                            val anyType = DataType(
                                it?.name,
                                it?.suplemento
                            )
                            viewModel.addValueType(anyType)
                            if (!item.hasChildren()) initRecyclerView()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        checkListTypes()

    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(activity)
        binding.recyclerTypes.layoutManager = manager
        binding.recyclerTypes.adapter = DataTypeAdapter(getListTypes()) { typeSelected ->
            onDeleteItemSelected(
                typeSelected
            )
        }
    }

    private fun onDeleteItemSelected(typeSelected: DataType) {
        (activity as MainActivity).toastView("Vas a ELIMINAR el item: '" + typeSelected.name.uppercase() + "'")
        val cif = getCifBusiness()
        if (typeSelected.suplemento != null) {
            database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
            val myRefDadesUser = database.getReference("AllBusiness/$cif/tipos/${typeSelected.name}")
            myRefDadesUser.removeValue()
        }

        viewModel.delValueType(typeSelected)
        initRecyclerView()

    }

    private fun getCifBusiness(): String {
        var cif = ""
        val auth = (activity as MainActivity).getAuth()
        val database2 =
            FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
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

    private fun checkListTypes() {
        //if (viewModel.listEmpty.value == 0){
        if (viewModel.llistaTypes.isEmpty()) {
            val anyType = DataType(
                "No hay ninguna tipo de reserva!",
                null
            )
            viewModel.addValueType(anyType)
        } else viewModel.setHayDatosList(true)
    }

    private fun getListTypes(): List<DataType> {
        return viewModel.llistaTypes
    }
}


