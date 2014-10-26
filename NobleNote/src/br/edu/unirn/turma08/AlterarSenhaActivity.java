package br.edu.unirn.turma08;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class AlterarSenhaActivity extends Activity {

	private int idUsuarioLogado;
	private String loginUsuarioLogado;
	private String nomeUsuarioLogado;
	private static final String USUARIO = "Usuario";
	private static final String USUARIO_ID = "Usuario_Id";
	private static final String USUARIO_LOGIN = "Usuario_Login";
	private static final String USUARIO_NOME = "Usuario_Nome";
	private Loading loading = new Loading();
	
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alterar_senha);
		
		sharedPreferences = getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
		idUsuarioLogado = sharedPreferences.getInt(USUARIO_ID, 0);
		loginUsuarioLogado = sharedPreferences.getString(USUARIO_LOGIN, "");
		nomeUsuarioLogado = sharedPreferences.getString(USUARIO_NOME, "");
	}

	public void alterarSenha(View v) {

		EditText txtSenhaAtual = (EditText) findViewById(R.id.txtSenhaAtual);
		EditText txtNovaSenha = (EditText) findViewById(R.id.txtSenhaNova);
		EditText txtNovaSenha2 = (EditText) findViewById(R.id.txtSenhaNova2);

		String senhaAtual = txtSenhaAtual.getText().toString();
		String senhaNova = txtNovaSenha.getText().toString();
		String senhaNova2 = txtNovaSenha2.getText().toString();
		String msg = "";

		if (senhaAtual.equals("")) {
			msg += "Informe a senha atual";
		} else if (senhaNova.equals("")) {
			msg += "Informe a nova senha";
		} else if (senhaNova2.equals("")) {
			msg += "Informe a confirmação da nova senha";
		} else if (!senhaNova.equals(senhaNova2)) {
			msg += "Nova senha e confirmação de senha diferentes";
		}
		if (msg.length() > 0) {
			Toast toast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			alterar(loginUsuarioLogado, senhaAtual, senhaNova);
		}
	}

	private void alterar(final String login, final String senhaAtual, final String senhaNova) {

		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {

			@Override
			protected void onPreExecute() {
				loading.show(AlterarSenhaActivity.this, "Aguarde...");
			}

			@Override
			protected Usuario doInBackground(Void... params) {
				try{
					return UsuarioREST.alterarSenha(login, senhaAtual, senhaNova);
				}catch (Exception e){
					return null;
				}
			}

			@Override
			protected void onPostExecute(Usuario usuario) {
				if (usuario == null) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Falha ao alterar a senha",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				loading.close();
				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}
}
