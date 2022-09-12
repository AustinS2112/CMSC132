package model;

/**
 * Represents an &lt;img&gt; tag. For this project you can assume we will not
 * update any of the attributes associated with this tag.
 * 
 * @author UMCP
 *
 */
public class ImageElement extends TagElement {
	private String imageURL;
	private int width;
	private int height;
	private String alt;
	private String attributes;

	public ImageElement(String imageURL, int width, int height, String alt, String attributes) {
		super("img", false, null, attributes);
		this.imageURL = imageURL;
		this.width = width;
		this.height = height;
		this.alt = alt;
		this.attributes = attributes;
	}// Constructor

	public String getImageURL() {
		return imageURL;
	}// getImageURL

	public String genHTML(int indentation) {
		String HTML = "";

		for (int i = 0; i < indentation; i++) {
			HTML = HTML + " ";
		} // for

		if (this.getIdStatus() == true) {// Id enabled
			HTML = HTML + "<img id=" + "\"" + this.getStringId() + "\"" + " src=" + "\"" + this.getImageURL() + "\""
					+ " width=" + "\"" + this.width + "\"" + " height=" + "\"" + this.height + "\"" + " alt=" + "\""
					+ this.alt + "\"" + ">";
		} else {// Id disabled
			HTML = HTML + "<img" + " src=" + "\"" + this.getImageURL() + "\"" + " width=" + "\"" + this.width + "\""
					+ " height=" + "\"" + this.height + "\"" + " alt=" + "\"" + this.alt + "\"" + ">";
		} // else

		return HTML;
	} // genHTML

}// ImageElement
