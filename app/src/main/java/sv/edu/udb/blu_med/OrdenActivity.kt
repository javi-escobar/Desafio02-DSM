package sv.edu.udb.blu_med

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sv.edu.udb.blu_med.datos.Medicamento

class OrdenActivity : AppCompatActivity() {
    private lateinit var txtOrden: TextView
    private lateinit var txtTotal: TextView
    private val medicamentosSeleccionados: ArrayList<Medicamento> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden)

        txtOrden = findViewById(R.id.txtOrden)
        txtTotal = findViewById(R.id.txtTotal)

        val datos: Bundle? = intent.extras
        datos?.let {
            medicamentosSeleccionados.addAll(it.getParcelableArrayList("medicamentosSeleccionados") ?: arrayListOf())
        }

        mostrarOrden()
    }

    private fun mostrarOrden() {
        val sb = StringBuilder()
        var total = 0.0

        for (medicamento in medicamentosSeleccionados) {
            sb.append("Medicamento: ${medicamento.nombreMedicamento}\n")
            sb.append("Precio: \$${medicamento.precio?.toString() ?: "No disponible"}\n")
            sb.append("\n")
            total += medicamento.precio ?: 0.0
        }

        txtOrden.text = sb.toString()
        txtTotal.text = "Total: \$${total}"
    }
}
