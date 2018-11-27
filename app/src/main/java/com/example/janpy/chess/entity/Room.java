package com.example.janpy.chess.entity;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements Parcelable {

    private String name;
    private String id;
    private ArrayList<Move> moves;
    private String playerOne;
    private String playerTwo;

    public Room(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.moves = new ArrayList<>();
        this.playerOne = "0";
        this.playerTwo = "0";
    }

    public Room() {
        this.moves = new ArrayList<>();
        this.name = "emptyRoom";
        this.id = UUID.randomUUID().toString();
        this.playerOne = "0";
        this.playerTwo = "0";
    }

    public String getPlayerOne(){
        return this.playerOne;
    }
    public String getPlayerTwo(){
        return this.playerTwo;
    }

    public void setPlayerOne(String playerOne){
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(String playerTwo){
        this.playerTwo = playerTwo;
    }

    public void addMove(Move move){
        if(moves == null) return;
        this.moves.add(move);
    }

    public void setName(String name){
        this.name = name;
    }
    public void setMoves(ArrayList<Move> moves){
        this.moves = moves;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Move> getMoves(){
        return this.moves;
    }
    public String getID(){
        return this.id;
    }

    public void printMoves(){
        for(Move obj: moves){
            Log.d("Trida", " from " + obj.getMoveFrom().x + "  " + obj.getMoveFrom().y + " to   " +
                    obj.getMoveTo().x + "  "  + obj.getMoveTo().y);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(id);
        out.writeList(moves);
        out.writeString(playerOne);
        out.writeString(playerTwo);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {

        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Room(Parcel in) {
        if(moves == null) moves = new ArrayList<>();
        name = in.readString();
        id = in.readString();
        //this.moves = in.readArrayList(null);
        in.readList(moves, getClass().getClassLoader());
        playerOne = in.readString();
        playerTwo = in.readString();
    }
}
