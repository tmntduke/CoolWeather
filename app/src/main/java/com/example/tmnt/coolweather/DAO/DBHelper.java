package com.example.tmnt.coolweather.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tmnt on 2016/2/1.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME = "My.db";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table T_Province(id integer primary key autoincrement ,provinceId varchar(25), provinceName varchar(25))";
        String sql1 = "create table T_City(id integer primary key autoincrement ,cityId varchar(25),cityName varchar(25),provinceId varchar(15))";
        String sql2 = "create table T_County(id integer primary key autoincrement ,countyId varchar(25),countyName varchar(25),cityId varchar(15),isSelect bool)";
        String sql3 = "create table T_Weather(id integer primary key autoincrement,countyId varchar(25), countyName varchar(15),weatherId varchar(25))";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
