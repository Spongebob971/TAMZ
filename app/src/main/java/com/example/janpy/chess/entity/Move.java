package com.example.janpy.chess.entity;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Move implements Parcelable {
    private Point from = new Point();
    private Point to = new Point();

    public Move(){

    }

    public Move(Point from, Point to){
        this.from = from;
        this.to = to;
    }

    public Point getMoveFrom(){
        return this.from;
    }

    public Point getMoveTo(){
        return this.to;
    }

    public void setMoveTo(Point to){
        this.to = to;
    }

    public void setMoveFrom(Point from){
        this.from = from;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(from.x);
        out.writeInt(from.y);
        out.writeInt(to.x);
        out.writeInt(to.y);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Move> CREATOR = new Parcelable.Creator<Move>() {

        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Move(Parcel in) {
        this.from.x = in.readInt();
        this.from.y = in.readInt();
        this.to.x = in.readInt();
        this.to.y = in.readInt();
    }

}
