package com.ganesh.weatherapp.view

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import com.ganesh.weatherapp.R
import java.util.*

object VoiceInvoker {

    private lateinit var intent: Intent

    fun initIntent(context: Context) {

        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.input))


    }


    fun getIntent(): Intent {
        return intent
    }


}