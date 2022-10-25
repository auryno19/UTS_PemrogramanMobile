package com.example.aurynonagataadyatma.bookstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper{

    public static final String BUKU = "buku";
    public static final String ID = "id";
    public static final String JUDUL = "judul";
    public static final String PENGARANG = "pengarang";
    public static final String PENERBIT = "penerbit";
    public static final String TAHUN = "tahun";
    public static final String STOK = "stok";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public DataHelper(@Nullable Context context){
        super(context, "bookstorage", null, 1);
        //TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //TODO Auto-generated constructor stub
        String sql = "create table " + BUKU + " (" + ID + " integer primary key, " + JUDUL + " text , " + PENGARANG + " text , " + PENERBIT + " text , " + TAHUN + " text , " + STOK + " text)";
        String sqlUser = "create table " + USER + " (" + ID + " integer primary key, " + NAME + " text , " + USERNAME + " text , " + EMAIL + " text , " + PASSWORD + " text )";

//        Log.d("Data", "onCreate: "+sql);
        db.execSQL(sql);
        db.execSQL(sqlUser);
        sql = "INSERT INTO " + BUKU + " (" + ID + ", " + JUDUL + ", " + PENGARANG + ", " + PENERBIT + ", " + TAHUN + ", " + STOK + ") VALUES ('1', 'Teknologi Informasi', 'Ahmad Fatih', 'Cipung', '2022', '3');";
        sqlUser = "INSERT INTO " + USER + " (" + ID + ", " + NAME + ", " + USERNAME + ", " + EMAIL + ", " + PASSWORD + ") VALUES ('1', 'Auryno', 'admin', 'adamin@gmail.com', 'admin123');";
        db.execSQL(sql);
        db.execSQL(sqlUser);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
        //TODO Auto-generated constructor stub

    }

    public ArrayList<Buku> getAllData(){
        ArrayList<Buku> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BUKU , null);

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String judul = cursor.getString(1);
            String pengarang = cursor.getString(2);
            String penerbit = cursor.getString(3);
            String tahun = cursor.getString(4);
            String stok = cursor.getString(5);
            Buku buku = new Buku(id, judul, pengarang, penerbit, tahun, stok);

            arrayList.add(buku);
        }
        return arrayList;
    }

    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(NAME, user.name);

        //Put username in  @values
        values.put(USERNAME, user.username);

        //Put email in  @values
        values.put(EMAIL, user.email);

        //Put password in  @values
        values.put(PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(USER, null, values);
    }


    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER,// Selecting Table
                new String[]{ID, NAME, USERNAME, EMAIL,PASSWORD},//Selecting columns want to query
                USERNAME + "=?",
                new String[]{user.username},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isUserExist(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER,// Selecting Table
                new String[]{ID, NAME, USERNAME, EMAIL, PASSWORD},//Selecting columns want to query
                USERNAME + "=?",
                new String[]{username},//Where clause
                null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }
        return false;
    }
}

