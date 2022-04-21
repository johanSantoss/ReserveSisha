package johan.santos.reservesisha.ui.usuallyUser

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
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.usuallyUser.recyclerViewBusiness.DataBusinessAdapter

class userMainFragment : Fragment() {

    companion object {
        fun newInstance() = userMainFragment()
        const val PATH_PHOTO = "https://somoviles.files.wordpress.com/2014/08/androidf6o.jpg"
    }

    private lateinit var binding : CurrentUserMainFragmentBinding
    private lateinit var viewModel: UserMainViewModel
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        binding = CurrentUserMainFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserMainViewModel::class.java)

        // cargar la lista de empresas
        reloadListBusiness()

        return binding.root
    }
    //----------------------------------------------------------------------------------------------------------------------------
    private fun reloadListBusiness(){
        viewModel.clearListBusiness()

        val myRef = database.getReference("AllBusiness/")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                snapshot.children.forEach { item ->
                    item.child("businessDates").getValue<DataBusiness>()?.let {
                        val anyBusiness = DataBusiness(
                            it.cif,
                            it.nom_business,
                            it.direccio,
                            it.telefono,
                            it.descripcio,
                            it.horaOpen,
                            it.horaClose,
                            it.logo
                        )
                        viewModel.addValueBusiness(anyBusiness)
                    }
                }
                checkListBusiness().apply {
                    initRecyclerView()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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
        val action = userMainFragmentDirections.actionUserMainFragmentToBusinessFragment(businessSelected.cif)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun checkListBusiness(){
        //if (viewModel.listEmpty.value == 0){
        if (viewModel.llistaBusiness.isEmpty()){
            val descripcio = "At this moment we don't have any business aviable to use and to you will make any reservation! "
            val anyBusiness = DataBusiness(
                "",
                "Don't have any business!",
                "",
                "",
                descripcio,
                "",
                "",
                PATH_PHOTO )
            viewModel.addValueBusiness(anyBusiness)
        }
    }

    private fun getListBusiness() : List<DataBusiness?> {
        return viewModel.llistaBusiness
    }


}