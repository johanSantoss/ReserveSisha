package johan.santos.reservesisha.ui.usuallyUser.manageBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ManageBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ManageBookingFragment()
    }

    private lateinit var viewModel: ManageBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manage_booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageBookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}