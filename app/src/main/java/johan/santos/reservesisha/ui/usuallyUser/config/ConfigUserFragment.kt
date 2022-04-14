package johan.santos.reservesisha.ui.usuallyUser.config

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigUserFragmentBinding
import johan.santos.reservesisha.ui.access.models.User_Current
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment
import johan.santos.reservesisha.ui.access.registre.RegistreFragment
import johan.santos.reservesisha.ui.usuallyUser.UserMainViewModel

class ConfigUserFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigUserFragment()
        private const val TAG = "ConfigUserFragment"
    }

    private lateinit var viewModel: ConfigUserViewModel
    private lateinit var binding : ConfigUserFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database2 : DatabaseReference
    private lateinit var datePicker: DatePickerFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConfigUserFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ConfigUserViewModel::class.java)
        auth = (activity as MainActivity).getAuth()

        if (viewModel.estadoRegistro.value == 1 ) {
            restaurarDatos()
        } else {
            //cargar los datos del usuario
            loadUser()
        }

        binding.btnUpdate.setOnClickListener {
            // actualizar datos del usuario en viewModel
            saveDatesUserViewModel()
            // actualizar datos del usuario en DataBase
            updateUser()
        }

        binding.etDataNaixement.setOnClickListener {
            showDatePickerDialog()
        }

        return binding.root
    }

    // generar un picker para poder seleccionar la fecha requerida
    private fun showDatePickerDialog() {
        datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(childFragmentManager, "datePicker")
    }
    // setea el "editText" con la fecha obtenida
    private fun onDateSelected(day:Int, month:Int, year:Int){
        binding.etDataNaixement.setText("$day/$month/$year")
    }

    private fun loadUser() {

        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database2.child("userDates").get().addOnSuccessListener {

            if (it.exists()){

                binding.etNomUsuari.setText(it.child("nom_usuari").value.toString())
                binding.etName.setText(it.child("nom").value.toString())
                binding.etCognom.setText(it.child("cognoms").value.toString())
                binding.etEdat.setText(it.child("edat").value.toString())
                if (it.child("sexe").value.toString().equals("Dona")){
                    binding.radioGroupSexe.check(binding.radioDone.id)
                } else {
                    binding.radioGroupSexe.check(binding.radioHome.id)
                }
                binding.etDataNaixement.setText(it.child("data_naixement").value.toString())
                binding.etCiutat.setText(it.child("ciutat").value.toString())
                binding.etIdentificadorPersonal.setText(it.child("identificador_personal").value.toString())
                saveDatesUserViewModel()

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }
    }

    private fun updateUser(){

        val user = mapOf<String,String>(
            "nom_usuari"                to viewModel.nomUsuari.value!!,
            "nom"                       to viewModel.nom.value!!,
            "cognoms"                   to viewModel.cognom.value!!,
            "edat"                      to viewModel.edat.value!!,
            "sexe"                      to viewModel.sexe.value!!,
            "data_naixement"            to viewModel.dataNaixement.value!!,
            "ciutat"                    to viewModel.ciutat.value!!,
            "identificador_personal"    to viewModel.identificadorPersonal.value!!
        )
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")

        database2.child("userDates").updateChildren(user).addOnSuccessListener {

            (activity as MainActivity).toastView("Successfuly Updated")

        }.addOnFailureListener{

            (activity as MainActivity).toastView("Failed Updated")

        }
    }

    private fun restaurarDatos(){
        binding.etName.setText(viewModel.nom.value)
        binding.etCognom.setText(viewModel.cognom.value)
        binding.etEdat.setText(viewModel.edat.value)
        if (viewModel.sexe.value == "Dona") {
            binding.radioGroupSexe.check(binding.radioDone.id)
        } else {
            binding.radioGroupSexe.check(binding.radioHome.id)
        }
        binding.etCiutat.setText(viewModel.ciutat.value)
        binding.etDataNaixement.setText(viewModel.dataNaixement.value)
        binding.etIdentificadorPersonal.setText(viewModel.identificadorPersonal.value)
        binding.etNomUsuari.setText(viewModel.nomUsuari.value)
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNom(binding.etName.text.toString().trim())
        viewModel.setCognom(binding.etCognom.text.toString().trim())
        viewModel.setEdatUser(binding.etEdat.text.toString().trim())
        if (binding.radioGroupSexe.checkedRadioButtonId == binding.radioDone.id){
            viewModel.setSexeUser("Dona")
        } else {
            viewModel.setSexeUser("Home")
        }
        viewModel.setCiutatUser(binding.etCiutat.text.toString().trim())
        viewModel.setDataNaixement(binding.etDataNaixement.text.toString().trim())
        viewModel.setIdentificadorPersonal(binding.etIdentificadorPersonal.text.toString().trim())
        viewModel.setNomUsuari(binding.etNomUsuari.text.toString().trim())
        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoRegistro(1)
    }

    override fun onStop() {
        super.onStop()
        saveDatesUserViewModel()
        //datePicker.exitTransition
    }

}