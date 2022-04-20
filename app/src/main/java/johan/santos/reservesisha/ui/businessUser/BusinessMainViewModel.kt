package johan.santos.reservesisha.ui.businessUser

import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBooking

class BusinessMainViewModel : ViewModel() {

    // llistaReservas
    private var _llistaReservas = mutableListOf<DataBooking>()
    val llistaReservas: List<DataBooking> get() = _llistaReservas

    fun addValueReserva (reserva : DataBooking){
        _llistaReservas.add(reserva)
    }
    fun clearReservas(){
        _llistaReservas.clear()
    }


}