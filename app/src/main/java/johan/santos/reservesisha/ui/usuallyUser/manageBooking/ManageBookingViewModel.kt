package johan.santos.reservesisha.ui.usuallyUser.manageBooking

import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataType

class ManageBookingViewModel : ViewModel() {

    // llistaReservas
    private var _llistaReservas = mutableListOf<DataBooking>()
    val llistaReservas: List<DataBooking> get() = _llistaReservas
    fun setLlistaReservas (novaLlista : MutableList<DataBooking>){
        _llistaReservas = novaLlista
    }
    fun addValueReserva (booking : DataBooking){
        _llistaReservas.add(booking)
    }
    fun delValueReservas (booking : DataBooking){
        _llistaReservas.remove(booking)
    }
    fun cleanListReservas(){
        _llistaReservas.clear()
    }

}