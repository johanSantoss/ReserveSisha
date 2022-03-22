package johan.santos.reservesisha.ui.adminUser.manageUsers.configUser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigUsersFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigUsersFragment()
    }

    private lateinit var viewModel: ConfigUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_users_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigUsersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}