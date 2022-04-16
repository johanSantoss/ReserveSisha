package johan.santos.reservesisha.ui.usuallyUser.manageBooking.recyclerViewBooking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.R
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.usuallyUser.recyclerViewBusiness.DataBusinessViewHolder

class DataBookingAdapter (private val llistaReservas : List<DataBooking>, private val onClickListener: (DataBooking) -> Unit) : RecyclerView.Adapter<DataBookingViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBookingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBookingViewHolder(layoutInflater.inflate(R.layout.item_booking, parent, false))
    }

    override fun onBindViewHolder(holder: DataBookingViewHolder, position: Int) {
        val item = llistaReservas[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = llistaReservas.size
}