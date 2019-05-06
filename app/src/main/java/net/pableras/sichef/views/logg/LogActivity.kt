package net.pableras.sichef.views.logg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_log.*
import kotlinx.android.synthetic.main.activity_sing.*
import net.pableras.sichef.R
import net.pableras.sichef.models.User
import net.pableras.sichef.views.home.HomeActivity
import org.jetbrains.anko.toast

class LogActivity : AppCompatActivity() {
    companion object {
        const val TAG = "pableras"
    }
    //autentificación con firebase
    private lateinit var auth: FirebaseAuth
    //usuario logeado
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        auth = FirebaseAuth.getInstance()

        //Para añadir un nuevo usuario
        btnReg.setOnClickListener {
            startActivity(Intent(this, SingActivity::class.java))
            finish()
        }

        //Log de un usuario ya existente
        btnSingIn.setOnClickListener {
            home()
        }

    }

    /***************************** SINGIN ********************************/
    private fun home() {
        if (etName.text.toString().isEmpty()){
            etName.error = "El email cipote"
            etName.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etName.text.toString()).matches()){
            etName.error = "El email no es valido"
            etName.requestFocus()
            return
        }

        if (etPassw.text.toString().isEmpty()){
            etPassw.error = "La Password melón"
            etPassw.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(etName.text.toString(), etPassw.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                }

            }
    }
    /***************************** SINGIN ********************************/

    /***************************** AUTH INICIAL ********************************/
    public override fun onStart() {
        super.onStart()
        // Checkea si el usuario esta logeado in (non-null) y actualiza UI accordingly.
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        //si hay un usuario logeado pasa directo a la home
        if(currentUser != null){
            goHome()
        }else{
            toast("Hello World!")
        }
    }
    /***************************** AUTH INICIAL ********************************/

    /***************************** GOHOME ********************************/
    private fun goHome() {
        //inicializamos el usuario
        user = User()
        if (auth.currentUser == null){
            //añadimos los datos tecleados en las cajas de texto
            user.email = etName.text.toString()
            //user.pasw = etPassw.text.toString()
            user.uid = auth.uid.toString()
        }else{
            //Guardamos los datos del usuario actual
            user.email = auth.currentUser!!.email.toString()
            user.uid = auth.currentUser!!.uid
        }

        //la pasamos el usuario a la home
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish() //FINALIZA LA ACTICITY
    }
    /***************************** GOHOME ********************************/
}
