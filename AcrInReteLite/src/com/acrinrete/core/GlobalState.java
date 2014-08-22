package com.acrinrete.core;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;

import com.acrinrete.ArticoloScreen;
import com.ads.core.BannerManager;
import com.ads.core.BannerView;

public class GlobalState extends Application {
	private List<Notizia> notizie = new LinkedList<Notizia>();
	private List<Notizia> notizieComune = new LinkedList<Notizia>();
	private boolean accelerometer_on = true;
	private boolean bannerLoaded = false;
	private boolean bannerDownloaded = false;

	public void storeBanners(List<BannerView> banners) {
		BannerManager manager = BannerManager.getInstance();
		manager.clearBanners();
		for (BannerView bannerView : banners) {
			manager.addBanner(bannerView);
		}
		bannerLoaded = true;
	}

	public void setupBanners(ArticoloScreen context) {
		if (!bannerLoaded || !bannerDownloaded) {
			BannerReader br = new BannerReader(context);
			br.execute();
		}
		else{
			context.setupBanners();
		}
	}

	public void setFirstNews(Notizia firstNews) {
		notizie.set(0, firstNews);
	}

	public Notizia getFirstNews() {
		Notizia firstNews = null;
		if (notizie.isEmpty()) {
			firstNews = new AcrinReteReader(getResources()).readFirstNews();
			notizie.add(firstNews);
		} else
			firstNews = notizie.get(0);
		return firstNews;
	}

	public void setNotizie(List<Notizia> notizie) {
		this.notizie = notizie;
	}

	public List<Notizia> getNotizie() {
		if (notizie == null || notizie.isEmpty() || notizie.size() == 1) {
			notizie = new AcrinReteReader(getResources()).read(0, 15);
		}
		return notizie;
	}

	public List<Notizia> getMoreNotizie() {
		List<Notizia> notizieOther = new AcrinReteReader(getResources())
				.readOther(notizie.size(), notizie.size() + 15);
		notizie.addAll(notizieOther);
		return notizieOther;
	}

	public List<Notizia> getNotizieComune() {
		if (notizieComune == null || notizieComune.isEmpty()) {
			notizieComune = new ComuneReader().read();
		}
		return notizieComune;
	}

	public void setNotizieComune(List<Notizia> notizieComune) {
		this.notizieComune = notizieComune;
	}

	public void reset() {
		notizie = new LinkedList<Notizia>();
		notizieComune = new LinkedList<Notizia>();
		bannerLoaded = false;
	}

	public void resetNews() {
		notizie = new LinkedList<Notizia>();
	}

	public void resetComune() {
		notizieComune = new LinkedList<Notizia>();
	}

	public boolean isAccelerometer_on() {
		return accelerometer_on;
	}

	public void setAccelerometer_on(boolean accelerometer_on) {
		this.accelerometer_on = accelerometer_on;
	}

	public boolean isBannerLoaded() {
		return bannerLoaded;
	}
	
	public void setBannerDownloaded(boolean bannerDownloaded) {
		this.bannerDownloaded = bannerDownloaded;
	}

}
