package com.example.janpy.chess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janpy.chess.Database.RoomDatabaseHandler;
import com.example.janpy.chess.MenuActivities.About;
import com.example.janpy.chess.MenuActivities.Statistics;
import com.example.janpy.chess.adapter.ViewAdapter;
import com.example.janpy.chess.entity.Move;
import com.example.janpy.chess.entity.Player;
import com.example.janpy.chess.entity.Room;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Room> roomList ;
    private ViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roomList = new ArrayList<>();
        initRecycler();
        initDatabaseListener();
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewAdapter = new ViewAdapter(roomList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(viewAdapter);
    }

    public void myClick(View view) {

        Intent i = new Intent(this, CreatingRoom.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
            case R.id.stats:
                Intent intent2 = new Intent(this, Statistics.class);
                startActivity(intent2);
                return true;
            case R.id.exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initDatabaseListener(){
        RoomDatabaseHandler.addOnRoomsListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                roomList.add(dataSnapshot.getValue(Room.class));
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                for (Room r: roomList) {
                    if(r.getID().equals(key)){
                        Room var = dataSnapshot.getValue(Room.class);
                        r.setName(var.getName());
                        r.setMoves(var.getMoves());
                        break;
                    }
                }
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (Room r: roomList) {
                    if(r.getID().equals(key)){
                        roomList.remove(r);
                        break;
                    }
                }
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

