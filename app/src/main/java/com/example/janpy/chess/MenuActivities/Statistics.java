package com.example.janpy.chess.MenuActivities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.janpy.chess.R;

import org.w3c.dom.Text;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        int amountOfGames = sharedPref.getInt("amountOfGames", 0);
        int amountOfWinGames = sharedPref.getInt("amountOfWinGames", 0);

        TextView editTextAllGames = findViewById(R.id.textView22);
        editTextAllGames.setText("" + amountOfGames);

        TextView editTextWinGames = findViewById(R.id.textView21);
        editTextWinGames.setText("" + amountOfWinGames);

    }
}
