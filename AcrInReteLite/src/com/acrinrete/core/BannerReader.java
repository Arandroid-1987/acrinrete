package com.acrinrete.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.acrinrete.ArticoloScreen;
import com.acrinrete.R;
import com.ads.core.BannerView;

public class BannerReader extends AsyncTask<Void, Void, List<BannerView>> {
	private ArticoloScreen context;
	private GlobalState gs;

	public BannerReader(ArticoloScreen context) {
		this.context = context;
		gs = (GlobalState) context.getApplication();
	}

	private List<BannerView> read(Context context) {
		List<BannerView> ris = new LinkedList<BannerView>();
		String feed = "http://www.arandroid.altervista.org/banner.txt";
		try {
			URL url = new URL(feed);
			InputStream in = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while (line != null) {
				StringTokenizer st = new StringTokenizer(line);
				String imgSrc = st.nextToken();
				Bitmap b = null;
				while (b == null) {
					InputStream is = new URL(imgSrc).openStream();
					b = BitmapFactory.decodeStream(is);
				}
				ImageView view = new ImageView(context);
				view.setImageBitmap(b);
				BannerView bv;
				if (st.countTokens() == 0) {
					bv = new BannerView(view, null);
				} else {
					String link = st.nextToken();
					bv = new BannerView(view, link);
				}
				ris.add(bv);
				line = br.readLine();
			}
			gs.setBannerDownloaded(true);
		} catch (Exception e) {
			e.printStackTrace();
			ris.clear();
			ImageView view = new ImageView(context);
			Bitmap b = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.banner3_spettacolo);
			view.setImageBitmap(b);
			BannerView bv = new BannerView(view, null);
			ris.add(bv);
			gs.setBannerDownloaded(false);
			return ris;
		}
		return ris;
	}

	@Override
	protected List<BannerView> doInBackground(Void... params) {
		return read(context);
	}

	@Override
	protected void onPostExecute(List<BannerView> result) {
		gs.storeBanners(result);
		context.setupBanners();
	}

}
