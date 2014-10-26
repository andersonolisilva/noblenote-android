package br.edu.unirn.turma08;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.unirn.turma08.dao.NoteDAO;
import br.edu.unirn.turma08.modelo.Note;

public class NotaActivity extends Activity {

	private int idUsuarioLogado;
	private static final String USUARIO = "Usuario";
	private static final String USUARIO_ID = "Usuario_Id";
	private static final String USUARIO_LOGIN = "Usuario_Login";
	private static final String USUARIO_NOME = "Usuario_Nome";
	private Loading loading = new Loading();

	private NoteDAO noteDAO;
	private Note note;

	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nota);

		sharedPreferences = getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
		idUsuarioLogado = sharedPreferences.getInt(USUARIO_ID, 0);
		String loginUsuarioLogado = sharedPreferences.getString(USUARIO_LOGIN,
				"");
		String nomeUsuarioLogado = sharedPreferences
				.getString(USUARIO_NOME, "");

		if (idUsuarioLogado == 0) {
			logout();
		}

		Intent i = getIntent();
		note = (Note) i.getSerializableExtra("NotaSelecionada");
		if (note != null) {
			EditText txtNota = (EditText) findViewById(R.id.txtNota);
			txtNota.setText(note.getAnotacao().trim());
		}else{
			Button btnExcluir = (Button) findViewById(R.id.btnApagarrNota);
			btnExcluir.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		loading.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_nota, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menuSair) {
			loading.show(this, "Aguarde...");
			logout();
		}
		if (id == R.id.menuAlterarSenha) {
			loading.show(this, "Aguarde...");
			senha();
		}
		return super.onOptionsItemSelected(item);
	}

	public void inserirNota(View v) {
		EditText txtNota = (EditText) findViewById(R.id.txtNota);

		String nota = txtNota.getText().toString().trim();

		if (!nota.equals("")) {
			gravarNota(nota);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Necessário informar a nota", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	public void removerNota(View v) {
		loading.show(NotaActivity.this, "Aguarde...");

		try {
			noteDAO = new NoteDAO(NotaActivity.this);
			noteDAO.open();
			noteDAO.delete(note);
			noteDAO.close();

			Intent intent = new Intent(NotaActivity.this, ListaNotaActivity.class);
			startActivity(intent);
		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Falha ao excluir nota", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			loading.close();
		}
	}

	private void gravarNota(String nota) {
		loading.show(NotaActivity.this, "Aguarde...");

		try {
			noteDAO = new NoteDAO(NotaActivity.this);
			noteDAO.open();

			if (note == null) {
				noteDAO.create(idUsuarioLogado, nota);
			} else {
				note.setAnotacao(nota);
				noteDAO.update(note);
			}

			noteDAO.close();

			Intent intent = new Intent(NotaActivity.this,
					ListaNotaActivity.class);
			startActivity(intent);

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Falha ao gravar nota", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			loading.close();
		}
	}

	private void logout() {
		sharedPreferences.edit().clear().commit();
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
	
	private void senha() {
		startActivity(new Intent(this, AlterarSenhaActivity.class));
		finish();
	}
}
