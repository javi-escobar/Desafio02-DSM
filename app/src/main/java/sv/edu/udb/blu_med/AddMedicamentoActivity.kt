package sv.edu.udb.blu_med

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sv.edu.udb.blu_med.datos.Medicamento

class AddMedicamentoActivity : AppCompatActivity() {
    private var edtNombreMedicamento: EditText? = null
    private var edtIndicaciones: EditText? = null
    private var edtDosis: EditText? = null
    private var edtEfectosSecundarios: EditText? = null
    private var edtPrecio: EditText? = null
    private var key = ""
    private var nombreMedicamento = ""
    private var indicaciones = ""
    private var dosis = ""
    private var efectosSecundarios = ""
    private var precio: Double? = null
    private var accion = ""
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicamento)
        inicializar()
    }

    private fun inicializar() {
        edtNombreMedicamento = findViewById<EditText>(R.id.edtNombreMedicamento)
        edtIndicaciones = findViewById<EditText>(R.id.edtIndicaciones)
        edtDosis = findViewById<EditText>(R.id.edtDosis)
        edtEfectosSecundarios = findViewById<EditText>(R.id.edtEfectosSecundarios)
        edtPrecio = findViewById<EditText>(R.id.edtPrecio)  // Inicializado para el precio

        val datos: Bundle? = intent.extras
        datos?.let {
            key = it.getString("key").toString()
            edtNombreMedicamento?.setText(it.getString("nombreMedicamento").toString())
            edtIndicaciones?.setText(it.getString("indicaciones").toString())
            edtDosis?.setText(it.getString("dosis").toString())
            edtEfectosSecundarios?.setText(it.getString("efectosSecundarios").toString())
            edtPrecio?.setText(it.getString("precio")?.toDoubleOrNull()?.toString())
            accion = it.getString("accion").toString()
        }
    }

    fun guardar(v: View?) {
        val nombreMedicamento: String = edtNombreMedicamento?.text.toString()
        val indicaciones: String = edtIndicaciones?.text.toString()
        val dosis: String = edtDosis?.text.toString()
        val efectosSecundarios: String = edtEfectosSecundarios?.text.toString()
        val precioText: String = edtPrecio?.text.toString()
        precio = precioText.toDoubleOrNull()

        database = FirebaseDatabase.getInstance().getReference("medicamentos")

        // Crea el objeto Medicamento
        val medicamento = Medicamento(nombreMedicamento, indicaciones, dosis, efectosSecundarios, precio)

        if (accion == "a") { // Agregar
            database.child(nombreMedicamento).setValue(medicamento).addOnSuccessListener {
                Toast.makeText(this, "Se guardó con éxito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}
