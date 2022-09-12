package tubeVideosManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Implements the functionality of the videos manager. It implements the
 * TubeVideosManagerInt interface. The video manager keeps track of videos in an
 * ArrayList called videoDatabase. It also keeps track of playlists that have
 * been created using an Arraylist. We have declared these instance variables
 * for you.
 * 
 * Feel free to add any private instance variables and private methods you
 * understand you need. Do not add static methods, static variables or any
 * public methods except a default constructor.
 * 
 * Some methods have been provided; do not modify them.
 * 
 * @author UMCP CS Department
 *
 */
public class TubeVideosManager implements TubeVideosManagerInt {
	private ArrayList<Video> videoDatabase; // Represents the videos database
	private ArrayList<Playlist> playlists; // Represents the playlist

	/**
	 * Creates any necessary data structures and performs any required
	 * initialization.
	 */
	public TubeVideosManager() {
		videoDatabase = new ArrayList<Video>();
		playlists = new ArrayList<Playlist>();

	}// TubeVIdeosManager

	/**
	 * Creates a video based on the parameter information and adds it to the
	 * videoDatase ArrayList. Returns true if video was added and false otherwise
	 * (e.g., invalid parameters were provided). This method will not check for the
	 * validity of parameters. If an exemption takes place while creating a video,
	 * the method will print the error message returned by the exception and return
	 * false. Use System.err.println to print the error message.
	 * 
	 * @return true if video added; false otherwise
	 */
	public boolean addVideoToDB(String title, String url, int durationInMinutes, Genre videoGenre) {

		try {
			Video video = new Video(title, url, durationInMinutes, videoGenre);
			videoDatabase.add(video);
			return true;

		} catch (IllegalArgumentException e) {
			System.err.println("Invalid parameters");
			return false;
		} // catch

	}// addVideoToDB

	/**
	 * Returns an ArrayList with copies of the videos in the database. Modifications
	 * to the returned ArrayList should not affect the database.
	 * 
	 * @return ArrayList of Video references
	 */
	public ArrayList<Video> getAllVideosInDB() {
		ArrayList<Video> copyList = new ArrayList<Video>(videoDatabase);

		return copyList;
	}// getALlVideosInDB

	/**
	 * Find a video in the database, returning a copy of the video if found;
	 * otherwise null will be returned. If the parameter is null or blank (according
	 * to the String class isBlank() method) an IllegalArgumentException (with any
	 * error message) will be thrown.
	 * 
	 * @return reference to copy of Video found or null
	 * 
	 */
	public Video findVideo(String title) {

		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Invalid title");
		} else {
			for (int i = 0; i < videoDatabase.size(); i++) {
				if (videoDatabase.get(i).getTitle() == title) {
					return new Video(videoDatabase.get(i));
				} // if
			} // for
			return null;
		} // else
	}// findVideo

	private Video getVideo(String title) {

		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Invalid title");
		} else {
			for (int i = 0; i < videoDatabase.size(); i++) {
				if (videoDatabase.get(i).getTitle() == title) {
					return videoDatabase.get(i);
				}
			} // for
			return null;
		} // else
	}// getVideo

	/**
	 * Adds the specified comments to the video with the specified title (if the
	 * video exists). If the parameters are invalid (null or empty string) the
	 * method will return false.
	 * 
	 * @return true if comments added; false otherwise
	 */
	public boolean addComments(String title, String comments) {
		if (title == null || title.isBlank() || !findTitleName(title)) {
			return false;
		} else {
			getVideo(title).addComments(comments);// don't use findvideo
			return true;
		} // else
	}// addComments

	/**
	 * Creates and adds a playlist to the manager if a playlist with the specified
	 * name does not already exist. The method will throw an
	 * IllegalArgumentException (with any message) if the parameter is invalid (null
	 * or blank).
	 * 
	 * @return true if playlist has been added; false otherwise
	 */
	public boolean addPlaylist(String playlistName) {
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("Invalid playlist name");
		} else if (!findPlaylistName(playlistName)) {// if name doesn't already exist
			Playlist playlist = new Playlist(playlistName);
			playlists.add(playlist);
			return true;
		} else {
			return false;
		} // else
	}// addPlaylist

	/**
	 * Returns array with the names of playlists. An empty array will be returned if
	 * there are no playlists.
	 * 
	 * @return Array of String references.
	 */
	public String[] getPlaylistsNames() {
		String[] emptyArray = new String[1];
		String[] playlistNames = new String[playlists.size()];

		if (playlists.size() == 0) {
			return emptyArray;
		} else {
			for (int i = 0; i < playlistNames.length; i++) {
				playlistNames[i] = playlists.get(i).getName();
			} // for
			return playlistNames;
		} // else
	}// getPlaylistsNames

	private boolean findPlaylistName(String playlistName) {
		for (Playlist e : playlists) {
			if (e.getName() == playlistName) {
				return true;
			}
		} // for
		return false;
	}// Auxiliary method to check existence of playlistName

	private boolean findTitleName(String title) {
		for (Video e : videoDatabase) {
			if (e.getTitle() == title) {
				return true;
			} // if
		} // for
		return false;
	}// Auxiliary method to check existence of video title

	/**
	 * Adds the title to the specified playlist if the title exists in the database
	 * and the playlist also exists.
	 * 
	 * @return true if the title is added to the playlist; false otherwise
	 */
	public boolean addVideoToPlaylist(String title, String playlistName) {

		if (findPlaylistName(playlistName) && findTitleName(title)) {
			getPlaylist(playlistName).addToPlaylist(title);
			return true;
		} else {
			return false;
		} // else
	}// addVideoToPlaylist

	/**
	 * Returns a copy of the playlist associated with the specified name; null
	 * otherwise (e.g., if playlist is not found). If the parameter is null or is
	 * blank string (according to String class isBlank() method) the method will
	 * throw an IllegalArgumentException (with any message) and perform no
	 * processing.
	 * 
	 * @return Reference to playlist or null
	 */
	public Playlist getPlaylist(String playlistName) {
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("Invalid name");
		} else {
			return findPlayListInternal(playlistName);
		} // else
	}// getPlaylist

	/**
	 * Clears the database by removing all videos and all playlists.
	 */
	public void clearDatabase() {
		videoDatabase.clear();
		playlists.clear();
	}// clearDatabase

	private ArrayList<Integer> playlistVideoDurations(Playlist playlist) {
		ArrayList<Integer> durations = new ArrayList<Integer>();

		for (int i = 0; i < playlist.getPlaylistVideosTitles().size(); i++) {
			durations.add(findVideo(playlist.getPlaylistVideosTitles().get(i)).getDurationInMinutes());
		} // for

		return durations;
	}// returns arraylist of all duration times in a playlist

	private ArrayList<Genre> playlistGenres(Playlist playlist) {
		ArrayList<Genre> videoGenres = new ArrayList<Genre>();

		for (int i = 0; i < playlist.getPlaylistVideosTitles().size(); i++) {
			videoGenres.add(findVideo(playlist.getPlaylistVideosTitles().get(i)).getGenre());
		} // for

		return videoGenres;
	}// returns arraylist of all genres in a playlist

	/**
	 * Returns a playlist with videos that match the specified parameter criteria.
	 * The maximumDurationInMinutes represents the maximum duration allowed for a
	 * video. If a parameter is null or the duration is a negative value, that
	 * particular criteria will be ignored. For example, if we call the method with
	 * null, -1, null a playlist with the titles of all videos in the database will
	 * be returned. The returned playlist will not be added to the list of playlists
	 * kept by the system.
	 */
	public Playlist searchForVideos(String playlistName, String title, int maximumDurationInMinutes, Genre genre) {
		Playlist foundVideos = new Playlist("Found videos");

		if (playlistName == null && title == null && maximumDurationInMinutes < 0 || genre == null) {
			Playlist allVideos = new Playlist("All Videos");
			for (int i = 0; i < videoDatabase.size(); i++) {
				allVideos.addToPlaylist(videoDatabase.get(i).getTitle());
			}
			return allVideos;
		} else {
			for (int i = 0; i < playlists.size(); i++) {

				if (playlists.get(i).getName() == playlistName
						|| playlists.get(i).getPlaylistVideosTitles().contains(title)
						|| playlistVideoDurations(playlists.get(i)).contains(maximumDurationInMinutes)
						|| playlistGenres(playlists.get(i)).contains(genre)) {
					foundVideos.addToPlaylist(title);
				} // if

			} // for

			return foundVideos;
		} // else
	}// searchForVideos

	/**
	 * Provided; please don't modify. Loads video information from the specified
	 * file into the database represented by the ArrayList videoDatabase.
	 * 
	 * Information for a video will appear in the file as follows: title, url,
	 * duration and genre (string) each on a line by itself. A line
	 * ("===============================") will mark the end of an entry. The last
	 * entry will also have this line. Do not add empty lines after the line
	 * identifying the end of data.
	 * 
	 * This method relies on the addVideoToDB method to load the data into the
	 * database. Feedback information is printed on the console if requested.
	 * 
	 * @param filename
	 * @return true if data loaded; false otherwise
	 * 
	 *         This method is brought to you by Pepsi, the CS Department and the
	 *         NFAB (National Foundation Against Bugs).
	 */
	public boolean loadVideosToDBFromFile(String filename, boolean printFeedback) {
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}

		int count = 0;
		while (fileScanner.hasNextLine()) {
			String title = fileScanner.nextLine();
			String url = fileScanner.nextLine();
			int durationInMinutes = fileScanner.nextInt();
			fileScanner.nextLine(); // to consume \n
			String genreString = fileScanner.nextLine();
			fileScanner.nextLine(); // gets rid of line marking end of video info

			/* Mapping genre to Genre enum type must match one present in Genre.java */
			Genre videoGenre = null;
			for (Genre genre : Genre.values()) {
				if (genreString.equals(genre.toString())) {
					videoGenre = genre;
					break;
				}
			}
			if (videoGenre == null) {
				System.err.println("Invalid genre found while loading data");
				System.exit(0);
			}

			if (printFeedback) {
				String message = "Loading: " + title + ", ";
				message += url + ", ";
				message += durationInMinutes + ", ";
				message += videoGenre + ", ";
				System.out.println(message);
			}

			addVideoToDB(title, url, durationInMinutes, videoGenre);
			count++;
		}

		if (printFeedback) {
			System.out.println("Video entries loaded: " + count);
		}

		return true;
	}

	/**
	 * Provided; please don't modify. Statistics about video database and playlists
	 * 
	 * @return string with statistics
	 */
	public String getStats() {
		String answer = "***** Statistics *****\n";

		answer += "Number of video entries: " + videoDatabase.size() + "\n";
		answer += "Number of playlists: " + playlists.size() + "\n";

		for (Genre genre : Genre.values()) {
			int count = 0;
			for (Video video : videoDatabase) {
				if (genre.equals(video.getGenre())) {
					count++;
				}
			}
			answer += "Genre \"" + genre + "\" count " + count + "\n";
		}

		return answer;
	}

	/**
	 * Provided; please don't modify. Generates HTML for the specified playlist in
	 * the specified file. An IllegalArgumentException will be thrown if any
	 * parameter is null or if the playlist does not exist.
	 */
	public void genHTMLForPlaylist(String filename, String playlistName, boolean printFeedback) {
		Playlist playlist;
		String htmlBody = "";

		if (playlistName == null || playlistName.isBlank() || (playlist = findPlayListInternal(playlistName)) == null) {
			throw new IllegalArgumentException("TubeVideosManager genHTMLForPlaylist: Invalid parameter(s)");
		}
		htmlBody += "<h2>Playlist: " + playlistName + "</h2>\n";
		for (String title : playlist.getPlaylistVideosTitles()) {
			String url = findVideo(title).getUrl();

			htmlBody += "<strong>" + title + "</strong><br>";
			htmlBody += "<iframe width=\"100\" height=\"100\" ";
			htmlBody += "src=" + "\"" + url + "\">" + "</iframe><br><br>";
		}
		generateHTMLPageWithBody(filename, htmlBody, printFeedback);
	}

	/* ***** Private methods ***** */

	/*
	 * Returns reference to playlist if found and null otherwise. You must implement
	 * and use this method.
	 */
	private Playlist findPlayListInternal(String playlistName) {
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("TubeVideosManager findPlaylistInternal: Invalid parameter(s)");
		} else {
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getName() == playlistName) {
					return playlists.get(i);
				} // if
			} // for
			return null;
		} // else
	}// findPlayListInternal

	/*
	 * Provided; please don't modify. Generates full HTML document using body
	 * parameter as HTML document body.
	 */
	private static void generateHTMLPageWithBody(String filename, String body, boolean printFeedback) {
		String page = "<!doctype html>\n";

		page += "<html lang=\"en\">\n";
		page += "<head>\n";
		page += "<title>Manager</title>\n";
		page += "<meta charset=\"utf-8\" />\n";
		page += "</head>\n";
		page += "<body>\n";
		page += body + "\n";
		page += "</body>\n";
		page += "</html>\n";

		writeStringToFile(filename, page);
		if (printFeedback) {
			System.out.println(
					filename + " has been created (You may need to refresh the Eclipse project to see the file).");
		}
	}

	/*
	 * Provided; please don't modify.
	 * 
	 * Writes a string to a file
	 */
	private static boolean writeStringToFile(String filename, String data) {
		try {
			FileWriter output = new FileWriter(filename);
			output.write(data);
			output.close();
		} catch (IOException exception) {
			System.err.println("ERROR: Writing to file " + filename + " failed.");
			return false;
		}
		return true;
	}
}
