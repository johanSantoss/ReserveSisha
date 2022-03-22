package johan.santos.reservesisha.ui.usuallyUser.manageBooking.configBooking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import johan.santos.reservesisha.R

class ConfigBookingFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigBookingFragment()
    }

    private lateinit var viewModel: ConfigBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfigBookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}