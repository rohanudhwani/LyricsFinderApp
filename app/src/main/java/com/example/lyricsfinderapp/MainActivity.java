package com.example.lyricsfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText edtArtistName, edtSongName;
    private Button btnGetLyrics;
    private TextView txtLyrics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtArtistName = findViewById(R.id.edtArtistName);
        edtSongName = findViewById(R.id.edtSongName);
        btnGetLyrics = findViewById(R.id.btnGetLyrics);
        txtLyrics = findViewById(R.id.txtLyrics);

        RequestQueue queue = Volley.newRequestQueue(this);
        String lyricsURL = "https://filthier-ponds.000webhostapp.com/lyrics.json";

        /* StringRequest request = new StringRequest(Request.Method.GET, lyricsURL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtTest.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }); */



        btnGetLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String artistName = edtArtistName.getText().toString();
                String tempAN = artistName.trim();
                String songName = edtSongName.getText().toString();
                String tempSN = songName.trim();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, lyricsURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray songs = response.getJSONArray("songs");

                                    for (int i=0; i<songs.length(); i++){
                                        JSONObject song = (JSONObject)songs.get(i);

                                        if(song.getString("artist_name").equals(tempAN)){
                                            if (song.getString("song_name").equals(tempSN)){
                                                txtLyrics.setText(song.getString("lyrics"));
                                            }
                                        }
                                    }


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(jsonObjectRequest);
            }
        });

    }


}