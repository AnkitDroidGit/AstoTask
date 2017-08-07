package com.ankit.astroankit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ankit.astroankit.models.ChannelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase AnkitDB = null;
    private Context ctx;
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AnkitLocalDB";

    // Table Names
    private static final String TABLE_FAVOURITE = "favourite";
    private static final String TABLE_ALL_CHANNELS = "all_channels";

    // Routes Table - column names
    private static final String KEY_CHANNEL_NAME = "channel_name";
    private static final String KEY_CHANNEL_NUMBER = "channel_number";
    private static final String KEY_CHANNEL_ID = "channel_id";

    // Table Create Statements
    // route table create statement
    private static final String CREATE_QUERY_FAVOURITE_TABLE = String.format(
            "create table " +
                    "%s (" +
                    "%s integer not null primary key," +
                    "%s text," +
                    "%s integer" +
                    ")",
            TABLE_FAVOURITE,
            KEY_CHANNEL_ID,
            KEY_CHANNEL_NAME,
            KEY_CHANNEL_NUMBER
    );
    private static final String CREATE_QUERY_ALL_CHANNELS_TABLE = String.format(
            "create table " +
                    "%s (" +
                    "%s integer not null primary key," +
                    "%s text," +
                    "%s integer" +
                    ")",
            TABLE_ALL_CHANNELS,
            KEY_CHANNEL_ID,
            KEY_CHANNEL_NAME,
            KEY_CHANNEL_NUMBER
    );


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
        AnkitDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_QUERY_FAVOURITE_TABLE);
        db.execSQL(CREATE_QUERY_ALL_CHANNELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public List<Integer> getFavouriteChannelIds() {
        Cursor cursor = null;
        List<Integer> IdList = new ArrayList<>();
        try {
            cursor = AnkitDB.query(true, TABLE_FAVOURITE, new String[]{"*"}, null, null, null, null, KEY_CHANNEL_ID + " ASC", null);
            while (cursor.moveToNext()) {
                Integer integer = cursor.getInt(cursor.getColumnIndex(KEY_CHANNEL_ID));
                IdList.add(integer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return IdList;
    }

    public List<ChannelModel> getFavouriteChannelModels() {
        Cursor cursor = null;
        List<ChannelModel> channelModelList = new ArrayList<>();
        try {
            cursor = AnkitDB.query(true, TABLE_FAVOURITE, new String[]{"*"}, null, null, null, null, KEY_CHANNEL_ID + " ASC", null);
            while (cursor.moveToNext()) {
                ChannelModel channelModel = new ChannelModel();
                channelModel.setChannelId(cursor.getInt(cursor.getColumnIndex(KEY_CHANNEL_ID)));
                channelModel.setChannelNumber(cursor.getInt(cursor.getColumnIndex(KEY_CHANNEL_NUMBER)));
                channelModel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_CHANNEL_NAME)));
                channelModelList.add(channelModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return channelModelList;
    }

    public void saveFavouriteId(ChannelModel id) {
        ContentValues values = new ContentValues();
        values.put(KEY_CHANNEL_ID, id.getChannelId());
        values.put(KEY_CHANNEL_NAME, id.getTitle());
        values.put(KEY_CHANNEL_NUMBER, id.getChannelNumber());
        AnkitDB.insertWithOnConflict(TABLE_FAVOURITE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void removeFavouriteId(Integer id) {
        ContentValues values = new ContentValues();
        values.put(KEY_CHANNEL_ID, id);
        AnkitDB.delete(TABLE_FAVOURITE, KEY_CHANNEL_ID + "=" + id, null);
//        AnkitDB.insertWithOnConflict(TABLE_FAVOURITE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveAllChannels(List<ChannelModel> channelList) {
        for (ChannelModel channelModel : channelList) {
            ContentValues values = new ContentValues();
            values.put(KEY_CHANNEL_ID, channelModel.getChannelId());
            values.put(KEY_CHANNEL_NAME, channelModel.getTitle());
            values.put(KEY_CHANNEL_NUMBER, channelModel.getChannelNumber());
            AnkitDB.insertWithOnConflict(TABLE_ALL_CHANNELS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public List<ChannelModel> get10Channels(int limit) {
        String query = "SELECT * FROM " + TABLE_ALL_CHANNELS + " limit " + (10 * limit) + ", " + (10 * (limit + 1));
        Cursor cursor = null;
        List<ChannelModel> channelModelList = new ArrayList<>();
        try {
            cursor = AnkitDB.rawQuery(query, null);
            //cursor = AnkitDB.query(true, TABLE_FAVOURITE, new String[]{"*"}, null, null, null, null, KEY_CHANNEL_ID + " ASC", null);
            while (cursor.moveToNext()) {
                ChannelModel channelModel = new ChannelModel();
                channelModel.setChannelId(cursor.getInt(cursor.getColumnIndex(KEY_CHANNEL_ID)));
                channelModel.setChannelNumber(cursor.getInt(cursor.getColumnIndex(KEY_CHANNEL_NUMBER)));
                channelModel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_CHANNEL_NAME)));
                channelModelList.add(channelModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return channelModelList;
    }
}
