package com.example.janpy.chess.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import com.example.janpy.chess.Game;
import com.example.janpy.chess.MoveCalculation;

import java.util.ArrayList;

public class Peon extends Figure {
    public Peon() {
        this.color = "white";
    }

    public Peon(String color, Bitmap bitmap) {
        super(color, bitmap);

    }

    @Override
    public void move()
    {
        super.move();
    }

    @Override
    public ArrayList<Point> getPossibleMoves(Figure[][] board, Point coordinates) {
        possibleMoves = null;
        possibleMoves = new ArrayList<>();
        MoveCalculation.peonCalculation(board, coordinates, possibleMoves , this);
        return possibleMoves;
    }


}
