import java.util.Scanner;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

public class MessageOfTheDay {

	/**
	 * The alphabet in a list.
	 */
	private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	/**
	 * Page to visit for the Puzzle.
	 */
	private static final String URL_PUZZLE = "http://cswebcat.swansea.ac.uk/puzzle";
	
	/**
	 * Page to visit to get the answers.
	 */
	private static final String URL_SOLUTION = "http://cswebcat.swansea.ac.uk/message?solution=";
	
	/**
	 * Text needed at the end of puzzle to validate it.
	 */
	private static final String END_PART = "CS-230";
	
	/**
	 * Incorrect answer error message.
	 */
	private static final String ERROR_MSG = "That answer's wrong";
	
	/**
	 * URL is not valid error message.
	 */
	private static final String BAD_URL = "Bad URL!";
	
     /* *
	 * Returns the message of the day.
	 * @return message of the day
	 */
	public static String getMsgDay() {
		String input = getInformation(URL_PUZZLE);
		String out = String.valueOf(input.length() + END_PART.length());

		int counter = 1;

		for (char c : input.toCharArray()) {
			int i = c - 65 + (counter % 2 == 0 ? counter : -counter);
			while (i < 0) {
				i += 26;
			}
			out += ALPHABET[i % 26];

			counter++;
		}
		return getInformation(URL_SOLUTION + out + END_PART);
	}
	
	/**
	 * Returns webpage output from given URL
	 * @param webURL URL to visit
	 * @return string from webpage
	 */
	private static String getInformation(String webURL) {
		URL url = null;
		Scanner sc = null;
		String out = "";
		try {
			url = new URL(webURL);
			sc = new Scanner(url.openStream());
			sc.useDelimiter("\n");
			while (sc.hasNext()) {
				out += sc.next();
			}
		} catch (MalformedURLException e2) {
			System.err.println(BAD_URL);
			// e2.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.err.println(ERROR_MSG);
			// e.printStackTrace();
			System.exit(0);
		} finally {
			sc.close();
		}
		return out;
	}
}
