package sv.edu.udb.blu_med

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    //Creamos la referencia del objeto firebaseauth
    private lateinit var auth: FirebaseAuth
    //Referencia a componentes de nuestro layout
    private lateinit var buttonRegister: Button
    private lateinit var  textViewLogin:TextView

    //Escuchador de FirebaseAuth
    private lateinit var authStateListener: AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Inicializamos el objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val emailField = findViewById<EditText>(R.id.txtEmail)
        val passwordField = findViewById<EditText>(R.id.txtPass)

        buttonRegister = findViewById<Button>(R.id.btnRegister)
        buttonRegister.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (validateEmail(email) && validatePassword(password)) {
                register(email, password)
            }
        }

        textViewLogin = findViewById<TextView>(R.id.textViewLogin)
        textViewLogin.setOnClickListener {
            goToLogin()
        }

        checkUser()
    }

    private fun register(email: String, password: String){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(this, "El campo de correo electrónico está vacío", Toast.LENGTH_SHORT).show()
            return false
        }

        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        )

        if (!emailPattern.matcher(email).matches()) {
            Toast.makeText(this, "Formato de correo no válido", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Validación de contraseña
    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            Toast.makeText(this, "El campo de contraseña está vacío", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun checkUser(){
        //Verificacion de usuario
        authStateListener = FirebaseAuth.AuthStateListener { auth ->

            if (auth.currentUser != null){
                //Cambiando la vista
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}