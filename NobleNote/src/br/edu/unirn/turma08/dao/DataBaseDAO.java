package br.edu.unirn.turma08.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseDAO extends SQLiteOpenHelper {

	public static final String TBL_NOTE = "note";
	public static final String NOTE_ID = "id";
	public static final String NOTE_USUARIO = "usuario";
	public static final String NOTE_DESCRICAO = "anotacao";
	public static final String NOTE_DATA = "data";

	private static final String DATABASE_NAME = "note.db";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TBL_NOTE
			+ "( " + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NOTE_USUARIO + " INTEGER NOT NULL, " + NOTE_DESCRICAO
			+ " TEXT NOT NULL, " + NOTE_DATA
			+ " INTEGER NOT NULL );";

	public DataBaseDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_NOTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TBL_NOTE);
		onCreate(db);
	}

}