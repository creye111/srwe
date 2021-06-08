package srwpe;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
///import javax.imageio.Image;
public class ImageScraper {
	private String url;
	private String defaultUrl="https://old.reddit.com/r/wallpapers/top/";
	private String defaultCSSQuery="a[class='title may-blank'],[data-event-action='title']";
	private String downloadPath;
	
	public ImageScraper() {
		url = defaultUrl;
	}
	
	public ImageScraper(String url) {
		if(url.startsWith("https://www.reddit.com")) {
			this.url =url.replace("https://www.reddit.com", "https://old.reddit.com");
			
		}
		else if(url.startsWith("www.reddit.com")) {
			this.url =url.replace("www.reddit.com", "https://old.reddit.com");
		
		}
		else if(url.startsWith("reddit.com")) {
			this.url =url.replace("reddit.com", "https://old.reddit.com");
		}
		else {
			this.url = url;
		}
		System.out.println("URL="+this.url);
		
	}
	/**
	 * Scrapes a sub-reddit's top posts for the day and returns an ArrayList of URLs as Strings.
	 * @return
	 */
	private ArrayList<String> getPostUrls() {
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
	private ArrayList<String> getImageLinks(ArrayList<String> postLinks) {
		ArrayList <String>imageLinks=null;
		if(postLinks != null) {
			imageLinks = new ArrayList<>();
			for(String post: postLinks) {
				if(post.startsWith("/r")) {	
					try {
						String getUrl = "https://old.reddit.com"+post;
						//System.out.println("Looking for images in: "+ getUrl);
						Document postDoc = Jsoup.connect(getUrl).get();
						Elements postImageList = postDoc.select("div.media-preview-content > a[href].may-blank ");
						String link;
						if(postImageList != null)
							for(Element i : postImageList) {
								link=i.attr("href");
								if(link!=null)
									imageLinks.add(link);
							}
						else
							link="";
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				else if(post.endsWith(".jpg")||post.endsWith(".png")) {
					imageLinks.add(post);
				}
				else {
					//do nothing?
					
				}
			}
		}
		return imageLinks;
	}
	/**
	 * Downloads all url sources given their urls.
	 * @param imageLinks: ArrayList <String> of urls.
	 * @return 0 means successfully downloaded all files in the image links.
	 * @throws Exception 
	 */
	private int downloadImages(ArrayList <String> imageLinks) throws Exception {
		Image img = null;
		String ext = ".jpg";
		int counter =0;
		String fileName = "image";
		if(imageLinks!=null) {
			for(String s:imageLinks) {
				URL url = new URL(s);
				if(s.endsWith(".jpg")) {
					ext = ".jpg";
					int res =downloadImage(url,fileName+counter+ext);
					
				}
				else if(s.endsWith(".png")){
					ext = ".png";
					int res =downloadImage(url,fileName+counter+ext);
				}
				else {
					//do nothing
					System.err.print("Invalid file extension for " + s);
				}
				counter++;
				
			}
		}
		else {
			return 1;
		}
		return 0;
	}
	private int downloadImage(URL src,String fileName) throws IOException {
		InputStream is = src.openStream();
		OutputStream os = new FileOutputStream(fileName);
		byte[] i = new byte[2048];
		int len;
		try {
			while((len = is.read(i))!=-1) {
				os.write(i,0,len);
			}
			is.close();
			os.close();
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}
	public int scrapeImages() {
		try {
			return downloadImages(getImageLinks(getPostUrls()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}
	public static void main(String args[]) {
		ImageScraper x = new ImageScraper();
		ArrayList <String>urls = x.getPostUrls();
		ArrayList <String> imgLinks = x.getImageLinks(urls);
		System.out.println("imageLinks::::");
		for(String s : imgLinks) {
			System.out.println(s);
		}
		
		
	}
}
