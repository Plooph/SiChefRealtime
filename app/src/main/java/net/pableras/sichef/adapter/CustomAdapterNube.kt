package net.pableras.sichef.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rownuberecetas.view.*
import kotlinx.android.synthetic.main.rowrecetas.view.*
import kotlinx.android.synthetic.main.rowrecetas.view.tvComensales
import net.pableras.sichef.models.RecetaAux

class CustomAdapterNube (val context: Context,
                         val layout: Int) : RecyclerView.Adapter<CustomAdapterNube.ViewHolder>() {

    private var dataList: List<RecetaAux> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setRecetas(recetas: List<RecetaAux>) {
        this.dataList = recetas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: RecetaAux){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvTitleNube.text = dataItem.title
            itemView.tvComensalesNube.text = dataItem.comensales
            itemView.tvUserNube.text = dataItem.user.email
            itemView.tag = dataItem

        }

    }
}