package com.acrinrete.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.acrinrete.R;
import com.acrinrete.core.Notizia;

public class NotiziaAdapter extends ArrayAdapter<Notizia> {
	private ImageView notiziaImage;
	private TextView notiziaTitle;
	private TextView notiziaAuthor;

	private List<Notizia> items = new ArrayList<Notizia>();

	public NotiziaAdapter(Context context, int textViewResourceId,
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

			row = inflater.inflate(R.layout.notizia, parent, false);
		}

		Notizia item = getItem(position);

		notiziaTitle = (TextView) row.findViewById(R.id.notiziaTitle);

		notiziaTitle.setText(item.getTitolo());

		notiziaAuthor = (TextView) row.findViewById(R.id.notiziaAuthor);
		notiziaAuthor.setText("di: " + item.getAutore());

		notiziaImage = (ImageView) row.findViewById(R.id.notiziaImage);
		if (item.getImage() != null)
			notiziaImage.setImageBitmap(item.getImage());

		return row;
	}
}
