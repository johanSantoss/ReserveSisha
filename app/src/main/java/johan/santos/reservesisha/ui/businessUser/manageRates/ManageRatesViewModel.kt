package johan.santos.reservesisha.ui.businessUser.manageRates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataRates

class ManageRatesViewModel : ViewModel() {

    // llista de Rate's
    private var _llistaRates = mutableListOf<DataRates>()
    val llistaRates : List<DataRates> get() = _llistaRates
    fun setLlistaRates (novaLlista : MutableList<DataRates>){
        _llistaRates = novaLlista
    }
    fun addValueRate (reserva : DataRates){
        _llistaRates.add(reserva)
    }
    fun delValueRate (reserva : DataRates){
        _llistaRates.remove(reserva)
    }
    fun cleanListRate(){
        _llistaRates.clear()
    }


    // hay Rates
    private var _hayDatosList = MutableLiveData(false)
    val hayDatosList : LiveData<Boolean> get() = _hayDatosList
    fun setHayDatosList (hayDatos : Boolean){
        _hayDatosList.value = hayDatos
    }

}