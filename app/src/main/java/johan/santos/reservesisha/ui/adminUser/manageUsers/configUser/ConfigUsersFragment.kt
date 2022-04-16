package johan.santos.reservesisha.ui.adminUser.manageUsers.configUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigUsersFragmentBinding
import johan.santos.reservesisha.databinding.MainActivityBinding
import johan.santos.reservesisha.databinding.NavBarUsersBinding
import johan.santos.reservesisha.ui.access.models.User
import johan.santos.reservesisha.ui.access.models.User_Admin
import johan.santos.reservesisha.ui.access.models.User_Business
import johan.santos.reservesisha.ui.access.models.User_Current
import johan.santos.reservesisha.ui.access.registre.RegistreFragment
import johan.santos.reservesisha.ui.access.registre.RegistreFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class ConfigUsersFragment : Fragment() {

    private lateinit var binding: ConfigUsersFragmentBinding
    private lateinit var database: FirebaseDatabase

    companion object {
        fun newInstance() = ConfigUsersFragment()
        private const val TAG = "RegistreFragment"
    }

    private lateinit var viewModel: ConfigUsersViewModel

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
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        val spinnerItems = ArrayAdapter<String>((activity as MainActivity), android.R.layout.simple_spinner_item)

        spinnerItems.addAll(listOf("CurrentUser", "Empresa", "Admin"))

        binding.spinner.adapter = spinnerItems

        binding.btnAddNewUser.setOnClickListener {
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


        //return inflater.inflate(R.layout.config_users_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigUsersViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createAccount(email: String, password: String, rol: String) {

            // [START create_user_with_email]
            (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail: success")
                        // missatge de "DONE"
                        (activity as MainActivity).toastView("Success Registre!")
                        // guardar los datos en la DDBB
                        saveDatesUserDataBase(rol)
                        Log.d(TAG, "saveDatesUserDataBase: success")
                        clearDates()
                        Log.d(TAG, "navigate(action): success")
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

        viewModel.setEstadoRegistro(1)
    }

    private fun saveDatesUserDataBase(rol: String) {
        Log.d(TAG, "saveDatesUserDataBase: start")
        val auth = (activity as MainActivity).getAuth()
        Log.d(TAG, "getAuth - success")
        // se genera una clase de USER con todos los datos del usuario

        var user: User

        if(rol == "Admin"){
             user = User_Admin(
                auth.currentUser!!.uid,
                "null",
                viewModel.email.value.toString(),
                rol,
                Date()

            )
        }else if(rol == "Empresa"){
                 user = User_Business(
                    auth.currentUser!!.uid,
                    "null",
                    viewModel.email.value.toString(),
                    rol,
                    "null",
                    "null",
                    0,
                    "null",
                    "null"
                )
        }else{
             user = User_Current(
                auth.currentUser!!.uid,
                "null",
                viewModel.email.value.toString(),
                rol,
                "null",
                "null",
                "null",
                "null",
                "null",
                "null",
                "null"
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
    }

}