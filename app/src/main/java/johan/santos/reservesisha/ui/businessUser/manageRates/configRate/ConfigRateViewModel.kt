package johan.santos.reservesisha.ui.businessUser.manageRates.configRate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigRateViewModel : ViewModel() {

    // Name
    private val _name = MutableLiveData<String>("")
    val name : LiveData<String> get() = _name
    fun setName (nameRate : String){
        _name.value = nameRate
    }

    // Price
    private val _price = MutableLiveData<String>("")
    val price : LiveData<String> get() = _price
    fun setPrice (estado : String){
        _price.value = estado
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }

}