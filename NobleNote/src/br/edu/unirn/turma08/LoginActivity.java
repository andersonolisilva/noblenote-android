package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class LoginActivity extends Activity {

	private static final String LOGIN_PREFERENCE = "login";
	private Loading loading = new Loading();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextView textLogin = (TextView) findViewById(R.id.txtLogin);

		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
		String usuarioLogado = settings.getString(LOGIN_PREFERENCE, "");

		textLogin.setText(usuarioLogado);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void logar(View v) {
		loading.show(this, "Aguarde...");
		
		TextView textLogin = (TextView) findViewById(R.id.txtLogin);
		TextView textSenha = (TextView) findViewById(R.id.txtSenha);

		final String login = textLogin.getText().toString();
		final String senha = textSenha.getText().toString();

		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {

			@Override
			protected Usuario doInBackground(Void... params) {
				Usuario usuario = UsuarioREST.logar(login, senha);
				return usuario;
			}

			@Override
			protected void onPostExecute(Usuario usuario) {

				SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(LOGIN_PREFERENCE, login);
				editor.commit();

//				Intent acao = new Intent(LoginActivity.this, ListaNotaActivity.class);
//				startActivity(acao);
				
				loading.close();

				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}
}
