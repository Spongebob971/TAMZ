package com.example.janpy.chess.figures;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.janpy.chess.Game;
import com.example.janpy.chess.R;

import java.util.ArrayList;

public abstract class Figure {
    private Bitmap bitPic;
    public String color;
    protected boolean firstMove = true;
    protected String enemyColor;
    protected ArrayList<Point> possibleMoves = new ArrayList<Point>();
    private String fileName;

    public Figure(){
        this.color = "white";
    }

    public Figure(String color, Bitmap bitMap){
        this.color = color;
        enemyColor = "white";
        if(this.color.equals("white")) enemyColor = "black";
        fileName =color + "_"+this.getClass().toString().substring(this.getClass().toString().lastIndexOf(".") + 1);
        fileName = fileName.toLowerCase();
        this.bitPic = bitMap;
        this.firstMove = true;
    }

    public void move(){
    }

    public ArrayList<Point> getPossibleMoves(Figure[][] board, Point coordiantes){
        return  new ArrayList<>();
    }

    public Bitmap getBitPic(){
        return this.bitPic;
    }

    public String getColor(){
        return this.color;
    }

    public void setEnemyColor( String enemyColor){
        this.enemyColor = enemyColor;
    }

    public String getEnemyColor(){
        return enemyColor;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean getFirstMove(){
        return firstMove;
    }
}
