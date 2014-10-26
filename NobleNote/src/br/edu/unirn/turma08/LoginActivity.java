package br.edu.unirn.turma08;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class LoginActivity extends Activity {

	private static final String USUARIO_ID = "usuario_id";
	private static final String USUARIO_LOGIN = "usuario_login";
	private static final String USUARIO_NOME = "usuario_nome";
	private Loading loading = new Loading();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		TextView textLogin = (TextView) findViewById(R.id.txtLogin);

		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
		int idUsuarioLogado = settings.getInt(USUARIO_ID, 0);
		// String loginUsuarioLogado = settings.getString(USUARIO_LOGIN, "");
		// String nomeUsuarioLogado = settings.getString(USUARIO_NOME, "");

		if (idUsuarioLogado != 0) {
			telaInicial();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		loading.close();
	}

	public void logar(View v) {
		EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		EditText txtSenha = (EditText) findViewById(R.id.txtSenha);

		String login = txtLogin.getText().toString();
		String senha = txtSenha.getText().toString();

		if (!login.equals("") && !senha.equals("")) {
			autenticar(login, senha);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Informe os campos de Usuario e senha", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	private void autenticar(String login, String senha) {

		final String userLogin = login;
		final String userSenha = senha;

		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {

			@Override
			protected void onPreExecute() {
				loading.show(LoginActivity.this, "Aguarde...");
			}

			@Override
			protected Usuario doInBackground(Void... params) {
				return UsuarioREST.logar(userLogin, userSenha);
			}

			@Override
			protected void onPostExecute(Usuario usuario) {
				if (usuario == null) {
					Toast toast = Toast.makeText(getApplicationContext(), "Usuario ou Senha inválida", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					loading.close();
				} else {
					SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt(USUARIO_ID, usuario.getId());
					editor.putString(USUARIO_LOGIN, usuario.getLogin());
					editor.putString(USUARIO_NOME, usuario.getNome());
					editor.commit();

					telaInicial();
				}
				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}

	public void novoUsuario(View v) {
		loading.show(LoginActivity.this, "Aguarde...");
		Intent intent = new Intent(LoginActivity.this,
				CadastrarUsuarioActivity.class);
		startActivity(intent);
	}

	public void recuperarSenha(View v) {
		loading.show(LoginActivity.this, "Aguarde...");
		Intent intent = new Intent(LoginActivity.this,
				RecuperarSenhaActivity.class);
		startActivity(intent);
	}

	private void telaInicial() {
		Intent intent = new Intent(LoginActivity.this, ListaNotaActivity.class);
		startActivity(intent);
	}

}
