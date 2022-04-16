package johan.santos.reservesisha.ui.adminUser.manageUsers.recyclerViewUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.R
import johan.santos.reservesisha.ui.access.models.DataUsers

class DataUsersAdapter (private val llistaSUsers : List<DataUsers>, private val onClickListener: (DataUsers) -> Unit) : RecyclerView.Adapter<DataUsersViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataUsersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataUsersViewHolder(layoutInflater.inflate(R.layout.item_users_manage, parent, false))
    }

    override fun onBindViewHolder(holder: DataUsersViewHolder, position: Int) {
        val item = llistaSUsers[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = llistaSUsers.size
}