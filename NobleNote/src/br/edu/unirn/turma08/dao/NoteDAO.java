package br.edu.unirn.turma08.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.edu.unirn.turma08.modelo.Note;

public class NoteDAO {

	private SQLiteDatabase database;
	private DataBaseDAO dbHelper;

	private String[] colunas = { DataBaseDAO.NOTE_ID,
								 DataBaseDAO.NOTE_DESCRICAO,
								 DataBaseDAO.NOTE_DATA};

	public NoteDAO(Context context) {
		dbHelper = new DataBaseDAO(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void create(int idUsuario, String descricao) {
		ContentValues values = new ContentValues();
		values.put(DataBaseDAO.NOTE_USUARIO, idUsuario);
		values.put(DataBaseDAO.NOTE_DESCRICAO, descricao);
		values.put(DataBaseDAO.NOTE_DATA, System.currentTimeMillis());
		database.insert(DataBaseDAO.TBL_NOTE, null, values);
	}

	public void delete(Note note) {
		database.delete(DataBaseDAO.TBL_NOTE, DataBaseDAO.NOTE_ID + " = "
				+ note.getId(), null);
	}

	public void update(Note note) {
		ContentValues values = new ContentValues();
		values.put(DataBaseDAO.NOTE_DESCRICAO, note.getAnotacao());
		values.put(DataBaseDAO.NOTE_DATA, System.currentTimeMillis());
		database.update(DataBaseDAO.TBL_NOTE, values, DataBaseDAO.NOTE_ID
				+ " = " + note.getId(), null);
	}
	
	public Note findById(int id){
		Note note = null;
		Cursor cursor = database.query(DataBaseDAO.TBL_NOTE, colunas, DataBaseDAO.NOTE_ID + " = "+ id, null, null, null, null);
		if (cursor.moveToFirst()) {
			note = new Note();
			note.setId(cursor.getInt(0));
			note.setAnotacao(cursor.getString(1));
			note.setData(new Date(cursor.getLong(2)));
		}
		return note;
	}

	public List<Note> getAllByUser(int idUsuario) {
		List<Note> notas = new ArrayList<Note>();

		Cursor cursor = database.query(DataBaseDAO.TBL_NOTE, colunas, DataBaseDAO.NOTE_USUARIO + " = "+ idUsuario, null, null, null, DataBaseDAO.NOTE_DATA+" DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notas.add(note);
			cursor.moveToNext();
		}
		cursor.close();
		return notas;
	}

	private Note cursorToNote(Cursor cursor) {
		Note note = new Note();
		note.setId(cursor.getInt(0));
		note.setAnotacao(cursor.getString(1));
		note.setData(new Date(cursor.getLong(2)));
		return note;
	}
}
