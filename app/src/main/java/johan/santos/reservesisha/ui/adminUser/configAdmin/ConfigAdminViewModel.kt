package johan.santos.reservesisha.ui.adminUser.configAdmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigAdminViewModel : ViewModel() {

    // nom d'usuari
    private val _nomUsuari = MutableLiveData<String>("")
    val nomUsuari: LiveData<String> get() = _nomUsuari
    fun setNomUsuari (nomUsuari : String){
        _nomUsuari.value = nomUsuari
    }

    // nom email
    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> get() = _email
    fun setEmail (email : String){
        _email.value = email
    }

    // nom password
    private val _pass = MutableLiveData<String>("")
    val pass: LiveData<String> get() = _pass
    fun setPass (pass : String){
        _pass.value = pass
    }

    // nom confirmacio password
    private val _passConf = MutableLiveData<String>("")
    val passConf: LiveData<String> get() = _passConf
    fun setPassConf (passConf : String){
        _passConf.value = passConf
    }

    // nom confirmacio password
    private val _swLogin = MutableLiveData<Boolean>(false)
    val swLogin: LiveData<Boolean> get() = _swLogin
    fun setSwLogin (check : Boolean){
        _swLogin.value = check
    }

    // nom confirmacio password
    private val _isExist = MutableLiveData<Boolean>(false)
    val isExist: LiveData<Boolean> get() = _isExist
    fun setIsExist (check : Boolean){
        _isExist.value = check
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }
}