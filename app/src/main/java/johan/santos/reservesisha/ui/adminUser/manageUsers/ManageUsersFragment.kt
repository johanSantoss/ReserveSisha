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
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.adminUser.AdminMainFragmentDirections
import johan.santos.reservesisha.ui.adminUser.manageUsers.recyclerViewUsers.DataUsersAdapter
import johan.santos.reservesisha.ui.usuallyUser.UserMainViewModel
import johan.santos.reservesisha.ui.usuallyUser.userMainFragment

class ManageUsersFragment : Fragment() {



    companion object {
        fun newInstance() = ManageUsersFragment()
    }

    private lateinit var viewModel: ManageUsersViewModel
    private lateinit var binding : ManageUsersFragmentBinding
    private lateinit var database: FirebaseDatabase

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



        // cargar la lista de Usuarios
        reloadListUsers()


        binding.button52.setOnClickListener {
            val action = ManageUsersFragmentDirections.actionManageUsersFragmentToConfigUsersFragment("", "", true)
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    private fun reloadListUsers(){

        val myRef = database.getReference("AllUsers/")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                viewModel.clearLlistaUSers()
                snapshot.children.forEach { item ->
                    /*
                    item.children.forEach { valors ->
                        valors.getValue<DataUsers>()?.let {
                            val anyUsers = DataUsers(
                                it.id_usuari,
                                it.nom_usuari,
                                it.email,
                                it.rol
                            )
                            viewModel.addValueUsers(anyUsers)
                        }
                    }*/

                    item.child("userDates").getValue<DataUsers>()?.let {
                        val anyUsers = DataUsers(
                            it.id_usuari,
                            it.nom_usuari,
                            it.email,
                            it.rol
                        )
                        viewModel.addValueUsers(anyUsers)
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
        binding.recyclerUsersM.layoutManager = manager
        binding.recyclerUsersM.adapter = DataUsersAdapter (getListUsers() as List<DataUsers>) { usersSelected ->
            onItemSelectedToDelete(
                usersSelected
            )
        }
    }

    private fun onItemSelectedToDelete (usersSelected : DataUsers){
        (activity as MainActivity).toastView("Has Borrado el User: " + usersSelected.nom_usuari)
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRefDadesUser = database.getReference("AllUsers/${usersSelected.id_usuari.toString()}")
        myRefDadesUser.removeValue().addOnSuccessListener {
            reloadListUsers()
        }
        //-------------------------------------------------------------------------------------------------------------------------------------
        /*
        falta recuperar la contraseña para poder borrar el usuario
         */


    }


    private fun getListUsers() : List<DataUsers?> {
        return viewModel.llistaUsers
    }

}