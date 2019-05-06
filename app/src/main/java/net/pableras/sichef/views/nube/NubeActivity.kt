package net.pableras.sichef.views.nube

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_nube.*
import kotlinx.android.synthetic.main.content_home.*
import net.pableras.sichef.R
import net.pableras.sichef.adapter.CustomAdapterNube
import net.pableras.sichef.adapter.CustomAdapterRecetas
import net.pableras.sichef.models.Receta
import net.pableras.sichef.models.RecetaAux
import net.pableras.sichef.models.User
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class NubeActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var recetasAux: ArrayList<RecetaAux>
    private lateinit var ref: DatabaseReference
    private lateinit var adapter: CustomAdapterNube

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nube)

        user = intent.getSerializableExtra("local") as User

        readRecetas(user)
        showRecet()
    }

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
                    recetasAux = ArrayList()
                    for (recet in p0.children){
                        val receta = recet.getValue(Receta::class.java)
                        if (receta!!.uid != user.uid){
                            //if (receta != null) {
                            val recetaAux = recet.getValue(RecetaAux::class.java)
                            recetaAux!!.user = user
                            recetasAux.add(recetaAux)
                        }
                    }
                    pintarAdapter(recetasAux)
                }else {
                    longToast("NO HAY RECETAS GUARDADAS")
                }
            }
        })
    }
    /***************************** LEE LAS RECETA ********************************/

    /***************************** PINTA LAS RECETA ********************************/
    private fun showRecet() {
        adapter = CustomAdapterNube(this, R.layout.rownuberecetas)
        rvRecetasNube.layoutManager = LinearLayoutManager(this)
        rvRecetasNube.adapter = adapter
    }

    fun pintarAdapter(recetasAux: ArrayList<RecetaAux>) {
        //llama a la funcion que actualiza el RV
        adapter.setRecetas(recetasAux)
    }
    /***************************** PINTA LAS RECETA ********************************/

    /*****************************  ONCLICKRECETANUBE ********************************/
    fun onClcikRecetaNube(view: View){
        val recetaAux = view.tag as RecetaAux
        intent = Intent(this, DetailRecetaNubeActivity::class.java)
        intent.putExtra("recetnube", recetaAux)
        startActivity(intent)
    }
    /*****************************  ONCLICKRECETANUBE ********************************/

}
