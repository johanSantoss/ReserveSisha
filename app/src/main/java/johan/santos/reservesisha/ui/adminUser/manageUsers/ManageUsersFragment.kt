package johan.santos.reservesisha.ui.adminUser.manageUsers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.AdminMainFragmentBinding
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.databinding.MainActivityBinding
import johan.santos.reservesisha.databinding.ManageUsersFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.adminUser.AdminMainFragmentDirections
import johan.santos.reservesisha.ui.adminUser.manageUsers.recyclerViewUsers.DataUsersAdapter
import johan.santos.reservesisha.ui.usuallyUser.UserMainViewModel
import johan.santos.reservesisha.ui.usuallyUser.userMainFragment

class ManageUsersFragment : Fragment() {

    private lateinit var binding : ManageUsersFragmentBinding
    private lateinit var database: FirebaseDatabase

    companion object {
        fun newInstance() = ManageUsersFragment()
    }

    private lateinit var viewModel: ManageUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.manage_users_fragment,
            container,
            false
        )

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        viewModel = ViewModelProvider(this).get(ManageUsersViewModel::class.java)



        // cargar la lista de empresas
        reloadListUsers()
        // realizar un check para comprobar que no hay nigúna empresa dispponible para poder mostrar al usuario
        checkListUsers()
        // inicializar el recycler view
        //initRecyclerView()

        binding.button52.setOnClickListener {
            val action = ManageUsersFragmentDirections.actionManageUsersFragmentToConfigUsersFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageUsersViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun reloadListUsers(){
        if(viewModel.llistaUsers.isNotEmpty()){
            viewModel.clearLlistaUSers()
        }

    }


    private fun initRecyclerView(){
        val manager = LinearLayoutManager(activity)
        binding.recyclerUsersM.layoutManager = manager
        binding.recyclerUsersM.adapter = DataUsersAdapter (getListUsers() as List<DataUsers>) { usersSelected ->
            onItemSelected(
                usersSelected
            )
        }
    }

    private fun onItemSelected (usersSelected : DataUsers){
//        (activity as MainActivity).toastView("Has seleccionado el item: " + usersSelected.nom_usuari)
    }

    private fun checkListUsers(){
        //if (viewModel.listEmpty.value == 0){

        val myRef = database.getReference("AllUsers/")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                            item.children.forEach { valors ->
                                valors.getValue<DataUsers>()?.let {

                                    binding.textView2.setText(it?.rol?:"")

                                    val anyUsers = DataUsers(
                                        it?.id_usuari?:"",
                                        it?.nom_usuari?:"",
                                        it?.rol?:"",
                                        it?.nom?:"",
                                        it?.cognoms?:""
                                    )
                                    viewModel.addValueUsers(anyUsers)
                                    initRecyclerView()
                                }
                            }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun saveListUsers(listUsers : MutableList<DataUsers>){
        viewModel.setLlistaUsers(listUsers)
    }

    private fun getListUsers() : List<DataUsers?> {
        return viewModel.llistaUsers
    }

}