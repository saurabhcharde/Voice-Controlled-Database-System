package com.saurabh.voicedatabase;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

    private TextView txtSpeechInput,roll,name,marks;
    private ImageButton btnSpeak1;
    Button  btnSpeak3,btnSpeak4;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    ArrayList<String> addParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

        btnSpeak1 = (ImageButton) findViewById(R.id.btnSpeak1);
        btnSpeak3 = (Button) findViewById(R.id.btnSpeak3);
        btnSpeak4 = (Button) findViewById(R.id.btnSpeak4);

        addParam=new ArrayList<>();
        // hide the action bar
        try{
            getActionBar().hide();
        }
        catch(NullPointerException npe){

        }

        btnSpeak1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput(2);
            }
        });
        btnSpeak4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uri ="192.168.137.1/speech/select.php";

                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+uri));

                 startActivity(intent);
            }
        });
        btnSpeak3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput(3);
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput(int id) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, id);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 2: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String param3=result.get(0);

                    String uri = String.format("192.168.137.1/speech/insert.php?param1=%1$s",
                            param3);//replace with your own ip--(run ipconfig in command prompt)

                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+uri));

                    startActivity(intent);

                }
                break;
            }
            case 3:{
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String id = result.get(0);
                    String uri = String.format("192.168.137.1/speech/delete.php?param1=%1$s",
                            id);

                    //sendRequest(uri);
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+uri));

                    startActivity(intent);
                }
            }
            break;

        }
    }

//Volley Support
  /*  public void sendRequest(final String result) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://"+result,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        txtSpeechInput.setText("Response is: "+ response.substring(0,500));
                        //txtSpeechInput.setText("Query OK");
                    }////
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtSpeechInput.setText("That didn't work!");
                sendRequest(result);
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }*/
}