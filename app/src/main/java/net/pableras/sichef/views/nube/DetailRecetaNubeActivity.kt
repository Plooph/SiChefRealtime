package net.pableras.sichef.views.nube

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.pableras.sichef.R
import net.pableras.sichef.databinding.ActivityDetailRecetaNubeBinding
import net.pableras.sichef.models.RecetaAux

class DetailRecetaNubeActivity : AppCompatActivity() {

    private lateinit var recetaAux: RecetaAux

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail_receta_nube)
        val binding: ActivityDetailRecetaNubeBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_receta_nube)

        recetaAux = intent.getSerializableExtra("recetnube") as RecetaAux

        binding.receta = recetaAux
    }
}
