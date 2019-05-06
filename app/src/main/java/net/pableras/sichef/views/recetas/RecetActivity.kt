package net.pableras.sichef.views.recetas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_recet.*
import net.pableras.sichef.R
import net.pableras.sichef.models.Receta
import net.pableras.sichef.models.User
import net.pableras.sichef.views.home.HomeActivity
import org.jetbrains.anko.toast

class RecetActivity : AppCompatActivity() {

    private lateinit var receta: Receta
    private lateinit var ref: DatabaseReference
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recet)

        user = intent.getSerializableExtra("user") as User
        receta = Receta(uid = user.uid)
        btnSave.setOnClickListener {
            newReceta()
        }
    }

    private fun newReceta() {

        ref = FirebaseDatabase.getInstance().getReference("recetas")
        val recetaId = ref.push().key.toString()
        receta.id = recetaId

        //añadir el id del usuario
        receta.uid = user.uid

        if (etTitle.text.isEmpty()) {
            etTitle.error
            toast("todos los campos son obligatorios")
        }else{
            receta.title = etTitle.text.toString()

            if (etComensales.text.isEmpty()) {
                etComensales.error
                toast("todos los campos son obligatorios")
            }else{
                receta.comensales = etComensales.text.toString()

                if (etIngredientes.text.isEmpty()) {
                    etIngredientes.error
                    toast("todos los campos son obligatorios")
                }else{
                    receta.ingredientes = etIngredientes.text.toString()

                    if (etPreparacion.text.isEmpty()) {
                        etPreparacion.error
                        toast("todos los campos son obligatorios")
                    }else{
                        receta.preparacion = etPreparacion.text.toString()

                        saveReceta(receta)
                    }
                }
            }
        }
    }

    private fun saveReceta(receta: Receta) {

        ref.child(receta.id).setValue(receta).addOnCompleteListener{
            toast("Receta guardada coorrectamente")
        }

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("receta", receta)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }
}
