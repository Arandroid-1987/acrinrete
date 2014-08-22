package com.acrinrete;

import static com.acrinrete.utils.Constraints.METEO;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.SlidingMenu;

public class MeteoScreen extends DefaultScreen {
	private GridView content;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// codice da copiare in tutte le activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_meteo_screen);
		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		handle = (ImageView) this.findViewById(R.id.handle);
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);
		// fine codice da copiare

		webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl("file:///android_asset/meteo.html");
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		SlidingMenu.pastina(this, position, drawer, METEO);
	}

}
