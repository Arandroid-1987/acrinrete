package com.acrinrete.utils;

import org.jsoup.Jsoup;

public class HtmlRemover {

	public static String removeHtml(String html) {
		return Jsoup.parse(html).text();
	}

	// public static String removeHtml(String html){
	// StringBuilder sb = new StringBuilder();
	// char[] array = html.toCharArray();
	// boolean add = true;
	// StringBuilder tmp = new StringBuilder();
	// for (int i = 0; i < array.length; i++) {
	// char c = array[i];
	// if (c == '<')
	// add = false;
	// else if (c == '>') {
	// add = true;
	// tmp = new StringBuilder();
	// } else {
	// if (add)
	// sb.append(c);
	// else {
	// tmp.append(c);
	// // if (tmp.toString().equals("br")) {
	// // sb.append("\n");
	// // }
	// }
	// }
	// }
	// return sb.toString();

}
