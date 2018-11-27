package com.example.janpy.chess.figures;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import com.example.janpy.chess.MoveCalculation;

import java.util.ArrayList;

public class Bishop extends Figure {
    public Bishop(String color, Bitmap bitmap) {
        super(color,bitmap);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public ArrayList<Point> getPossibleMoves(Figure[][] board, Point coordinates) {
        possibleMoves = null;
        possibleMoves = new ArrayList<>();
        MoveCalculation.bishopCalculation( board, coordinates, possibleMoves , this);
        return possibleMoves;
    }
}
