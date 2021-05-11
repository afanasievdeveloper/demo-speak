package com.example.demo_speak

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sm = SpeechManager(this).apply { initialize() }
        val spBtn = findViewById<Button>(R.id.speakButton)

        //nü3er2
        val text = "<speak>\n" +
            "    <phoneme ph=\"nü3er2\"/>。 \n" +
            "</speak>"

        spBtn.setOnClickListener { sm.speak(text) }
    }
}

class SpeechManager(
    private val context: Context,
    private val language: Locale = Locale.SIMPLIFIED_CHINESE,
    private val speed: Float = 1f
) {

    private lateinit var textToSpeech: TextToSpeech

    fun initialize() {
        textToSpeech = TextToSpeech(context) {
            textToSpeech.language = language
            textToSpeech.setSpeechRate(speed)
            speak("") // :)
        }
    }

    fun speak(text: String): Int {
        return textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}