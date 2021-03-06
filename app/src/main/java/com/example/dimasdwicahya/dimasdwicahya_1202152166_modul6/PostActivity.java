package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by asus a456 on 01/04/2018.
 */

public class PostActivity extends AppCompatActivity {
    TextView user, title, caption;
    ImageView image;
    EditText sourcekomentar;
    RecyclerView rc;
    CommentAdapter adapter;
    ArrayList<DBComment> list;
    DatabaseReference dref;
    ProgressDialog pd;
    String usernya, idfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user = findViewById(R.id.user);
        image = findViewById(R.id.gambardaripost);
        sourcekomentar = findViewById(R.id.sourcekomentar);
        pd = new ProgressDialog(this);
        title = findViewById(R.id.titlepostnya);
        caption = findViewById(R.id.deskripsigambarpost);
        dref = FirebaseDatabase.getInstance().getReference().child("Comment");
        rc = findViewById(R.id.rckomentar);
        list = new ArrayList<>();
        adapter = new CommentAdapter(this, list);

        rc.setHasFixedSize(true);
//        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setLayoutManager(new GridLayoutManager(this, 2));
        rc.setAdapter(adapter);


        String[] usersaatini = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@");
        usernya = usersaatini[0];
        idfoto = getIntent().getStringExtra("key");
        user.setText(getIntent().getStringExtra("user"));
        title.setText(getIntent().getStringExtra("title"));
        caption.setText(getIntent().getStringExtra("caption"));
        Glide.with(this).load(getIntent().getStringExtra("image")).into(image);

        //Event listener ketika data berubah di Firebase
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DBComment cur = dataSnapshot.getValue(DBComment.class);
                if (cur.getmFoto().equals(idfoto)) {
                    list.add(cur);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Method untuk mempost komentar
    public void postcomment(View view) {
        //Menampilkan dialog
        pd.setMessage("Memberikan komentar... ");
        pd.show();

        //Inisialisasi objek
        DBComment com = new DBComment(usernya, sourcekomentar.getText().toString(), idfoto);

        //Input data ke Firebase
        dref.push().setValue(com).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PostActivity.this, "Komentar berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    sourcekomentar.setText(null);
                } else {
                    Toast.makeText(PostActivity.this, "Gagal memberikan komentar", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });
    }
}