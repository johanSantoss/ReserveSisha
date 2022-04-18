package johan.santos.reservesisha.ui.usuallyUser.manageBooking

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
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        binding = ManageBookingFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ManageBookingViewModel::class.java)

        // cargar la lista de reservas
        reloadListBooking()
        // realizar un check para comprobar que no hay nigúna reserva dispponible para poder mostrar al usuario
        checkListBooking()
        // inicializar el recycler view
        initRecyclerView()


        return binding.root
    }

    //----------------------------------------------------------------------------------------------------------------------------
    private fun reloadListBooking(){

        val auth = (activity as MainActivity).getAuth()
        val myRef = database.getReference("AllUsers/${auth.currentUser?.uid}/userDates/reserva")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { valors ->
                        valors.getValue<DataBooking>()?.let {


                            val anyBooking = DataBooking(
                                it?.nom_business?:"",
                                it?.nom_reserva?:"",
                                it?.num_personas?:0,
                                it?.fecha?:"",
                                it?.hora?:"",
                                it?.tipo_reserva?:"",
                                it?.direccion?:"",
                                it?.id_user?:"",
                                it?.id_empresa?:"",
                                it?.confirmada?:false
                            )
                            viewModel.addValueReserva(anyBooking)
                            initRecyclerView()
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

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
                0,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                0,
                false
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