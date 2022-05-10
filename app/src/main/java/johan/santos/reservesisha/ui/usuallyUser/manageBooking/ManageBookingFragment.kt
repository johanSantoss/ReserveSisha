package johan.santos.reservesisha.ui.usuallyUser.manageBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking.DataBookingAdapter
import johan.santos.reservesisha.ui.usuallyUser.userMainFragment

class ManageBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ManageBookingFragment()
        const val PATH_PHOTO = "https://somoviles.files.wordpress.com/2014/08/androidf6o.jpg"
    }

    private lateinit var viewModel: ManageBookingViewModel
    private lateinit var binding : ManageBookingFragmentBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        binding = ManageBookingFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ManageBookingViewModel::class.java)

        auth = (activity as MainActivity).getAuth()

        // cargar la lista de reservas
        reloadListBooking()

        return binding.root
    }

    private fun reloadListBooking(){

        viewModel.cleanListReservas()

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val path = "AllUsers/${auth.currentUser!!.uid}/userDates/reservas"
        val myRef = database.getReference(path)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                    item.getValue<DataBooking>()?.let {
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
                initRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    //----------------------------------------------------------------------------------------------------------------------------
//    private fun reloadListBooking(){
//
//        val auth = (activity as MainActivity).getAuth()
//        val myRef = database.getReference("AllUsers/${auth.currentUser?.uid}/userDates/reserva")
//
//        myRef.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//                snapshot.children.forEach { valors ->
//                        valors.getValue<DataBooking>()?.let {
//
//                            val anyBooking = DataBooking(
//                                it?.nom_business?:"",
//                                it?.nom_reserva?:"",
//                                it?.num_personas?:0,
//                                it?.fecha?:"",
//                                it?.hora?:"",
//                                it?.tipo_reserva?:"",
//                                it?.direccion?:"",
//                                it?.id_user?:"",
//                                it?.id_empresa?:"",
//                                it?.id_booking,
//                                it?.tarifa,
//                                it?.confirmada?:false
//                            )
//                            //val anyBooking = valors.
//
//                            viewModel.addValueReserva(anyBooking)
//                            initRecyclerView()
//                        }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(activity)
        binding.recyclerBooking.layoutManager = manager
        binding.recyclerBooking.adapter = DataBookingAdapter (getListBooking()) { businessSelected ->
            onItemSelected(
                businessSelected
            )
        }
    }

    private fun onItemSelected (businessSelected : DataBooking){
        (activity as MainActivity).toastView("Has seleccionado el item: " + businessSelected.nom_business)
    }

    private fun checkListBooking(){
        //if (viewModel.listEmpty.value == 0){
        if (viewModel.llistaReservas.isEmpty()){
            val descripcio = "At this moment we don't have any business aviable to use and to you will make any reservation! "
            val anyBooking = DataBooking(
                "Don't have any business!",
                "Any",
                "",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "",
                "false"
            )
            viewModel.addValueReserva(anyBooking)
        }
    }

    private fun saveListBusiness(listBusiness : MutableList<DataBooking>){
        viewModel.setLlistaReservas(listBusiness)
    }

    private fun getListBooking() : List<DataBooking> {
        return viewModel.llistaReservas
    }


}