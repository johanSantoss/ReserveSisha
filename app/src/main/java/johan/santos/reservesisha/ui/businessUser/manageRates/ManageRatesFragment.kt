package johan.santos.reservesisha.ui.businessUser.manageRates

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
import johan.santos.reservesisha.databinding.BusinessMainFragmentBinding
import johan.santos.reservesisha.databinding.ManageRatesFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.businessUser.BusinessMainViewModel
import johan.santos.reservesisha.ui.businessUser.manageRates.recyclerViewRates.DataRatesAdapter
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking.DataBookingAdapter

class ManageRatesFragment : Fragment() {

    companion object {
        fun newInstance() = ManageRatesFragment()
    }

    private lateinit var viewModel  : ManageRatesViewModel
    private lateinit var binding    : ManageRatesFragmentBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var database2  : DatabaseReference
    private var cif = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManageRatesFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ManageRatesViewModel::class.java)

        cif = (activity as MainActivity).getPersonalID()

        //(activity as MainActivity).toastView(cif)

        // cargar la lista de reservas
        reloadListRates()

        binding.btnAddNewRate.setOnClickListener {
            viewModel.setHayDatosList(true)
            val action = ManageRatesFragmentDirections.actionManageRatesFragmentToConfigRateFragment("", true)
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    private fun reloadListRates(){
        viewModel.cleanListRate()

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val path = "AllBusiness/$cif/rates/"
        val myRef = database.getReference(path)

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                    item.getValue<DataRates>()?.let {
                        val anyRate = DataRates(
                            it.name,
                            it.price
                        )
                        viewModel.addValueRate(anyRate)
                    }
                }
                initRecyclerView()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(activity)
        binding.recyclerRates.layoutManager = manager
        binding.recyclerRates.adapter = DataRatesAdapter (getListRates()) { rateSelected ->
            onDeleteItemSelected(
                rateSelected
            )
        }
    }

    private fun onDeleteItemSelected (rateSelected : DataRates){
        (activity as MainActivity).toastView("Vas a ELIMINAR el item: '" + rateSelected.name.uppercase() + "'")
//        val cif = getCifBusiness()

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRefDadesUser = database.getReference("AllBusiness/$cif/rates/${rateSelected.name}")
        myRefDadesUser.removeValue()


        viewModel.delValueRate(rateSelected)
        initRecyclerView()

    }

//    private fun getCifBusiness() {
//
//        val auth = (activity as MainActivity).getAuth()
//        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates")
//        database2.get().addOnSuccessListener {
//            if (it.exists()) {
//
//                cif = it.child("cif").value.toString()
//
//            } else {
//                (activity as MainActivity).toastView("User Doesn't Exist")
//            }
//        }.addOnFailureListener {
//            (activity as MainActivity).toastView("Failed conection")
//        }
//
//    }


    private fun getListRates() : List<DataRates> {
        return viewModel.llistaRates
    }


}