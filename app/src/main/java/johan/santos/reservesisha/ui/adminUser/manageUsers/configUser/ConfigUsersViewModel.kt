package johan.santos.reservesisha.ui.adminUser.manageUsers.configUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigUsersViewModel : ViewModel() {
    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> get() = _email
    fun setEmail (email : String){
        _email.value = email
    }

    // password
    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> get() = _password
    fun setPassword (pass : String){
        _password.value = pass
    }

    // password
    private val _rol = MutableLiveData<String>("")
    val rol : LiveData<String> get() = _rol
    fun setRol (rol : String){
        _rol.value = rol
    }

    // CIF
    private val _numCIF = MutableLiveData<String>("")
    val numCIF: LiveData<String> get() = _numCIF
    fun setNumCIF (numCIF : String){
        _numCIF.value = numCIF
    }

    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }
}