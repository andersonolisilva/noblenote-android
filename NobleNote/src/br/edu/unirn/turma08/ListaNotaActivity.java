package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.edu.unirn.turma08.dao.NoteDAO;
import br.edu.unirn.turma08.modelo.Note;

public class ListaNotaActivity extends Activity {

	private int idUsuarioLogado;
	private static final String USUARIO = "Usuario";
	private static final String USUARIO_ID = "Usuario_Id";
	private static final String USUARIO_LOGIN = "Usuario_Login";
	private static final String USUARIO_NOME = "Usuario_Nome";
	private Loading loading = new Loading();

	private NoteAdapter adapter;

	private NoteDAO noteDAO;
	
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_nota);

		sharedPreferences = getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
		idUsuarioLogado = sharedPreferences.getInt(USUARIO_ID, 0);
		String loginUsuarioLogado = sharedPreferences.getString(USUARIO_LOGIN, "");
		String nomeUsuarioLogado = sharedPreferences.getString(USUARIO_NOME, "");

		if (idUsuarioLogado > 0) {
			noteDAO = new NoteDAO(this);
			noteDAO.open();
			popularNotas(idUsuarioLogado);
			noteDAO.close();
		}else{
			logout();
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
	
	public void novaNota(View v){
		loading.show(ListaNotaActivity.this, "Aguarde...");
		Intent intent = new Intent(ListaNotaActivity.this, NotaActivity.class);
		startActivity(intent);
	}

	private void popularNotas(int id) {
		ListView lista = (ListView) findViewById(R.id.listaNotas);
		adapter = new NoteAdapter(this, noteDAO.getAllByUser(id));
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				loading.show(ListaNotaActivity.this, "Aguarde...");
				
				Note note = (Note) parent.getItemAtPosition(position);
				Intent intent = new Intent(ListaNotaActivity.this, NotaActivity.class);
				intent.putExtra("NotaSelecionada", note);
				
				startActivity(intent);
			}
		});
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
