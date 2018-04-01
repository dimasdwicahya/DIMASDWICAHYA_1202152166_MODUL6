package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by asus a456 on 01/04/2018.
 */

public class HomeUser extends Fragment {
    //Deklarasi Variabel
    DatabaseReference ref;
    PostAdapter adapter;
    ArrayList<DBPost> list;
    RecyclerView rc;

    public HomeUser() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inisialisasi
        View v = inflater.inflate(R.layout.activity_homeuser, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("post");
        list = new ArrayList<>();
        adapter = new PostAdapter(list, this.getContext());
        rc = v.findViewById(R.id.rvhomeuser);

        //Menampilkan recyclerview
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rc.setAdapter(adapter);

        //Event listener ketika data pada Firebase berubah
        ref.addValueEventListener(new ValueEventListener() {

            //utuk membaca data yg di post ke firebase
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DBPost cur = data.getValue(DBPost.class);
                    if (cur.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        cur.setKey(data.getKey());
                        list.add(cur);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
}
