package johan.santos.reservesisha.ui.access.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

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

}