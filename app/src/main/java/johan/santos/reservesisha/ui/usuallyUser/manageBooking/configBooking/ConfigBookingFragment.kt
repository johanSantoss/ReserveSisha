package johan.santos.reservesisha.ui.usuallyUser.manageBooking.configBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigBookingFragmentBinding
import johan.santos.reservesisha.databinding.ConfigUserFragmentBinding
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment
import johan.santos.reservesisha.ui.usuallyUser.config.ConfigUserViewModel

class ConfigBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigBookingFragment()
        private const val TAG = "ConfigUserFragment"
    }

    private lateinit var viewModel: ConfigBookingViewModel
    private lateinit var binding : ConfigBookingFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database2 : DatabaseReference
    private lateinit var datePicker: DatePickerFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ConfigBookingFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ConfigBookingViewModel::class.java)
        auth = (activity as MainActivity).getAuth()

        if (viewModel.estadoReserva.value == 1 ) {
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

        binding.etDataReserva.setOnClickListener {
            showDatePickerDialog()
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(childFragmentManager, "datePicker")
    }
    // setea el "editText" con la fecha obtenida
    private fun onDateSelected(day:Int, month:Int, year:Int){
        binding.etDataReserva.setText("$day/$month/$year")
    }

    private fun loadUser() {

        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates/reserva")
        database2.child("idempresa").get().addOnSuccessListener {

            if (it.exists()){

                binding.etName.setText(it.child("nom_business").value.toString())
                binding.etNomReserva.setText(it.child("nom_reserva").value.toString())
                binding.etNumPersonas.setText(it.child("num_personas").value.toString())
                binding.etDataReserva.setText(it.child("fecha").value.toString())
                binding.etHoraReserva.setText(it.child("hora").value.toString())
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
            "nom_business"                to viewModel.nomEmpresa.value!!,
            "nom_reserva"                       to viewModel.nomReserva.value!!,
            "num_personas"                   to viewModel.nPersonas.value!!,
            "fecha"                      to viewModel.dataReserva.value!!,
            "hora"                      to viewModel.horaReserva.value!!,
            "tipo_reserva"            to viewModel.tipoReserva.value!!,
            "direccion"                    to viewModel.direccio.value!!,
            "id_user"    to viewModel.idUser.value!!,
            "id_empresa"    to viewModel.idEmpresa.value!!,
            "confirmada"    to viewModel.confirmada.value!!.toString()
        )
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates/reservas")

        database2.child("idempresa").updateChildren(user).addOnSuccessListener {

            (activity as MainActivity).toastView("Successfuly Updated")

        }.addOnFailureListener{

            (activity as MainActivity).toastView("Failed Updated")

        }
    }

    private fun restaurarDatos(){
        binding.etName.setText(viewModel.nomEmpresa.value)
        binding.etNomReserva.setText(viewModel.nomReserva.value)
        binding.etNumPersonas.setText(viewModel.nPersonas.value)
        binding.etDataReserva.setText(viewModel.dataReserva.value)
        binding.etHoraReserva.setText(viewModel.horaReserva.value)
        // tipo reserva
    }

    private fun saveDatesUserViewModel(){
        viewModel.setNomEmpresa(binding.etName.text.toString().trim())
        viewModel.setNomReserva(binding.etNomReserva.text.toString().trim())
        viewModel.setNPersonas(binding.etNumPersonas.text.toString().trim())
        viewModel.setDataReserva(binding.etDataReserva.text.toString().trim())
        viewModel.setHoraReserva(binding.etHoraReserva.text.toString().trim())
        //tipo reserva

        // indica que los datos sehan guardado y por lo tanto se han de restaurar
        viewModel.setEstadoReserva(1)
    }

    override fun onStop() {
        super.onStop()
        saveDatesUserViewModel()
        //datePicker.exitTransition
    }

}