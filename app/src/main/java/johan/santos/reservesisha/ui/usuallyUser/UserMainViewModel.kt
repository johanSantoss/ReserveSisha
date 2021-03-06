package johan.santos.reservesisha.ui.usuallyUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBusiness

class UserMainViewModel : ViewModel() {

    // llistaEmpresas
    private var _llistaBusiness = mutableListOf<DataBusiness>()
    val llistaBusiness: List<DataBusiness> get() = _llistaBusiness
    fun addValueBusiness (business : DataBusiness){
        _llistaBusiness.add(business)
    }
    fun clearListBusiness(){
        _llistaBusiness.clear()
    }



}