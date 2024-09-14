package sv.edu.udb.blu_med

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import sv.edu.udb.blu_med.datos.Medicamento

class AdaptadorMedicamento(private val context: Context, private val medicamentos: ArrayList<Medicamento>) : BaseAdapter() {

    override fun getCount(): Int {
        return medicamentos.size
    }

    override fun getItem(position: Int): Medicamento {
        return medicamentos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.medicamento_layout, parent, false)

        val tvNombreMedicamento = view.findViewById<TextView>(R.id.tvNombreMedicamento)
        val tvIndicaciones = view.findViewById<TextView>(R.id.tvIndicaciones)
        val tvDosis = view.findViewById<TextView>(R.id.tvDosis)
        val tvEfectosSecundarios = view.findViewById<TextView>(R.id.tvEfectosSecundarios)
        val tvPrecio = view.findViewById<TextView>(R.id.tvPrecio)

        val medicamento = getItem(position)

        tvNombreMedicamento.text = medicamento.nombreMedicamento
        tvIndicaciones.text = medicamento.indicaciones
        tvDosis.text = medicamento.dosis
        tvEfectosSecundarios.text = medicamento.efectosSecundarios
        tvPrecio.text = medicamento.precio?.toString() ?: "No Asignado"

        return view
    }
}
