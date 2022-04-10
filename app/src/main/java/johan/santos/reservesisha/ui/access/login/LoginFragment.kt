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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import johan.santos.reservesisha.MainActivity
import johan.santos.reservesisha.R
import johan.santos.reservesisha.databinding.LoginFragmentBinding
import johan.santos.reservesisha.ui.access.models.DataUserType
import johan.santos.reservesisha.ui.access.registre.RegistreFragment

class LoginFragment : Fragment() {

    private lateinit var binding : LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var database2: FirebaseDatabase
    private lateinit var database: DatabaseReference
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

        //auth = (activity as MainActivity).getAuth()
        // Conection to DataBase
        //database2 = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")



        if ((activity as MainActivity).getAuth().currentUser != null) setInitFragment()

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
        if ((activity as MainActivity).getAuth() != null){
            // [START sign_in_with_email]
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            // toast para informar el "success" del login
                            Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show()
                            // get user to apply directions fragment
                            setInitFragment()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()

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

    private fun setInitFragment(){
        // get type of user------------------------------------------------------------------------------------------------------------
        var typeUser : String = getTypeUser()
        //var typeUser = "Admin"
        /*
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
        */
        (activity as MainActivity).toastView(typeUser)
    }
    // -----------------------------------------------------------------------------------------------------------------------
    class DatosUsuari (
        val type: String = ""
    )
    private fun getTypeUser() : String {

        var type = "fallo-1"
        // Se genera el acceso a la DDBB al nodo de cada usuari
        //val myRefDadesUser = database.getReference("AllUsers/LlistatUsersType/${auth.currentUser?.uid}")
        //database.child("AllUsers").child("LlistatUsersType").child(user.uid).child("type").addListenerForSingleValueEvent()
        database2 = FirebaseDatabase.getInstance("https://reservesisha96-default-rtdb.europe-west1.firebasedatabase.app/")

        val myRefDadesUser = database2.getReference("AllUsers/LlistatUsersType/${(activity as MainActivity).getAuth().currentUser?.uid}")
        myRefDadesUser.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<DatosUsuari>()
                type = value?.type?:"jansfkjsnfkjndskj"
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        myRefDadesUser.child("type").get()

        return type
    }
    private fun getTypeUser2() : String {

        var type = "fallo-1"
        // Se genera el acceso a la DDBB al nodo de cada usuari
        //val myRefDadesUser = database.getReference("AllUsers/LlistatUsersType/${auth.currentUser?.uid}")
        //database.child("AllUsers").child("LlistatUsersType").child(user.uid).child("type").addListenerForSingleValueEvent()
        database = FirebaseDatabase.getInstance().getReference("AllUsers/LlistatUsersType")
        val prueba1 = database.child(auth.currentUser!!.uid).get()

        database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            type = "fallo-5"
            if (it.exists()){
                val value = it.child("type").value
                type = "fallo-2"
                type = value.toString()
            } else {
                type = "fallo-3"
            }

        }.addOnFailureListener{
            type = "fallo-4"

        }

        return type
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
        if ((activity as MainActivity).getAuth().currentUser != null){
            typeUser = getTypeUser()
            // mostrar menus según el "user" que ha hecho LOGIN
            when (typeUser) {
                "Admin"         -> (activity as MainActivity).enableMenuAdmin()
                "Business"      -> (activity as MainActivity).enableMenuBusiness()
                "CurrentUser"   -> (activity as MainActivity).enableMenuCurrentUser()
            }
        }
    }

}