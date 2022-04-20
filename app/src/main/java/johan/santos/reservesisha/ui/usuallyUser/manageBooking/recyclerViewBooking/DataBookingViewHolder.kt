package johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.databinding.ItemBookingBinding
import johan.santos.reservesisha.databinding.ManageBookingFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragmentDirections
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingFragmentDirections

class DataBookingViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemBookingBinding.bind(view)
    private lateinit var database: FirebaseDatabase

    fun render (item : DataBooking, onClickListener: (DataBooking) -> Unit) {
        binding.tvBookingName.text          = item.nom_business
        binding.tvNameResContent.text       = item.nom_reserva
        binding.tvNumPersonasContent.text   = item.num_personas.toString()
        binding.tvFechaResContent.text      = item.fecha
        binding.tvHoraResContent.text       = item.hora
        binding.tvTipoResContent.text       = item.tipo_reserva
        binding.tvDireccioResContent.text   = item.direccion

        binding.tvEditBooking.setOnClickListener {
            val action = ManageBookingFragmentDirections.actionManageBookingFragmentToConfigBookingFragment()
            it.findNavController().navigate(action)
        }

        binding.tvDeleteBooking.setOnClickListener {
            database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
            // delete on Business
            val myRefDadesBusiness = database.getReference("AllBusiness/${item.id_empresa}/reservas/${item.id_booking}")
            myRefDadesBusiness.removeValue()

            // delete on Current_User
            val myRefDadesUser = database.getReference("AllUsers/${item.id_user}/reservas/${item.id_booking}")
            myRefDadesUser.removeValue()
        }

        itemView.setOnClickListener {
            onClickListener(item)
        }
    }

}