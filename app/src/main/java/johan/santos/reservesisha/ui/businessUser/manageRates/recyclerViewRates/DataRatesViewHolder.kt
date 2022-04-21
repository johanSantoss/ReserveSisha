package johan.santos.reservesisha.ui.businessUser.manageRates.recyclerViewRates

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.databinding.ItemRatesBinding
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.access.models.DataRates
import johan.santos.reservesisha.ui.adminUser.manageUsers.ManageUsersFragmentDirections
import johan.santos.reservesisha.ui.businessUser.manageRates.ManageRatesFragmentDirections

class DataRatesViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemRatesBinding.bind(view)

    fun render (item : DataRates, onClickListener: (DataRates) -> Unit) {

        binding.tvNameRate.text = item.name
        binding.tvPriceContent.text = item.price

        binding.ivItemRateEdit.setOnClickListener {
            val action = ManageRatesFragmentDirections.actionManageRatesFragmentToConfigRateFragment(item.name, false)
            it.findNavController().navigate(action)
        }

        binding.ivItemRateDelete.setOnClickListener {
            onClickListener(item)
        }

    }

}