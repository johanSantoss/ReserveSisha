package johan.santos.reservesisha.ui.usuallyUser.manageBooking.configBooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBooking
import johan.santos.reservesisha.ui.access.models.DataRates

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

    private var _llistaReservas: MutableList<DataRates> = mutableListOf()
    val llistaReservas : List<DataRates> get() = _llistaReservas
    fun addValueReservas (reserva : DataRates){
        _llistaReservas.add(reserva)
    }

    // contador salidaRegistro/nuevoRegistro
    // 1 - Registro en marcha
    private val _estadoReserva = MutableLiveData(0)
    val estadoReserva: LiveData<Int> get() = _estadoReserva
    fun setEstadoReserva (estadoReserva: Int){
        _estadoReserva.value = estadoReserva
    }
}