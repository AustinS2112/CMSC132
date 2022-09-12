package model;

import java.util.*;

/**
 * Represents a web page. Web page elements are stored in an ArrayList of
 * Element objects. A title is associated with every page. This class implements
 * the Comparable interface. Pages will be compared based on the title.
 * 
 * @author UMCP
 *
 */
public class WebPage implements Comparable<WebPage> {
	private ArrayList<Element> elements;
	private String title;

	public WebPage(String title) {
		this.title = title;

		elements = new ArrayList<Element>();
	}// constructor

	public int addElement(Element element) {
		elements.add(element);

		if (element instanceof TagElement) {
			return ((TagElement) element).getId();
		} else {
			return -1;
		}
	}// addElement;

	public String getWebPageHTML(int indentation) {
		String HTML = "";

		// start tag---------------------------------------
		HTML = HTML + "<!doctype html>\n<html>\n";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "<head>\n";

		for (int i = 0; i < indentation + 3; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "<meta charset=" + "\"" + "utf-8" + "\"" + "/>\n";

		for (int i = 0; i < indentation + 3; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "<title>" + title + "</title>\n";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "</head>\n";

		// content-----------------------------------------
		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "<body>\n";

		for (int idx = 0; idx < elements.size(); idx++) {
			if (elements.get(idx) != null) {
				HTML = HTML + elements.get(idx).genHTML(indentation) + "\n";
			} // if
		} // for

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		HTML = HTML + "</body>\n";

		// end tag-----------------------------------------
		HTML = HTML + "</html>";

		return HTML;
	}// getWebPageHTML

	/*
	 * Writes to the specified file the web page page using the provided
	 * indentation. Relies on the Utilities method writeToFile
	 */
	public void writeToFile(String filename, int indentation) {
		Utilities.writeToFile(filename, this.getWebPageHTML(indentation));
	}// writeToFile

	public Element findElem(int id) {
		for (int i = 0; i < elements.size(); i++) {
			if (((TagElement) elements.get(i)).getId() == id) {
				return elements.get(i);
			} // if
		} // for
		return null;
	}// findElem

	public String stats() {
		int listCount = 0;
		int paraCount = 0;
		int tableCount = 0;
		double indTableUtilization = 0;
		String results = "";

		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof ListElement) {
				listCount++;
			} else if (elements.get(i) instanceof ParagraphElement) {
				paraCount++;
			} else if (elements.get(i) instanceof TableElement) {
				indTableUtilization = indTableUtilization + ((TableElement) elements.get(i)).getTableUtilization();
				tableCount++;
			}
		} // for
		results = results + "List Count: " + listCount + "\n";
		results = results + "Paragraph Count: " + paraCount + "\n";
		results = results + "Table Count: " + tableCount + "\n";
		results = results + "TableElement Utilization: " + (indTableUtilization / tableCount);

		return results;
	}// stats

	public int compareTo(WebPage webPage) {
		return this.title.compareTo(webPage.title);
	}// compareTo

	public static void enableId(boolean choice) {
		enableId(choice);
	}// enableId

}// WebPage
