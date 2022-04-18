package johan.santos.reservesisha.ui.usuallyUser.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataBusiness

class BusinessViewModel : ViewModel() {
    // Empresas

    private val _business = MutableLiveData<DataBusiness>()
    val business: LiveData<DataBusiness> get() = _business
    fun setBusiness (business : DataBusiness){
        _business.value = business
    }
}