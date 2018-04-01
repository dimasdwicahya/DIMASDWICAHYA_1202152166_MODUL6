package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by asus a456 on 01/04/2018.
 */

public class SignupActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    ProgressDialog mProgressDialog;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //referensi
        user = findViewById(R.id.upuser);
        pass = findViewById(R.id.uppass);
        mProgressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Intent pindah = new Intent(SignupActivity.this, HomeActivity.class);
                    pindah.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(pindah);
                }
            }
        };

    }

    //Method untuk activity ketika dimulai
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    //Method ketika activity berakhir
    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    //Method untuk membuat akun
    public void aksidaftar(View view) {
        //menampilkan pesan
        mProgressDialog.setMessage("Membuat akun baru ...");
        mProgressDialog.show();

        //Membaca inputan dari kolom inputan
        String inuser = user.getText().toString().trim();
        String inpass = pass.getText().toString().trim();

        //Apakah input user tersebut kosong?

        //Jika tidak :
        if (!TextUtils.isEmpty(inuser) || !TextUtils.isEmpty(inpass)) {
            //Membuat user baru berdasarkan input user
            auth.createUserWithEmailAndPassword(inuser, inpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Kondisi jika pembuatan user berhasil
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Akun sudah dibuat sebelumnya!!", Toast.LENGTH_SHORT).show();
                        Intent movehome = new Intent(SignupActivity.this, LoginActivity.class);
                        movehome.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        finish();

                        //Kondisi ketika pembuatan user baru gagal
                    } else {
                        Log.w("Firebase", task.getException());
                        Toast.makeText(SignupActivity.this, "Gagal membuat akun baru!", Toast.LENGTH_SHORT).show();
                        user.setText(null);
                        pass.setText(null);
                    }
                    //Tutup dialog ketika kondisi berhasil atau pun tidak
                    mProgressDialog.dismiss();
                }
            });

            //Ketika input user kosong
        } else {
            Toast.makeText(this, "Data inputan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            user.setText(null);
            pass.setText(null);
        }

    }

}