package com.example.janpy.chess;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import com.example.janpy.chess.figures.Bishop;
import com.example.janpy.chess.figures.Figure;
import com.example.janpy.chess.figures.King;
import com.example.janpy.chess.figures.Knight;
import com.example.janpy.chess.figures.Peon;
import com.example.janpy.chess.figures.Queen;
import com.example.janpy.chess.figures.Rook;

import java.text.Bidi;
import java.util.ArrayList;

public class MoveCalculation {
    private static ArrayList<Point> enemyPossibleMoves = new ArrayList<>();
    private static Point enemyCoordinates = new Point();

    public static void peonCalculation(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Peon figure){
        if(figure.getColor().equals("black")){
            if( coordinates.x > 0 && coordinates.y < 7 && board[coordinates.x -1 ][coordinates.y + 1] != null){
                if(board[coordinates.x - 1][coordinates.y + 1].getColor().equals("white")){
                    possibleMoves.add(new Point(coordinates.x - 1,coordinates.y + 1));
                }
            }
            if(coordinates.x < 7 && coordinates.y < 7 && board[coordinates.x +1 ][coordinates.y + 1] != null){
                if(board[coordinates.x + 1][coordinates.y + 1].getColor().equals("white")){
                    possibleMoves.add(new Point(coordinates.x + 1,coordinates.y + 1));
                }
            }
            if( coordinates.y < 7 && board[coordinates.x][coordinates.y + 1] == null){
                possibleMoves.add( new Point(coordinates.x, coordinates.y + 1));
                if( figure.getFirstMove() && coordinates.y + 2 <= 7 &&board[coordinates.x][coordinates.y + 2] == null){
                    possibleMoves.add(new Point(coordinates.x, coordinates.y + 2));
                }
            }
        }
        else{
            if( coordinates.x > 0 && coordinates.y > 0 && board[coordinates.x -1 ][coordinates.y - 1] != null){
                if(board[coordinates.x - 1][coordinates.y - 1].getColor().equals("black")){
                    possibleMoves.add(new Point(coordinates.x - 1,coordinates.y - 1));
                }
            }
            if(coordinates.x < 7 && coordinates.y > 0 && board[coordinates.x + 1 ][coordinates.y - 1] != null){
                if(board[coordinates.x + 1][coordinates.y - 1].getColor().equals("black")){
                    possibleMoves.add(new Point(coordinates.x + 1,coordinates.y - 1));
                }
            }
            if( coordinates.y > 0 && board[coordinates.x][coordinates.y - 1] == null){
                possibleMoves.add(new Point(coordinates.x, coordinates.y - 1));
                if(figure.getFirstMove() && coordinates.y - 2 >= 0 &&board[coordinates.x][coordinates.y - 2] == null){
                    possibleMoves.add(new Point(coordinates.x, coordinates.y - 2));
                }
            }
        }
    }

    public static void knightCalculation(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Knight figure){
        if(figure.getColor().equals("black")) figure.setEnemyColor("white");
        else figure.setEnemyColor("black");

        if(coordinates.x - 2 >= 0  && coordinates.y - 1 >= 0 &&(board[coordinates.x - 2][coordinates.y - 1] == null ||board[coordinates.x - 2][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 2, coordinates.y - 1));
        }
        if(coordinates.x - 2 >= 0  && coordinates.y + 1 <= 7 &&(board[coordinates.x - 2][coordinates.y + 1] == null ||board[coordinates.x - 2][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 2, coordinates.y + 1));
        }
        if(coordinates.x + 2 <= 7  && coordinates.y + 1 <= 7 &&(board[coordinates.x + 2][coordinates.y + 1] == null ||board[coordinates.x + 2][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 2, coordinates.y + 1));
        }
        if(coordinates.x + 2 <= 7  && coordinates.y - 1 >= 0 &&(board[coordinates.x + 2][coordinates.y - 1] == null ||board[coordinates.x + 2][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 2, coordinates.y - 1));
        }
        if(coordinates.x - 1 >= 0  && coordinates.y + 2 <= 7 &&(board[coordinates.x - 1][coordinates.y + 2] == null ||board[coordinates.x - 1][coordinates.y + 2].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y + 2));
        }
        if(coordinates.x - 1 >= 0  && coordinates.y - 2 >= 0 &&(board[coordinates.x - 1][coordinates.y - 2] == null ||board[coordinates.x - 1][coordinates.y - 2].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add( new Point(coordinates.x - 1, coordinates.y - 2));
        }
        if(coordinates.x + 1 <= 7  && coordinates.y + 2 <= 7 &&(board[coordinates.x + 1][coordinates.y + 2] == null ||board[coordinates.x + 1][coordinates.y + 2].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 1, coordinates.y + 2));
        }
        if(coordinates.x + 1 <= 7  && coordinates.y - 2 >= 0 &&(board[coordinates.x + 1][coordinates.y - 2] == null ||board[coordinates.x + 1][coordinates.y - 2].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 1, coordinates.y - 2));
        }
    }

    public static void bishopCalculation(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Bishop figure){
        if(figure.getColor().equals("black")) figure.setEnemyColor("white");
        else figure.setEnemyColor("black");
        addPossibleMovesFirstQuadrantBishop(board, coordinates, possibleMoves, figure);
        addPossibleMovesSecondQuadrantBishop(board, coordinates, possibleMoves, figure);
        addPossibleMovesThirdQuadrantBishop(board, coordinates,possibleMoves, figure);
        addPossibleMovesForthQuadrantBishop(board, coordinates, possibleMoves, figure);
    }
    private static void addPossibleMovesFirstQuadrantBishop(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x < 7 && coordinates.y < 7  && (board[coordinates.x + 1][coordinates.y + 1] == null || board[coordinates.x + 1][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x +1, coordinates.y + 1));
            coordinates.x += 1;
            coordinates.y += 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesFirstQuadrantBishop(board, coordinates, possibleMoves, figure);
            coordinates.x -= 1;
            coordinates.y -= 1;
        }
    }

    private static void addPossibleMovesSecondQuadrantBishop(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x < 7 && coordinates.y > 0  && (board[coordinates.x + 1][coordinates.y - 1] == null || board[coordinates.x + 1][coordinates.y - 1].getColor().equals(figure.getEnemyColor()) )){
            possibleMoves.add(new Point(coordinates.x +1, coordinates.y - 1));
            coordinates.x += 1;
            coordinates.y -= 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesSecondQuadrantBishop(board, coordinates, possibleMoves, figure);
            coordinates.x -= 1;
            coordinates.y += 1;
        }
    }

    private static void addPossibleMovesThirdQuadrantBishop(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x > 0 && coordinates.y > 0  && (board[coordinates.x - 1][coordinates.y - 1] == null || board[coordinates.x - 1][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){

            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y - 1));
            coordinates.x -= 1;
            coordinates.y -= 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesThirdQuadrantBishop(board, coordinates, possibleMoves, figure);
            coordinates.x += 1;
            coordinates.y += 1;
        }
    }

    private static void addPossibleMovesForthQuadrantBishop(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.y < 7 && coordinates.x > 0 && (board[coordinates.x - 1][coordinates.y + 1] == null || board[coordinates.x - 1][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y + 1));
            coordinates.x -= 1;
            coordinates.y += 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesForthQuadrantBishop(board, coordinates, possibleMoves, figure);
            coordinates.x += 1;
            coordinates.y -= 1;
        }
    }

    public static void rookCalculation(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Rook figure){
        if(figure.getColor().equals("black")) figure.setEnemyColor("white");
        else figure.setEnemyColor("black");
        addPossibleMovesFirstQuadrantRook(board, coordinates, possibleMoves, figure);
        addPossibleMovesSecondQuadrantRook(board, coordinates, possibleMoves, figure);
        addPossibleMovesThirdQuadrantRook(board, coordinates,possibleMoves, figure);
        addPossibleMovesForthQuadrantRook(board, coordinates, possibleMoves, figure);
    }

    private static void addPossibleMovesFirstQuadrantRook(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x < 7 && (board[coordinates.x + 1][coordinates.y] == null || board[coordinates.x + 1][coordinates.y].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x +1, coordinates.y));
            coordinates.x += 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesFirstQuadrantRook(board, coordinates, possibleMoves, figure);
            coordinates.x -= 1;
        }
    }

    private static void addPossibleMovesSecondQuadrantRook(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x > 0 && (board[coordinates.x - 1][coordinates.y] == null || board[coordinates.x - 1][coordinates.y].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y));
            coordinates.x -= 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesSecondQuadrantRook(board, coordinates, possibleMoves, figure);
            coordinates.x += 1;
        }
    }

    private static void addPossibleMovesThirdQuadrantRook(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.y > 0 && (board[coordinates.x][coordinates.y - 1] == null || board[coordinates.x][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x, coordinates.y - 1));
            coordinates.y -= 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesThirdQuadrantRook(board, coordinates, possibleMoves, figure);
            coordinates.y += 1;
        }
    }

    private static void addPossibleMovesForthQuadrantRook(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.y < 7 && (board[coordinates.x][coordinates.y + 1] == null || board[coordinates.x][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add( new Point(coordinates.x, coordinates.y + 1));
            coordinates.y += 1;
            if(board[coordinates.x][coordinates.y] == null) addPossibleMovesForthQuadrantRook(board, coordinates, possibleMoves, figure);
            coordinates.y -= 1;
        }
    }

    public static void queenCalculation(Figure[][] board, Point coordinates, ArrayList<Point> possibleMoves, Queen figure){
        if(figure.getColor().equals("black")) figure.setEnemyColor("white");
        else figure.setEnemyColor("black");
        addPossibleMovesFirstQuadrantBishop(board, coordinates, possibleMoves, figure);
        addPossibleMovesSecondQuadrantBishop(board, coordinates, possibleMoves, figure);
        addPossibleMovesThirdQuadrantBishop(board, coordinates,possibleMoves, figure);
        addPossibleMovesForthQuadrantBishop(board, coordinates, possibleMoves, figure);
        addPossibleMovesFirstQuadrantRook(board, coordinates, possibleMoves, figure);
        addPossibleMovesSecondQuadrantRook(board, coordinates, possibleMoves, figure);
        addPossibleMovesThirdQuadrantRook(board, coordinates,possibleMoves, figure);
        addPossibleMovesForthQuadrantRook(board, coordinates, possibleMoves, figure);
    }

    public static void kingCalculation(Figure[][] board, Point coordinates,  ArrayList<Point> possibleMoves, King figure) {
        enemyPossibleMoves = null;
        enemyPossibleMoves = new ArrayList<>();

        int deleteMovementI;

        if (figure.getColor().equals("black")) figure.setEnemyColor("white");
        else figure.setEnemyColor("black");
        basicKingMoves(board, coordinates, possibleMoves, figure);
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                deleteMovementI = 1;
                if (board[j][k] != null && board[j][k].getColor().equals(figure.getEnemyColor())) {
                    enemyCoordinates.x = j;
                    enemyCoordinates.y = k;
                    if (board[j][k].getFileName().equals("black_king") || board[j][k].getFileName().equals("white_king")) {
                        basicKingMoves(board, enemyCoordinates, enemyPossibleMoves, board[j][k]);
                    } else {
                        enemyPossibleMoves.addAll(board[j][k].getPossibleMoves(board, enemyCoordinates));
                        if (board[j][k].getFileName().equals("black_peon") || board[j][k].getFileName().equals("white_peon")) {
                            if(board[j][k].getFileName().equals("black_peon")){
                                int sizeMovement = enemyPossibleMoves.size();
                                if( k +2 <= 7 && board[j][k + 2] == null && board[j][k + 1] == null && board[j][k].getFirstMove()){
                                    enemyPossibleMoves.remove(sizeMovement - deleteMovementI);
                                    deleteMovementI++;
                                }
                                if( k + 1 <= 7&&  board[j][k + 1] == null) enemyPossibleMoves.remove(sizeMovement - deleteMovementI);
                            }
                            else{
                                int sizeMovement = enemyPossibleMoves.size();
                                if( k - 2 >= 0 && board[j][k - 2] == null && board[j][k - 1] == null && board[j][k].getFirstMove()){
                                    enemyPossibleMoves.remove(sizeMovement - deleteMovementI);
                                    deleteMovementI++;
                                }
                                if( k - 1 >= 0 && board[j][k - 1] == null) enemyPossibleMoves.remove(sizeMovement - deleteMovementI);
                            }
                        }
                    }
                    for (Point objPosMove : new ArrayList<>(possibleMoves)) {
                        for (Point objEnemyPosMove : enemyPossibleMoves) {
                            if (objPosMove.x == objEnemyPosMove.x && objPosMove.y == objEnemyPosMove.y) {
                                possibleMoves.remove(objPosMove);
                            }
                        }
                    }
                }
            }
        }
        checkMovementAllowedPositions(board, coordinates, possibleMoves, figure, enemyPossibleMoves);
    }

    public static void basicKingMoves(Figure[][] board, Point coordinates,  ArrayList<Point> possibleMoves, Figure figure){
        if( coordinates.x < 7 && (board[coordinates.x + 1][coordinates.y] == null || board[coordinates.x + 1][coordinates.y].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 1, coordinates.y));
        }
        if( coordinates.x < 7 && coordinates.y < 7 && (board[coordinates.x + 1][coordinates.y + 1] == null || board[coordinates.x + 1][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 1, coordinates.y + 1));
        }
        if( coordinates.x < 7 && coordinates.y > 0 && (board[coordinates.x + 1][coordinates.y - 1] == null || board[coordinates.x + 1][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x + 1, coordinates.y - 1));
        }
        if( coordinates.x > 0 && (board[coordinates.x - 1][coordinates.y] == null || board[coordinates.x - 1][coordinates.y].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y));
        }
        if( coordinates.x > 0 && coordinates.y < 7 && (board[coordinates.x - 1][coordinates.y + 1] == null || board[coordinates.x - 1][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y + 1));
        }
        if( coordinates.x > 0 && coordinates.y > 0 && (board[coordinates.x - 1][coordinates.y - 1] == null || board[coordinates.x - 1][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x - 1, coordinates.y - 1));
        }
        if( coordinates.y > 0 && (board[coordinates.x][coordinates.y - 1] == null || board[coordinates.x][coordinates.y - 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x, coordinates.y - 1));
        }
        if(  coordinates.y < 7 && (board[coordinates.x][coordinates.y + 1] == null || board[coordinates.x][coordinates.y + 1].getColor().equals(figure.getEnemyColor()))){
            possibleMoves.add(new Point(coordinates.x, coordinates.y + 1));
        }
        if(figure.getFirstMove()){
            if(figure.getColor().equals("white")){
                if( coordinates.x <= 4 && board[coordinates.x + 1][coordinates.y] == null && board[coordinates.x + 2][coordinates.y] == null && board[coordinates.x+3][ coordinates.y] != null &&
                        board[coordinates.x + 3][coordinates.y].getFileName().equals("white_rook")){
                    possibleMoves.add(new Point(coordinates.x + 2, coordinates.y));
                }
                if(coordinates.x >= 4 && board[coordinates.x - 1][coordinates.y] == null && board[coordinates.x - 2][coordinates.y] == null &&
                      board[coordinates.x - 3][coordinates.y] == null && board[coordinates.x - 4][ coordinates.y] != null && board[coordinates.x - 4][coordinates.y].getFileName().equals("white_rook")){
                    possibleMoves.add(new Point(coordinates.x - 2, coordinates.y));
                }
            }
            if(figure.getColor().equals("black")){
                if( coordinates.x <= 4 && board[coordinates.x + 1][coordinates.y] == null && board[coordinates.x + 2][coordinates.y] == null &&
                        board[coordinates.x+3][ coordinates.y] != null && board[coordinates.x + 3][coordinates.y].getFileName().equals("black_rook")){
                    possibleMoves.add(new Point(coordinates.x + 2, coordinates.y));
                }
                if( coordinates.x >= 4 && board[coordinates.x - 1][coordinates.y] == null && board[coordinates.x - 2][coordinates.y] == null &&
                        board[coordinates.x - 3][coordinates.y] == null && board[coordinates.x - 4][ coordinates.y] != null && board[coordinates.x - 4][coordinates.y].getFileName().equals("black_rook")){
                    possibleMoves.add(new Point(coordinates.x - 2, coordinates.y));
                }
            }
        }
    }


    private static void checkMovementAllowedPositions(Figure[][] board, Point coordinates,  ArrayList<Point> possibleMoves, King figure,  ArrayList<Point> enemyPossibleMoves ){
        // check movement allowedPos
        for(Point possibleResult : new ArrayList<>(possibleMoves)){
            if(board[possibleResult.x][possibleResult.y] != null){
                Figure tmp = board[possibleResult.x][possibleResult.y];
                board[possibleResult.x][possibleResult.y] = figure;
                board[coordinates.x][coordinates.y] = null;
                for(int j = 0 ; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        enemyPossibleMoves = null;
                        enemyPossibleMoves = new ArrayList<>();
                        enemyCoordinates.x = j;
                        enemyCoordinates.y = k;
                        if (board[j][k] != null && board[j][k].getColor().equals(figure.getEnemyColor())) {
                            if (board[j][k].getFileName().equals("black_king") || board[j][k].getFileName().equals("white_king")){
                                board[possibleResult.x][possibleResult.y] = null;
                                basicKingMoves(board, enemyCoordinates, enemyPossibleMoves, board[j][k]);
                                board[possibleResult.x][possibleResult.y] = figure;
                            }
                            else{
                                enemyPossibleMoves = board[j][k].getPossibleMoves(board, enemyCoordinates);
                                if (board[j][k].getFirstMove()) {
                                    if (board[j][k].getFileName().equals("black_peon")) {
                                        if ( k + 2 <= 7 && board[j][k + 2] == null && board[j][k + 1] == null && board[j][k].getFirstMove()){
                                            if(enemyPossibleMoves.isEmpty());
                                            else{
                                                int sizeMoves = enemyPossibleMoves.size();
                                                enemyPossibleMoves.remove(sizeMoves - 1);
                                                enemyPossibleMoves.remove(sizeMoves - 2);
                                            }
                                        }
                                        else if( k + 2 <= 7 && board[j][k+2] != null && board[j][k+1] == null){
                                            if(enemyPossibleMoves.isEmpty());
                                            else {
                                                int sizeMoves = enemyPossibleMoves.size();
                                                enemyPossibleMoves.remove(sizeMoves - 1);
                                            }
                                        }
                                    } else if (board[j][k].getFileName().equals("white_peon")) {
                                        if ( k - 2 >= 0 && board[j][k - 2] == null && board[j][k - 1] == null && board[j][k].getFirstMove()){
                                            if(enemyPossibleMoves.isEmpty());
                                            else{
                                                int sizeMoves = enemyPossibleMoves.size();
                                                enemyPossibleMoves.remove(sizeMoves - 1);
                                                enemyPossibleMoves.remove(sizeMoves - 2);
                                            }
                                        }
                                        else if ( k - 2 >= 0 &&  board[j][k-2] !=null && board[j][k-1] == null){
                                            if(enemyPossibleMoves.isEmpty());
                                            else {
                                                int sizeMoves = enemyPossibleMoves.size();
                                                enemyPossibleMoves.remove(sizeMoves - 1);
                                            }
                                        }

                                    }
                                }
                            }
                            for (Point epm : enemyPossibleMoves) {
                                for (Point PM : new ArrayList<>(possibleMoves)) {
                                    if (epm.x == PM.x && epm.y == PM.y) {
                                        possibleMoves.remove(PM);
                                    }
                                }
                            }
                        }
                    }
                }
                board[coordinates.x][coordinates.y] = figure;
                board[possibleResult.x][possibleResult.y] = tmp;
            }
            else{
                board[possibleResult.x][possibleResult.y] = figure;
                board[coordinates.x][coordinates.y] = null;
                for(int j = 0 ; j < 8; j++){
                    for(int k = 0 ; k < 8; k++){
                        enemyPossibleMoves = null;
                        enemyPossibleMoves = new ArrayList<>();
                        enemyCoordinates.x = j;
                        enemyCoordinates.y = k;
                        if(board[j][k] != null && board[j][k].getColor().equals(figure.getEnemyColor())){
                            if(board[j][k].getFileName().equals("black_king") || board[j][k].getFileName().equals("white_king")) continue;
                            enemyPossibleMoves = board[j][k].getPossibleMoves(board, enemyCoordinates);
                            if (board[j][k].getFirstMove()){
                                if(board[j][k].getFileName().equals("black_peon")){
                                    if( k + 2 <= 7 && board[j][k + 2] == null && board[j][k + 1] == null && board[j][k].getFirstMove()){
                                        if(enemyPossibleMoves.isEmpty());
                                        else {
                                            int sizeEnemy = enemyPossibleMoves.size();
                                            enemyPossibleMoves.remove(sizeEnemy - 1);
                                            enemyPossibleMoves.remove(sizeEnemy - 2);
                                        }
                                    }
                                    else if( k + 2 <= 7 && board[j][k + 1] == null && board[j][k+2] != null){
                                        int sizeEnemy = enemyPossibleMoves.size();
                                        enemyPossibleMoves.remove(sizeEnemy - 1);
                                    }
                                }
                                else if(board[j][k].getFileName().equals("white_peon")){
                                    if( k-2 >= 0 && board[j][k - 2] == null && board[j][k-1] == null && board[j][k].getFirstMove()){
                                        if(enemyPossibleMoves.isEmpty());
                                        else{
                                            //bug prediction...... counting always k-2 even though I already dont have first move on TRUE
                                            int sizeEnemy = enemyPossibleMoves.size();
                                            enemyPossibleMoves.remove(sizeEnemy - 1);
                                            enemyPossibleMoves.remove( sizeEnemy - 2);
                                        }
                                    }
                                    else if( k - 2 >= 0 && board[j][k-1] == null && board[j][k-2] != null){
                                        int sizeEnemy = enemyPossibleMoves.size();
                                        enemyPossibleMoves.remove(sizeEnemy - 1);
                                    }
                                }
                            }
                            for(Point epm: enemyPossibleMoves){
                                for(Point PM : new ArrayList<>(possibleMoves)){
                                    if(epm.x == PM.x && epm.y == PM.y){
                                        possibleMoves.remove(PM);
                                    }
                                }
                            }
                        }
                    }
                }
                board[coordinates.x][coordinates.y] = figure;
                board[possibleResult.x][possibleResult.y] = null;
            }
        }
    }

    public static void isMyKingChecked(Figure[][] board, Point coordinatesClickedFigure,  ArrayList<Point> possibleMoves, Figure figure){

        if(figure.getFileName().equals("white_king") || figure.getFileName().equals("black_king")) return;

        Point kingTmpCoordiantes = new Point();
        // findMyKing
        for(int i = 0 ; i < 8; i++){
            for(int j = 0 ; j < 8; j++){
                if( board[i][j] != null && board[i][j].getColor().equals(figure.getColor())){
                    if(board[i][j].getFileName().equals("white_king") || board[i][j].getFileName().equals("black_king")){
                        kingTmpCoordiantes.x = i;
                        kingTmpCoordiantes.y = j;
                    }
                }
            }
        }

        Figure tmp = null;
        ArrayList<Point> enemyPossibleMoves = new ArrayList<>();
        Point enemyCoordinates = new Point();
        for(Point obj : new ArrayList<>(possibleMoves)){
            tmp = null;
            if(board[obj.x][obj.y] != null )tmp = board[obj.x][obj.y];
            board[obj.x][obj.y] = figure;
            board[coordinatesClickedFigure.x][coordinatesClickedFigure.y] = null;

            //pokud je king ohrozeny
            for(int i = 0 ; i < 8; i++){
                for(int j = 0 ; j < 8; j++){
                    if(board[i][j] != null && board[i][j].getColor().equals(figure.getEnemyColor())){
                        enemyPossibleMoves = null;
                        enemyPossibleMoves = new ArrayList<>();
                        enemyCoordinates.x = i;
                        enemyCoordinates.y = j;
                        enemyPossibleMoves = board[i][j].getPossibleMoves(board, enemyCoordinates);
                        for(Point obj2 : enemyPossibleMoves){
                           if(obj2.x == kingTmpCoordiantes.x && obj2.y == kingTmpCoordiantes.y){
                               possibleMoves.remove(obj);
                           }
                        }
                    }
                }
            }
            board[obj.x][obj.y] = tmp;
            board[coordinatesClickedFigure.x][coordinatesClickedFigure.y] = figure;
        }
    }
}