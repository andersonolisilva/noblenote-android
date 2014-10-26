package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import br.edu.unirn.turma08.dao.NoteDAO;

public class ListaNotaActivity extends Activity {

	private int idUsuarioLogado;
	private static final String USUARIO_ID = "usuario_id";
	private static final String USUARIO_LOGIN = "usuario_login";
	private static final String USUARIO_NOME = "usuario_nome";
	private Loading loading = new Loading();

	private NoteAdapter adapter;

	private NoteDAO noteDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_nota);

		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
		idUsuarioLogado = settings.getInt(USUARIO_ID, 0);
		// String loginUsuarioLogado = settings.getString(USUARIO_LOGIN, "");
		// String nomeUsuarioLogado = settings.getString(USUARIO_NOME, "");

		if (idUsuarioLogado != 0) {
			logout();
		}

		noteDAO = new NoteDAO(this);
		noteDAO.open();
		
		noteDAO.create("Nota 1");

		popularNotas(idUsuarioLogado);

	}

	@Override
	protected void onStop() {
		// loading.close();
		super.onStop();
	}

	@Override
	protected void onResume() {
		// noteDAO.open();
		popularNotas(idUsuarioLogado);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// noteDAO.close();
		super.onPause();
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
			logout();
		}
		return super.onOptionsItemSelected(item);
	}

	private void popularNotas(int id) {
		ListView lista = (ListView) findViewById(R.id.listaNotas);
		adapter = new NoteAdapter(this, noteDAO.getAllByUser(id));
		lista.setAdapter(adapter);
	}

	private void logout() {
		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(USUARIO_ID, 0);
		editor.putString(USUARIO_LOGIN, "");
		editor.putString(USUARIO_NOME, "");
		editor.commit();

		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
