package johan.santos.reservesisha.ui.access.registre

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.RegistreFragmentBinding
import johan.santos.reservesisha.ui.access.models.User_Current

class RegistreFragment : Fragment() {

    private lateinit var binding: RegistreFragmentBinding
    private lateinit var viewModel: RegistreViewModel
    private lateinit var database: FirebaseDatabase

    companion object {
        fun newInstance() = RegistreFragment()
        private const val TAG = "EmailPassword"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.registre_fragment,
            container,
            false
        )

        // Get the viewModel
        viewModel = ViewModelProvider(this).get(RegistreViewModel::class.java)

        // Conection to DataBase
        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        binding.btnNewRegister.setOnClickListener {
            val missatge = controlDadesRegistre()
            if (missatge != null)
                (activity as MainActivity).toastView(missatge)
            else {
                saveDatesUserViewModel()
                viewModel.setEstadoRegistro(1)
                createAccount(viewModel.email.value.toString(), viewModel.password.value.toString(), viewModel.password2.value.toString())
            }
        }

        binding.btnClear.setOnClickListener {
            clearDates()
        }

        if (viewModel.estadoRegistro.value == 1 ) restaurarDatos()

        return  binding.root
//        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        (activity as MainActivity).disableMenus()

        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        saveDatesUserViewModel()
        supportActionBar?.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistreViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createAccount(email: String, password: String, password2: String) {

        if (password == password2){
            // [START create_user_with_email]
            (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = (activity as MainActivity).getAuth().currentUser
                        (activity as MainActivity).toastView("Success Registre!")
                        // guardar los datos en la DDBB
                        saveDatesUserDataBase()
                        // go to login fragment
                        val action = RegistreFragmentDirections.actionRegistreFragmentToLoginFragment()
                        NavHostFragment.findNavController(this).navigate(action)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        (activity as MainActivity).toastView("Failed Registre!")
                    }
                }
            // [END create_user_with_email]
        } else {
            (activity as MainActivity).toastView("Passwords not equals!")
        }
    }
    private fun restaurarDatos(){
        if (viewModel.nom.value != "") binding.editTextName.setText(viewModel.nom.value)
        if (viewModel.edat.value != "") binding.editTextEdat.setText(viewModel.edat.value)
        if (viewModel.sexe.value != null) binding.radioGroupRegistre.check(viewModel.sexe.value!!)
        if (viewModel.ciutat.value != "") binding.editTextCiutat.setText(viewModel.ciutat.value)
        if(viewModel.email.value != "") binding.editTextEmailRegister.setText(viewModel.email.value)
        if (viewModel.password.value != "" ) binding.editTextPassword.setText(viewModel.password.value)
        if (viewModel.password2.value != "") binding.editTextPassword2.setText(viewModel.password2.value)
    }

    private fun clearDates(){
        binding.editTextName.text.clear()
        binding.editTextEdat.text.clear()
        binding.radioGroupRegistre.clearCheck()
        binding.editTextCiutat.text.clear()
        binding.editTextEmailRegister.text.clear()
        binding.editTextPassword.text.clear()
        binding.editTextPassword2.text.clear()
    }

    private fun saveDatesUserViewModel(){
        if (binding.editTextName.text != null){
            viewModel.setNom(binding.editTextName.text.toString().trim())
        }
        if (binding.editTextCognom.text != null){
            viewModel.setCognom(binding.editTextCognom.text.toString().trim())
        }
        if (binding.editTextEdat.text != null){
            viewModel.setEdatUser(binding.editTextEdat.text.toString().trim())
        }
        if (binding.radioGroupRegistre.checkedRadioButtonId != null){
            viewModel.setSexeUser(binding.radioGroupRegistre.checkedRadioButtonId)
        }
        if (binding.editTextCiutat.text != null){
            viewModel.setCiutatUser(binding.editTextCiutat.text.toString().trim())
        }
        if (binding.editTextDataNaixement.text != null){
            viewModel.setDataNaixement(binding.editTextDataNaixement.text.toString().trim())
        }
        if (binding.editTextNomUsuari.text != null){
            viewModel.setNomUsuari(binding.editTextNomUsuari.text.toString().trim())
        }
        if (binding.editTextEmailRegister.text != null){
            viewModel.setEmail(binding.editTextEmailRegister.text.toString().trim())
        }
        if (binding.editTextPassword.text != null){
            viewModel.setPassword(binding.editTextPassword.text.toString().trim())
        }
        if (binding.editTextPassword2.text != null){
            viewModel.setPassword2(binding.editTextPassword2.text.toString().trim())
        }
    }
    private fun saveDatesUserDataBase(){
        val auth = Firebase.auth
        var sexe : String
        // se setea el sexe
        if (viewModel.sexe.value == binding.radioBtn1.id) sexe = "Dona" else sexe = "Home"
        // se genera una clase de USER con todos los datos del usuario
        val user = User_Current(
            auth.currentUser!!.uid,
            viewModel.nomUsuari.value.toString(),
            viewModel.email.value.toString(),
            "CurrentUser",
            viewModel.nom.value.toString(),
            viewModel.cognom.value.toString(),
            viewModel.edat.value.toString(),
            sexe,
            viewModel.dataNaixement.value.toString(),
            viewModel.ciutat.value.toString(),
            ""
        )
        // Se genera el acceso a la DDBB
        val myRef = database.getReference("AllUsers/CurrentUsers/${auth.currentUser?.uid}/userDates")
        // Se settean y suben los datos del nuevo usuario
        myRef.setValue(user)
    }

    private fun controlDadesRegistre() : String? {
        var missatgeSortida : String? = null

        if (binding.editTextName.text.isEmpty() ) {
            missatgeSortida = "Falta el nombre!"
        } else if (binding.editTextCognom.text.isEmpty()) {
            missatgeSortida = "Faltan els cognoms!"
        } else if (binding.editTextEdat.text.isEmpty()) {
            missatgeSortida = "Falta la edat!"
        } else if (binding.radioGroupRegistre.checkedRadioButtonId == null ){
            missatgeSortida = "Falta el sexe!"
        } else if (binding.editTextCiutat.text.isEmpty()){
            missatgeSortida = "Falta la ciutat!"
        } else if (binding.editTextDataNaixement.text.isEmpty()){
            missatgeSortida = "Falta la data de naixement!"
        } else if (binding.editTextNomUsuari.text.isEmpty()){
            missatgeSortida = "Falta el nom d'usuari!"
        } else if (binding.editTextEmailRegister.text.isEmpty()){
            missatgeSortida = "Falta el email!"
        } else if (binding.editTextPassword.text == null ){
            missatgeSortida = "Falta introduir el password"
        } else if (binding.editTextPassword2.text == null ){
            missatgeSortida = "Falta repetir la el password"
        }

        return missatgeSortida
    }

}