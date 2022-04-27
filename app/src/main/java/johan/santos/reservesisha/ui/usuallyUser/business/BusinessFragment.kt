package johan.santos.reservesisha.ui.usuallyUser.business

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.BusinessFragmentBinding
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.businessUser.manageTypes.configType.ConfigTypeFragmentArgs
import johan.santos.reservesisha.ui.usuallyUser.UserMainViewModel
import johan.santos.reservesisha.ui.usuallyUser.manageBooking.ManageBookingFragmentDirections

class BusinessFragment : Fragment() {

    companion object {
        fun newInstance() = BusinessFragment()
        const val PATH_PHOTO1 = "https://estaticos-cdn.prensaiberica.es/clip/09190588-d4d0-49ac-a40e-bbfbab69a6fb_16-9-aspect-ratio_default_0.jpg"
        const val PATH_PHOTO2 = "https://d500.epimg.net/cincodias/imagenes/2015/10/29/lifestyle/1446136907_063470_1446137018_noticia_normal.jpg"
    }

    private lateinit var viewModel: BusinessViewModel
    private lateinit var binding : BusinessFragmentBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var database2 : DatabaseReference
    //private var oldRate                : DataBusiness = DataBusiness("","", "", "", "", "", "", "")
    val args    : BusinessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        binding = BusinessFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)

        Glide.with(binding.imageView.context).load(PATH_PHOTO1).into(binding.imageView)
        Glide.with(binding.imMaps.context).load(PATH_PHOTO2).into(binding.imMaps)

        loadBusiness()

        binding.btnReservar.setOnClickListener {
            val action = BusinessFragmentDirections.actionBusinessFragmentToConfigBookingFragment(args.cif, true, binding.tvNomEmpresa.text.toString())
            it.findNavController().navigate(action)
        }

        return binding.root
    }

    private fun loadBusiness(){
        database2 = FirebaseDatabase.getInstance().getReference("AllBusiness/${args.cif}")
        database2.child("businessDates").get().addOnSuccessListener {
            if (it.exists()){

                binding.tvNomEmpresa.setText(it.child("nom_business").value.toString())
                binding.tvTelefonoContent.setText(it.child("telefono").value.toString())
                binding.tvDireccioResContent.setText(it.child("direccio").value.toString())
                binding.tvDescripcioContent.setText(it.child("descripcio").value.toString())

            }else{
                (activity as MainActivity).toastView("Rate! Doesn't Exist")
            }
        }.addOnFailureListener{
            (activity as MainActivity).toastView("Failed")
        }

    }

}