package johan.santos.reservesisha.ui.businessUser.configBusiness

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigBusinessFragmentBinding
import johan.santos.reservesisha.databinding.ConfigUserFragmentBinding
import johan.santos.reservesisha.ui.access.models.TimePickerFragment
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment

class ConfigBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigBusinessFragment()
        private const val TAG = "ConfigBusinessFragment"
    }

    private lateinit var viewModel: ConfigBusinessViewModel
    private lateinit var binding : ConfigBusinessFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database2 : DatabaseReference
    private lateinit var datePicker: DatePickerFragment
    private lateinit var timePicker: TimePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding     = ConfigBusinessFragmentBinding.inflate(layoutInflater)
        viewModel   = ViewModelProvider(this).get(ConfigBusinessViewModel::class.java)

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

        binding.etHoraOpen.setOnClickListener {
            showTimePickerDialog(1)

        }

        binding.etHoraClose.setOnClickListener {
            showTimePickerDialog(2)
        }

        return binding.root
    }

    private fun showTimePickerDialog(num : Int) {
        var timePicker: TimePickerFragment
        if (num == 1){
            timePicker = TimePickerFragment {
                onTimeIni(it)
            }
        } else {
            timePicker = TimePickerFragment {
                onTimeFin(it)
            }
        }
        timePicker.show(childFragmentManager, "time")

    }

    private fun onTimeIni(time : String){
        binding.etHoraOpen.setText(time)
    }
    private fun onTimeFin(time : String){
        binding.etHoraClose.setText(time)
    }

    private fun restaurarDatos(){

        binding.etUserNomUsuari.setText(viewModel.nomUsuari.value)
        binding.etUserMail.setText(viewModel.email.value)
        binding.etBusiNomEmpresa.setText(viewModel.nomEmpresa.value)
        binding.etBusiDirecio.setText(viewModel.direccio.value)
        binding.etHoraOpen.setText(viewModel.horaInici.value)
        binding.etHoraClose.setText(viewModel.horaFinal.value)
        binding.etBusiTel.setText(viewModel.telefon.value)
        binding.etDescripcio.setText(viewModel.descripcio.value)
        binding.etBusiIdentificador.setText(viewModel.identEmpresa.value)

    }

    private fun loadUser() {
        // datos del User empresa
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database2.child("userDates").get().addOnSuccessListener {

            if (it.exists()){

                binding.etUserNomUsuari.setText(it.child("nom_usuari").value.toString())
                binding.etUserMail.setText(it.child("email").value.toString())
                binding.etBusiNomEmpresa.setText(it.child("nom_business").value.toString())
                binding.etBusiDirecio.setText(it.child("direccio").value.toString())
                binding.etBusiTel.setText(it.child("telefon").value.toString())
                binding.etDescripcio.setText(it.child("descripcio").value.toString())
                binding.etBusiIdentificador.setText(it.child("cif").value.toString())

                saveDatesUserViewModel()

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }

        // datos de la empresa
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/${viewModel.identEmpresa.value}")
        database2.child("businessDates").get().addOnSuccessListener {

            if (it.exists()){

                binding.etHoraOpen.setText(it.child("horaOpen").value.toString())
                binding.etHoraClose.setText(it.child("horaClose").value.toString())

                saveDatesUserViewModel()

            }else{
                binding.etHoraOpen.setText("")
                binding.etHoraClose.setText("")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }

    }

    private fun updateUser(){
        // update dates Usurari
        var user = mapOf<String,String>(
            "nom_usuari"    to viewModel.nomUsuari.value!!,
            "email"         to viewModel.email.value!!,
            "nom_business"  to viewModel.nomEmpresa.value!!,
            "direccio"      to viewModel.direccio.value!!,
            "telefon"       to viewModel.telefon.value!!,
            "descripcio"    to viewModel.direccio.value!!
        )
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")

        database2.child("userDates").updateChildren(user).addOnSuccessListener {

            (activity as MainActivity).toastView("Successfuly Updated dataUser")

        }.addOnFailureListener{

            (activity as MainActivity).toastView("Failed Updated dataUser")

        }

        // update dates Empresa
        user = mapOf<String,String>(
            "horaOpen"    to viewModel.nomUsuari.value!!,
            "horaClose"   to viewModel.email.value!!
        )
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/${viewModel.identEmpresa.value!!}")

        database2.child("businessDates").updateChildren(user).addOnSuccessListener {

            (activity as MainActivity).toastView("Successfuly Updated dataBusiness")

        }.addOnFailureListener{

            (activity as MainActivity).toastView("Failed Updated dataBusiness")

        }

    }

    private fun saveDatesUserViewModel(){

        viewModel.setNomUsuari(binding.etUserNomUsuari.text.toString().trim())
        viewModel.setEmail(binding.etUserMail.text.toString().trim())
        viewModel.setNomEmpresa(binding.etBusiNomEmpresa.text.toString().trim())
        viewModel.setDireccio(binding.etBusiDirecio.text.toString().trim())
        viewModel.setHoraInici(binding.etHoraOpen.text.toString().trim())
        viewModel.setHoraFinal(binding.etHoraClose.text.toString().trim())
        viewModel.setTelefon(binding.etBusiTel.text.toString().trim())
        viewModel.setDescripcio(binding.etDescripcio.text.toString().trim())
        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoRegistro(1)
    }

    /*
    override fun onDestroy() {
        super.onDestroy()
        // actualizar datos del usuario en viewModel
        saveDatesUserViewModel()
        // actualizar datos del usuario en DataBase
        updateUser()
    }*/

}