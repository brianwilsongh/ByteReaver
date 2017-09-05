import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static HashSet<String> findEmails(String input, String origin) {

		// HashSet that will contain emails from input string
		HashSet<String> emailsDiscovered = new HashSet<>();

		// take out everything except the emails within the string, return as array
		// split input into array using delimiter of unlimited whitespace to capture everything
		String[] splitWordArray = input.split("\\s+");

		for (String word : splitWordArray) {

			if (word != null) {
				// if the word fits the format of an email (e.g.john.doe@email.com), consider it for HashSet

				// strip out only the text between potential HTML tags, filter
				// out filenames and API things
				// group 1 is 'username' group 2 is subdomain of url if exists, regex just negates chars that aren't valid for usernames
				// group 3 is domain like 'gmail' group 4 is like '.com'
				
				//RFC822 compliant regex for emails
				Pattern pattern = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
				Matcher matcher = pattern.matcher(word);
				if (matcher.find()) {
					
					word = matcher.group();
					word = word.replaceAll("mailto:", "");
					emailsDiscovered.add(word.toLowerCase());
					System.out.println("RegexUtils.purify discovered email: " + word);

				}
			}
		}

		// turn the hash set into an array of strings
		return emailsDiscovered;

	}

	public static HashSet<PersonObject> findNames(String input) {
		//get rid of non-name words that might register as names
		Pattern p = Pattern.compile("[A-Z][a-z]+");
		Matcher m = p.matcher(input);
		while (m.find()){
			if (Utils.wordInFilter(m.group(), Filters.internetCommon)){
				input = input.replaceAll(m.group(), "x");
			}
		}

		Pattern pattern = Pattern.compile(
				"([A-Z][a-z\\-]+)_([A-Z]\\.?_)?([A-Z][A-Za-z\\-\\']+)");
		Matcher matcher = pattern.matcher(input.replaceAll(" ", "_"));
		HashSet<PersonObject> temp = new HashSet<>();
		while (matcher.find()) {
			try {
//				System.out.println("RegexUtils HIT|||--- " + matcher.group() + "\n");

				// store these fields to create a PersonObject
				String fName = "";
				String mInitial = "";
				String lName = "";

				boolean flag = true;
				// flag to check if the potential PersonObject is valid - has first & last name)

				if (matcher.group(1) != null && !Utils.wordInFilter(matcher.group(1), Filters.frequentEnglish) && 
						!Utils.wordInFilter(matcher.group(1), Filters.internetCommon)) {
					// group 1 should be the first name
					fName = matcher.group(1);
				} else {
					// PersonObject needs a valid first name!
//					System.out.println("REJECT -- First Name " + matcher.group(1));
					flag = false;
				}

				if (matcher.group(2) != null && !Utils.wordInFilter(matcher.group(2), Filters.frequentEnglish)) {
					// group 2 should be middle initial
					mInitial = matcher.group(2).replaceAll("_", "");
				}

				if (matcher.group(3) != null && !Utils.wordInFilter(matcher.group(3), Filters.frequentEnglish) && 
						!Utils.wordInFilter(matcher.group(3), Filters.internetCommon)) {
					// group 3 should be the last name, don't add if no first
					// name
					lName = matcher.group(3);
				} else {
					// NTO needs a valid last name!
//					System.out.println("REJECT -- Last name" + matcher.group(2));
					flag = false;
				}
				 

				if (flag) {
					// PersonObject is legit because flag is true, create new one with given info
					System.out.println("Creating new Person Object: " + fName + ", " + lName + ", \n");
					temp.add(new PersonObject(fName, mInitial, lName));
				}

			} catch (Exception e) {
				// e.printStackTrace();
			}

		}

		return temp;

	}

	public static boolean urlDomainNameMatch(String urlA, String urlB) {
		// check if the host names of two urls match, regardless of www in front
		// of them or not
		// this currently checks if the url pulled by Jsoup matches the base URL
		// input by the user

		// build a url to make string for A, then B
		String hostA = "";
		try {
			URL builtUrlA = new URL(urlA);
			hostA = builtUrlA.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String hostB = "";
		try {
			URL builtUrlB = new URL(urlB);
			hostB = builtUrlB.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// now extract substring from strings, domain name, to take care of www
		// or not www in URLs
		String siteNameA = "";
		// search for the pattern I want, like "google.com" in
		// http://www.google.com/maps using RegEx
		Pattern patternA = Pattern.compile("([^\\.]+)\\.(co.)?([^\\.]+)$");
		Matcher matcherA = patternA.matcher(hostA);
		if (matcherA.find()) {
			siteNameA = matcherA.group();
		}

		String siteNameB = "";
		// search for the pattern I want, like "google.com/" in
		// http://www.google.com/maps
		Pattern patternB = Pattern.compile("([^\\.]+)\\.(co.)?([^\\.]+)$");
		Matcher matcherB = patternB.matcher(hostB);
		if (matcherB.find()) {
			siteNameB = matcherB.group();
		}

		return siteNameA.equals(siteNameB);

	}

	public static boolean unwantedUrlDestination(String url) {
		if (url.matches(".+(.jpg|.jpeg|.png|.gif|.pdf|.stm|.aspx|#|.xml|.json){1}$")
				|| url.matches("^(#|mailto:|tel:|redirect){1}.+") || url.contains("javascript")) {
			// if the url ends in a filename like those above, it's no good
			System.out.println("RegexUtils.unwanted deemed <" + url + "> to be undesireable destination url");
			return true;
		}
		return false;
	}

	/* KEYWORD SYSTEM METHODS BELOW */

	public static String cleanText(String input, String origin, boolean filterMonth, boolean filterDay,
			boolean filterCommon, boolean filterInternetCommon, boolean filterGeography) {
		// booleans are filters that take month, day, very common words like "a"
		// and "the"
		// split input into array to clean using delimiter of unlimited
		// whitespace to capture everything
		String[] dirtyWordArray = input.split("\\s+");

		StringBuilder cleanString = new StringBuilder();

		// use enhanced for loop to clean out non-alpha
		// include words with comma, period, exclamation, apostrophe at
		// start/fin, etc...
		for (String word : dirtyWordArray) {
			if (word.matches("[a-z[A-Z]]+") && !word.matches("null") && !origin.contains(word.toLowerCase())
					&& !word.matches("[EeCcMmPp][DdSs][Tt]")) {
				// if the word is made up entirely of alphabet chars
				if (passedKeywordFilter(word.toLowerCase(), filterMonth, filterDay, filterCommon, filterInternetCommon,
						filterGeography)) {
					cleanString.append(word);
					cleanString.append(" ");
				}

			} else if ((word.matches("[a-z[A-Z]]+\\!") || word.matches("[a-zA-Z]+\\.?")
					|| word.matches("[a-zA-Z]+\\??")) && !word.matches("null")
					&& !origin.contains(word.toLowerCase())) {
				// if the word is at the end of a sentence with a !, . or ?
				// then...
				if (passedKeywordFilter(word.toLowerCase(), filterMonth, filterDay, filterCommon, filterInternetCommon,
						filterGeography)) {
					cleanString.append(word.substring(0, word.length() - 1));
					cleanString.append(" ");
				}
			} else {
				cleanString.append("");
			}
		}
//		System.out.println("RegexUtils.cleanText -- cleanString is: " + cleanString);
		return cleanString.toString();

	}

	private static boolean passedKeywordFilter(String word, boolean filterMonth, boolean filterDay,
			boolean filterCommon, boolean filterInternetCommon, boolean filterGeography) {

		if (Utils.wordInFilter(word, Filters.monthStrings)) {
			return false;
		}
		if (Utils.wordInFilter(word, Filters.dayStrings)) {
			return false;
		}
		if (Utils.wordInFilter(word, Filters.commonStrings)) {
			return false;
		}
		if (Utils.wordInFilter(word, Filters.internetCommon)) {
			return false;
		}
		if (Utils.wordInFilter(word, Filters.geographyStrings)) {
			return false;
		}
		if (word.length() < 5) {
			// generally we do not want shorter words, specific nouns are rarely
			// short
			return false;
		}
		return true;

	}

}
