package com.example.janpy.chess.Database;

import android.os.Parcelable;
import android.provider.ContactsContract;

import com.example.janpy.chess.Game;
import com.example.janpy.chess.entity.Room;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomDatabaseHandler {

     public static void add(Room room){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChessRooms");
        myRef.child(room.getID()).setValue(room);
    }

    public static void remove(Room room){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChessRooms");
        myRef.child(room.getID()).removeValue();
    }

    public static void addMoves(Room room){
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference ref = database.getReference("ChessRooms");
         ref.child(room.getID()).child("moves").setValue(room.getMoves());
    }

    public static void addOnRoomsListener(ChildEventListener childListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChessRooms");
        myRef.addChildEventListener(childListener);
     }

    public static void addPlayer(Room room){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ChessRooms");
        if(room.getPlayerOne().equals("0")){
            ref.child(room.getID()).child("playerOne").setValue("filled");
            room.setPlayerOne("filled");
        }
        else if(room.getPlayerTwo().equals("0")){
            ref.child(room.getID()).child("playerTwo").setValue("filled");
            room.setPlayerTwo("filled");
        }
    }

     public static void addOnMovesListener(ChildEventListener childEventListener){
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference("ChessRooms");

         myRef.child(Game.room.getID()).child("moves").addChildEventListener(childEventListener);
     }
}
