package johan.santos.reservesisha.ui.adminUser.manageUsers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ManageUsersFragment : Fragment() {

    companion object {
        fun newInstance() = ManageUsersFragment()
    }

    private lateinit var viewModel: ManageUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manage_users_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageUsersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}