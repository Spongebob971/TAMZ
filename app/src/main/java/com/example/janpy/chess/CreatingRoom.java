package com.example.janpy.chess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.janpy.chess.Database.RoomDatabaseHandler;
import com.example.janpy.chess.entity.Player;
import com.example.janpy.chess.entity.Room;

public class CreatingRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_room);
    }

    public void myClick(View view) {
        EditText nameOfRoom = findViewById(R.id.editText);
        Room room = new Room( nameOfRoom.getText().toString());
        RoomDatabaseHandler.add(room);
        RoomDatabaseHandler.addPlayer(room);
        Player.color = "white";
        Player.movementAllowed = true;
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("parcel", room);
        startActivity(intent);
    }
}
