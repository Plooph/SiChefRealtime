package net.pableras.sichef.views.logg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sing.*
import net.pableras.sichef.R
import org.jetbrains.anko.*
import java.util.regex.Pattern

/*
Esta activity se dedica a Registrar a los nuevos usuarios
 */

@Suppress("UNREACHABLE_CODE")
class SingActivity : AppCompatActivity() {

    companion object {
        const val TAG = "pableras"
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing)

        auth = FirebaseAuth.getInstance()

        btnSingInReg.setOnClickListener {
            SingUpReg()
        }

    }

    private fun SingUpReg(){
        if (etNameReg.text.toString().isEmpty()){
            etNameReg.error = "El email cipote"
            etNameReg.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etNameReg.text.toString()).matches()){
            etNameReg.error = "El email no es valido"
            etNameReg.requestFocus()
            return
        }

        if (etPasswReg.text.toString().isEmpty()){
            etPasswReg.error = "La Password melón"
            etPasswReg.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(etNameReg.text.toString(), etPasswReg.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    //val user = auth.currentUser
                    startActivity(Intent(this, LogActivity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    toast("Authentication failed try again.")
                }
            }
    }

}
