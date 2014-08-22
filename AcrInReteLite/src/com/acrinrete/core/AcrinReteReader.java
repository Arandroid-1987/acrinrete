package com.acrinrete.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.acrinrete.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AcrinReteReader {
	private Resources resources;
	private NodeList nl;

	public AcrinReteReader(Resources resources) {
		this.resources = resources;
	}

	public List<Notizia> read(int from, int to) {
		String feed = "http://www.acrinrete.info/feed3.asp";
		List<Notizia> notizie = new LinkedList<Notizia>();
		try {
			URL url = new URL(feed);
			InputStream in = url.openStream();
			DocumentBuilderFactory dbf;
			dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(in);
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("item");
			if (nl != null && nl.getLength() > 0) {
				for (int i = from; i < to; i++) {
					Element item = (Element) nl.item(i);
					Element titleElem = (Element) item.getElementsByTagName(
							"title").item(0);
					String title = titleElem.getFirstChild().getNodeValue();
					Element descElem = (Element) item.getElementsByTagName(
							"description").item(0);
					Element imgElem = (Element) descElem.getElementsByTagName(
							"img").item(0);
					String img = imgElem.getAttribute("src");
					Element linkElem = (Element) item.getElementsByTagName(
							"link").item(0);
					Element autElem = (Element) item.getElementsByTagName(
							"author").item(0);
					String link = linkElem.getFirstChild().getNodeValue();
					String desc = descElem.getLastChild().getNodeValue().trim();
					String aut = autElem.getFirstChild().getNodeValue();

					Bitmap b = null;
					try {
						InputStream is = new URL(img).openStream();
						b = BitmapFactory.decodeStream(is);
					} catch (IOException ex) {
						b = BitmapFactory.decodeResource(resources,
								R.drawable.immm);
					}
					Notizia n = new Notizia();
					n.setAutore(aut);
					n.setDescrizione(desc);
					n.setLink(link);
					n.setImage(b);
					n.setTitolo(title);
					notizie.add(n);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return notizie;
	}

	public List<Notizia> readOther(int from, int to) {
		String feed = "http://www.acrinrete.info/feed4.asp";
		List<Notizia> notizie = new LinkedList<Notizia>();
		try {
			if (nl == null) {
				URL url = new URL(feed);
				InputStream in = url.openStream();
				DocumentBuilderFactory dbf;
				dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				nl = docEle.getElementsByTagName("item");
			}
			if (nl != null && nl.getLength() > 0) {
				for (int i = from; i < to; i++) {
					Element item = (Element) nl.item(i);
					Element titleElem = (Element) item.getElementsByTagName(
							"title").item(0);
					String title = titleElem.getFirstChild().getNodeValue();
					Element descElem = (Element) item.getElementsByTagName(
							"description").item(0);
					Element imgElem = (Element) descElem.getElementsByTagName(
							"img").item(0);
					String img = imgElem.getAttribute("src");
					Element linkElem = (Element) item.getElementsByTagName(
							"link").item(0);
					Element autElem = (Element) item.getElementsByTagName(
							"author").item(0);
					String link = linkElem.getFirstChild().getNodeValue();
					String desc = descElem.getLastChild().getNodeValue().trim();
					String aut = autElem.getFirstChild().getNodeValue();

					Bitmap b = null;
					try {
						InputStream is = new URL(img).openStream();
						b = BitmapFactory.decodeStream(is);
					} catch (IOException ex) {
					b = BitmapFactory
							.decodeResource(resources, R.drawable.immm);
					 }
					Notizia n = new Notizia();
					n.setAutore(aut);
					n.setDescrizione(desc);
					n.setLink(link);
					n.setImage(b);
					n.setTitolo(title);
					notizie.add(n);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return notizie;

	}

	public Notizia readFirstNews() {
		try {
			URL url = new URL("http://www.acrinrete.info/feed3.asp");
			InputStream in = url.openStream();
			DocumentBuilderFactory dbf;
			dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(in);
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("item");
			if (nl != null && nl.getLength() > 0) {
				Element item = (Element) nl.item(0);
				Element titleElem = (Element) item
						.getElementsByTagName("title").item(0);
				String title = titleElem.getFirstChild().getNodeValue();
				Element descElem = (Element) item.getElementsByTagName(
						"description").item(0);
				Element imgElem = (Element) descElem
						.getElementsByTagName("img").item(0);
				String img = imgElem.getAttribute("src");
				Element linkElem = (Element) item.getElementsByTagName("link")
						.item(0);
				Element autElem = (Element) item.getElementsByTagName("author")
						.item(0);
				String link = linkElem.getFirstChild().getNodeValue();
				String desc = descElem.getLastChild().getNodeValue().trim();
				String aut = autElem.getFirstChild().getNodeValue();

				Bitmap b = null;
				try {
					InputStream is = new URL(img).openStream();
					b = BitmapFactory.decodeStream(is);
				} catch (IOException ex) {
					b = BitmapFactory
							.decodeResource(resources, R.drawable.immm);
				}
				Notizia n = new Notizia();
				n.setAutore(aut);
				n.setDescrizione(desc);
				n.setLink(link);
				n.setImage(b);
				n.setTitolo(title);
				return n;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
