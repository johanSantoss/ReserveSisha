package johan.santos.reservesisha.ui.businessUser.manageTypes.configType

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigTypeViewModel : ViewModel() {

    // Name
    private val _name = MutableLiveData<String>("")
    val name : LiveData<String> get() = _name
    fun setName (nameRate : String){
        _name.value = nameRate
    }

    // Suplemento
    private val _suplemento = MutableLiveData<Int?>(null)
    val suplemento : LiveData<Int?> get() = _suplemento
    fun setSuplemento (estado : Int){
        _suplemento.value = estado
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }

}