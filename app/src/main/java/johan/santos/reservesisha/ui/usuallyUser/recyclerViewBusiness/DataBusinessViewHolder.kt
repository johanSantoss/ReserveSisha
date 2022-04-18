package johan.santos.reservesisha.ui.usuallyUser.recyclerViewBusiness

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.databinding.ItemBusinessBinding
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingFragmentDirections
import johan.santos.reservesisha.ui.usuallyUser.userMainFragment

class DataBusinessViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemBusinessBinding.bind(view)

    fun render (item : DataBusiness, onClickListener: (DataBusiness) -> Unit) {
        binding.tvItemBusinessName.text = item.nom_business
        binding.tvItemBusinessDetails.text = item.descripcio
        Glide.with(binding.ivBusiness.context).load(item.logo).into(binding.ivBusiness)

        binding.ivBusiness.setOnClickListener {
            Toast.makeText(binding.ivBusiness.context, item.nom_business, Toast.LENGTH_SHORT).show()
        }

        itemView.setOnClickListener {
//            onClickListener(item)

//            val action = userMainFragmentDirection.actionManageBookingFragmentToConfigBookingFragment()
//            it.findNavController().navigate(action)
        }
    }

}