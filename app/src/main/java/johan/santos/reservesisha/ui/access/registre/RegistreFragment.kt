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
        private const val TAG = "RegistreFragment"
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
            //val missatge :String? = null
            if (missatge != null)
                (activity as MainActivity).toastView(missatge)
            else {
                saveDatesUserViewModel()
                Log.d(TAG, "saveDatesUserViewModel: success")
                createAccount(viewModel.email.value.toString(), viewModel.password.value.toString(), viewModel.password2.value.toString())
                Log.d(TAG, "createAccount: success")
            }
        }

        binding.editTextDataNaixement.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnClear.setOnClickListener {
            clearDates()
        }

        if (viewModel.estadoRegistro.value == 1 ) restaurarDatos()

        return  binding.root
    }



    private fun createAccount(email: String, password: String, password2: String) {

        if (password == password2){
            Log.d(TAG, "equalsPasswords: success")
            // [START create_user_with_email]
            (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail: success")
                        // missatge de "DONE"
                        (activity as MainActivity).toastView("Success Registre!")
                        // guardar los datos en la DDBB
                        saveDatesUserDataBase()
                        Log.d(TAG, "saveDatesUserDataBase: success")
                        // go to login fragment
                        val action = RegistreFragmentDirections.actionRegistreFragmentToLoginFragment()
                        NavHostFragment.findNavController(this).navigate(action)
                        Log.d(TAG, "navigate(action): success")
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
        binding.editTextName.setText(viewModel.nom.value)
        binding.editTextCognom.setText(viewModel.cognom.value)
        binding.editTextEdat.setText(viewModel.edat.value)
        if (viewModel.sexe.value != null) binding.radioGroupRegistre.check(viewModel.sexe.value!!)
        binding.editTextCiutat.setText(viewModel.ciutat.value)
        binding.editTextDataNaixement.setText(viewModel.dataNaixement.value)
        binding.editTextIdentificadorPersonal.setText(viewModel.identificadorPersonal.value)
        binding.editTextNomUsuari.setText(viewModel.nomUsuari.value)
        binding.editTextEmailRegister.setText(viewModel.email.value)
        binding.editTextPassword.setText(viewModel.password.value)
        binding.editTextPassword2.setText(viewModel.password2.value)
    }

    private fun clearDates(){
        binding.editTextName.text.clear()
        binding.editTextCognom.text.clear()
        binding.editTextEdat.text.clear()
        binding.radioGroupRegistre.clearCheck()
        binding.editTextCiutat.text.clear()
        binding.editTextDataNaixement.text.clear()
        binding.editTextIdentificadorPersonal.text.clear()
        binding.editTextNomUsuari.text.clear()
        binding.editTextEmailRegister.text.clear()
        binding.editTextPassword.text.clear()
        binding.editTextPassword2.text.clear()
    }

    // generar un picker para poder seleccionar la fecha requerida
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(childFragmentManager, "datePicker")
    }
    // setea el "editText" con la fecha obtenida
    private fun onDateSelected(day:Int, month:Int, year:Int){
        binding.editTextDataNaixement.setText("$day/$month/$year")
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNom(binding.editTextName.text.toString().trim())
        viewModel.setCognom(binding.editTextCognom.text.toString().trim())
        viewModel.setEdatUser(binding.editTextEdat.text.toString().trim())
        viewModel.setSexeUser(binding.radioGroupRegistre.checkedRadioButtonId)
        viewModel.setCiutatUser(binding.editTextCiutat.text.toString().trim())
        viewModel.setDataNaixement(binding.editTextDataNaixement.text.toString().trim())
        viewModel.setIdentificadorPersonal(binding.editTextIdentificadorPersonal.text.toString().trim())
        viewModel.setNomUsuari(binding.editTextNomUsuari.text.toString().trim())
        viewModel.setEmail(binding.editTextEmailRegister.text.toString().trim())
        viewModel.setPassword(binding.editTextPassword.text.toString().trim())
        viewModel.setPassword2(binding.editTextPassword2.text.toString().trim())
        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoRegistro(1)
    }

    private fun saveDatesUserDataBase() {
        Log.d(TAG, "saveDatesUserDataBase: start")
        val auth = (activity as MainActivity).getAuth()
        Log.d(TAG, "getAuth - success")
        var sexe: String
        // se setea el sexe
        if (viewModel.sexe.value == binding.radioBtn1.id) sexe = "Dona" else sexe = "Home"
        Log.d(TAG, "setSexe - success")
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
            viewModel.identificadorPersonal.value.toString()
        )
        Log.d(TAG, "create_DataClass - success")
        // Se genera el acceso a la DDBB al nodo de cada usuari
        val myRefDadesUser =
            database.getReference("AllUsers/CurrentUsers/${auth.currentUser!!.uid}/userDates")
        Log.d(TAG, "connect_saveDataClass - success")
        // Se settean y suben los datos del nuevo usuario
        myRefDadesUser.setValue(user)
        Log.d(TAG, "saveDataClass - success")

        // Se genera el acceso a la DDBB a la llista amb els noms de usuaris disponibles
        val myRefNameUser = database.getReference(
            "AllUsers/LlistatUsersName/${
                viewModel.nomUsuari.value.toString().lowercase()
            }"
        )
        Log.d(TAG, "connect_saveNameUser_to_llista - success")
        // Se settean y suben el nou nom d'usuari a la llista que els compte tots
        myRefNameUser.setValue(auth.currentUser!!.uid)

        Log.d(TAG, "saveNameUser_to_llista - success")

        Log.d(TAG, "saveDatesUserDataBase: end")
    }

    /**
     * hay que verificar que el 'NameUser' no existeixi --------------------------------------------------------------------------------------------
     * -- Pendiente a implementar --
     */
    private fun controlNameUser() : Boolean{
        var existeix = false
        /*
        // Se genera el acceso a la DDBB a la llista amb els noms de usuaris disponibles
        val myRefNameUser = database.getReference("/AllUsers/LlistatUsersName/${viewModel.nomUsuari.toString().lowercase()}")
        // Se settean y suben el nou nom d'usuari a la llista que els compte tots
        if (myRefNameUser.key != null) {
            existeix = true
            (activity as MainActivity).toastView(myRefNameUser.key!!)
        }
        myRefNameUser.
        */

        return existeix
    }

    private fun controlDadesRegistre() : String? {
        var missatgeSortida : String? = null

        if (binding.editTextName.text.isEmpty() ) {
            missatgeSortida = "Falta el nombre!"
        } else if (binding.editTextCognom.text.isEmpty()) {
            missatgeSortida = "Faltan els cognoms!"
        } else if (binding.editTextEdat.text.isEmpty()) {
            missatgeSortida = "Falta la edat!"
        } else if (binding.radioGroupRegistre.checkedRadioButtonId == -1){
            missatgeSortida = "Falta el sexe!"
        } else if (binding.editTextCiutat.text.isEmpty()){
            missatgeSortida = "Falta la ciutat!"
        } else if (binding.editTextDataNaixement.text.isEmpty()){
            missatgeSortida = "Falta la data de naixement!"
        } else if (binding.editTextIdentificadorPersonal.text.isEmpty()){
            missatgeSortida = "Falta el DNI/NIE!"
        } else if (controlNameUser()){
            missatgeSortida = "El nom d'usuari ja existeix!"
        } else if (binding.editTextNomUsuari.text.isEmpty()){
            missatgeSortida = "Falta el nom d'usuari!"
        } else if (binding.editTextEmailRegister.text.isEmpty()){
            missatgeSortida = "Falta el email!"
        } else if (binding.editTextPassword.text.isEmpty() ){
            missatgeSortida = "Falta introduir el password"
        } else if (binding.editTextPassword2.text.isEmpty()){
            missatgeSortida = "Falta repetir la el password"
        }

        return missatgeSortida
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

        (activity as MainActivity).logut()
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistreViewModel::class.java)
    } */

}