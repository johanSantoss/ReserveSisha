package johan.santos.reservesisha.ui.usuallyUser.business

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.BusinessFragmentBinding
import johan.santos.reservesisha.databinding.CurrentUserMainFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataBusiness
import johan.santos.reservesisha.ui.usuallyUser.UserMainViewModel

class BusinessFragment : Fragment() {

    companion object {
        fun newInstance() = BusinessFragment()
    }

    private lateinit var viewModel: BusinessViewModel
    private lateinit var binding : BusinessFragmentBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")
        binding = BusinessFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)

        cargarDatos()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun cargarDatos(){
        val myRef = database.getReference("AllBusiness/")// + cif de la empresa a buscar

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                snapshot.children.forEach { item ->
                    item.children.forEach { valors ->
                        valors.getValue<DataBusiness>()?.let {

                            val anyBusiness = DataBusiness(
                                it?.cif?:"",
                                it?.nom_business?:"",
                                it?.direccio?:"",
                                it?.telefono?:"",
                                it?.descripcio?:"",
                                it?.horaOpen?:"",
                                it?.horaClose?:"",
                                it?.logo?:""
                            )
                            viewModel.setBusiness(anyBusiness)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}