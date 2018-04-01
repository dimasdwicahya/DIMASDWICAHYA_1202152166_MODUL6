package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by asus a456 on 01/04/2018.
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    Context con;
    List<DBComment> list;

    //Constructor untuk adapter
    public CommentAdapter(Context con, List<DBComment> list) {
        this.con = con;
        this.list = list;
    }

    //Return ViewHolder dari Recyclerview
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(con).inflate(R.layout.rec_comment, parent, false));
    }

    //Mengikat nilai dari list dengan view
    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        DBComment cur = list.get(position);
        holder.pengomentar.setText(cur.getmPengomentar());
        holder.komentar.setText(cur.getmKomentar());

    }

    //Mendapatkan jumlah item pada recyclerview
    @Override
    public int getItemCount() {
        return list.size();
    }

    //Subclass sebagai viewholder
    class CommentHolder extends RecyclerView.ViewHolder {
        TextView pengomentar, komentar;

        public CommentHolder(View itemView) {
            super(itemView);
            pengomentar = itemView.findViewById(R.id.yangkomen);
            komentar = itemView.findViewById(R.id.komennya);
        }
    }
}