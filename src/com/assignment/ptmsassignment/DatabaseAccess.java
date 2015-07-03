package com.assignment.ptmsassignment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

class DatabaseAccess {
	
	protected static SQLiteDatabase createDatabase(String path) throws SQLiteException{
		SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		return database;
	}
	
	protected static void createTable(SQLiteDatabase database, String sql) throws SQLiteException{
		database.execSQL(sql);
	}
	protected static void dropTable(SQLiteDatabase database, String table) throws SQLiteException{
		database.execSQL("DROP TABLE IF EXISTS "+table);
	}
	protected static void insert(SQLiteDatabase database, String sql) throws SQLiteException{
		database.execSQL(sql);
	}
	protected static int insert(SQLiteDatabase database, String table, ContentValues values) throws SQLiteException{
		
		int rowPosition = (int) database.insert(table, null, values);
		return rowPosition;
	}
	protected static Cursor select(SQLiteDatabase database, String sql) throws SQLiteException{
		Cursor cursor = database.rawQuery(sql, null);
		return cursor;
	}
	protected static void connectionClose(SQLiteDatabase database){
		database.close();
	}
	
	protected static SQLiteDatabase readWriteDatabase(String path) throws SQLiteException{
		SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		return database;
	}
	protected static SQLiteDatabase readDatabase(String path) throws SQLiteException{
		SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		return database;
	}
}
