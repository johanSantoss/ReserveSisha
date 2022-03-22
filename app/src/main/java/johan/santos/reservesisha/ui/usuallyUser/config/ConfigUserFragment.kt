package johan.santos.reservesisha.ui.usuallyUser.config

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigUserFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigUserFragment()
    }

    private lateinit var viewModel: ConfigUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigUserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}