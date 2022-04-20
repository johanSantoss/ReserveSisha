package johan.santos.reservesisha.ui.adminUser.manageUsers.recyclerViewUsers

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.databinding.ItemUsersManageBinding
import johan.santos.reservesisha.ui.access.login.LoginFragmentDirections
import johan.santos.reservesisha.ui.access.models.DataUsers
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragment
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragmentDirections

class DataUsersViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemUsersManageBinding.bind(view)
    private lateinit var database: FirebaseDatabase

    fun render (item : DataUsers, onClickListener: (DataUsers) -> Unit) {
        binding.tvItemUsersId.text = item.id_usuari
        binding.tvItemUserName.text = item.nom_usuari
        binding.tvItemUserRol.text = item.rol

        binding.tvItemEdit.setOnClickListener{
            //item.id_usuari.toString()
            val action = ManageUsersFragmentDirections.actionManageUsersFragmentToConfigUsersFragment(item.id_usuari.toString(), item.rol.toString() , false)
            it.findNavController().navigate(action)
        }

        binding.tvItemUserDelete.setOnClickListener{
            onClickListener(item)
        }

    }

}