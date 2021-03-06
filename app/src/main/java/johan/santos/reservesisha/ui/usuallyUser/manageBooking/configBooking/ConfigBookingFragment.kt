package johan.santos.reservesisha.ui.usuallyUser.manageBooking.configBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.ConfigBookingFragmentBinding
import johan.santos.reservesisha.databinding.ConfigUserFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.access.models.TimePickerFragment
import johan.santos.reservesisha.ui.access.registre.DatePickerFragment
import johan.santos.reservesisha.ui.businessUser.manageTypes.configType.ConfigTypeFragmentArgs
import johan.santos.reservesisha.ui.usuallyUser.config.ConfigUserViewModel
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class ConfigBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigBookingFragment()
        private const val TAG = "ConfigUserFragment"
    }

    private lateinit var viewModel: ConfigBookingViewModel
    private lateinit var binding : ConfigBookingFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var database2 : DatabaseReference
    private lateinit var database3 : DatabaseReference
    private lateinit var spinnerItems : ArrayAdapter<String>
    private lateinit var datePicker: DatePickerFragment
    val args    : ConfigBookingFragmentArgs by navArgs()
    var directionBookin = ""
    var rateBooking = ""
    var idReserva = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ConfigBookingFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ConfigBookingViewModel::class.java)
        auth = (activity as MainActivity).getAuth()

        loadSpinerBooking()

        binding.etName.setText(args.nomBusiness)

        if (viewModel.estadoReserva.value == 1 ) {
            restaurarDatos()
        } else {
            if (!args.newBooking){
            //cargar los datos del usuario
                loadUser()
            }
        }

        binding.btnUpdate.setOnClickListener {
            // actualizar datos del usuario en viewModel
            saveDatesUserViewModel()
            getDireccion()
            getTarifa()
            // actualizar datos del usuario en DataBase
            //updateUser()
        }

        binding.etDataReserva.setOnClickListener {
            showDatePickerDialog()
        }

        binding.etHoraReserva.setOnClickListener {
            showTimePickerDialog()

        }

        return binding.root
    }

    private fun loadSpinerBooking() {
        spinnerItems = ArrayAdapter<String>((activity as MainActivity), android.R.layout.simple_spinner_item)

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        val path = "AllBusiness/${args.idBooking}/types"
        val myRef = database.getReference(path)

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                        item.getValue<DataRates>()?.let {

                            spinnerItems.add(it.name)
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.spinnerBooking.adapter = spinnerItems
    }

    private fun showTimePickerDialog() {
        var timePicker: TimePickerFragment

        timePicker = TimePickerFragment {
            binding.etHoraReserva.setText(it)
        }

        timePicker.show(childFragmentManager, "time")

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

        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates/reservas")
        database2.child(args.idBooking).get().addOnSuccessListener {

            if (it.exists()){
                binding.etNomReserva.setText(it.child("nom_reserva").value.toString())
                binding.etNumPersonas.setText(it.child("num_personas").value.toString())
                binding.etDataReserva.setText(it.child("fecha").value.toString())
                binding.etHoraReserva.setText(it.child("hora").value.toString())
                idReserva = it.child("id_booking").value.toString()
                saveDatesUserViewModel()

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }
    }

    private fun updateUser(){
        val c: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val strDate: String = sdf.format(c.getTime())
        var idBooking = ""

        if(idReserva == ""){
            idBooking = args.idBooking + strDate
        }else{
            idBooking = idReserva
        }

        val user = mapOf<String,String>(
            "nom_reserva"                       to viewModel.nomReserva.value!!,
            "num_personas"                   to viewModel.nPersonas.value!!,
            "fecha"                      to viewModel.dataReserva.value!!,
            "hora"                      to viewModel.horaReserva.value!!,
            "tipo_reserva"            to viewModel.tipoReserva.value!!,
        )
        database2 = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}/userDates/reservas")
        database3 = FirebaseDatabase.getInstance().getReference("AllBusiness/${args.idBooking}/reservas/")

        if(args.newBooking){
            //id_booking = nombre usuario mas la fecha de la reserva
            var booking = DataBooking(
                args.nomBusiness,
                viewModel.nomReserva.value.toString(),
                viewModel.nPersonas.value.toString(),
                viewModel.dataReserva.value.toString(),
                viewModel.horaReserva.value.toString(),
                viewModel.tipoReserva.value.toString(),
                directionBookin,
                auth.currentUser!!.uid,
                args.idBooking,
                idBooking,
                rateBooking,
                "false"

            )

            database2.child(args.idBooking).setValue(booking)
            database3.child(idBooking).setValue(booking)

            (activity as MainActivity).toastView("Successfuly Add")

            val action = ConfigBookingFragmentDirections.actionConfigBookingFragmentToBusinessFragment(args.idBooking)
            NavHostFragment.findNavController(this).navigate(action)

        }else{
            database2.child(args.idBooking).updateChildren(user).addOnSuccessListener {

                (activity as MainActivity).toastView("Successfuly Updated")

            }.addOnFailureListener{

                (activity as MainActivity).toastView("Failed")

            }

            database3.child(idBooking).updateChildren(user).addOnSuccessListener {

                (activity as MainActivity).toastView("Successfuly Updated")

            }.addOnFailureListener{

                (activity as MainActivity).toastView("Failed")

            }

            val action = ConfigBookingFragmentDirections.actionConfigBookingFragmentToManageBookingFragment()
            NavHostFragment.findNavController(this).navigate(action)

        }

    }

    private fun getTarifa(){

        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/${args.idBooking}/types")
        database2.child(viewModel.tipoReserva.value.toString()).get().addOnSuccessListener {

            if (it.exists()){

                rateBooking = it.child("suplemento").value.toString()

                if(directionBookin != ""){
                    updateUser()
                }

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }
    }

    private fun getDireccion() {

        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/${args.idBooking}/")
        database2.child("businessDates").get().addOnSuccessListener {

            if (it.exists()){

                directionBookin = it.child("direccio").value.toString()

                if(rateBooking != ""){
                    updateUser()
                }

            }else{
                (activity as MainActivity).toastView("User Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
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
        viewModel.setTipoReserva(binding.spinnerBooking.selectedItem.toString())
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