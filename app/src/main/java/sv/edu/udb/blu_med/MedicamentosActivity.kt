    package sv.edu.udb.blu_med

    import android.app.AlertDialog
    import android.content.DialogInterface
    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.AdapterView
    import android.widget.ListView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.android.material.floatingactionbutton.FloatingActionButton
    import com.google.firebase.database.*
    import sv.edu.udb.blu_med.datos.Medicamento

    class MedicamentosActivity : AppCompatActivity() {
        var consulta: Query = refMedicamentos.orderByChild("nombre")
        var medicamentos: MutableList<Medicamento>? = null
        var listaMedicamentos: ListView? = null
        private val medicamentosSeleccionados: MutableList<Medicamento> = mutableListOf()

        protected override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_medicamentos)
            inicializar()
        }

        private fun inicializar() {
            val fab_agregar: FloatingActionButton = findViewById(R.id.btnVerOrden)
            listaMedicamentos = findViewById(R.id.ListaMedicamentos)

            fab_agregar.setOnClickListener {
                val i = Intent(this, AddMedicamentoActivity::class.java)
                i.putExtra("accion", "a")
                i.putExtra("key", "")
                i.putExtra("nombreMedicamento", "")
                i.putExtra("indicaciones", "")
                i.putExtra("dosis", "")
                i.putExtra("efectosSecundarios", "")
                startActivity(i)
            }

            medicamentos = ArrayList()

            consulta.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    medicamentos!!.clear()
                    for (dato in dataSnapshot.children) {
                        val medicamento: Medicamento? = dato.getValue(Medicamento::class.java)
                        medicamento?.key = dato.key
                        medicamento?.let {
                            medicamentos!!.add(it)
                        }
                    }
                    val adapter = AdaptadorMedicamento(this@MedicamentosActivity, medicamentos as ArrayList<Medicamento>)
                    listaMedicamentos!!.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

            listaMedicamentos!!.setOnItemClickListener { parent, view, position, id ->
                val medicamento = medicamentos!![position]
                if (medicamentosSeleccionados.contains(medicamento)) {
                    medicamentosSeleccionados.remove(medicamento)
                    view.setBackgroundColor(android.graphics.Color.WHITE)
                } else {
                    medicamentosSeleccionados.add(medicamento)
                    view.setBackgroundColor(android.graphics.Color.LTGRAY)
                }
            }

            // Agregar botón para ver la orden
            val btnVerOrden: FloatingActionButton = findViewById(R.id.btnVerOrden)
            btnVerOrden.setOnClickListener {
                val intent = Intent(this, OrdenActivity::class.java)
                intent.putParcelableArrayListExtra("medicamentosSeleccionados", ArrayList(medicamentosSeleccionados))
                startActivity(intent)
            }
        }


        companion object {
            var database: FirebaseDatabase = FirebaseDatabase.getInstance()
            var refMedicamentos: DatabaseReference = database.getReference("medicamentos")
        }
    }