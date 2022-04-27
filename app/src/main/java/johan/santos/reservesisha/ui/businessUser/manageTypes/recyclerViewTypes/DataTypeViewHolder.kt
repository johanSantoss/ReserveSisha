package johan.santos.reservesisha.ui.businessUser.manageTypes.recyclerViewTypes

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import johan.santos.reservesisha.databinding.ItemTypeBinding
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.businessUser.manageTypes.ManageTypesFragmentDirections

class DataTypeViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemTypeBinding.bind(view)

    fun render (item : DataType, onClickListener: (DataType) -> Unit) {

        binding.tvNameType.text = item.name
        binding.tvSuplementoContent.text = item.suplemento

        binding.ivItemEdit.setOnClickListener {
            val action = ManageTypesFragmentDirections.actionManageTypesFragmentToConfigTypeFragment(item.name.toString(), false)
            it.findNavController().navigate(action)
        }

        binding.ivItemDelete.setOnClickListener {
            onClickListener(item)
        }

    }

}