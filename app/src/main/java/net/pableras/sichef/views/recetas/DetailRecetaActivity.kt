package net.pableras.sichef.views.recetas

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.pableras.sichef.R
import net.pableras.sichef.models.Receta
import net.pableras.sichef.databinding.ActivityDetailRecetaBinding

class DetailRecetaActivity : AppCompatActivity() {

    private lateinit var receta: Receta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailRecetaBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_receta)
        receta = intent.getSerializableExtra("detail") as Receta

        binding.receta = receta

    }
}
