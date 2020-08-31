package com.gmail.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gmail.babyneeds.model.Needs;
import com.gmail.babyneeds.util.GlobalVars;

public class NeedsDatabaseHandler extends SQLiteOpenHelper {

    Context context;

    public NeedsDatabaseHandler(@Nullable Context context) {
        super(context, GlobalVars.TABLE_NAME, null, GlobalVars.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + GlobalVars.TABLE_NAME + "(" + GlobalVars.COLUMN_1 + " INTEGER PRIMARY KEY, " + GlobalVars.COLUMN_2 + " TEXT, " + GlobalVars.COLUMN_3 + " INTEGER, " + GlobalVars.COLUMN_4 + " TEXT, " +
                GlobalVars.COLUMN_5 + " INTEGER, " + GlobalVars.COLUMN_6 + " BIGINT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + GlobalVars.TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addNeeds(Needs needs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GlobalVars.COLUMN_2, needs.getName());
        values.put(GlobalVars.COLUMN_3, needs.getQuantity());
        values.put(GlobalVars.COLUMN_4, needs.getColor());
        values.put(GlobalVars.COLUMN_5, needs.getSize());
        values.put(GlobalVars.COLUMN_6, System.currentTimeMillis());

        long id = db.insert(GlobalVars.TABLE_NAME, null, values);
        needs.setId((int)id);

        db.close();
    }

    public Needs getNeeds(int i)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Needs needs = null;
        String GET_VALUES = "SELECT * FROM " + GlobalVars.TABLE_NAME + " WHERE " + GlobalVars.COLUMN_1 + " = " + i;
        Cursor cursor = db.rawQuery(GET_VALUES, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            needs = new Needs(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
            DateFormat dateFormat = DateFormat.getDateInstance();
            String dateFormatted = dateFormat.format(new Date(cursor.getLong(5)).getTime());
            needs.setDate(dateFormatted);
        }
        db.close();
        return needs;
    }

    public List<Needs> getAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Needs> needsList = new ArrayList<>();
        String GET_VALUES = "SELECT * FROM " + GlobalVars.TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_VALUES, null);
        if(cursor.moveToFirst()) {
             do{
                Needs needs = new Needs(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String dateFormatted = dateFormat.format(new Date(cursor.getLong(5)).getTime());
                needs.setDate(dateFormatted);
                needsList.add(0, needs);
            }while (cursor.moveToNext());
        }
        db.close();
        return needsList;
    }

    public void deleteNeeds(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GlobalVars.TABLE_NAME, GlobalVars.COLUMN_1 + " = " + id ,null);
        db.close();
    }

    public int getLenght()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int i;
        String GET_VALUES = "SELECT * FROM " + GlobalVars.TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_VALUES, null);
        i = cursor.getCount();
        db.close();
        return i;
    }

    public int updateNeeds(Needs needs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GlobalVars.COLUMN_2, needs.getName());
        values.put(GlobalVars.COLUMN_3, needs.getQuantity());
        values.put(GlobalVars.COLUMN_4, needs.getSize());
        values.put(GlobalVars.COLUMN_5, needs.getDate());

        return db.update(GlobalVars.TABLE_NAME, values,GlobalVars.COLUMN_1 + "=" + needs.getId(), null);
    }
}
