package sv.edu.udb.blu_med.datos

data class Compra(
    val idCompra: String = "",
    val medicamentos: List<Medicamento> = emptyList(),
    val total: Double = 0.0
)
