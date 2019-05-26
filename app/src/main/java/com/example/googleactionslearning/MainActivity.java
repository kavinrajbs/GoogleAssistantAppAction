package com.example.googleactionslearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements  TextToSpeech.OnInitListener{
    private static final String TAG = "MainActivity";
    private static final String ACTION_VOICE_SEARCH = "com.google.android.gms.actions.SEARCH_ACTION";
    private TextToSpeech textToSpeech;
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleVoiceSearch(getIntent());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleVoiceSearch(intent);

    }

    @Override
    protected void onDestroy() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void handleVoiceSearch(Intent intent) {
        Log.d(TAG, "handleVoiceSearch: ");
        if (intent != null && ACTION_VOICE_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            textToSpeech = new TextToSpeech(this,this);
            //Toast.makeText(this,"Inside handle voice",Toast.LENGTH_SHORT).show();
        }
    }


    private void speakOut() {
        if(query.contains("battery")) {
            textToSpeech.speak("Hey kavin , It's 90 percent charge now", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(query.contains("climate"))
        {
            textToSpeech.speak("Hey kavin , temperature is 20 degree celsius", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
                Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
            Toast.makeText(MainActivity.this, "Initilization Failed!", Toast.LENGTH_SHORT).show();
        }


    }
}
