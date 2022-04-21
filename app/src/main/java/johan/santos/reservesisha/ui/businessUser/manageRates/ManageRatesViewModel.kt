package johan.santos.reservesisha.ui.businessUser.manageRates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataRates

class ManageRatesViewModel : ViewModel() {

    // llista de Rate's
    private var _llistaRates: MutableList<DataRates> = mutableListOf()
    val llistaRates : List<DataRates> get() = _llistaRates
    fun addValueRate (reserva : DataRates){
        _llistaRates.add(reserva)
    }
    fun delValueRate (reserva : DataRates){
        _llistaRates.remove(reserva)
    }
    fun cleanListRate(){
        _llistaRates.clear()
    }

}