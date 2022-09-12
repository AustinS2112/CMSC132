package tubeVideosManager;

import java.util.ArrayList;

/**
 * Sample driver that illustrates some of the functionality of the system you
 * need to implement. A text file with the generated output is part of this code
 * distribution. Feel free to use this driver to write your student tests.
 * 
 * @author UMCP CS Department
 *
 */
public class SampleDriver {

	public static void main(String[] args) {
		String output = "";

		/* Creating video and adding comments */
		output += "============== One ==============\n";
		String title = "How to Draw in Java Tutorial";
		String url = "https://www.youtube.com/embed/ifVf9ejuFWI";
		int durationInMinutes = 17;
		Genre genre = Genre.Educational;
		Video video = new Video(title, url, durationInMinutes, genre);
		video.addComments("Nice video");
		video.addComments("Recommended");
		output += video;
		output += "Comments: " + video.getComments() + "\n\n";

		output += "============== Two ==============\n";
		/* Creating playlist and adding video */
		Playlist playlist = new Playlist("Favorites");
		playlist.addToPlaylist(title);
		playlist.addToPlaylist(title); // We can add title multiple times
		playlist.addToPlaylist("Hello");
		output += playlist + "\n";
		playlist.removeFromPlaylistAll(title);
		output += "After remove" + "\n";
		output += playlist + "\n\n";

		output += "============== Three ==============\n";
		TubeVideosManager tubeVideosManager = new TubeVideosManager();

		/* Adding first video */
		tubeVideosManager.addVideoToDB(title, url, durationInMinutes, genre);

		/* Adding second video */
		title = "Git & GitHub Crash Course for Beginners";
		url = "https://www.youtube.com/embed/SWYqp7iY_Tc";
		durationInMinutes = 33;
		genre = Genre.Educational;
		tubeVideosManager.addVideoToDB(title, url, durationInMinutes, genre);

		/* Getting videos */
		ArrayList<Video> videos = tubeVideosManager.getAllVideosInDB();
		output += videos + "\n\n";

		output += "============== Cuatro ==============\n";
		/* Loading Videos */
		tubeVideosManager = new TubeVideosManager();
		boolean printFeedback = false;
		tubeVideosManager.loadVideosToDBFromFile("videosInfoSet2.txt", printFeedback);
		videos = tubeVideosManager.getAllVideosInDB();
		output += videos + "\n";
		String playlistName = "ToWatch";
		tubeVideosManager.addPlaylist(playlistName);
		title = "One Day More";
		tubeVideosManager.addVideoToPlaylist(title, playlistName);
		playlist = tubeVideosManager.getPlaylist(playlistName);
		output += "Displaying Playlist\n" + playlist + "\n\n";
		output += tubeVideosManager.getStats() + "\n";

		output += "============== Five ==============\n";
		playlistName = "Music Videos";
		title = null; // Any title will match
		durationInMinutes = -1; // We don't care about duration
		Genre targetGenre = Genre.Music;
		playlist = tubeVideosManager.searchForVideos(playlistName, title, durationInMinutes, targetGenre);
		output += "Playlist Resulting from Search\n";
		output += playlist + "\n\n";

		output += "============== Six ==============\n";
		playlistName = "MySelection";
		tubeVideosManager.addPlaylist(playlistName);
		tubeVideosManager.addVideoToPlaylist("One Day More", playlistName);
		tubeVideosManager.addVideoToPlaylist("Better brain health | DW Documentary", playlistName);
		output += tubeVideosManager.getPlaylist(playlistName);
		String htmlFile = "sampleHTML.html";
		printFeedback = false;
		tubeVideosManager.genHTMLForPlaylist(htmlFile, playlistName, printFeedback);
		output += "\nFile " + htmlFile + " has HTML for playlist\n\n";

		/* Final Output */
		System.out.println(output);
	}
}
