package com.acrinrete.core;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComuneReader {
	private String aut;
	private String desc;
	private String title;



	public List<Notizia> read() {
		List<Notizia> notizie = new LinkedList<Notizia>();
		try {
			URL url = new URL("http://www.acrinrete.info/feed_Comune.asp");
			InputStream in = url.openStream();
			DocumentBuilderFactory dbf;
			dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(in);
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("item");
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Element item = (Element) nl.item(i);
					Element titleElem = (Element) item.getElementsByTagName(
							"title").item(0);
					title = titleElem.getFirstChild().getNodeValue();
					Element descElem = (Element) item.getElementsByTagName(
							"description").item(0);
					Element autElem = (Element) item.getElementsByTagName(
							"author").item(0);
					desc = descElem.getFirstChild().getNodeValue().trim();
					aut = autElem.getFirstChild().getNodeValue();
					Notizia n = new Notizia();
					n.setAutore(aut);
					n.setDescrizione(desc);
					n.setTitolo(title);
					notizie.add(n);

				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return notizie;

	}

}
