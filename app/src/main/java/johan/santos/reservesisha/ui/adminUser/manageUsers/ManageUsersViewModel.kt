package johan.santos.reservesisha.ui.adminUser.manageUsers

import androidx.lifecycle.ViewModel
import johan.santos.reservesisha.ui.access.models.DataUsers

class ManageUsersViewModel : ViewModel() {
    // llistaUsuaris
    private var _llistaUsers = mutableListOf<DataUsers>()
    val llistaUsers: List<DataUsers> get() = _llistaUsers
    fun setLlistaUsers (novaLlista : MutableList<DataUsers>){
        _llistaUsers = novaLlista
    }
    fun addValueUsers (users : DataUsers){
        _llistaUsers.add(users)
    }
    fun delValueUser (users : DataUsers){
        _llistaUsers.remove(users)
    }
    fun clearLlistaUSers(){
        _llistaUsers.clear()
    }
}