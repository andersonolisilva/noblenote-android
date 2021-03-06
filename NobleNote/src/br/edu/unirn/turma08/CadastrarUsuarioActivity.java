package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class CadastrarUsuarioActivity extends Activity {
	
	private Loading loading = new Loading();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);
	}

	public void cadastrarUsuario(View v) {

		EditText txtNome = (EditText) findViewById(R.id.txtNome);
		EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
		EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);

		String nome = txtNome.getText().toString();
		String login = txtLogin.getText().toString();
		String senha = txtSenha.getText().toString();
		String telefone = txtTelefone.getText().toString();
		String msg = "";

		if (nome.equals("")) {
			msg += "Informe o nome";
		} else if (login.equals("")) {
			msg += "Informe o login";
		} else if (senha.equals("")) {
			msg += "Informe a senha";
		} else if (telefone.equals("")) {
			msg += "Informe o n�mero do telefone";
		}
		
		if (msg.length() > 0) {
			Toast toast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNumeroTelefone(telefone);
			cadastrar(usuario);
		}
	}

	private void cadastrar(final Usuario usuario) {
		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {

			@Override
			protected void onPreExecute() {
				loading.show(CadastrarUsuarioActivity.this, "Aguarde...");
			}

			@Override
			protected Usuario doInBackground(Void... params) {
				Usuario u = null;
				try {
					u = UsuarioREST.cadastrar(usuario);
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
				return u;
			}

			@Override
			protected void onPostExecute(Usuario usuario) {
				if (usuario == null) {
					Toast toast = Toast.makeText(getApplicationContext(), "Falha ao inserir o usu�rio", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					loading.close();
				} else {
					Intent intent = new Intent(CadastrarUsuarioActivity.this, ListaNotaActivity.class);
					startActivity(intent);
				}
				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}
}
