package johan.santos.reservesisha

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import johan.santos.reservesisha.databinding.MainActivityBinding
import johan.santos.reservesisha.databinding.NavBarAdminBinding
import johan.santos.reservesisha.databinding.NavBarBusinessBinding
import johan.santos.reservesisha.databinding.NavBarUsersBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appBarConfiguration2: AppBarConfiguration
    private lateinit var appBarConfiguration3: AppBarConfiguration
    private lateinit var binding: NavBarUsersBinding
    private lateinit var binding2: NavBarBusinessBinding
    private lateinit var binding3: NavBarAdminBinding
    private lateinit var bindingMain : MainActivityBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bindingMain = MainActivityBinding.inflate(layoutInflater)

        auth = Firebase.auth

        binding = NavBarUsersBinding.inflate(layoutInflater)

        binding2 = NavBarBusinessBinding.inflate(layoutInflater)

        binding3 = NavBarAdminBinding.inflate(layoutInflater)

    }

    fun toastView(message : CharSequence){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun disableMenus() {

//        val bottomNavigationView3 = findViewById<BottomNavigationView>(R.id.bottom_navigation_view3)
//        bottomNavigationView3.visibility = View.INVISIBLE
//
//        val bottomNavigationView2 = findViewById<BottomNavigationView>(R.id.bottom_navigation_view2)
//        bottomNavigationView2.visibility = View.INVISIBLE
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
//        bottomNavigationView.visibility = View.INVISIBLE
    }

    fun enableMenuAdmin(){
//        val bottomNavigationView3 = findViewById<BottomNavigationView>(R.id.bottom_navigation_view3)
//        bottomNavigationView3.visibility = View.VISIBLE

    }

    fun enableMenuBusiness(){
//        val bottomNavigationView2 = findViewById<BottomNavigationView>(R.id.bottom_navigation_view2)
//        bottomNavigationView2.visibility = View.VISIBLE

    }

    fun enableMenuCurrentUser(){
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
//        bottomNavigationView.visibility = View.VISIBLE
    }

    fun upBottonBarUser(){
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userMainFragment, R.id.manageBookingFragment, R.id.configUserFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun upBottonBarBusiness(){
        setContentView(binding2.root)

        val navView2: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view2)

        val navController2 = findNavController(R.id.nav_host_fragment_content_main2)

        val appBarConfiguration2 = AppBarConfiguration(
            setOf(
                R.id.businessMainFragment, R.id.manageRatesFragment, R.id.manageTablesFragment, R.id.configAdminFragment
            )
        )

        setupActionBarWithNavController(navController2, appBarConfiguration2)
        navView2.setupWithNavController(navController2)
    }

    fun upBottonBarAdmin(){
        setContentView(binding3.root)

        val navView3: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view3)

        val navController3 = findNavController(R.id.nav_host_fragment_content_main3)

        val appBarConfiguration3 = AppBarConfiguration(
            setOf(
                R.id.adminMainFragment, R.id.configAdminFragment
            )
        )

        setupActionBarWithNavController(navController3, appBarConfiguration3)
        navView3.setupWithNavController(navController3)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    fun getAuth() : FirebaseAuth {
        return auth
    }

    fun logutAndExit(){
        auth.signOut()
        finish();
        startActivity(getIntent());
    }

    fun logut(){
        auth.signOut()
    }

    private fun login (){
        //bindingMain.fragmentContainerView.
    }


}