package johan.santos.reservesisha.ui.businessUser.manageTables

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ManageTablesFragment : Fragment() {

    companion object {
        fun newInstance() = ManageTablesFragment()
    }

    private lateinit var viewModel: ManageTablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manage_tables_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageTablesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}