package com.example.demo_speak

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ibm.icu.text.Transliterator
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sm = SpeechManager(this).apply { initialize() }
        val txt = findViewById<TextView>(R.id.testText)
        val spBtn = findViewById<Button>(R.id.speakButton)
        val spBtn2 = findViewById<Button>(R.id.speak2Button)
        val spBtn3 = findViewById<Button>(R.id.speak3Button)


        val w = "西服"
        val t = "xī*fú"



        val t2 = Pinyin.accent(w)
        val t3 = Pinyin.noAccent(w)

        txt.text = "西服"

        spBtn.setOnClickListener {
            sm.speak(w, t)
        }
        spBtn2.setOnClickListener {
            sm.speak(w, t2)
        }
        spBtn3.setOnClickListener {
            sm.speak(w, t3)
        }
    }
}

class SpeechManager(
    private val context: Context,
    private val language: Locale = Locale.SIMPLIFIED_CHINESE,
    private val speed: Int = 81,
) {

    private val tones = listOf(
        Tone('ā', 'a', 1), Tone('á', 'a', 2), Tone('ǎ', 'a', 3), Tone('à', 'a', 4), Tone('a', 'a', 5),
        Tone('ō', 'o', 1), Tone('ó', 'o', 2), Tone('ǒ', 'o', 3), Tone('ò', 'o', 4), Tone('o', 'o', 5),
        Tone('ē', 'e', 1), Tone('é', 'e', 2), Tone('ě', 'e', 3), Tone('è', 'e', 4), Tone('e', 'e', 5),
        Tone('ī', 'i', 1), Tone('í', 'i', 2), Tone('ǐ', 'i', 3), Tone('ì', 'i', 4), Tone('i', 'i', 5),
        Tone('ū', 'u', 1), Tone('ú', 'u', 2), Tone('ǔ', 'u', 3), Tone('ù', 'u', 4), Tone('u', 'u', 5),
        Tone('ǖ', 'ü', 1), Tone('ǘ', 'ü', 2), Tone('ǚ', 'ü', 3), Tone('ǜ', 'ü', 4), Tone('ü', 'ü', 5),
        Tone('ǖ', 'ü', 1), Tone('ǘ', 'ü', 2), Tone('ǚ', 'ü', 3), Tone('ǜ', 'ü', 4), Tone('ü', 'ü', 5)
    )

    private lateinit var textToSpeech: TextToSpeech

    fun initialize() {
        textToSpeech = TextToSpeech(context, { onInit() }, googleTtsEngine)
    }

    fun speak(word: String, transcription: String) {
        val simplifiedWord = simplifyWord(word, transcription)
        val simplifiedTranscription = simplifyTranscription(transcription)
        Log.w("TEST", "$simplifiedWord - $simplifiedTranscription")
        val xml = speechXml(simplifiedWord, simplifiedTranscription, speed)
        speak(xml)
    }

    fun speak(hieroglyph: Char, transcriptionSyllable: String) {
        speak(hieroglyph.toString(), transcriptionSyllable)
    }

    private fun onInit() {
        textToSpeech.language = language
        textToSpeech.setSpeechRate(1f)
        speak("") // Пытаемся воспроизвести пустую строку. Если китайский язык не установлен, он начнет загружаться. :)
    }

    private fun speak(text: String): Int {
        return textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun speechXml(word: String, transcription: String, speed: Int): String {
        return "" +
            "<speak version=\"1.0\" xml:lang=\"zh-cn\">" +
            /**/"<prosody rate=\"$speed%\">" +
            /*  */"<phoneme alphabet=\"ipa\" ph=\"$transcription\">$word</phoneme>" +
            /**/"</prosody>" +
            "</speak>"
    }

    private fun simplifyWord(word: String, transcription: String): String {
        val needDropLastSymbol = (transcription.endsWith("nr*") || transcription.endsWith("n*r") || transcription.endsWith("nr"))
            && word.length > 1
            && word.last() == '儿'
        return if (needDropLastSymbol) word.dropLast(1) else word
    }

    private fun simplifyTranscription(value: String): String {
        var result = ""
        val syllables = value.split("…", "，", ",", " ", "*", "'", "’")
        for (syllable in syllables) {
            var changedSyllable: String? = null
            for (tone in tones) {
                if (syllable.contains(tone.symbol)) {
                    changedSyllable = syllable.replace(tone.symbol, tone.replacement) + tone.number
                    break
                }
            }
            result += changedSyllable ?: syllable
        }
        return result.replace("[<>{}]".toRegex(), "")
    }

    private data class Tone(
        val symbol: Char,
        val replacement: Char,
        val number: Int
    )

    private companion object {
        const val googleTtsEngine = "com.google.android.tts"
    }
}


object Pinyin {

    fun accent(text: String): String {
        return Transliterator.getInstance("Han-Latin").transliterate(text)
    }

    fun noAccent(text: String): String {
        return Transliterator.getInstance("Han-Latin; nfd; [:nonspacing mark:] remove; nfc").transliterate(text)
    }
}