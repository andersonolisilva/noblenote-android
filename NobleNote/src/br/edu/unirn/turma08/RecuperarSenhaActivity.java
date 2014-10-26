package br.edu.unirn.turma08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.rest.UsuarioREST;

public class RecuperarSenhaActivity extends Activity {

	private Loading loading = new Loading();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recuperar_senha);
	}

	public void recuperarSenha(View v) {

		EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
		EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);

		String login = txtLogin.getText().toString();
		String telefone = txtTelefone.getText().toString();
		String msg = "";

		if (login.equals("")) {
			msg += "Informe o login";
		} else if (telefone.equals("")) {
			msg += "Informe o número do telefone";
		}
		if (msg.length() > 0) {
			Toast toast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			recuperar(login, telefone);
		}
	}

	private void recuperar(final String login, final String telefone) {

		AsyncTask<Void, Void, Usuario> task = new AsyncTask<Void, Void, Usuario>() {

			@Override
			protected void onPreExecute() {
				loading.show(RecuperarSenhaActivity.this, "Aguarde...");
			}

			@Override
			protected Usuario doInBackground(Void... params) {
				try{
					return UsuarioREST.recuperar(login, telefone);
				}catch (Exception e){
					return null;
				}
			}

			@Override
			protected void onPostExecute(Usuario usuario) {
				if (usuario == null) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Falha ao recuperar a senha do usuário",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(RecuperarSenhaActivity.this);
					builder.setTitle("Sua senha:");
					builder.setMessage(usuario.getSenha());
					builder.setPositiveButton("Fechar", null);
					AlertDialog alerta = builder.create();
					alerta.show();
				}
				loading.close();
				super.onPostExecute(usuario);
			}
		};
		task.execute(new Void[] {});
	}
}
