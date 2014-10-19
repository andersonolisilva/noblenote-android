package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class LoginActivity extends Activity {

//	private static final String LOGIN_PREFERENCE = "login";
	public Loading loading = new Loading();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

//		TextView textLogin = (TextView) findViewById(R.id.txtLogin);
//
//		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
//		String usuarioLogado = settings.getString(LOGIN_PREFERENCE, "");
//
//		textLogin.setText(usuarioLogado);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void logar(View v) {
		EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		EditText txtSenha = (EditText) findViewById(R.id.txtSenha);

		String login = txtLogin.getText().toString();
		String senha = txtSenha.getText().toString();
		
		if (!login.equals("") && !senha.equals("")) {
			autenticar(login, senha);
		}else{
			Toast toast = Toast.makeText(getApplicationContext(), "Informe os campos de Usuario e senha", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	
	private void autenticar(String login, String senha){
		
		final String userLogin = login;
		final String userSenha = senha;
		
		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {
			
			@Override
			protected void onPreExecute(){
				loading.show(LoginActivity.this, "Aguarde...");
			}

			@Override
			protected Usuario doInBackground(Void... params) {
				Usuario usuario = UsuarioREST.logar(userLogin, userSenha);
				return usuario;
			}

			@Override
			protected void onPostExecute(Usuario usuario) {
				if (usuario == null) {
					Toast toast = Toast.makeText(getApplicationContext(), "Usuario ou senha inválida", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					loading.close();
				}else{
					Intent intent = new Intent(LoginActivity.this, ListaNotaActivity.class);
					startActivity(intent);
					finish();
				}
				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}
	
	public void novoUsuario(View v) {
		loading.show(LoginActivity.this, "Aguarde...");
		Intent intent = new Intent(LoginActivity.this, CadastrarUsuarioActivity.class);
		startActivity(intent);
	}
	
	public void recuperarSenha(){
		Toast toast = Toast.makeText(getApplicationContext(), "Cenas do próximos capítulos...", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
