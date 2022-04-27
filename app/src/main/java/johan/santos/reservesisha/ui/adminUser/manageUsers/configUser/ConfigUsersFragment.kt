package johan.santos.reservesisha.ui.adminUser.manageUsers.configUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigUsersFragmentBinding
import johan.santos.reservesisha.ui.access.models.*
import java.util.*


class ConfigUsersFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigUsersFragment()
        private const val TAG = "RegistreFragment"
    }

    private lateinit var viewModel: ConfigUsersViewModel
    private lateinit var binding: ConfigUsersFragmentBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var spinnerItems : ArrayAdapter<String>
    val args    : ConfigUsersFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.config_users_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ConfigUsersViewModel::class.java)

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        spinnerItems = ArrayAdapter<String>((activity as MainActivity), android.R.layout.simple_spinner_item)

        spinnerItems.addAll(listOf("CurrentUser", "Business", "Admin"))

        binding.spinner.adapter = spinnerItems

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
                if(binding.spinner.selectedItem.toString() == "Business"){
                    binding.editTextCIF.visibility = View.VISIBLE
                }else{
                    binding.editTextCIF.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        binding.btnAddNewUser.setOnClickListener {
            if (!args.newUser){
                updateUser()
            } else {
                createUser()
            }
        }

        if (!args.newUser) {
            cargarUser()
        }

        return binding.root
    }

    private fun cargarUser(){
        when (args.rol){
            "Business"      -> cargarUserBusines()
            "CurrentUser"   -> cargarUserCurrentUser()
            "Admin"         -> cargarUserAdmin()
        }
    }

    private fun cargarUserAdmin(){
        val myRef = database.getReference("AllUsers/${args.user}/userDates")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<User_Admin>()
                binding.editTextMailAuth.setText(value?.email)
                binding.spinner.setSelection(spinnerItems.getPosition(value?.rol))
                saveDatesUserViewModel()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun cargarUserBusines(){
        val myRef = database.getReference("AllUsers/${args.user}/userDates")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<User_Business>()
                binding.editTextMailAuth.setText(value?.email)
                binding.spinner.setSelection(spinnerItems.getPosition(value?.rol))
                binding.editTextCIF.isVisible = true
                binding.editTextCIF.setText(value?.cif)
                saveDatesUserViewModel()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun cargarUserCurrentUser(){
        val myRef = database.getReference("AllUsers/${args.user}/userDates")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<User_Current>()
                binding.editTextMailAuth.setText(value?.email)
                binding.spinner.setSelection(spinnerItems.getPosition(value?.rol))
                saveDatesUserViewModel()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun updateUser(){
        saveDatesUserViewModel()
        var user : Map<String, String>  = mapOf<String,String>()

        when (viewModel.rol.value){
            "Business"      -> {
                user = mapOf<String,String>(
                    "cif"                to viewModel.numCIF.value!!
                )
            }
            "CurrentUser"   -> {
                user = mapOf<String,String>(
                    "email"                to viewModel.email.value!!
                )
            }
            "Admin"         -> {
                user = mapOf<String,String>(
                    "email"                to viewModel.email.value!!
                )
            }
        }
/*
        val user = mapOf<String,String>(
            "cif"                to viewModel.numCIF.value!!
        )*/

        val database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${args.user}")

        database2.child("userDates").updateChildren(user).addOnSuccessListener {

        }.addOnFailureListener{

        }

        back()
    }

    private fun back(){
        val action = ConfigUsersFragmentDirections.actionConfigUsersFragmentToManageUsersFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun createUser(){
        val missatge = controlDadesRegistre()

        if (missatge != null)
            (activity as MainActivity).toastView(missatge)
        else {
            saveDatesUserViewModel()
            Log.d(TAG, "saveDatesUserViewModel: success")
            createAccount(viewModel.email.value.toString(), viewModel.password.value.toString(), binding.spinner.selectedItem.toString())
            Log.d(TAG, "createAccount: success")

        }
    }

    private fun createAccount(email: String, password: String, rol: String) {


        // [START create_user_with_email]
        (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail: success")
                    // missatge de "DONE"
                    //(activity as MainActivity).toastView("Success Registre!")
                    // guardar los datos en la DDBB
                    saveDatesUserDataBase(rol)
                    Log.d(TAG, "saveDatesUserDataBase: success")
                    clearDates()
                    Log.d(TAG, "navigate(action): success")
                    back()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    (activity as MainActivity).toastView("Failed Registre!")
                }
            }
    }

    private fun controlDadesRegistre() : String? {
        var missatgeSortida : String? = null

        if (binding.editTextMailAuth.text.isEmpty() ) {
            missatgeSortida = "Falta el email!"
        } else if (binding.editTextPassAuth.text.isEmpty()) {
            missatgeSortida = "Falta la password!"
        }

        return missatgeSortida
    }

    private fun saveDatesUserViewModel(){
        viewModel.setEmail(binding.editTextMailAuth.text.toString().trim())
        viewModel.setPassword(binding.editTextPassAuth.text.toString().trim())
        val rol = binding.spinner.selectedItem.toString()
        viewModel.setNumCIF(binding.editTextCIF.text.toString())
        viewModel.setRol(rol)
        viewModel.setEstadoRegistro(1)
    }
    private fun getNowDate() : String{
        val c = Calendar.getInstance()

        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val month: Int = c.get(Calendar.MONTH)
        val year: Int = c.get(Calendar.YEAR)

        val nowDate = "$day/$month/$year"
        return nowDate
    }

    private fun saveDatesUserDataBase(rol: String) {
        var cif = ""
        Log.d(TAG, "saveDatesUserDataBase: start")
        val auth = (activity as MainActivity).getAuth()
        Log.d(TAG, "getAuth - success")
        // se genera una clase de USER con todos los datos del usuario

        cif = viewModel.numCIF.value.toString()


        var user: User

        if(rol == "Admin"){
             user = User_Admin(
                auth.currentUser!!.uid,
                "",
                viewModel.email.value.toString(),
                rol,
                getNowDate()

            )
        }else if(rol == "Business"){
                 user = User_Business(
                    auth.currentUser!!.uid,
                    "",
                    viewModel.email.value.toString(),
                    rol,
                    "",
                    "",
                    "",
                    "",
                    cif
                )
        }else{
             user = User_Current(
                auth.currentUser!!.uid,
                "",
                viewModel.email.value.toString(),
                rol,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }

        Log.d(TAG, "create_DataClass - success")
        // Se genera el acceso a la DDBB al nodo de cada usuari
        val myRefDadesUser = database.getReference("AllUsers/${auth.currentUser!!.uid}/userDates")
        Log.d(TAG, "connect_saveDataClass - success")
        // Se settean y suben los datos del nuevo usuario
        myRefDadesUser.setValue(user)
        Log.d(TAG, "saveDataClass - success")

        Log.d(TAG, "saveDatesUserDataBase: end")
    }

    private fun clearDates(){
        binding.editTextMailAuth.text.clear()
        binding.editTextPassAuth.text.clear()
        binding.editTextCIF.text.clear()
    }

}