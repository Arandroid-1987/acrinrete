package com.acrinrete.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acrinrete.R;
import com.acrinrete.core.Notizia;

public class ContattiAdapter extends ArrayAdapter<Notizia> {
	private TextView notiziaTitle;
	private TextView notiziaAuthor;

	private List<Notizia> items = new ArrayList<Notizia>();

	public ContattiAdapter(Context context, int textViewResourceId,
			List<Notizia> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}

	public int getCount() {
		return this.items.size();
	}

	public Notizia getItem(int index) {
		return this.items.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.riga_contatti, parent, false);
		}

		Notizia item = getItem(position);

		notiziaTitle = (TextView) row.findViewById(R.id.notiziaTitle);

		notiziaTitle.setText(item.getTitolo());

		notiziaAuthor = (TextView) row.findViewById(R.id.notiziaAuthor);
		notiziaAuthor.setText(item.getAutore());

		return row;
	}
}
