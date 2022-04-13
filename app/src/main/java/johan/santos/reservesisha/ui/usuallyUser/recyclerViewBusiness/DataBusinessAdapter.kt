package johan.santos.reservesisha.ui.usuallyUser.recyclerViewBusiness

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.R
import johan.santos.reservesisha.ui.access.models.DataBusiness

class DataBusinessAdapter (private val llistaSBusiness : List<DataBusiness>, private val onClickListener: (DataBusiness) -> Unit) : RecyclerView.Adapter<DataBusinessViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBusinessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBusinessViewHolder(layoutInflater.inflate(R.layout.item_business, parent, false))
    }

    override fun onBindViewHolder(holder: DataBusinessViewHolder, position: Int) {
        val item = llistaSBusiness[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = llistaSBusiness.size
}