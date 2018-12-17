package com.example.janpy.chess.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.janpy.chess.Database.RoomDatabaseHandler;
import com.example.janpy.chess.Game;
import com.example.janpy.chess.LouncherActivity;
import com.example.janpy.chess.MainActivity;
import com.example.janpy.chess.R;
import com.example.janpy.chess.entity.ChessBoard;
import com.example.janpy.chess.entity.Move;
import com.example.janpy.chess.entity.Player;
import com.example.janpy.chess.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
    private List<Room> myRooms;
    private Context context;
    public ViewAdapter(List<Room> rooms){
        myRooms = rooms;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chessroom, parent, false);
        context = parent.getContext();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id = v.findViewById(R.id.textViewId);
                String stringId = id.getText().toString();

                for(Room r : myRooms){

                    if(r.getID().equals(stringId)){
                        //**********************************************

                        ArrayList<Move> moves = new ArrayList<>();
                        moves = RoomDatabaseHandler.getMoves(r);
                       // moves  = RoomDatabaseHandler.moves;

                        Log.d("Trida", "helloVIEWADAOTER");

                        for(Move move : moves){
                            Log.d("Trida", " move X : "  + move.getMoveFrom().x + "  " + move.getMoveFrom().y);
                        }

                        r.setMoves(moves);

                        Player.color = "black";
                        if(moves.size() % 2 == 1){
                            Player.movementAllowed = true;
                        }else{
                            Player.movementAllowed = false;
                        }

                        Player.color = "black";
                        Player.movementAllowed = false;

                        //********************************************
                        for( Room room : Player.createdRooms){
                            Log.d("Trida", room.getID());
                            Log.d("Trida", r.getID());

                            if(room.getID().equals(r.getID())){
                                Player.color = "white";
                                if(moves.size() % 2 == 0){
                                    Player.movementAllowed = true;
                                }else{
                                    Player.movementAllowed = false;
                                }
                            }
                        }
                        //control castle

                        Intent intent = new Intent(context, Game.class);
                        intent.putExtra("parcel", r);
                        context.startActivity(intent);
                        break;
                    }
                }
            }
        });
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.bind(myRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return myRooms.size();
    }

    //myViewHOlder jeden radek
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView roomId;
        public String idRoom;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView1);
            roomId = itemView.findViewById(R.id.textViewId);
        }

        public void bind(Room room){
            name.setText(room.getName());
            roomId.setText(room.getID());
            idRoom = room.getID();
        }
    }
}
