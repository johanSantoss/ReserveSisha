package johan.santos.reservesisha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        // Binding
        // binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(binding.root)

        auth = Firebase.auth


    }

    fun disableMenus() {

    }

    fun enableMenuAdmin(){

    }

    fun enableMenuBusiness(){

    }

    fun enableMenuCurrentUser(){

    }

    fun getAuth() : FirebaseAuth {
        return auth
    }


}