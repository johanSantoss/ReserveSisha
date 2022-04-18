package johan.santos.reservesisha.ui.usuallyUser.manageBooking.configBooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigBookingViewModel : ViewModel() {
    // nom empresa
    private val _nomEmpresa = MutableLiveData<String>("")
    val nomEmpresa: LiveData<String> get() = _nomEmpresa
    fun setNomEmpresa (empresaName : String){
        _nomEmpresa.value = empresaName
    }

    // nom reserva
    private val _nomReserva = MutableLiveData<String>("")
    val nomReserva: LiveData<String> get() = _nomReserva
    fun setNomReserva (nomReserva : String){
        _nomReserva.value = nomReserva
    }

    // numero de personas
    private val _nPersonas = MutableLiveData<String>("")
    val nPersonas: LiveData<String> get() = _nPersonas
    fun setNPersonas (nPersonas : String){
        _nPersonas.value = nPersonas
    }

    // data de reserva
    private val _dataReserva = MutableLiveData<String>("")
    val dataReserva: LiveData<String> get() = _dataReserva
    fun setDataReserva (dataReserva : String){
        _dataReserva.value = dataReserva
    }

    // hora reserva
    private val _horaReserva = MutableLiveData<String>("")
    val horaReserva: LiveData<String> get() = _horaReserva
    fun setHoraReserva (horaReserva : String){
        _horaReserva.value = horaReserva
    }

    // tipo reserva
    private val _tipoReserva = MutableLiveData<String>("")
    val tipoReserva: LiveData<String> get() = _tipoReserva
    fun setTipoReserva (tipoReserva : String){
        _tipoReserva.value = tipoReserva
    }

    //direccion
    private val _direccio = MutableLiveData<String>("")
    val direccio: LiveData<String> get() = _direccio
    fun setDireccio (direccio : String){
        _direccio.value = direccio
    }

    //id user
    private val _id_user = MutableLiveData<String>("")
    val idUser: LiveData<String> get() = _id_user
    fun setIdUser (id_user : String){
        _id_user.value = id_user
    }

    //id empresa
    private val _id_empresa = MutableLiveData<String>("")
    val idEmpresa: LiveData<String> get() = _id_empresa
    fun setIdEmpresa (id_empresa : String){
        _id_empresa.value = id_empresa
    }

    //confirmacio reseva
    private val _confirmada = MutableLiveData<Boolean>()
    val confirmada: LiveData<Boolean> get() = _confirmada
    fun setConfirmada (confirmada : Boolean){
        _confirmada.value = confirmada
    }

    // nom d'usuari
    private val _userMap = MutableLiveData<Map<String, String>>()
    val userMap: LiveData<Map<String, String>> get() = _userMap
    fun setUserMap (userMap : Map<String, String>){
        _userMap.value = userMap
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoReserva = MutableLiveData(0)
    val estadoReserva: LiveData<Int> get() = _estadoReserva
    fun setEstadoReserva (estadoReserva: Int){
        _estadoReserva.value = estadoReserva
    }
}