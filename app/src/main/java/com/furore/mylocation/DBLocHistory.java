package com.furore.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by diksha on 22/4/15.
 */
public class DBLocHistory {

    public static final String KEY_LATITIDE = "lattitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LOC_ADDRESS = "address";
  //  public static final String KEY_LOC_NAME = "loc name";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";


    private static final String DATABASE_NAME = "LocationHistory";
    public static final String DATABASE_TABLE = "LocationDetails";
    private static final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    public SQLiteDatabase ourDatabase;


    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DBLocHistory.DATABASE_NAME, null,
                    DBLocHistory.DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL("CREATE TABLE " + DBLocHistory.DATABASE_TABLE + " ("
                    + DBLocHistory.KEY_LATITIDE + " TEXT NOT NULL, "
                    + DBLocHistory.KEY_LONGITUDE + " TEXT NOT NULL, "
                    + DBLocHistory.KEY_LOC_ADDRESS + " TEXT NOT NULL, "
              //      + DBLocHistory.KEY_LOC_NAME + " TEXT NOT NULL, "
                    + DBLocHistory.KEY_DATE + " TEXT NOT NULL, "
                    + DBLocHistory.KEY_TIME + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DBLocHistory(Context c) {
        ourContext = c;
    }

    public DBLocHistory open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String latitude,String longitude,String address,String date,String time) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(KEY_LATITIDE, latitude);
        cv.put(KEY_LONGITUDE, longitude);
      //  cv.put(KEY_LOC_NAME,locName);
        cv.put(KEY_LOC_ADDRESS, address);
        cv.put(KEY_DATE, date);
        cv.put(KEY_TIME,time);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public ArrayList<LocPojo> getGroups() {

        ArrayList<LocPojo> result = new ArrayList<>();
        String latitude,longitude,address,locName,date,time;

        Cursor mCursor = ourDatabase.rawQuery("SELECT * FROM "
                + DBLocHistory.DATABASE_TABLE, null);

        if (mCursor.moveToFirst()) {

            do {
                latitude = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LATITIDE));
                longitude = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LONGITUDE));
                address = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LOC_ADDRESS));
            /*    locName = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LOC_NAME));*/
                date = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_DATE));
                time = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_TIME));



                result.add(0, new LocPojo(latitude,longitude,address,date,time));

            } while (mCursor.moveToNext());
        }

        return result;
    }

    LocPojo getLastLoc(){

        LocPojo lp=null;

        String latitude,longitude,address,date,time;

        Cursor mCursor = ourDatabase.rawQuery("SELECT * FROM "
                + DBLocHistory.DATABASE_TABLE, null);

        if (mCursor.moveToFirst()) {

            do {
                latitude = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LATITIDE));
                longitude = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LONGITUDE));
                address = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_LOC_ADDRESS));
                date = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_DATE));
                time = mCursor.getString(mCursor
                        .getColumnIndex(DBLocHistory.KEY_TIME));



                lp=(new LocPojo(latitude,longitude,address,date,time));

            } while (mCursor.moveToNext());
        }

        return lp;
    }
}

