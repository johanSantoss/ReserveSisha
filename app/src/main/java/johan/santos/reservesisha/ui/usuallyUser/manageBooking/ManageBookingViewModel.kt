package johan.santos.reservesisha.ui.usuallyUser.manageBooking

import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBooking

class ManageBookingViewModel : ViewModel() {

    // llistaReservas
    private var _llistaReservas = mutableListOf<DataBooking>()
    val llistaReservas: List<DataBooking> get() = _llistaReservas
    fun setLlistaReservas (novaLlista : MutableList<DataBooking>){
        _llistaReservas = novaLlista
    }
    fun addValueReserva (reserva : DataBooking){
        _llistaReservas.add(reserva)
    }

}