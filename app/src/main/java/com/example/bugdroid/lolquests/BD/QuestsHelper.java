package com.example.bugdroid.lolquests.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bugdroid.lolquests.Objects.Quest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BugDroid on 05/12/2016.
 */

public class QuestsHelper extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION = 16;
    private static final String DATABASE_NAME = "FinalQuestsList7";
    private static final String TABLE_QUEST = "quests";
    private static final String TABLE_QHISTORY = "questshistory";
    private static final String TABLE_USER = "user";


    private SQLiteDatabase dbase;

    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_QUEST = "quest";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_CSGOAL = "csgoal";
    private static final String KEY_EXP = "Exp";

    private static final String KEY_HISTORY_ID = "id";
    private static final String KEY_HISTORY_IDQUEST = "idquest";
    private static final String KEY_HISTORY_PROGRESS = "progress";
    private static final String KEY_HISTORY_CSGOAL = "csgoal";

    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_REGION = "region";
    private static final String KEY_USER_POINTS = "points";
    private static final String KEY_USER_NICK = "nick";
    private static final String KEY_USER_QCOMPLETED = "qcompleted";

    public QuestsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;

        String sqlquests = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TYPE
                + " INTEGER, " + KEY_QUEST  + " TEXT, " + KEY_PROGRESS +
                " INTEGER, " + KEY_CSGOAL + " INTEGER, " + KEY_EXP + " INTEGER )";

        String sqluser = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  KEY_USER_REGION  + " TEXT, "
                + KEY_USER_NICK + " TEXT, " + KEY_USER_POINTS  + " TEXT, " + KEY_USER_QCOMPLETED +
                " INTEGER )";

        String sqlquestshistory = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  KEY_USER_REGION  + " TEXT, "
                + KEY_USER_NICK + " TEXT, " + KEY_USER_POINTS  + " TEXT, " + KEY_USER_QCOMPLETED +
                " INTEGER )";

        db.execSQL(sqlquests);
        db.execSQL(sqluser);
        addQuest();
    }

    private void addQuest() {
        Quest q1 = new Quest(1, "Farm more than 100 minions !", 0, 100, 200);
        this.addQuest(q1);
        Quest q2 = new Quest(1, "Farm more than 150 minions !", 0, 150, 200);
        this.addQuest(q2);
        Quest q3 = new Quest(1, "Farm more than 175 minions !", 0, 175, 200);
        this.addQuest(q3);
        Quest q4 = new Quest(1, "Farm more than 200 minions !", 0, 200, 200);
        this.addQuest(q4);
        Quest q5 = new Quest(1, "Farm more than 230 minions !", 0, 230, 200);
        this.addQuest(q5);
        Quest q6 = new Quest(1, "Farm more than 250 minions !", 0, 250, 200);
        this.addQuest(q6);
        Quest q7 = new Quest(1, "Farm more than 300 minions !", 0, 300, 200);
        this.addQuest(q7);
    }

    public void addQuest (Quest q) {
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, q.getQuestType());
        values.put(KEY_QUEST, q.getDesc());
        values.put(KEY_PROGRESS, q.getProgress());
        values.put(KEY_CSGOAL, q.getCsGoal());
        values.put(KEY_EXP, q.getExp());

        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<Quest> getAllQuestions() {

        List<Quest> quesList = new ArrayList<Quest>();

        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;

        dbase = this.getReadableDatabase();

        Cursor cursor = dbase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Quest q = new Quest();

                q.setId(cursor.getInt(0));
                q.setDesc(cursor.getString(1));
                q.setProgress(cursor.getInt(2));
                q.setExp(cursor.getInt(3));

                quesList.add(q);
            } while (cursor.moveToNext());
        }
        return quesList;
    }

    public Quest getRandomQuest (long id) {

        dbase = getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_QUEST +
                " WHERE " + KEY_ID + "= ? " ;
        String[] args = new String[]{id + ""};

        Cursor result = this.dbase.rawQuery(sql, args);

        Quest q = new Quest();

        if(result != null && result.moveToFirst()) {
            result.moveToFirst();
            q.setQuestType(result.getInt(result.getColumnIndex(KEY_TYPE)));
            q.setDesc(result.getString(result.getColumnIndex(KEY_QUEST)));
            q.setProgress(result.getInt(result.getColumnIndex(KEY_PROGRESS)));
            q.setCsGoal(result.getInt(result.getColumnIndex(KEY_CSGOAL)));
            q.setExp(result.getInt(result.getColumnIndex(KEY_EXP)));
            result.close();
        }
        return q;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
