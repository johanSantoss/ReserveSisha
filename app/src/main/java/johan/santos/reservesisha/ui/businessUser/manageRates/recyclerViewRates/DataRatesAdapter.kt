package johan.santos.reservesisha.ui.businessUser.manageRates.recyclerViewRates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.R
import johan.santos.reservesisha.ui.access.models.DataRates

class DataRatesAdapter (private val llistaRates : List<DataRates>, private val onClickListener: (DataRates) -> Unit) : RecyclerView.Adapter<DataRatesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRatesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataRatesViewHolder(layoutInflater.inflate(R.layout.item_rates, parent, false))
    }

    override fun onBindViewHolder(holder: DataRatesViewHolder, position: Int) {
        val item = llistaRates[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = llistaRates.size
}