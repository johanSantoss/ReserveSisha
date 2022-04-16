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
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragment
import johan.santos.reservesisha.ui.usuallyUser.config.ConfigUserViewModel

class ConfigAdminFragment : Fragment() {

    private lateinit var viewModel: ConfigAdminViewModel
    private lateinit var binding: ConfigAdminFragmentBinding
    private lateinit var binding2: NavBarBusinessBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var datePicker: DatePickerFragment
    val args: ConfigAdminFragmentArgs by navArgs()



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


        val spinnerItems = ArrayAdapter<String>((activity as MainActivity), android.R.layout.simple_spinner_item)

        spinnerItems.addAll(listOf("CurrentUser", "Business", "Admin"))

        binding.spinner2.adapter = spinnerItems

        if (viewModel.estadoRegistro.value == 1 ) {
            restaurarDatos()
        } else {
            //cargar los datos del usuario
            cargarDatosUser()
        }

        binding.editTextDataNaixement.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnUpdateData.setOnClickListener {

            saveDatesUserViewModel()
            updateUser()
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(childFragmentManager, "datePicker")
    }
    // setea el "editText" con la fecha obtenida
    private fun onDateSelected(day:Int, month:Int, year:Int){
        binding.editTextDataNaixement.setText("$day/$month/$year")
    }

    fun cargarDatosUser(){
        database = FirebaseDatabase.getInstance().getReference("AllUsers/${args.idUsuari}")
        database.child("userDates").get().addOnSuccessListener {

            if (it.exists()){

                binding.editTextNomUsuari.setText(it.child("nom_usuari").value.toString())
                binding.editTextName.setText(it.child("nom").value.toString())
                binding.editTextCognom.setText(it.child("cognoms").value.toString())
                binding.editTextEdat.setText(it.child("edat").value.toString())
                binding.editTextDataNaixement.setText(it.child("data_naixement").value.toString())
                binding.editTextCiutat.setText(it.child("ciutat").value.toString())
                var rol = binding.spinner2.adapter.getItem(0)
                if(binding.spinner2.adapter.getItem(0) == it.child("rol").value.toString()){
                    binding.spinner2.setSelection(0)
                }else if(binding.spinner2.adapter.getItem(1) == it.child("rol").value.toString()){
                    binding.spinner2.setSelection(1)
                }else{
                    binding.spinner2.setSelection(2)
                }


                binding.editTextEmailRegister.setText(it.child("email").value.toString())
                saveDatesUserViewModel()

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNom(binding.editTextName.text.toString().trim())
        viewModel.setCognom(binding.editTextCognom.text.toString().trim())
        viewModel.setEdatUser(binding.editTextCognom.text.toString().trim())
        viewModel.setCiutatUser(binding.editTextCiutat.text.toString().trim())
        viewModel.setDataNaixement(binding.editTextDataNaixement.text.toString().trim())
        viewModel.setNomUsuari(binding.editTextNomUsuari.text.toString().trim())
        viewModel.setEmail(binding.editTextEmailRegister.text.toString())
        viewModel.setPass(binding.editTextPassword.text.toString())
        viewModel.setPassConf(binding.editTextPassword2.text.toString())
        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoRegistro(1)
    }

    private fun restaurarDatos(){
        binding.editTextName.setText(viewModel.nom.value)
        binding.editTextCognom.setText(viewModel.cognom.value)
        binding.editTextEdat.setText(viewModel.edat.value)
        binding.editTextCiutat.setText(viewModel.ciutat.value)
        binding.editTextDataNaixement.setText(viewModel.dataNaixement.value)
        binding.editTextNomUsuari.setText(viewModel.nomUsuari.value)
        binding.editTextEmailRegister.setText(viewModel.email.value)
        binding.editTextPassword.setText(viewModel.pass.value)
        binding.editTextPassword2.setText(viewModel.passConf.value)
    }

    private fun updateUser(){

        val user = mapOf<String,String>(
            "nom_usuari"                to viewModel.nomUsuari.value!!,
            "nom"                       to viewModel.nom.value!!,
            "cognoms"                   to viewModel.cognom.value!!,
            "edat"                      to viewModel.edat.value!!,
            "data_naixement"            to viewModel.dataNaixement.value!!,
            "ciutat"                    to viewModel.ciutat.value!!,
            "email"                     to viewModel.email.value!!
        )

        if(viewModel.pass.value != viewModel.passConf.value){
            Toast.makeText(activity,"La contrasenya no coincideix", Toast.LENGTH_SHORT).show()
        }else{
//            auth.currentUser?.updateEmail(viewModel.email.value!!)?.addOnSuccessListener {
//                Log.d(TAG, "updateEmail:success")
//            }?.addOnFailureListener{
//                Log.d(TAG, "updateEmail:failure")
//            }
//
//            if(viewModel.pass.value?.isEmpty() == false){
//                auth.currentUser?.updatePassword(viewModel.pass.value!!)?.addOnSuccessListener {
//                    Log.d(TAG, "updatePass:success")
//                }?.addOnFailureListener{
//                    Log.d(TAG, "updatePass:failure")
//                }
//            }

            database = FirebaseDatabase.getInstance().getReference("AllUsers/${args.idUsuari}")

            database.child("userDates").updateChildren(user).addOnSuccessListener {

                (activity as MainActivity).toastView("Successfuly Updated")

            }.addOnFailureListener{

                (activity as MainActivity).toastView("Failed Updated")

            }
        }
    }


}