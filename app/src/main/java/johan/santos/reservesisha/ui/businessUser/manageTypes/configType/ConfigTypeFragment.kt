package johan.santos.reservesisha.ui.businessUser.manageTypes.configType

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigTypeFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigTypeFragment()
    }

    private lateinit var viewModel: ConfigTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_type_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigTypeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}