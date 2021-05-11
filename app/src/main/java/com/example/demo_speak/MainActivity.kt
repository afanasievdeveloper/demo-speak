package com.example.demo_speak

import android.content.Context
import android.os.Bundle
import android.os.Handler
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
        val spBtn2 = findViewById<Button>(R.id.speak2Button)
        val spBtn3 = findViewById<Button>(R.id.speak3Button)
        val spBtn4 = findViewById<Button>(R.id.speak4Button)
        val spBtn5 = findViewById<Button>(R.id.speak5Button)
        val spBtn6 = findViewById<Button>(R.id.speak6Button)
        val spBtn7 = findViewById<Button>(R.id.speak7Button)
        val spBtn8 = findViewById<Button>(R.id.speak8Button)

        //女儿
        //nǚ'ér

        //nǚ'ér
        //nü3 er2

        val still1 = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"hai2\">还</phoneme>\n" +
            "</speak> "

        val toReturn = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"huan2\">还</phoneme>\n" +
            "</speak> "

        val dau = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"nü3er2\">女儿</phoneme>\n" +
            "</speak> "

        val wh = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"nar3\">哪儿</phoneme>\n" +
            "</speak> "

        val a1 = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"ma4\">骂</phoneme>\n" +
            "</speak> "


        val b = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"ma5\">吗</phoneme>\n" +
            "</speak> "


        val c = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"ma1\">妈</phoneme>\n" +
            "</speak> "

        val d = "<speak version=\"1.0\" xml:lang=\"zh-cn\">\n" +
            " <phoneme alphabet=\"py\" ph=\"ma3\">马</phoneme>\n" +
            "</speak> "

        spBtn.setOnClickListener {
            sm.speak(still1)
        }
        spBtn2.setOnClickListener {
            sm.speak(toReturn)
        }
        spBtn3.setOnClickListener {
            sm.speak(dau)
        }
        spBtn4.setOnClickListener {
            sm.speak(wh)
        }
        spBtn5.setOnClickListener {
            sm.speak(a1)
        }
        spBtn6.setOnClickListener {
            sm.speak(b)
        }
        spBtn7.setOnClickListener {
            sm.speak(c)
        }

        spBtn8.setOnClickListener {
            sm.speak(d)
        }
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