package srwpe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SubRedditWallpaperMain extends Application{
	private double xDim=600.0,yDim=200.0;
	private String appName="Subreddit Wallpaper Grabber";
	private String defaultSubLink = "https://www.reddit.com/r/EarthPorn/";
	private String titleCSSStyle ="-fx-font: 18 \"Comic Sans MS\";";
	@Override
	public void start(Stage base) throws Exception {
		base.setTitle(appName);
		BorderPane root = new BorderPane();
		Scene baseScene = new Scene(root,xDim,yDim);
		
		Text title = new Text(appName);
		//TODO: If download_path in srwe_config.txt is null, then show Select Directory dialog to select a default location.
		
		title.setStyle(titleCSSStyle);
		TextField subredditLinkInput = new TextField(defaultSubLink);
		Button runButton = new Button("Run");
		//TODO: Run the image scraper when button is clicked.
		root.setTop(title);
		root.setCenter(subredditLinkInput);
		root.setBottom(runButton);
		base.setScene(baseScene);
		base.show();
		
	}
	
	public static void main (String args[]) {
		Application.launch(args);
	}
}
