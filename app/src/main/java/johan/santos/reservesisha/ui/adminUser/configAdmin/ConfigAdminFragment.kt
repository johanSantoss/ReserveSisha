package johan.santos.reservesisha.ui.adminUser.configAdmin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigAdminFragmentBinding
import johan.santos.reservesisha.databinding.ConfigUsersFragmentBinding
import johan.santos.reservesisha.databinding.NavBarBusinessBinding
import johan.santos.reservesisha.ui.access.login.LoginFragment
import johan.santos.reservesisha.ui.access.models.User_Admin
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragment
import johan.santos.reservesisha.ui.usuallyUser.config.ConfigUserViewModel
import java.util.*

class ConfigAdminFragment : Fragment() {

    private lateinit var viewModel: ConfigAdminViewModel
    private lateinit var binding: ConfigAdminFragmentBinding
    private lateinit var binding2: NavBarBusinessBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userAdmin : User_Admin? = null

    companion object {
        fun newInstance() = ConfigAdminFragment()
        private const val TAG = "RegistreFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.config_admin_fragment,
            container,
            false
        )
        auth = (activity as MainActivity).getAuth()
        viewModel = ViewModelProvider(this).get(ConfigAdminViewModel::class.java)


        binding2 = NavBarBusinessBinding.inflate(layoutInflater)
        val navView: BottomNavigationView = binding2.bottomNavigationView2
        // Find the menu item and then disable it
        navView.menu.findItem(R.id.businessMainFragment).isEnabled = true

        if (viewModel.estadoRegistro.value == 1 ) {
            restaurarDatos()
        } else {
            //cargar los datos del usuario
            cargarDatosUser()
        }

        binding.btnUpdateData.setOnClickListener {
            saveDatesUserViewModel()
            if (binding.swDatosLogin.isChecked) {
                updateUserAndLogin()
            } else {
                updateUser()
            }
        }

        return binding.root
    }


    fun cargarDatosUser(){
        database = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database.child("userDates").get().addOnSuccessListener {

            if (it.exists()){

                binding.editTextNomUsuari.setText(it.child("nom_usuari").value.toString())
                binding.editTextEmailRegister.setText(it.child("email").value.toString())
                userAdmin = User_Admin(
                    it.child("id_usuari").value.toString(),
                    it.child("nom_usuari").value.toString(),
                    it.child("email").value.toString(),
                    "Admin",
                    it.child("data_creacio").value.toString()
                )
                saveDatesUserViewModel()

            }else{
                (activity as MainActivity).toastView("Modificar Datos")
                binding.editTextEmailRegister.setText(auth.currentUser!!.email)
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNomUsuari(binding.editTextNomUsuari.text.toString().trim())
        viewModel.setEmail(binding.editTextEmailRegister.text.toString())
        viewModel.setPass(binding.editTextPassword.text.toString())
        viewModel.setPassConf(binding.editTextPassword2.text.toString())
        if (binding.swDatosLogin.isChecked) viewModel.setSwLogin(true)
        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoRegistro(1)
    }

    private fun restaurarDatos(){
        binding.editTextNomUsuari.setText(viewModel.nomUsuari.value)
        binding.editTextEmailRegister.setText(viewModel.email.value)
        binding.editTextPassword.setText(viewModel.pass.value)
        binding.editTextPassword2.setText(viewModel.passConf.value)
        if (viewModel.swLogin.value!!) binding.swDatosLogin.isChecked = true
    }

    private fun getNowDate() : String{
        val c = Calendar.getInstance()

        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val month: Int = c.get(Calendar.MONTH)
        val year: Int = c.get(Calendar.YEAR)

        val nowDate = "$day/$month/$year"
        return nowDate
    }

    private fun updateUser(){

        if (binding.editTextNomUsuari.text.toString() != "") {
            val user = User_Admin(
                auth.currentUser!!.uid,
                viewModel.nomUsuari.value!!,
                viewModel.email.value!!,
                "Admin",
                getNowDate()
            )

            // Se genera el acceso a la DDBB al nodo de cada usuari
            database = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates")
            // Se settean y suben los datos del nuevo usuario
            database.setValue(user)
        } else {
            val message : String? = controlDadesUpdate()
            if (message != null) (activity as MainActivity).toastView(message)
        }

    }

    private fun updateUserAndLogin(){
        val message : String? = controlDadesUpdate()
        if (message != null) {
            (activity as MainActivity).toastView(message)
        } else {
            updateUser()


            auth.currentUser?.updateEmail(viewModel.email.value!!)?.addOnSuccessListener {
                Log.d(TAG, "updateEmail:success")
                auth.currentUser?.updatePassword(viewModel.pass.value!!)?.addOnSuccessListener {
                    Log.d(TAG, "updatePass:success")
                    (activity as MainActivity).toastView("Email & Pass & Dates - ACTUALIZADOS!")
                }?.addOnFailureListener {
                    Log.d(TAG, "updatePass:failure")
                }

            }?.addOnFailureListener{
                Log.d(TAG, "updateEmail:failure")
            }

        }
    }

    private fun controlDadesUpdate() : String? {
        var missatgeSortida : String? = null

        if (binding.editTextNomUsuari.text.isEmpty() ) {
            missatgeSortida = "Falta el nombre de usuario!"
        } else if (binding.editTextEmailRegister.text.isEmpty()) {
            missatgeSortida = "Falta el Mail!"
        } else if (binding.editTextPassword.text.isEmpty()) {
            missatgeSortida = "Falta la pass"
        } else if (binding.editTextPassword2.text.isEmpty()) {
            missatgeSortida = "Falta la confirmación de la pass"
        } else if (!binding.editTextPassword.text.toString().equals(binding.editTextPassword2.text.toString())){
            missatgeSortida = "Las pass son diferentes"
        }

        return missatgeSortida
    }

    override fun onStop() {
        super.onStop()
        saveDatesUserViewModel()
    }

}