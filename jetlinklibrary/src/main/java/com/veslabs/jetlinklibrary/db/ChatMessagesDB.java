package com.veslabs.jetlinklibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.veslabs.jetlinklibrary.network.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burhan Aras on 10/31/2016.
 */
public class ChatMessagesDB extends SQLiteOpenHelper {
    private static final String TAG = ChatMessagesDB.class.getSimpleName();

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "EBebekMessagesDb";

    // Table Names
    private static final String TABLE_CHAT_MESSAGES = "chat_message";

    // CHAT MESSAGES Table - column names    private static final String KEY_ID = "id";
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_CHATMESSAGE_SENT_BY_ME = "sentByMe";
    private static final String KEY_CHATMESSAGE_SEEN = "seen";
    private static final String KEY_CHATMESSAGE_TEXT = "text";
    private static final String KEY_CHATMESSAGE_TYPE = "type";

    // Tag table create statement
    private static final String CREATE_TABLE_CHAT_MESSAGES = "CREATE TABLE "
            + TABLE_CHAT_MESSAGES + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CHATMESSAGE_SENT_BY_ME + " TEXT,"
            + KEY_CHATMESSAGE_SEEN + " TEXT,"
            + KEY_CHATMESSAGE_TEXT + " TEXT,"
            + KEY_CHATMESSAGE_TYPE + " INTEGER,"
            + KEY_CREATED_AT + " TEXT" + ")";


    public ChatMessagesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        db.execSQL(CREATE_TABLE_CHAT_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT_MESSAGES);
        onCreate(db);
    }

    public long insertChatMessage(Message chatmessage) {
        Log.d(TAG, "insertChatMessage()." + chatmessage.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_CHATMESSAGE_SENT_BY_ME, !chatmessage.isIncoming());
            values.put(KEY_CHATMESSAGE_SEEN, chatmessage.isSeen());
            values.put(KEY_CHATMESSAGE_TEXT, chatmessage.getText());
            values.put(KEY_CHATMESSAGE_TYPE, chatmessage.getMessageType());
            values.put(KEY_CREATED_AT, getDateTime());

            Log.d(TAG, "Values:" + values.toString());
            long id = db.insert(TABLE_CHAT_MESSAGES, null, values);
            Log.d(TAG, "insertChatMessage  succesful for messageId " + id);
            return id;

        } catch (Exception e) {
            if (e != null) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return -1;
        } finally {
            if (db != null) db.close();
            if (cursor != null) cursor.close();
        }
    }

    private String getDateTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public long updateChatMessage(Message chatmessage) {
        Log.d(TAG, "updateChatMessage()." + chatmessage.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_CHATMESSAGE_SENT_BY_ME, !chatmessage.isIncoming());
            values.put(KEY_CHATMESSAGE_SEEN, chatmessage.isSeen());
            values.put(KEY_CHATMESSAGE_TEXT, chatmessage.getText());
            values.put(KEY_CHATMESSAGE_TYPE, chatmessage.getMessageType());
            values.put(KEY_CREATED_AT, getDateTime());

            Log.d(TAG, "Values:" + values.toString());
            long id = db.update(TABLE_CHAT_MESSAGES, values, KEY_ID + " = ?", new String[]{chatmessage.getId()});
            Log.d(TAG, "updateChatMessage  succesful for messageId " + id);
            return id;

        } catch (Exception e) {
            if (e != null) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return -1;
        } finally {
            if (db != null) db.close();
            if (cursor != null) cursor.close();
        }
    }


    public Message getMessage(String messageID) {
        Log.d(TAG, "getMessage():" + messageID);
        String selectQuery = "SELECT  * FROM " + TABLE_CHAT_MESSAGES + " WHERE " + KEY_ID + " = ? ";
        Log.d(TAG, selectQuery);
        Message chatMessage = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, new String[]{messageID});

            if (cursor.moveToFirst()) {
                chatMessage = new Message();
                chatMessage.setIncoming(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_SENT_BY_ME))).equalsIgnoreCase("0"));
                chatMessage.setSeen(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_SEEN))).equalsIgnoreCase("1"));
                chatMessage.setText(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_TEXT))));
                chatMessage.setMessageType(cursor.getInt((cursor.getColumnIndex(KEY_CHATMESSAGE_TYPE))));
                chatMessage.setDate(Long.valueOf(cursor.getString((cursor.getColumnIndex(KEY_CREATED_AT)))));
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (db != null) db.close();
            if (cursor != null) cursor.close();
        }

        Log.d(TAG, (chatMessage != null) ? "Returned: " + chatMessage.toString() : " null returned.");
        return chatMessage;
    }


    public List<Message> getAllMessages() {
        Log.d(TAG, "getAllMessages()");
        List<Message> messages = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHAT_MESSAGES;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {

            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            Message chatMessage;
            if (cursor.moveToFirst()) {
                do {
                    chatMessage = new Message();
                    chatMessage.setIncoming(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_SENT_BY_ME))).equalsIgnoreCase("0"));
                    chatMessage.setSeen(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_SEEN))).equalsIgnoreCase("1"));
                    chatMessage.setText(cursor.getString((cursor.getColumnIndex(KEY_CHATMESSAGE_TEXT))));
                    chatMessage.setMessageType(cursor.getInt((cursor.getColumnIndex(KEY_CHATMESSAGE_TYPE))));
                    chatMessage.setDate(Long.valueOf(cursor.getString((cursor.getColumnIndex(KEY_CREATED_AT)))));
                    messages.add(chatMessage);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (db != null) db.close();
            if (cursor != null) cursor.close();
        }


        Log.d(TAG, messages.size() + " chat messages returned.");
        return messages;
    }


    public void deleteMessage(Message message) {
        Log.d(TAG, "deleteMessage() called with: message = [" + message + "]");
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CHAT_MESSAGES, KEY_ID + " = ? ", new String[]{message.getId()});
        Log.d(TAG, result + " chat messages deleted.");
    }

    public void deleteAll() {
        Log.d(TAG, "deleteAll() called");
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT_MESSAGES);
        } catch (Exception e) {
            Log.e(TAG, "deleteAll: ", e);
        }
    }
}
