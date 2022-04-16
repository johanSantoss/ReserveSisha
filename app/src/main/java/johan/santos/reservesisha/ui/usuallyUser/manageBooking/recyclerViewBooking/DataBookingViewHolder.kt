package johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.databinding.ItemBookingBinding
import johan.santos.reservesisha.ui.access.models.DataBooking

class DataBookingViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemBookingBinding.bind(view)

    fun render (item : DataBooking, onClickListener: (DataBooking) -> Unit) {
        binding.tvBookingName.text          = item.nom_business
        binding.tvNameResContent.text       = item.nom_reserva
        binding.tvNumPersonasContent.text   = item.num_personas.toString()
        binding.tvFechaResContent.text      = item.fecha
        binding.tvHoraResContent.text       = item.hora
        binding.tvTipoResContent.text       = item.tipo_reserva
        binding.tvDireccioResContent.text   = item.direccion

        binding.tvBookingName.setOnClickListener {
            Toast.makeText(binding.tvBookingName.context, item.nom_business, Toast.LENGTH_SHORT).show()

        }

        binding.tvDireccioResContent.setOnClickListener {
            Toast.makeText(binding.tvBookingName.context, "Hay que abrir un intent a GoogleMaps", Toast.LENGTH_SHORT).show()
        }

        itemView.setOnClickListener {
            onClickListener(item)
        }
    }

}