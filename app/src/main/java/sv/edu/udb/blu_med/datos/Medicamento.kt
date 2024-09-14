package sv.edu.udb.blu_med.datos

import android.os.Parcel
import android.os.Parcelable

class Medicamento() : Parcelable {
    var nombreMedicamento: String? = null
    var indicaciones: String? = null
    var dosis: String? = null
    var efectosSecundarios: String? = null
    var precio: Double? = null
    var key: String? = null
    var med: MutableMap<String, Boolean> = HashMap()

    constructor(
        nombreMedicamento: String?,
        indicaciones: String?,
        dosis: String?,
        efectosSecundarios: String?,
        precio: Double?
    ) : this() {
        this.nombreMedicamento = nombreMedicamento
        this.indicaciones = indicaciones
        this.dosis = dosis
        this.efectosSecundarios = efectosSecundarios
        this.precio = precio
    }

    private constructor(parcel: Parcel) : this() {
        nombreMedicamento = parcel.readString()
        indicaciones = parcel.readString()
        dosis = parcel.readString()
        efectosSecundarios = parcel.readString()
        precio = parcel.readValue(Double::class.java.classLoader) as? Double
        key = parcel.readString()
        med = mutableMapOf<String, Boolean>().apply {
            parcel.readMap(this, Boolean::class.java.classLoader)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreMedicamento)
        parcel.writeString(indicaciones)
        parcel.writeString(dosis)
        parcel.writeString(efectosSecundarios)
        parcel.writeValue(precio)
        parcel.writeString(key)
        parcel.writeMap(med)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicamento> {
        override fun createFromParcel(parcel: Parcel): Medicamento {
            return Medicamento(parcel)
        }

        override fun newArray(size: Int): Array<Medicamento?> {
            return arrayOfNulls(size)
        }
    }
}
