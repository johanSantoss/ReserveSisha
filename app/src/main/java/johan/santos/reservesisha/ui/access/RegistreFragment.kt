package johan.santos.reservesisha.ui.access

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class RegistreFragment : Fragment() {

    companion object {
        fun newInstance() = RegistreFragment()
    }

    private lateinit var viewModel: RegistreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registre_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}