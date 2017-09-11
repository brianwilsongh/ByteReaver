import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

	public static URL makeURL(String string, String firstUrl) {

		// remove the trailing slash if there is one, for uniformity's sake
		if (string.endsWith("/")) {
			string = string.substring(0, string.length() - 1);
		}

		if (!string.startsWith("http")) {
			System.out.println("NUtils.makeURL failed to detect protocol in " + string);
			if (!string.toLowerCase().contains(firstUrl.toLowerCase())) {
				// if it finds a pulled link that is expressed as a relative
				// rather than absolute
				System.out.println("NUtils.makeURL detected relative href, " + string + " attempting to turn into "
						+ firstUrl + string);
				string = firstUrl + string;
			}
			// if the user forgot to put in the protocol, guess http and put it
			// in
			// TODO: find a way to see if the real URL is http or https and
			// append the correct one
			string = "http://" + string;
			System.out.println("NetworkUtils.makeURl appended (guessed) http:// protocol to make " + string);
		}

		URL returnURL = null;

		try {
			// make the URL out of the string
			returnURL = new URL(string);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("NUtils.makeURL failed to make url from: " + string);
		}
		return returnURL;
	}

	public static String insertWebSubdomian(String theURL) {
		if (theURL.matches("(http://){1}(www){0}[^(www)]+")) {
			System.out.println("NetworkUtils insertWebSubdomain found no www subdomain, fixing this url: " + theURL);
			StringBuilder temp = new StringBuilder(theURL);
			temp.insert(7, "www.");
			System.out.println("NetworkUtils" + " newly fixed: " + temp.toString());
			theURL = temp.toString();
		}

		if (theURL.matches("(https://){1}(www){0}[^(www)]+")) {
			System.out.println("NetworkUtils" + " makeURL found no www subdomain, fixing this url: " + theURL);
			StringBuilder temp = new StringBuilder(theURL);
			temp.insert(7, "www.");
			System.out.println("NetworkUtils newly fixed: " + temp.toString());
			theURL = temp.toString();
		}

		return theURL;
	}

	public static boolean urlHostPathMatch(URL urlA, URL urlB) {
		// check if the paths match of built URL objects
//			System.out.println("Comparing: " + urlA.toString() + " " + urlB.toString());
		try {
			// build a url to make string for A, then B
			String protocolA = urlA.getProtocol();
			String protocolB = urlB.getProtocol();

			String hostA = urlA.getHost();
			String hostB = urlB.getHost();
			// strip the www. out of the host A/B if it exists
			if (hostA.substring(0, 4).equals("www.")) {
				hostA = hostA.substring(4, hostA.length());
			}
			if (hostB.substring(0, 4).equals("www.")) {
				hostB = hostB.substring(4, hostB.length());
			}

			String pathA = urlA.getPath();
			String pathB = urlB.getPath();

			String rebuiltUrlA = protocolA + hostA + pathA;

			if (rebuiltUrlA.equals(protocolB + hostB + pathB)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getHostName(String urlA) {
		// just get the host eg 'www.example.com'
		URL theBuiltUrl = makeURL(urlA, null);
//		System.out.println("this should be like www.example.com -- test: " + theBuiltUrl.getHost());
		return theBuiltUrl.getHost();
	}
	
}
