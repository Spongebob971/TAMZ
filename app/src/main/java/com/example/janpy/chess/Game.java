package com.example.janpy.chess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janpy.chess.Database.RoomDatabaseHandler;
import com.example.janpy.chess.entity.ChessBoard;
import com.example.janpy.chess.entity.Move;
import com.example.janpy.chess.entity.Player;
import com.example.janpy.chess.entity.Room;
import com.example.janpy.chess.figures.Bishop;
import com.example.janpy.chess.figures.Figure;
import com.example.janpy.chess.figures.King;
import com.example.janpy.chess.figures.Knight;
import com.example.janpy.chess.figures.Peon;
import com.example.janpy.chess.figures.Queen;
import com.example.janpy.chess.figures.Rook;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Game extends AppCompatActivity {
    private static Figure[][] board;
    public static Point screenSize = new Point();
    public static Room room;

    public static boolean flagMovement;
    public static SharedPreferences sharedPreferences;
    public static Display display;
    public static MediaPlayer mp;
    public static List<TableRow> tableRows;

    private ChessBoard chessBoard ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chessBoard = new ChessBoard(this);
        tableRows = new ArrayList<TableRow>();
        mp = MediaPlayer.create(this, R.raw.movement);
        setContentView(chessBoard);
        Intent i = getIntent();
        room = (Room) i.getParcelableExtra("parcel");
        RoomDatabaseHandler.addPlayer(room);
        init();
        initDatabaseListener();
    }

    public void init(){
        board = new Figure[8][8];
        initBoardFigures();
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        display.getSize(screenSize);
    }

    private void initBoardFigures(){
        for(int i = 0 ; i < 8; i++){
            board[i][6] = new Peon("white",  BitmapFactory.decodeResource(getResources(), R.drawable.white_peon) );
            board[i][1] = new Peon("black",BitmapFactory.decodeResource(getResources(), R.drawable.black_peon) );
        }
        board[0][0] = new Rook("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_rook));
        board[7][0] = new Rook("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_rook));
        board[0][7] = new Rook("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_rook));
        board[7][7] = new Rook("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_rook));
        board[1][0] = new Knight("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_knight));
        board[6][0] = new Knight("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_knight));
        board[1][7] = new Knight("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_knight));
        board[6][7] = new Knight("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_knight));
        board[2][0] = new Bishop("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_bishop));
        board[5][0] = new Bishop("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_bishop));
        board[2][7] = new Bishop("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_bishop));
        board[5][7] = new Bishop("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_bishop));
        board[3][0] = new Queen("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_queen));
        board[3][7] = new Queen("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_queen));
        board[4][7] = new King("white", BitmapFactory.decodeResource(getResources(), R.drawable.white_king));
        board[4][0] = new King("black", BitmapFactory.decodeResource(getResources(), R.drawable.black_king));
    }

    private void initDatabaseListener(){
        RoomDatabaseHandler.addOnMovesListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Player.movementAllowed = !Player.movementAllowed;

                Move move = new Move();
                if(Game.room.getMoves().isEmpty()){
                    move.setMoveFrom(new Point(-1,-1));
                    move.setMoveTo(new Point(-1,-1));
                }else{
                    move = Game.room.getMoves().get(Game.room.getMoves().size() - 1);
                }

                Move moveFromServer = dataSnapshot.getValue(Move.class);
                if(moveFromServer == null || (move.getMoveTo().equals(moveFromServer.getMoveTo()) && move.getMoveFrom().equals(moveFromServer.getMoveFrom()))) return;
                Game.room.addMove(dataSnapshot.getValue(Move.class));
                Game.room.printMoves();
                //castle CONTROL
                if(Game.board[moveFromServer.getMoveFrom().x][moveFromServer.getMoveFrom().y].getFileName().equals("black_king")
                        ||Game.board[moveFromServer.getMoveFrom().x][moveFromServer.getMoveFrom().y].getFileName().equals("white_king")){
                    if(moveFromServer.getMoveFrom().x  + 2 == moveFromServer.getMoveTo().x ){
                        Game.board[moveFromServer.getMoveTo().x - 1 ][moveFromServer.getMoveTo().y] = Game.board[moveFromServer.getMoveTo().x + 1 ][moveFromServer.getMoveTo().y];
                        Game.board[moveFromServer.getMoveTo().x + 1 ][moveFromServer.getMoveTo().y] = null;
                    }

                    if(moveFromServer.getMoveFrom().x - 2 == moveFromServer.getMoveTo().x){
                        Game.board[moveFromServer.getMoveTo().x + 1][moveFromServer.getMoveTo().y] = Game.board[moveFromServer.getMoveTo().x - 2][moveFromServer.getMoveTo().y];
                        Game.board[moveFromServer.getMoveTo().x - 2][moveFromServer.getMoveTo().y] = null;
                    }
                }

                Game.board[moveFromServer.getMoveTo().x][moveFromServer.getMoveTo().y] = Game.board[moveFromServer.getMoveFrom().x][moveFromServer.getMoveFrom().y];
                Game.board[moveFromServer.getMoveFrom().x][moveFromServer.getMoveFrom().y] = null;

                chessBoard.invalidate();
                mp.start();
                if(chessBoard.controlCheckMate()){
                    sharePrefrencesHandler();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Trida", "Moves Changed");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Trida", "Moves Added");
                Toast.makeText(getBaseContext(), "This is my Toast message DEL!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sharePrefrencesHandler(){
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int tmp = sharedPreferences.getInt("amountOfGames", 0);
        tmp++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("amountOfGames", tmp);

        int size = sharedPreferences.getInt("sizeOfTableGame", 0);
        Date date = new Date(System.currentTimeMillis());
        editor.putInt("Win_" + size,    0);
        editor.putInt("Lose_" + size,    1);
        editor.putLong("Date_" + size,  date.getTime());
        editor.putInt("sizeOfTableGame",++size);
        editor.putInt("amountOfGames", tmp);
        editor.apply();
    }


    public static SharedPreferences getSharedPreferences(Context context){
        return  context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
    }


    public static Figure[][] getBoard(){
        return board;
    }
}

