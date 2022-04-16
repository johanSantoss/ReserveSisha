package johan.santos.reservesisha.ui.adminUser.manageUsers.recyclerViewUsers

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.databinding.ItemUsersManageBinding
import johan.santos.reservesisha.ui.access.login.LoginFragmentDirections
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragment
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragmentDirections

class DataUsersViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemUsersManageBinding.bind(view)

    fun render (item : DataUsers, onClickListener: (DataUsers) -> Unit) {
        binding.tvItemUsersId.text = item.id_usuari
        binding.tvItemUserName.text = item.nom_usuari
        binding.tvItemUsersRName.text = item.nom
        binding.tvItemUsersLastName.text = item.cognoms
        binding.tvItemUserRol.text = item.rol

        binding.tvItemEdit.setOnClickListener{
            val action = ManageUsersFragmentDirections.actionManageUsersFragmentToConfigAdminFragment(item.id_usuari.toString())
            it.findNavController().navigate(action)
        }

//        itemView.setOnClickListener {
//            onClickListener(item)
//        }
    }

}