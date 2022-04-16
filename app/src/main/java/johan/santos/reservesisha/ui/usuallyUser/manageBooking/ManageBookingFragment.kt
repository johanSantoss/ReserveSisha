package johan.santos.reservesisha.ui.usuallyUser.manageBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking.DataBookingAdapter
import johan.santos.reservesisha.ui.usuallyUser.userMainFragment

class ManageBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ManageBookingFragment()
        const val PATH_PHOTO = "https://somoviles.files.wordpress.com/2014/08/androidf6o.jpg"
    }

    private lateinit var viewModel: ManageBookingViewModel
    private lateinit var binding : ManageBookingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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