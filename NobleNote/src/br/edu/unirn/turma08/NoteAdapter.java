package br.edu.unirn.turma08;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.edu.unirn.turma08.modelo.Note;

public class NoteAdapter  extends ArrayAdapter<Note> {

	private final Context context;
	List<Note> notas = new ArrayList<Note>();

	public NoteAdapter(Context context, List<Note> notas) {
		super(context, R.layout.notelayout, notas);
		this.context = context;
		this.notas = notas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.notelayout, parent, false);
		
		TextView txtAnotacao = (TextView) rowView.findViewById(R.id.anotacao);
		TextView txtDataAnotacao = (TextView) rowView.findViewById(R.id.dataanotacao);
		
		Note nota = notas.get(position);
		
		txtAnotacao.setText(nota.getAnotacao());
		
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		txtDataAnotacao.setText(formatDate.format(nota.getData()));

		return rowView;
	}

}
