package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    //Deklarasi variabel
    EditText user;
    EditText pass;
    ProgressDialog mProgressDialog;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inisialisasi / Reference
        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPassword);
        mProgressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Intent move = new Intent(LoginActivity.this, HomeActivity.class);
                    move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(move);
                    finish();
                }
            }
        };
    }

    //Method untuk menghentikan state
    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    //Method untuk memulai menjalankan aplikasi
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    //Method untuk aksi tombol masuk (login)
    public void masuk(View view) {
        mProgressDialog.setMessage("Login");

        //get data inputan
        String inuser = user.getText().toString();
        String inpass = pass.getText().toString();


        //cek konidsi inputan apakah ada atau tidak
        if (!TextUtils.isEmpty(inuser) || !TextUtils.isEmpty(inpass)) {
            mProgressDialog.show();

            //Login sesuai dengan username dan password user
            auth.signInWithEmailAndPassword(inuser, inpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Ketika login berhasil
                    if (task.isSuccessful()) {
                        Intent move = new Intent(LoginActivity.this, HomeActivity.class);
                        move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(move);
                        finish();

                        //Ketika login gagal
                    } else {
                        Toast.makeText(LoginActivity.this, "Gagal Login, Periksa username dan password anda!", Toast.LENGTH_SHORT).show();
                    }

                    //Tutup dialog ketika login berhasil atau gagal
                    mProgressDialog.dismiss();
                }
            });

            //Kalau inputan kosong
        } else {
            Snackbar.make(findViewById(R.id.rootlogin), "Data yang anda masukkan kosong!", Snackbar.LENGTH_LONG).show();
        }
    }

    //Method ketika user mendaftar
    public void daftar(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}