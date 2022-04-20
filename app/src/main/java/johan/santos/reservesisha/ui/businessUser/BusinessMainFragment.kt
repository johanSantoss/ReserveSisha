package johan.santos.reservesisha.ui.businessUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.BusinessFragmentBinding
import johan.santos.reservesisha.databinding.BusinessMainFragmentBinding
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingFragment
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingViewModel
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking.DataBookingAdapter

class BusinessMainFragment : Fragment() {

    companion object {
        fun newInstance() = ManageBookingFragment()
        const val PATH_PHOTO = "https://somoviles.files.wordpress.com/2014/08/androidf6o.jpg"
    }

    private lateinit var viewModel  : BusinessMainViewModel
    private lateinit var binding    : BusinessMainFragmentBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BusinessMainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(BusinessMainViewModel::class.java)

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        // cargar la lista de reservas
        reloadListBooking()

        return binding.root
    }

    private fun reloadListBooking(){
        viewModel.clearReservas()
        val cif = getCifBusiness()
        val myRef = database.getReference("AllBusiness/$cif/reservas")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                snapshot.children.forEach { item ->
                    item.children.forEach { valors ->
                        valors.getValue<DataBooking>()?.let {
                            val anyBooking = DataBooking(
                                it.nom_business,
                                it.nom_reserva,
                                it.num_personas,
                                it.fecha,
                                it.hora,
                                it.tipo_reserva,
                                it.direccion,
                                it.id_user,
                                it.id_empresa,
                                it.id_booking,
                                it.tarifa,
                                it.confirmada
                            )
                            viewModel.addValueReserva(anyBooking)
                        }
                    }
                }
                initRecyclerView()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getCifBusiness() : String {
        var cif = ""
        val auth = (activity as MainActivity).getAuth()
        val  database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database2.child("userDates").get().addOnSuccessListener {
            if (it.exists()){

                cif = it.child("cif").value.toString()

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed conection")
        }

        return cif
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(activity)
        binding.recyclerBooking.layoutManager = manager
        binding.recyclerBooking.adapter = DataBookingAdapter (getListBooking()) { bookingSelected ->
            onItemSelected(
                bookingSelected
            )
        }
    }

    private fun onItemSelected (bookingSelected : DataBooking){
        (activity as MainActivity).toastView("Has seleccionado el item: " + bookingSelected.nom_business)
    }

    private fun getListBooking() : List<DataBooking> {
        return viewModel.llistaReservas
    }


}