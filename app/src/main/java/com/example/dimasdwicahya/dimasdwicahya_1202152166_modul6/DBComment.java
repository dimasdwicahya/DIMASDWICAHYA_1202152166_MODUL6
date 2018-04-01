package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

/**
 * Created by asus a456 on 01/04/2018.
 */



public class DBComment {
    //Deklarasi
    String mPengomentar,mKomentar, mFoto ;

    public DBComment(){
    }

    //Constructor dari class ini
    public DBComment(String pengomentar, String komentar, String foto) {
        this.mPengomentar = pengomentar;
        this.mKomentar = komentar;
        this.mFoto = foto;
    }

    //Sisany getter untuk variabel dari class ini


    public String getmPengomentar() {
        return mPengomentar;
    }

    public String getmKomentar() {
        return mKomentar;
    }

    public String getmFoto() {
        return mFoto;
    }
}