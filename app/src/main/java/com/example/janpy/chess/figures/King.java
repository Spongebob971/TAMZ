package com.example.janpy.chess.figures;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.janpy.chess.MoveCalculation;

import java.util.ArrayList;

public class King extends Figure {

    public King(String color, Bitmap bitmap) {
        super(color, bitmap);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public ArrayList<Point> getPossibleMoves(Figure[][] board, Point coordiantes) {
        possibleMoves = null;
        possibleMoves = new ArrayList<>();
        MoveCalculation.kingCalculation(board, coordiantes, possibleMoves, this);
        return  possibleMoves;
    }
}
