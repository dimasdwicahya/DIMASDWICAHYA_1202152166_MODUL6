package com.example.dimasdwicahya.dimasdwicahya_1202152166_modul6;

/**
 * Created by asus a456 on 01/04/2018.
 */


//Enkapsulasi data post
public class DBPost {
    String image, title, caption, user, key;

    //Dibutuhkan kosong untuk membaca data
    public DBPost() {
    }

    //Constructor dari class ini
    public DBPost(String caption, String image, String title, String user) {
        this.image = image;
        this.title = title;
        this.caption = caption;
        this.user = user;
    }

    //Mendapatkan key dari Firebase
    public String getKey() {
        return key;
    }

    //Menentukan key dari Firebase
    public void setKey(String key) {
        this.key = key;
    }

    //Sisanya getter variabel dari class ini
    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
    }

    public String getUser() {
        return user;
    }

}