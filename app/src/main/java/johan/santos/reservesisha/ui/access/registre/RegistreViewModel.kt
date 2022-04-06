package johan.santos.reservesisha.ui.access.registre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistreViewModel : ViewModel() {

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
    private val _edat = MutableLiveData<String>()
    val edat: LiveData<String> get() = _edat
    fun setEdatUser (edatUser : String){
        _edat.value = edatUser
    }

    // sexe
    private val _sexe = MutableLiveData<Int?>(null)
    val sexe: LiveData<Int?> get() = _sexe
    fun setSexeUser (sexeUser : Int){
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

    // nom d'usuari
    private val _nomUsuari = MutableLiveData<String>("")
    val nomUsuari: LiveData<String> get() = _nomUsuari
    fun setNomUsuari (nomUsuari : String){
        _nomUsuari.value = nomUsuari
    }

    // mail
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

    // password2
    private val _password2 = MutableLiveData<String>("")
    val password2: LiveData<String> get() = _password2
    fun setPassword2 (pass : String){
        _password2.value = pass
    }


    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoRegistro = MutableLiveData(0)
    val estadoRegistro: LiveData<Int> get() = _estadoRegistro
    fun setEstadoRegistro (estadoRegistro : Int){
        _estadoRegistro.value = estadoRegistro
    }



}