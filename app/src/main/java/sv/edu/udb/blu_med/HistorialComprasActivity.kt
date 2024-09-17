package sv.edu.udb.blu_med

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import sv.edu.udb.blu_med.datos.Compra
import sv.edu.udb.blu_med.datos.Medicamento
import sv.edu.udb.blu_med.adapters.AdaptadorCompra

class HistorialComprasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var compraAdapter: AdaptadorCompra
    private lateinit var database: DatabaseReference
    private val listaCompras: ArrayList<Compra> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_compras)

        recyclerView = findViewById(R.id.recyclerHistorialCompras)
        recyclerView.layoutManager = LinearLayoutManager(this)

        compraAdapter = AdaptadorCompra(listaCompras)
        recyclerView.adapter = compraAdapter

        database = FirebaseDatabase.getInstance().getReference("compras")

        cargarHistorialCompras()
    }

    private fun cargarHistorialCompras() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaCompras.clear()
                for (compraSnapshot in snapshot.children) {
                    val idCompra = compraSnapshot.child("idCompra").getValue(String::class.java) ?: continue
                    val total = compraSnapshot.child("total").getValue(Double::class.java) ?: 0.0

                    val medicamentos = mutableListOf<Medicamento>()
                    val medicamentosSnapshot = compraSnapshot.child("medicamentos")
                    for (medicamentoSnapshot in medicamentosSnapshot.children) {
                        val nombreMedicamento = medicamentoSnapshot.child("nombreMedicamento").getValue(String::class.java)
                        val indicaciones = medicamentoSnapshot.child("indicaciones").getValue(String::class.java)
                        val dosis = medicamentoSnapshot.child("dosis").getValue(String::class.java)
                        val efectosSecundarios = medicamentoSnapshot.child("efectosSecundarios").getValue(String::class.java)
                        val precio = medicamentoSnapshot.child("precio").getValue(Double::class.java)

                        val medicamento = Medicamento(nombreMedicamento, indicaciones, dosis, efectosSecundarios, precio)
                        medicamentos.add(medicamento)
                    }

                    val compra = Compra(idCompra, medicamentos, total)
                    listaCompras.add(compra)
                }
                compraAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HistorialComprasActivity, "Error al cargar el historial", Toast.LENGTH_SHORT).show()
            }
        })
    }



}
