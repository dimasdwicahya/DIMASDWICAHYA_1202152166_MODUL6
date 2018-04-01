package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class HomeAll extends Fragment {
    //Deklarasi Variabel

    RecyclerView rc;
    DatabaseReference dataref;
    ArrayList<DBPost> list;
    com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6.PostAdapter adapterPost;

    public HomeAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inisialisasi
        View v = inflater.inflate(R.layout.activity_homeall, container, false);
        rc = v.findViewById(R.id.rchomeall);
        list = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
        adapterPost = new PostAdapter(list, this.getContext());

        //Menampilkan recyclerview
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rc.setAdapter(adapterPost);

        //Digunakan untuk melihat perubahan data pada firebase
        dataref.addValueEventListener(new ValueEventListener() {

            //Read semua data yang dipost ke firebase
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DBPost post = data.getValue(DBPost.class);
                    post.key = data.getKey();
                    list.add(post);
                    adapterPost.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}
