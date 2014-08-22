package com.acrinrete;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.acrinrete.core.GlobalState;
import com.acrinrete.ui.DialogBuilder;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.Constraints;
import com.acrinrete.utils.HtmlRemover;
import com.acrinrete.utils.SlidingMenu;
import com.ads.core.BannerManager;

public class ArticoloScreen extends DefaultScreen {

	private TextView tvTitolo;
	private TextView tvAutore;
	private TextView tvTesto;
	private ImageView imgv;
	private boolean primaNotizia;
	private Bitmap bitmap;
	private LinearLayout containerLayout;
	private BannerManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_articolo_screen);

		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		handle = (ImageView) this.findViewById(R.id.handle);
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);

		Intent intent = getIntent();
		primaNotizia = intent.getBooleanExtra("primaNotizia", false);
		if (!primaNotizia) {
			drawer.setVisibility(View.INVISIBLE);
		}

		tvTitolo = (TextView) findViewById(R.id.tvTitolo);
		tvAutore = (TextView) findViewById(R.id.tvAutore);
		tvTesto = (TextView) findViewById(R.id.testotv);
		imgv = (ImageView) findViewById(R.id.immagine);
		String[] s = intent.getStringArrayExtra("dati");
		bitmap = ((Bitmap) intent.getParcelableExtra("img"));

		String Titolo = s[0];
		String Autore = s[1];
		String Testo = s[2];
		if (bitmap != null)
			imgv.setImageBitmap(bitmap);
		tvTitolo.setText(Titolo);
		tvAutore.setText("di: " + Autore);
		tvTesto.setText(HtmlRemover.removeHtml(Testo).toString());

		// banners
		manager = BannerManager.getInstance();
		containerLayout = (LinearLayout) findViewById(R.id.bannerLL);
		GlobalState gs = (GlobalState) getApplication();
		gs.setupBanners(this);
	}

	public void setupBanners() {
		manager.setContainerLayout(containerLayout);
		manager.setContext(this);
		manager.load();
	}

	@Override
	public void onBackPressed() {
		if (!primaNotizia) {
			containerLayout.removeAllViews();
			setResult(RESULT_OK);
			finish();
		} else {
			Dialog d = DialogBuilder.createExitDialog(this);
			d.show();
		}
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (primaNotizia) {
			SlidingMenu.pastina(this, position, drawer, Constraints.PRIMO);
		} else {
			SlidingMenu.pastina(this, position, drawer, -1);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (primaNotizia) {
			super.onSensorChanged(event);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_menu:
			if (primaNotizia) {
				drawer.animateOpen();
			}
			break;
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsScreen.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		containerLayout.removeAllViews();
	}

}
