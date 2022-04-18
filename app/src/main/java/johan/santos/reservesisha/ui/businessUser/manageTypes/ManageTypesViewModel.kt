package johan.santos.reservesisha.ui.businessUser.manageTypes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataType
import johan.santos.reservesisha.ui.access.models.DataRates

class ManageTypesViewModel : ViewModel() {

    // llista de Rate's
    private var _llistaTypes = mutableListOf<DataType>()
    val llistaTypes : List<DataType> get() = _llistaTypes
    fun setLlistaTypes (novaLlista : MutableList<DataType>){
        _llistaTypes = novaLlista
    }
    fun addValueType (type : DataType){
        _llistaTypes.add(type)
    }
    fun delValueType (type : DataType){
        _llistaTypes.remove(type)
    }
    fun cleanListTypes(){
        _llistaTypes.clear()
    }


    // hay Types
    private var _hayDatosList = MutableLiveData(false)
    val hayDatosList : LiveData<Boolean> get() = _hayDatosList
    fun setHayDatosList (hayDatos : Boolean){
        _hayDatosList.value = hayDatos
    }

}

