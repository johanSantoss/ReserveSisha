package johan.santos.reservesisha.ui.businessUser.manageTypes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
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
    private lateinit var database2 : DatabaseReference
    var cif: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManageTypesFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ManageTypesViewModel::class.java)
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        cif = (activity as MainActivity).getPersonalID()
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

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val path = "AllBusiness/$cif/types/"
        val myRef = database.getReference(path)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                viewModel.cleanListTypes()

            snapshot.children.forEach { item ->
                item.getValue<DataType>()?.let {
                        val anyType = DataType(
                            it?.name.toString(),
                            it?.suplemento.toString()
                        )
                        viewModel.addValueType(anyType)
                    }
                }
                initRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //checkListTypes()

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
        (activity as MainActivity).toastView("Vas a ELIMINAR el item: '" + typeSelected.name?.uppercase() + "'")
        if (typeSelected.suplemento != null) {
            val myRefDadesUser = database.getReference("AllBusiness/$cif/types/${typeSelected.name}")
            myRefDadesUser.removeValue()
        }
    }

    private fun getListTypes(): List<DataType> {
        return viewModel.llistaTypes
    }
}


