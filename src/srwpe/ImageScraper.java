package srwpe;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImageScraper {
	private String url;
	private String defaultUrl="https://old.reddit.com/r/EarthPorn/top/";
	private String defaultCSSQuery="a[class='title may-blank'],[data-event-action='title']";
	private String downloadPath;
	
	public ImageScraper() {
		url = defaultUrl;
	}
	
	public ImageScraper(String url) {
		this.url = url;
	}
	/**
	 * Scrapes a sub-reddit's top posts for the day and returns an ArrayList of URLs as Strings.
	 * @return
	 */
	public ArrayList<String> getPostUrls() {
		ArrayList  <String>postUrls= new ArrayList<>();
		try {
			Document subredditHTML = Jsoup.connect(url).get();
			Elements postLinks = subredditHTML.select(defaultCSSQuery);
			
			for(Element e:postLinks) {
				String link = e.attr("href");
				postUrls.add(link);
				System.out.println(link);
			}
			
			return postUrls;
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		return postUrls;
	}
	/**
	 * Returns an ArrayList of Strings with the links to the main image of each post.
	 * @param postLinks
	 * @return
	 */
	public ArrayList<String> getImageLinks(ArrayList<String> postLinks) {
		ArrayList <String>imageLinks = new ArrayList<>();
		for(String post: postLinks) {
			try {
				Document postDoc = Jsoup.connect(post).get();
				Element postImage = postDoc.selectFirst("");
				String link = postImage.attr("href");
				imageLinks.add(link);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return imageLinks;
	}
	/**
	 * Downloads all url sources given their urls.
	 * @param imageLinks: ArrayList <String> of urls.
	 * @return 0 means successfully downloaded all files in the image links.
	 */
	public int downloadImages(ArrayList <String> imageLinks) {
		return 0;
	}
	public static void main(String args[]) {
		ImageScraper x = new ImageScraper();
		x.getPostUrls();
	}
}
