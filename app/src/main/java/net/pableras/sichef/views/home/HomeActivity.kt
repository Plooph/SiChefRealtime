package net.pableras.sichef.views.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import net.pableras.sichef.R
import net.pableras.sichef.adapter.CustomAdapterRecetas
import net.pableras.sichef.models.Receta
import net.pableras.sichef.models.User
import net.pableras.sichef.views.logg.LogActivity
import net.pableras.sichef.views.recetas.DetailRecetaActivity
import net.pableras.sichef.views.recetas.RecetActivity
import org.jetbrains.anko.longToast

/*
Hay que guardar el usuario en un shareprefent para mantenerlo en local y disponer de los datos
Cajon deslizable para añador el resto de funciones.
Modificación estetica.
 */
@Suppress("CAST_NEVER_SUCCEEDS")
class HomeActivity : AppCompatActivity() {

    private lateinit var user: User
    private var receta: Receta? = null
    //variable firebase
    private lateinit var ref: DatabaseReference
    //variables para el rv y el adapter
    private lateinit var recetas: ArrayList<Receta>
    private lateinit var adapter: CustomAdapterRecetas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Recoge el usuario desde el Login y recetasActivity
        user = intent.getSerializableExtra("user") as User
        //longToast("Usuario ${user.email}")
        if (receta != null){
            receta = intent.getSerializableExtra("receta") as Receta
        }

        //inicializa el arrayList de recetas
        recetas = ArrayList()

        newRecet.setOnClickListener {
            createReceta(user)
        }

        //pinta las recetas
        readRecetas(user)
        showRecet()
    }
    /***************************** NUEVA RECETA ********************************/
    private fun createReceta(user: User) {
        val intent = Intent(this, RecetActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish() //FINALIZA LA ACTIVITY
    }
    /***************************** NUEVA RECETA ********************************/

    /***************************** LEE LAS RECETA ********************************/
    private fun readRecetas(user: User) {
        //Optine la referencia del firebase -> recetas (nombre donde se almacenan los datos)
        ref = FirebaseDatabase.getInstance().getReference("recetas")
        //Se pone a la escucha de eventos
        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}
            //cuando cambia los datos se actualiza
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    //Para evitar que pinte dos veces el array
                    recetas = ArrayList()
                    for (recet in p0.children){
                        val receta = recet.getValue(Receta::class.java)
                        //if (receta!!.user.uid == user.uid){
                        if (receta != null) {
                            recetas.add(receta)
                        }
                    }
                    //pintarAdapter(recetas)
                    //llama a la funcion que actualiza el RV
                    adapter.setRecetas(recetas)
                }else {
                    longToast("NO HAY RECETAS GUARDADAS")
                }
            }
        })
    }
    /***************************** LEE LAS RECETA ********************************/

    /***************************** PINTA LAS RECETA ********************************/
    private fun showRecet() {
        adapter = CustomAdapterRecetas(this, R.layout.rowrecetas)
        rvRecetLocal.layoutManager = LinearLayoutManager(this)
        rvRecetLocal.adapter = adapter
    }

    /***************************** PINTA LAS RECETA ********************************/

    /***************************** MENU ********************************/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.homemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LogActivity::class.java))
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /***************************** MENU ********************************/

    /***************************** ONCLICKRECETA ********************************/
    fun onClcikReceta(view: View){
        val receta = view.tag as Receta
        val intent = Intent(this, DetailRecetaActivity::class.java)
        intent.putExtra("detail", receta)
        startActivity(intent)
    }
    /***************************** ONCLICKRECETA ********************************/
}
