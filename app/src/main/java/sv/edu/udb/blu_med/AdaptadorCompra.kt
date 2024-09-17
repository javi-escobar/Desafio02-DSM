package sv.edu.udb.blu_med.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.blu_med.R
import sv.edu.udb.blu_med.datos.Compra

class AdaptadorCompra(private val listaCompras: ArrayList<Compra>) :
    RecyclerView.Adapter<AdaptadorCompra.CompraViewHolder>() {

    class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMedicamentos: TextView = itemView.findViewById(R.id.txtMedicamentos)
        val txtTotalCompra: TextView = itemView.findViewById(R.id.txtTotalCompra)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.compra_layout, parent, false)
        return CompraViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = listaCompras[position]
        val medicamentos = compra.medicamentos.joinToString(", ") { it.nombreMedicamento ?: "Desconocido" }
        holder.txtMedicamentos.text = "Medicamentos: $medicamentos"
        holder.txtTotalCompra.text = "Total: \$${compra.total}"
    }

    override fun getItemCount(): Int {
        return listaCompras.size
    }
}
