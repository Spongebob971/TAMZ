package com.example.janpy.chess.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.janpy.chess.Database.RoomDatabaseHandler;
import com.example.janpy.chess.Game;
import com.example.janpy.chess.MoveCalculation;
import com.example.janpy.chess.R;
import com.example.janpy.chess.figures.Figure;
import com.example.janpy.chess.figures.Peon;

import java.util.ArrayList;

public class ChessBoard extends View {
    private Paint rect_paint_grey = new Paint();
    private Paint rect_paint_white = new Paint();
    private Paint circle_paint_red = new Paint();
    private Point coordinatesFrom = new Point();
    private Boolean endOfGame = false;

    private Point poleSize = new Point();
    private Figure[][] board;
    private Figure figureBuffer = new Peon("White",BitmapFactory.decodeResource(getResources(), R.drawable.white_peon));
    private Point figureBufferCoordinates = new Point();
    private Point touchCoordinates = new Point();
    private ArrayList<Point> possibleMoves = new ArrayList<Point>();
    private int initY, initX;
    private int drawingNumber;

    public ChessBoard(Context context) {
        super(context);
        colorInit();
    }

    public ChessBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroud(canvas);
        if(board == null) board = Game.getBoard();
            for(int i = 0 ; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if(Player.color.equals("white")){
                            canvas.drawBitmap(board[i][j].getBitPic(), null, new Rect(i * poleSize.x, j * poleSize.y,
                                    i * poleSize.x + poleSize.x, j * poleSize.y + poleSize.y), null);
                        }
                        else if( Player.color.equals("black")){
                            canvas.drawBitmap(board[i][j].getBitPic(), null, new Rect(Math.abs(i - 7) * poleSize.x, Math.abs(j - 7) * poleSize.y,
                                    Math.abs(i - 7) * poleSize.x + poleSize.x, Math.abs(j - 7) * poleSize.y + poleSize.y), null);
                        }
                    }
                }
            }
        drawPossibleMoves(canvas);
            if(endOfGame){
                drawEndOfGame(canvas);
            }
    }

    private void drawBackgroud(Canvas canvas){
        Drawable d = getResources().getDrawable(R.drawable.desk);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);


        poleSize.x = canvas.getWidth()/ 8;
        poleSize.y = canvas.getWidth()/ 8;
        canvas.drawRect(0,0,50,50, rect_paint_grey);
        canvas.drawRect(100,100,150,150, rect_paint_white);
        drawingNumber = 1;
        initY = -1;
        initX = 0;
        for(int i = 0 ; i < 64; i++){
            if(i % 8 == 0 ){
                initY++;
                initX = 0;
                drawingNumber ^= 1;
            }
            if(i % 2 == drawingNumber) canvas.drawRect(poleSize.x * initX,poleSize.y * initY,(poleSize.x * initX) + poleSize.x, (poleSize.y * initY) + poleSize.y, rect_paint_white);
            else canvas.drawRect(poleSize.x * initX,poleSize.y * initY,(poleSize.x * initX) + poleSize.x, (poleSize.y * initY) + poleSize.y, rect_paint_grey);
            initX++;
        }
     }

    private void drawPossibleMoves(Canvas canvas){
        for (Point object: possibleMoves) {
            if(Player.color.equals("white")){
                canvas.drawCircle( (object.x * poleSize.x) + poleSize.x / 2, (object.y * poleSize.y) + poleSize.y / 2, 10, circle_paint_red);
            }
            else{
                canvas.drawCircle( ( Math.abs(object.x - 7) * poleSize.x) + poleSize.x / 2, (Math.abs(object.y - 7) * poleSize.y) + poleSize.y / 2, 10, circle_paint_red);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(Player.movementAllowed){
                    if(touchCoordinates.x > 7 || touchCoordinates.y > 7 || touchCoordinates.y < 0 || touchCoordinates.x < 0){
                    }else{
                        touchCoordinates.x = (int)event.getX() / poleSize.x;
                        touchCoordinates.y = (int)event.getY() / poleSize.y;
                        if(Player.color.equals("black")){
                            touchCoordinates.x = Math.abs(touchCoordinates.x - 7);
                            touchCoordinates.y = Math.abs(touchCoordinates.y - 7);
                        }
                        if(possibleMoves.isEmpty() || (board[touchCoordinates.x][touchCoordinates.y] != null && board[touchCoordinates.x][touchCoordinates.y].getColor().equals(Player.color))){
                            if(board[touchCoordinates.x][touchCoordinates.y] != null){
                                if( board[touchCoordinates.x][touchCoordinates.y].getColor().equals(Player.color)){
                                    figureBufferCoordinates.x = touchCoordinates.x;
                                    figureBufferCoordinates.y = touchCoordinates.y;
                                    figureBuffer = board[touchCoordinates.x][touchCoordinates.y];
                                    possibleMoves = board[touchCoordinates.x][touchCoordinates.y].getPossibleMoves(board, touchCoordinates);
                                    MoveCalculation.isMyKingChecked(board, touchCoordinates, possibleMoves, board[touchCoordinates.x][touchCoordinates.y]);
                                    invalidate();
                                }
                            }
                        }
                        for(Point obj : possibleMoves){
                            if(obj.x == touchCoordinates.x && obj.y == touchCoordinates.y){
                                ArrayList<Move> moves = Game.room.getMoves();
                                moves.add(new Move(new Point(figureBufferCoordinates.x, figureBufferCoordinates.y), new Point(touchCoordinates.x, touchCoordinates.y)));
                                Game.room.setMoves(moves);
                                move();
                                invalidate();
                                RoomDatabaseHandler.addMoves(Game.room);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void move(){
        Game.mp.start();
        for( Point obj : possibleMoves){
            if(obj.x == touchCoordinates.x && obj.y == touchCoordinates.y){
                board[touchCoordinates.x][touchCoordinates.y] = figureBuffer;
                board[figureBufferCoordinates.x][figureBufferCoordinates.y] = null;
                if(board[touchCoordinates.x][touchCoordinates.y].getFileName().equals("white_peon") ||
                        board[touchCoordinates.x][touchCoordinates.y].getFileName().equals("black_peon")) board[touchCoordinates.x][touchCoordinates.y].setFirstMove(false);
                break;
            }
        }
        possibleMoves = null;
        possibleMoves = new ArrayList<>();
        controlEnemyCheckMate();
    }

    private void colorInit(){
        rect_paint_grey.setStyle(Paint.Style.FILL);
        rect_paint_grey.setColor(Color.rgb(104, 89, 42));
        rect_paint_white.setStyle(Paint.Style.FILL);
        rect_paint_white.setColor(Color.rgb(240, 230, 192));
        circle_paint_red.setStyle(Paint.Style.FILL);
        circle_paint_red.setColor(Color.rgb(255,0,0));
    }

    public boolean controlCheckMate(){
        ArrayList<Point> moves = new ArrayList<>();
        Point tmpPoint = new Point();
        for(int i = 0 ; i < 8; i++){
            for (int j = 0 ; j < 8; j++){
                if(board[i][j] == null) return false;
                if(board[i][j] != null && board[i][j].getColor().equals(Player.color)){
                    moves = board[i][j].getPossibleMoves(board, tmpPoint);
                    MoveCalculation.isMyKingChecked(board, touchCoordinates, moves, board[touchCoordinates.x][touchCoordinates.y]);
                    if(!moves.isEmpty()) return false;
                }
            }
        }
            endOfGame = true;
            Log.d("Trida", "konec");
            invalidate();
            return true;
    }

    public void controlEnemyCheckMate(){
        ArrayList<Point> moves = new ArrayList<>();
        String enemyColor = "white";
        if(Player.color.equals("white")) enemyColor = "black";
        Point tmpPoint = new Point();
        for(int i = 0 ; i < 8; i++){
            for (int j = 0 ; j < 8; j++){
                if(board[i][j] != null && board[i][j].getColor().equals(enemyColor)){
                    tmpPoint.x = i;
                    tmpPoint.y = j;
                    moves = board[i][j].getPossibleMoves(board, tmpPoint);
                    MoveCalculation.isMyKingChecked(board, tmpPoint, moves, board[tmpPoint.x][tmpPoint.y]);
                    if(!moves.isEmpty()){
                        for(Point obh : moves){
                            Log.d("Trida", "  x " + obh.x + "    y   " + obh.y);
                        }
                        return;
                    }
                }
            }
        }
            endOfGame = true;
            invalidate();
            sharedPreferencesHandler();
    }

    private void sharedPreferencesHandler(){
        SharedPreferences sharedPreferences = Game.getSharedPreferences(getContext());
        int tmp = sharedPreferences.getInt("amountOfGames", 0);
        tmp++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("amountOfGames", tmp);
        tmp = sharedPreferences.getInt("amountOfWinGames", 0);
        tmp++;
        editor.putInt("amountOfWinGames", tmp);
        editor.apply();
    }

    private void drawEndOfGame(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTextSize(40);
        float length = paint.measureText("End of Game");
        canvas.drawText("End of Game", canvas.getWidth() / 2 - (length / 2) , canvas.getHeight() / 2, paint);
        RoomDatabaseHandler.remove(Game.room);
    }
}
