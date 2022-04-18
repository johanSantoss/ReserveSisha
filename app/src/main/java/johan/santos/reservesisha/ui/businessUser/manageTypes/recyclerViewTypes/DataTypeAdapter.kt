package johan.santos.reservesisha.ui.businessUser.manageTypes.recyclerViewTypes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.R
import johan.santos.reservesisha.ui.access.models.DataType

class DataTypeAdapter (private val llistaTypes : List<DataType>, private val onClickListener: (DataType) -> Unit) : RecyclerView.Adapter<DataTypeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataTypeViewHolder(layoutInflater.inflate(R.layout.item_type, parent, false))
    }

    override fun onBindViewHolder(holder: DataTypeViewHolder, position: Int) {
        val item = llistaTypes[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = llistaTypes.size
}