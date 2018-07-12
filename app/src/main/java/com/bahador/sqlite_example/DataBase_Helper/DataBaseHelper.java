package com.bahador.sqlite_example.DataBase_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* Programmer Advice:
    Working on database is very sensitive because you are filling your phone database.
    If you see an exception while running your program, it's better to delete your app and install again.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Information.db";
    private static final String TABLE_NAME = "PersonInfo_table";

    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "SURNAME";
    private static final String COL_4 = "PHONE";

    /**
     * @param context get your main context to use information
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, PHONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    // For inserting data to our database we must add function below

    public boolean insertData(String name, String surname, String phone) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // we don't use column number 1 because i enabled AUTOINCREMENT before.
        values.put(COL_2, name);
        values.put(COL_3, surname);
        values.put(COL_4, phone);
        long results = database.insert(TABLE_NAME, null, values);
        return results != -1; //checking data if inserted in Database, it returns true otherwise false
    }

    // For getting all of our data from database, we must write function below
    public Cursor getAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData(String id, String name, String surname, String phone) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, name);
        values.put(COL_3, surname);
        values.put(COL_4, phone);
        int results = database.update(TABLE_NAME, values, "ID = ?", new String[]{id});
        return results > 0; // it returns true otherwise false
    }

    public Integer deleteData(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}