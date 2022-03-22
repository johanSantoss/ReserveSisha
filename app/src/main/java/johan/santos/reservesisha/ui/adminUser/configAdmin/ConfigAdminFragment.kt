package johan.santos.reservesisha.ui.adminUser.configAdmin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigAdminFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigAdminFragment()
    }

    private lateinit var viewModel: ConfigAdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigAdminViewModel::class.java)
        // TODO: Use the ViewModel
    }

}