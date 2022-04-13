package johan.santos.reservesisha.ui.usuallyUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.ui.access.login.LoginViewModel
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.usuallyUser.recyclerViewBusiness.DataBusinessAdapter

class userMainFragment : Fragment() {

    companion object {
        fun newInstance() = userMainFragment()
        const val PATH_PHOTO = "https://somoviles.files.wordpress.com/2014/08/androidf6o.jpg"
    }

    private lateinit var binding : CurrentUserMainFragmentBinding
    private lateinit var viewModel: UserMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentUserMainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserMainViewModel::class.java)

        // cargar la lista de empresas
        reloadListBusiness()
        // realizar un check para comprobar que no hay nigúna empresa dispponible para poder mostrar al usuario
        checkListBusiness()
        // inicializar el recycler view
        initRecyclerView()


        return binding.root
    }

    private fun reloadListBusiness(){

    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(activity)
        binding.recyclerBusiness.layoutManager = manager
        binding.recyclerBusiness.adapter = DataBusinessAdapter (getListBusiness() as List<DataBusiness>) { businessSelected ->
            onItemSelected(
                businessSelected
            )
        }
    }

    private fun onItemSelected (businessSelected : DataBusiness){
        (activity as MainActivity).toastView("Has seleccionado el item: " + businessSelected.nom_business)
    }

    private fun checkListBusiness(){
        //if (viewModel.listEmpty.value == 0){
        if (viewModel.llistaBusiness.isEmpty()){
            val descripcio = "At this moment we don't have any business aviable to use and to you will make any reservation! "
            val anyBusiness = DataBusiness(
                "",
                "Don't have any business!",
                "",
                descripcio,
                PATH_PHOTO )
            viewModel.addValueBusiness(anyBusiness)
        }
    }

    private fun saveListBusiness(listBusiness : MutableList<DataBusiness>){
        viewModel.setLlistaBusiness(listBusiness)
    }

    private fun getListBusiness() : List<DataBusiness?> {
        return viewModel.llistaBusiness
    }


}