package johan.santos.reservesisha.ui.businessUser.configBusiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigBusinessViewModel : ViewModel() {

    // nom d'usuari
    private val _nomUsuari = MutableLiveData<String>("")
    val nomUsuari: LiveData<String> get() = _nomUsuari
    fun setNomUsuari (nomUsuari : String){
        _nomUsuari.value = nomUsuari
    }

    // email
    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> get() = _email
    fun setEmail (email : String){
        _email.value = email
    }

    // nom empresa
    private val _nomEmpresa = MutableLiveData<String>("")
    val nomEmpresa: LiveData<String> get() = _nomEmpresa
    fun setNomEmpresa (nomEmpresa : String){
        _nomEmpresa.value = nomEmpresa
    }

    // direccio
    private val _direccio = MutableLiveData<String>("")
    val direccio: LiveData<String> get() = _direccio
    fun setDireccio (dir : String){
        _direccio.value = dir
    }

    // hora inici
    private val _horaInici = MutableLiveData<String>("")
    val horaInici: LiveData<String> get() = _horaInici
    fun setHoraInici (horaInici : String){
        _horaInici.value = horaInici
    }

    // hora final
    private val _horaFinal = MutableLiveData<String>("")
    val horaFinal : LiveData<String> get() = _horaFinal
    fun setHoraFinal (horaFinal : String){
        _horaFinal.value = horaFinal
    }

    // telèfon
    private val _telefon = MutableLiveData<String>("")
    val telefon : LiveData<String> get() = _telefon
    fun setTelefon (telefon : String){
        _telefon.value = telefon
    }

    // descripcio
    private val _descripcio = MutableLiveData<String>("")
    val descripcio : LiveData<String> get() = _descripcio
    fun setDescripcio (descri : String){
        _descripcio.value = descri
    }

    // Document d'identitat
    private val _identEmpresa = MutableLiveData<String>("")
    val identEmpresa: LiveData<String> get() = _identEmpresa
    fun setIdentiEmpresa (identificadorPersonal : String){
        _identEmpresa.value = identificadorPersonal
    }

    // user map
    private val _userMap = MutableLiveData<Map<String, String>>()
    val userMap: LiveData<Map<String, String>> get() = _userMap
    fun setUserMap (userMap : Map<String, String>){
        _userMap.value = userMap
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }
}