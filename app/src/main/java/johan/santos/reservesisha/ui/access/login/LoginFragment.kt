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
import com.google.firebase.auth.FirebaseUser
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding : LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var typeUser : String

    companion object {
        fun newInstance() = LoginFragment()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )

        auth = (activity as MainActivity).getAuth()

        if (auth.currentUser != null) setInitFragment(auth.currentUser!!)

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
        if (auth != null){
            // [START sign_in_with_email]
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            // toast para informar el "success" del login
                            Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show()
                            // get user
                            auth.currentUser.apply {
                                // set directions to fragment
                                this?.let { setInitFragment(it) }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            clearText()
                        }
                    }
            } catch (e: IllegalArgumentException)  {
                Toast.makeText(activity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    private  fun clearText(){
        // neteja text "MailBox"
        binding.editTextMailAuth.editableText.clear()
        // nateja text "PassBox"
        binding.editTextPassAuth.editableText.clear()
    }

    private fun setInitFragment(user: FirebaseUser){
        // get type of user------------------------------------------------------------------------------------------------------------
        var typeUser = "Admin"
        // generar action al directions to Main Fragment
        //var action: NavDirections? = null
        var action: NavDirections? = LoginFragmentDirections.actionLoginFragmentToAdminMainFragment()
        // set action según el tipo de usuario que ha realizado "Login"
        when (typeUser) {
            "Admin"         -> action = LoginFragmentDirections.actionLoginFragmentToAdminMainFragment()
            "Business"      -> action = LoginFragmentDirections.actionLoginFragmentToBusinessMainFragment()
            "CurrentUser"   -> action = LoginFragmentDirections.actionLoginFragmentToUserMainFragment()
        }
        NavHostFragment.findNavController(this).navigate(action!!)
    }

    override fun onResume() {
        super.onResume()
        // recuperar "ActionBar" para esconderla
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()

        // enconder todos los menus
        (activity as MainActivity).disableMenus()
    }

    override fun onStop() {
        super.onStop()
        /*
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.show()
         */
        // get type of user--------------------------------------------------------------------------------------------
        typeUser = "holaa"
        // mostrar menus según el "user" que ha hecho LOGIN
        when (typeUser) {
            "admin"         -> (activity as MainActivity).enableMenuAdmin()
            "business"      -> (activity as MainActivity).enableMenuBusiness()
            "currentUser"   -> (activity as MainActivity).enableMenuCurrentUser()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }//

}