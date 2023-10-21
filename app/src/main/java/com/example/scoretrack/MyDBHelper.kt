package com.example.registerme

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper (context:Context) : SQLiteOpenHelper (context, "USERDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, EMAIL TEXT, PWD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun loginCheck(mail:String, password:String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT  *  FROM USERS WHERE EMAIL = '"+ mail +"' AND PWD = '"+ password +"'", null)
    }

    @SuppressLint("Range")
    fun getAllUsers(): ArrayList<String>{
        val db: SQLiteDatabase = this.readableDatabase
        val cursor : Cursor?
        val arrayList = ArrayList<String>()
        cursor = db.rawQuery(" SELECT * FROM USERS", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            arrayList.add(
                cursor.getString(cursor.getColumnIndex("USERID")) + " - "+
                        cursor.getString(cursor.getColumnIndex("NAME")) + " "+
                        cursor.getString(cursor.getColumnIndex("SURNAME")));
            cursor.moveToNext();
        }
        return arrayList
    }
}