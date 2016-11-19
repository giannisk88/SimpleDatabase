package com.example.simpledatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by giannis-hp on 16/11/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    //the name of the database
    public static final String DATABASE_NAME = "Student.db";
    //the name of the table
    public static final String TABLE_NAME = "student_table";
    //columns
    public static final String col_1 = "ID";
    public static final String col_2 = "NAME";
    public static final String col_3 = "SURNAME";
    public static final String col_4 = "MARKS";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //whenever the oncreate is called i will create a table with a query
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if it exists the same database i will delete it and i will create a new one
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String marks){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,name);
        contentValues.put(col_3,surname);
        contentValues.put(col_4,marks);
        //if the data doesn't inserted to the table then the insert method returns -1
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == 1)
            return false;
            else
            return true;
    }
    //here i am going to show all the records(raws) of the table
    public Cursor getAllData(){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public boolean updateData(String id, String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_2,name);
        contentValues.put(col_3,surname);
        contentValues.put(col_4,marks);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[] {id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }


}
