package johan.santos.reservesisha.ui.access.login


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.LoginFragmentBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
        private const val TAG = "LoginFragment"
    }

    private lateinit var binding : LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var database : DatabaseReference
    private var typeUser : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = LoginFragmentBinding.inflate(layoutInflater)

        if ((activity as MainActivity).getAuth().currentUser != null) readData()

        // Get the viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnAuthetification.setOnClickListener {
            // guardar mail en viewModel
            viewModel.setEmail(binding.editTextMailAuth.text.toString().trim())
            // guardar pass en viewModel
            viewModel.setPassword(binding.editTextPassAuth.text.toString().trim())
            // realizar SING con mial y pass
            signIn( viewModel.email.value.toString(), viewModel.password.value.toString())
        }

        binding.textBtnRegister.setOnClickListener {
            // generar action al directions to Registre Fragment
            val action = LoginFragmentDirections.actionLoginFragmentToRegistreFragment()
            // ejecutar navigate to registre con el "action" generado en la parte superior
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        try {
            (activity as MainActivity).getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        readData()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        (activity as MainActivity).toastView("Authentication failed.")
                    }
                }
        } catch (e: IllegalArgumentException)  {
            (activity as MainActivity).toastView("Authentication failed.")
        }
    }

    private fun readData() {
        val auth = (activity as MainActivity).getAuth()
        database = FirebaseDatabase.getInstance().getReference("AllUsers/${auth.currentUser!!.uid}")
        database.child("userDates").get().addOnSuccessListener {

            if (it.exists()){
                val firstname = it.child("rol").value
                typeUser = firstname.toString()
                Toast.makeText(activity,firstname.toString(),Toast.LENGTH_SHORT).show()
                setInitFragment()
            } else {
                var action: NavDirections = LoginFragmentDirections.actionLoginFragmentToAdminMainFragment()
                NavHostFragment.findNavController(this).navigate(action)
                setInitToolBar()
                (activity as MainActivity).upBottonBarAdmin()
            }

        }.addOnFailureListener{
            Toast.makeText(activity,"FailedReadData",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setInitFragment(){
        var action: NavDirections? = null
        // set action según el tipo de usuario que ha realizado "Login"
        when (typeUser) {
            "Admin"         -> action = LoginFragmentDirections.actionLoginFragmentToAdminMainFragment()
            "Business"      -> action = LoginFragmentDirections.actionLoginFragmentToBusinessMainFragment()
            "CurrentUser"   -> action = LoginFragmentDirections.actionLoginFragmentToUserMainFragment()
        }
        if (action != null) {
            NavHostFragment.findNavController(this).navigate(action)
            setInitBar()
        }
    }

    private fun setInitBar(){
        setInitToolBar()
        if ((activity as MainActivity).getAuth().currentUser != null){
            // mostrar menus según el "user" que ha hecho LOGIN
            when (typeUser) {
                "Admin"         -> (activity as MainActivity).upBottonBarAdmin()
                "Business"      -> (activity as MainActivity).upBottonBarBusiness()
                "CurrentUser"   -> (activity as MainActivity).upBottonBarUser()
            }
        }
    }

    private fun setInitToolBar(){
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.show()
    }

    override fun onResume() {
        super.onResume()
        // recuperar "ActionBar" para esconderla
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()
        // enconder todos los menus
        (activity as MainActivity).disableMenus()
    }

}