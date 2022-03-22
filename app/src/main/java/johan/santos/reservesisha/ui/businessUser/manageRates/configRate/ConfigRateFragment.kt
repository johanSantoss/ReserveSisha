package johan.santos.reservesisha.ui.businessUser.manageRates.configRate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigRateFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigRateFragment()
    }

    private lateinit var viewModel: ConfigRateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_rate_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigRateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}