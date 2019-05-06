package net.pableras.sichef.views.recetas

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.pableras.sichef.R
import net.pableras.sichef.databinding.ActivityDetailRecetaBinding

import net.pableras.sichef.models.Receta
import net.pableras.sichef.models.RecetaAux

class DetailRecetaActivity : AppCompatActivity() {

    private lateinit var recetaAux: RecetaAux

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailRecetaBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_receta)
        recetaAux = intent.getSerializableExtra("detail") as RecetaAux


        binding.receta = recetaAux

    }
}
