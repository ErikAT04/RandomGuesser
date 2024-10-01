package androidS.erikat.randnumgues

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Declaración de variables:
        var numMax:Int = 10
        var numInt:Int = 5
        var totalIntentos:Int = 0
        var numOpt:Int = 0
        var numOculto:Int = 0
        //Declaración de objetos de la actividad:
        val intText:EditText = findViewById(R.id.editTextInt)
        val maxText:EditText = findViewById(R.id.editTextMax)
        val optText:EditText = findViewById(R.id.editTextOpt)
        val confBtt:Button = findViewById(R.id.buttonConf)
        val okBtt:Button = findViewById(R.id.buttonOk)
        val playBtt:Button = findViewById(R.id.buttonPlay)
        val guessBtt:Button = findViewById(R.id.buttonGuess)
        val guessesTView:TextView = findViewById(R.id.textViewGuesses)

        confBtt.setOnClickListener {
            playBtt.isEnabled = false
            intText.isEnabled = true
            maxText.isEnabled = true
            confBtt.isEnabled = false
            okBtt.isEnabled = true
        }

        okBtt.setOnClickListener {
            try{
                numMax = Integer.parseInt(maxText.text.toString())
                numInt = Integer.parseInt(intText.text.toString())
                if (numMax<=0 || numInt<=0){
                    shortToastCreator("Los valores deberían ser positivos y mayores a 0")
                } else if (numMax <= numInt){
                    shortToastCreator("Para dar más juego, haz que el numero de intentos sea menor que el valor máximo posible")
                } else {
                    playBtt.isEnabled = true
                    intText.isEnabled = false
                    intText.text = null
                    maxText.isEnabled = false
                    maxText.text = null
                    confBtt.isEnabled = true
                    okBtt.isEnabled = false
                    shortToastCreator("Configuración aplicada")
                }
            }catch (e:NumberFormatException){
                shortToastCreator("Los valores de los textos deben ser enteros")
            }
        }

        playBtt.setOnClickListener {
            totalIntentos = numInt
            numOculto = Random.nextInt(numMax)
            optText.isEnabled = true
            optText.isVisible = true
            guessBtt.isEnabled = true
            guessBtt.isVisible = true
            playBtt.isEnabled = false
            confBtt.isEnabled = false
            guessesTView.isVisible = true
            guessesTView.text = ("Te quedan $totalIntentos intentos")
        }

        guessBtt.setOnClickListener {
            try {
                numOpt = Integer.parseInt(optText.text.toString())
                totalIntentos -= 1
                if (numOpt == numOculto || totalIntentos == 0){
                    optText.isEnabled = false
                    optText.isVisible = false
                    guessBtt.isEnabled = false
                    guessBtt.isVisible = false
                    playBtt.isEnabled = true
                    confBtt.isEnabled = true
                    guessesTView.isVisible = false
                    shortToastCreator(if (numOpt == numOculto) "Adivinado en ${numInt-totalIntentos} intentos. ¡Enhorabuena!" else "¡Perdiste! El número era $numOculto")
                } else {
                    shortToastCreator("Pista: El número que buscas es ${if (numOpt > numOculto) "menor" else "mayor"}.")
                }
                guessesTView.text = ("Te quedan $totalIntentos intentos")
            }catch (e:NumberFormatException){
                shortToastCreator("Escribe un valor numérico")
            }
        }
    }

    fun shortToastCreator(message:String){ //Creo una función que cree un Toast para ahorrar código muy semejante
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}