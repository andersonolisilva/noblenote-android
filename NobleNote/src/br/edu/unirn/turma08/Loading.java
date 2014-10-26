package br.edu.unirn.turma08;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {

	private ProgressDialog progress;

	public void show(Context context, String message) {

		if (progress == null) {
			progress = new ProgressDialog(context);
			progress.setTitle("");
			progress.setCancelable(false);
			progress.setMessage(message);
		}
		progress.show();
	}

	public void close() {

		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}
	}
}
