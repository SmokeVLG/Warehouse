package com.ecwid.warehouse.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_TABLE = ("CREATE TABLE " + Config.TABLE_WAREHOUSE + "("
                + Config.COLUMN_WAREHOUSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_WAREHOUSE_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_WAREHOUSE_PATH_TO_IMAGE + " TEXT, " //nullable
                + Config.COLUMN_WAREHOUSE_COAST + " TEXT "
                + ")")

        db.execSQL(CREATE_TABLE)

        Log.d(TAG, "DB created!")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_WAREHOUSE)

        onCreate(db)
    }

    companion object {

        private val TAG = DatabaseHelper::class.java.simpleName
        private var databaseHelper: DatabaseHelper? = null

        private val DATABASE_VERSION = 1

        private val DATABASE_NAME = Config.DATABASE_NAME

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (databaseHelper == null) {
                databaseHelper = DatabaseHelper(context)
            }
            return databaseHelper!!
        }
    }

}
