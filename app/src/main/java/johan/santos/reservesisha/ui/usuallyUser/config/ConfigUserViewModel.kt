package johan.santos.reservesisha.ui.usuallyUser.config

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigUserViewModel : ViewModel() {

    // nom
    private val _nom = MutableLiveData<String>("")
    val nom: LiveData<String> get() = _nom
    fun setNom (userName : String){
        _nom.value = userName
    }

    // cognom
    private val _cognom = MutableLiveData<String>("")
    val cognom: LiveData<String> get() = _cognom
    fun setCognom (userCognoms : String){
        _cognom.value = userCognoms
    }

    // edat
    private val _edat = MutableLiveData<String>("")
    val edat: LiveData<String> get() = _edat
    fun setEdatUser (edatUser : String){
        _edat.value = edatUser
    }

    // sexe
    private val _sexe = MutableLiveData<String>("")
    val sexe: LiveData<String> get() = _sexe
    fun setSexeUser (sexeUser : String){
        _sexe.value = sexeUser
    }

    // ciutat
    private val _ciutat = MutableLiveData<String>("")
    val ciutat: LiveData<String> get() = _ciutat
    fun setCiutatUser (ciutatUser : String){
        _ciutat.value = ciutatUser
    }

    // data de naixement
    private val _dataNaixement = MutableLiveData<String>("")
    val dataNaixement: LiveData<String> get() = _dataNaixement
    fun setDataNaixement (dataNaixement : String){
        _dataNaixement.value = dataNaixement
    }

    // Document d'identitat
    private val _identificardorPersonal = MutableLiveData<String>("")
    val identificadorPersonal: LiveData<String> get() = _identificardorPersonal
    fun setIdentificadorPersonal (identificadorPersonal : String){
        _identificardorPersonal.value = identificadorPersonal
    }

    // nom d'usuari
    private val _nomUsuari = MutableLiveData<String>("")
    val nomUsuari: LiveData<String> get() = _nomUsuari
    fun setNomUsuari (nomUsuari : String){
        _nomUsuari.value = nomUsuari
    }
    // nom d'usuari
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