package johan.santos.reservesisha.ui.businessUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.BusinessFragmentBinding
import johan.santos.reservesisha.databinding.BusinessMainFragmentBinding
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BusinessMainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(BusinessMainViewModel::class.java)

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

    private fun checkListBooking(){
        //if (viewModel.listEmpty.value == 0){
        if (viewModel.llistaReservas.isEmpty()){
            val descripcio = "At this moment we don't have any business aviable to use and to you will make any reservation! "
            val anyBooking = DataBooking(
                "No hay reservas!",
                "Any",
                0,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
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