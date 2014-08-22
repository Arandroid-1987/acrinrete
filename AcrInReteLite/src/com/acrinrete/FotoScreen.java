package com.acrinrete;

import static com.acrinrete.utils.Constraints.FOTO;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;

import com.acrinrete.ui.GalleryImageAdapter;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.ImageUtils;
import com.acrinrete.utils.SlidingMenu;

public class FotoScreen extends DefaultScreen implements OnItemSelectedListener,
		OnItemClickListener {
	private GridView content;

	private ImageView selectedImageView;
	private ImageView leftArrowImageView;
	private ImageView rightArrowImageView;

	private Spinner selector;

	private Gallery gallery;

	private int selectedImagePosition = 0;

	private List<Drawable> drawables;

	private GalleryImageAdapter galImageAdapter;

	private int tipo_galleria = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_foto_screen);
		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		handle = (ImageView) this.findViewById(R.id.handle);
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);

		Intent intent = getIntent();
		tipo_galleria = intent.getIntExtra("dati", -1);

		selector = (Spinner) findViewById(R.id.selector);

		// codice spinner
		ArrayAdapter<CharSequence> adp = new ArrayAdapter<CharSequence>(
				FotoScreen.this, android.R.layout.simple_spinner_item);

		String[] names = new String[] { getString(R.string.acri_notte),
				getString(R.string.belvedere), getString(R.string.panorama) };

		for (String name : names) {
			adp.add(name);
		}
		
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selector.setAdapter(adp);
		selector.setOnItemSelectedListener(this);
		
		// fine codice spinner

		createGalleryDialog().show();
	}

	private void setupUI() {

		selectedImageView = (ImageView) findViewById(R.id.selected_imageview);
		leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
		gallery = (Gallery) findViewById(R.id.gallery);

		leftArrowImageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (selectedImagePosition < drawables.size() - 1) {
					++selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);

			}
		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				selectedImagePosition = pos;

				if (selectedImagePosition > 0
						&& selectedImagePosition < drawables.size() - 1) {

					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_enabled));
					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_enabled));

				} else if (selectedImagePosition == 0) {

					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_disabled));

				} else if (selectedImagePosition == drawables.size() - 1) {

					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_disabled));
				}

				// changeBorderForSelectedImage(selectedImagePosition);
				setSelectedImage(selectedImagePosition);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		galImageAdapter = new GalleryImageAdapter(this, drawables);

		gallery.setAdapter(galImageAdapter);

		if (drawables.size() > 0) {

			gallery.setSelection(selectedImagePosition, false);

		}

		if (drawables.size() == 1) {

			rightArrowImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.arrow_right_disabled));
		}

	}

	private void getDrawablesList() {

		drawables = new ArrayList<Drawable>();
		if (tipo_galleria == 0) {
			drawables.add(getResources().getDrawable(R.drawable.notte_01));
			drawables.add(getResources().getDrawable(R.drawable.notte_02));
			drawables.add(getResources().getDrawable(R.drawable.notte_03));
			drawables.add(getResources().getDrawable(R.drawable.notte_04));
			drawables.add(getResources().getDrawable(R.drawable.notte_05));
		} else if (tipo_galleria == 1) {
			drawables.add(getResources().getDrawable(R.drawable.belv_01));
			drawables.add(getResources().getDrawable(R.drawable.belv_02));
			drawables.add(getResources().getDrawable(R.drawable.belv_03));
			drawables.add(getResources().getDrawable(R.drawable.belv_04));
			drawables.add(getResources().getDrawable(R.drawable.belv_05));
		} else if (tipo_galleria == 2) {
			drawables.add(getResources().getDrawable(R.drawable.pano_01));
			drawables.add(getResources().getDrawable(R.drawable.pano_02));
			drawables.add(getResources().getDrawable(R.drawable.pano_03));
			drawables.add(getResources().getDrawable(R.drawable.pano_04));
			drawables.add(getResources().getDrawable(R.drawable.pano_05));
		}

	}

	private void setSelectedImage(int selectedImagePosition) {

		BitmapDrawable bd = (BitmapDrawable) drawables
				.get(selectedImagePosition);
		Bitmap b = ImageUtils.scaleDownBitmap(bd.getBitmap(), 230, this);
		selectedImageView.setImageBitmap(b);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		tipo_galleria = arg2;
		getDrawablesList();
		setupUI();
	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}

	private AlertDialog createGalleryDialog() {
		final String[] items = { "Acri di Notte", "Belvedere", "Panorama" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Quale Galleria vuoi vedere?");
		builder.setCancelable(false);
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						dialog.dismiss();
						tipo_galleria = item;
						getDrawablesList();
						setupUI();
						selector.setSelection(tipo_galleria);
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		SlidingMenu.pastina(this, position, drawer, FOTO);
	}
}