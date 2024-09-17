package sv.edu.udb.blu_med

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sv.edu.udb.blu_med.datos.Medicamento
import sv.edu.udb.blu_med.datos.Compra
import java.util.*

class OrdenActivity : AppCompatActivity() {
    private lateinit var txtOrden: TextView
    private lateinit var txtTotal: TextView
    private lateinit var database: DatabaseReference
    private val medicamentosSeleccionados: ArrayList<Medicamento> = ArrayList()
    private var totalCompra: Double = 0.0

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
        totalCompra = 0.0

        for (medicamento in medicamentosSeleccionados) {
            sb.append("Medicamento: ${medicamento.nombreMedicamento}\n")
            sb.append("Precio: \$${medicamento.precio?.toString() ?: "No disponible"}\n")
            sb.append("\n")
            totalCompra += medicamento.precio ?: 0.0
        }

        txtOrden.text = sb.toString()
        txtTotal.text = "Total: \$${totalCompra}"
    }

    fun realizarCompra(v: View?) {
        if (medicamentosSeleccionados.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar al menos un medicamento", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("compras")

        // ID para la compra
        val idCompra = UUID.randomUUID().toString()

        val compra = Compra(idCompra, medicamentosSeleccionados, totalCompra)

        // Guarda la compra en Firebase
        database.child(idCompra).setValue(compra).addOnSuccessListener {
            Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Error al realizar la compra", Toast.LENGTH_SHORT).show()
        }
    }

    fun verHistorialCompras(v: View?) {
        val intent = Intent(this, HistorialComprasActivity::class.java)
        startActivity(intent)
    }
}
