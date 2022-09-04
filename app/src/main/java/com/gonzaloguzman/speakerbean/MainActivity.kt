package com.gonzaloguzman.speakerbean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.gonzaloguzman.speakerbean.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityMainBinding

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)
        binding.btnPlay.setOnClickListener {
            speak()
        }
    }

    private fun speak() {
        var message = binding.etMessage.text.toString()
        if(message.isEmpty()) message = getString(R.string.tts_funny_empty_message)

            tts!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Snackbar.make(binding.root, getString(R.string.tts_ready), Snackbar.LENGTH_SHORT).show()
            tts!!.language = Locale("ES")
        } else {
            Snackbar.make(binding.root, getString(R.string.tts_no_ready), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}