package johan.santos.reservesisha.ui.adminUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.AdminMainFragmentBinding
import johan.santos.reservesisha.databinding.LoginFragmentBinding
import johan.santos.reservesisha.ui.access.login.LoginFragmentDirections


class AdminMainFragment : Fragment() {

    private lateinit var binding : AdminMainFragmentBinding
    private lateinit var viewModel: AdminMainViewModel
    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = AdminMainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.admin_main_fragment,
            container,
            false
        )
        auth = (activity as MainActivity).getAuth()

        binding.btnLogut.setOnClickListener {
            (activity as MainActivity).logutAndExit()
        }

        binding.manageUsers.setOnClickListener {
            val action = AdminMainFragmentDirections.actionAdminMainFragmentToManageUsersFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.manageBusiness.setOnClickListener {
            val action = AdminMainFragmentDirections.actionAdminMainFragmentToConfigAdminFragment(auth.currentUser?.uid.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}