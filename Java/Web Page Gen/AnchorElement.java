package model;

/**
 * Represents the &lt;a&gt; tag.
 * 
 * @author UMCP
 *
 */
public class AnchorElement extends TagElement {
	private String linkText;
	private String url;
	private static boolean endTag;

	public AnchorElement(String url, String linkText, String attributes) {
		super("a", true, null, attributes);// anchor element tagName = a, endTag is true, no content
		this.url = url;
		this.linkText = linkText;
	} // Constructor

	public String getLinkText() {
		String linkTextCopy = linkText;

		return linkTextCopy;
	} // getLinkText

	public String getUrlText() {
		String urlCopy = url;

		return urlCopy;
	} // getUrlText

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // indentation

		if (this.getIdStatus() == false) {// id disabled
			HTML = HTML + "<a " + "href=" + "\"" + url + "\"" + ">" + linkText + "</a>";
		} else {// id enabled
			HTML = HTML + "<a id=" + "\"" + this.getStringId() + "\"" + " href=" + "\"" + url + "\"" + ">" + linkText
					+ "</a>";
		} // else

		return HTML;
	} // genHTML

} // AnchorElement
