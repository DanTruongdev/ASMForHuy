package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.database.entity.Hike;
import com.example.myapplication.database.entity.Observation;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public MyDatabaseHelper(Context context){
        super(context,"HikeManagementDatabase",null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        sqLiteDatabase.execSQL(Hike.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(Observation.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Hike.TABLE_NAME) ;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Observation.TABLE_NAME) ;
        onCreate(sqLiteDatabase);
    }

    //Methods for Hike entity
    public Cursor getAllHike(){
        String query = "SELECT * FROM " + Hike.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public long addNewHike(String hikeName, String hikeLocation, String dateOfHike, boolean parkingAvailable,
                        float lengthOfHike, String difficultLevel, int hikerNumber, String typeOfHike, String hikeDescription){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Hike.COLUMN_NAME, hikeName);
        values.put(Hike.COLUMN_LOCATION, hikeLocation);
        values.put(Hike.COLUMN_HIKE_DATE, dateOfHike);
        values.put(Hike.COLUMN_PARKING_AVAILABLE, parkingAvailable);
        values.put(Hike.COLUMN_HIKE_LENGTH, lengthOfHike);
        values.put(Hike.COLUMN_DIFFICULT_LEVEL, difficultLevel);
        values.put(Hike.COLUMN_HIKER_NUMBER, ""+hikerNumber);
        values.put(Hike.COLUMN_HIKE_TYPE, typeOfHike);
        values.put(Hike.COLUMN_HIKE_DESCRIPTION, hikeDescription);

        long result = sqLiteDatabase.insert(Hike.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return result;

    }

    public long updateHike(String hikeId, String hikeName, String hikeLocation, String dateOfHike, boolean parkingAvailable,
                           float lengthOfHike, String difficultLevel, int hikerNumber, String typeOfHike, String hikeDescription){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Hike.COLUMN_NAME, hikeName);
        values.put(Hike.COLUMN_LOCATION, hikeLocation);
        values.put(Hike.COLUMN_HIKE_DATE, dateOfHike);
        values.put(Hike.COLUMN_PARKING_AVAILABLE, parkingAvailable);
        values.put(Hike.COLUMN_HIKE_LENGTH, lengthOfHike);
        values.put(Hike.COLUMN_DIFFICULT_LEVEL, difficultLevel);
        values.put(Hike.COLUMN_HIKER_NUMBER, ""+hikerNumber);
        values.put(Hike.COLUMN_HIKE_TYPE, typeOfHike);
        values.put(Hike.COLUMN_HIKE_DESCRIPTION, hikeDescription);

        long result = sqLiteDatabase.update(Hike.TABLE_NAME, values, Hike.COLUMN_ID+"=?", new String[]{hikeId});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteOneHike(String hikeId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(Hike.TABLE_NAME, Hike.COLUMN_ID+"=?", new String[]{hikeId});
        return result;
    }

    public  void deleteAllHike(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + Hike.TABLE_NAME);
    }

    public Cursor searchHike(String keyWord){
        String query = "SELECT * FROM " + Hike.TABLE_NAME+ " WHERE "
                + Hike.COLUMN_NAME + " LIKE '%" + keyWord + "%'"
                + " OR " + Hike.COLUMN_LOCATION + " LIKE '%" + keyWord + "%'"
                + " OR " + Hike.COLUMN_HIKE_LENGTH + " LIKE '%" + keyWord + "%'"
                + " OR " + Hike.COLUMN_HIKE_DATE + " LIKE '%" + keyWord + "%'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor sortHike(int order){
        String query = "";
        if (order == 0) query = "SELECT * FROM " + Hike.TABLE_NAME+ " ORDER BY " + Hike.COLUMN_NAME + " ASC";
        else if (order == 1) query = "SELECT * FROM " + Hike.TABLE_NAME+ " ORDER BY " + Hike.COLUMN_NAME + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    //Methods for Hike entity
    public Cursor getAllObservation(int observatioId){
        String query = "SELECT * FROM " + Observation.TABLE_NAME+ " WHERE " + Observation.COLUMN_HIKE_ID + " = " + observatioId;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }


    public long addNewObservation(String observationName, int hikeId, byte[] observationImage, String timeOfObservation, String observationComment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Observation.COLUMN_NAME, observationName);
        values.put(Observation.COLUMN_HIKE_ID, hikeId);
        values.put(Observation.COLUMN_IMAGE, observationImage);
        values.put(Observation.COLUMN_TIME, timeOfObservation);
        values.put(Observation.COLUMN_COMMENT, observationComment);
        long result = sqLiteDatabase.insert(Observation.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return result;
    }

    public long updateObservation(String observationId, String observationName, int hikeId, byte[] observationImage, String timeOfObservation, String observationComment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Observation.COLUMN_NAME, observationName);
        values.put(Observation.COLUMN_HIKE_ID, hikeId);
        values.put(Observation.COLUMN_IMAGE, observationImage);
        values.put(Observation.COLUMN_TIME, timeOfObservation);
        values.put(Observation.COLUMN_COMMENT, observationComment);
        long result = sqLiteDatabase.update(Observation.TABLE_NAME, values, Observation.COLUMN_ID+"=?", new String[]{observationId});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteOneObservation(String observationId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(Observation.TABLE_NAME, Observation.COLUMN_ID+"=?", new String[]{observationId});
        return result;
    }

    public  void deleteAllObservation(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + Observation.TABLE_NAME);
    }

    public Cursor searchObservation(String keyWord, int hikeId){
        String query = "SELECT * FROM " + Observation.TABLE_NAME+ " WHERE "
                + Observation.COLUMN_HIKE_ID + " = " + hikeId
                + " AND " + Observation.COLUMN_NAME + " LIKE '%" + keyWord + "%'"
                + " OR " + Observation.COLUMN_TIME + " LIKE '%" + keyWord + "%'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor sortObservation(int order, int hikeId){
        String query = "";
        if (order == 0) query = "SELECT * FROM " + Observation.TABLE_NAME+ " WHERE " + Observation.COLUMN_HIKE_ID + " = " + hikeId + " ORDER BY " + Observation.COLUMN_NAME + " ASC";
        else if (order == 1) query = "SELECT * FROM " + Observation.TABLE_NAME+ " WHERE " + Observation.COLUMN_HIKE_ID + " = " + hikeId + " ORDER BY " + Observation.COLUMN_NAME+ " DESC";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }


}
