package com.example.chandraanshugarg.maintainus;

/**
 * Created by akankshitadash on 10/7/16.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteClosable;

public class myDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_Version= 1;
    private static final String DATABASE_Name= "info.db";
    private static final String TABLE_Name= "Info";
    private static final String COLUMN_ID= "_id";
    private static final String COLUMN_CAUSE= "_cause";
    private static final String COLUMN_DESC="_description";

    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_Name, factory, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_Name + "("
                    + COLUMN_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CAUSE + " TEXT, "
                    + COLUMN_DESC  + " TEXT"
                    + ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_Name );
        onCreate(db);

    }

    // Add a new row to the database

    public void addCauseDesc(CauseDescription causeDescription) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAUSE, causeDescription.get_cause());
        values.put(COLUMN_DESC, causeDescription.get_description());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_Name,null,values);
        db.close();
    }

    //Delete a row from database

  //  public void deleteCause(String causeName) {
    //    SQLiteDatabase db = getWritableDatabase();
      //  db.execSQL("DELETE FROM " + TABLE_Name + " WHERE "+ COLUMN_CAUSE + "=\"" + causeName + "\";");
    //}

    //Print out causes as a string

    public String databasetoString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_Name + " WHERE 1";

        Cursor c = db.rawQuery( query, null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("_cause"))!=null) {
                dbString += c.getString(c.getColumnIndex("_cause"));
                dbString += "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }
}
