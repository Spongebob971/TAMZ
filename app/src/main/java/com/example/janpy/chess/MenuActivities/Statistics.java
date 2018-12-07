package com.example.janpy.chess.MenuActivities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.janpy.chess.Game;
import com.example.janpy.chess.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        sharedPreferencesHandler();

    }

    private void sharedPreferencesHandler(){
        TableLayout  tl = (TableLayout)findViewById(R.id.tableLayout);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        int size = sharedPref.getInt("sizeOfTableGame", 0);
        Date myDate;
        int win;
        int lose;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for(int i=0;i<size;i++)
        {
            TableRow tr = new TableRow(this);
            TextView textViewDate = new TextView(this);
            TextView textViewWin = new TextView(this);
            TextView textViewLose = new TextView(this);

            textViewDate.setGravity(Gravity.CENTER);
            textViewLose.setGravity(Gravity.CENTER);
            textViewWin.setGravity(Gravity.CENTER);

            myDate = new Date(sharedPref.getLong( "Date_" + i, 0));
            win = sharedPref.getInt("Win_" + i, 0);
            lose = sharedPref.getInt("Lose_" + i, 0);
            try {
                textViewDate.setText(format.format(myDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textViewWin.setText("" + win);
            textViewLose.setText("" + lose);

            tr.addView(textViewDate);
            tr.addView(textViewWin);
            tr.addView(textViewLose);
            tl.addView(tr);
        }

        TableRow tr = new TableRow(this);
        TextView textViewDate = new TextView(this);
        TextView textViewWin = new TextView(this);
        TextView textViewLose = new TextView(this);

        textViewDate.setGravity(Gravity.CENTER);
        textViewLose.setGravity(Gravity.CENTER);
        textViewWin.setGravity(Gravity.CENTER);

        int amountOfGames = sharedPref.getInt("amountOfGames", 0);
        int amountOfWinGames = sharedPref.getInt("amountOfWinGames", 0);

        textViewDate.setText("Celkem");
        textViewWin.setText("" + amountOfWinGames);
        textViewLose.setText("" + (amountOfGames - amountOfWinGames));

        tr.addView(textViewDate);
        tr.addView(textViewWin);
        tr.addView(textViewLose);
        tl.addView(tr);
    }
}
