import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.rules.Timeout;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Main {
	

	// TEST HashSet for name/title objects
	private static HashSet<PersonObject> masterSetPersonObjects = new HashSet<>();

	// the origin of this crawl, first arg to main
	private static String originUrl;

	// file I/O
	private static String filename = "untitled";
	private static FileWriter fileWriter;
	private static BufferedWriter bufferedWriter;
	private static PrintWriter printWriter;

	// cap on emails to extract (if any), third arg to main
	private static int extractionCap = 100;

	// cap on pages to hit, third arg to main
	private static int pageHitCap = 1000;

	// the variable to reference the webdriver
	private static WebDriver driver;

	// arraylists to store visited and unvisited urls, and the HashSet of
	// ArrayLists to store contact objects
	private static HashSet<String> visitedLinks = new HashSet<>();
	private static LinkedList<String> collectedLinks = new LinkedList<>();
	private static HashSet<ContactObject> masterContactSet = new HashSet<>();
	private static HashMap<String, Integer> masterKeywordMap = new HashMap<>();

	// HashMap of keywords only for the current page
	private static HashMap<String, Integer> currentPageKeywordMap = new HashMap<>();

	// boolean to terminate the program early based on certain conditions
	private static boolean crawlComplete;

	// integer to store pages hit
	private static int pagesHit = 0;

	public static void main(String[] args) {
		// set up the Webdriver, currently chrome driver
		initiateWebdriver();
		// set up all the arguments supplied by the user

		setupArgs(args);
		long initialTime = System.nanoTime();

		// first round
		pagesHit++;
		driver.get(originUrl);
		visitedLinks.add(originUrl);
		handleJavascriptAlert(driver);
		mapKeywords(driver.findElement(By.tagName("body")).getText());
		pullContacts(driver.findElement(By.tagName("body")).getText(), originUrl);
		pullLinks(driver.getPageSource());

		crawlComplete = false;

		//create PrintWriter for appending to the the output file
		try {
			filename = "Output_".concat(new SimpleDateFormat("MM.dd.yyyy").format(new Date()))
					.concat("_" + NetworkUtils.getHostName(originUrl) + ".txt");
			
			fileWriter = new FileWriter(filename);
			bufferedWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(bufferedWriter);
			String startTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
			printWriter.println("START ".concat(startTime).concat("\n"));
			bufferedWriter.flush();
			
			try {
				Runtime.getRuntime().addShutdownHook(new ShutdownThread());
			} catch (Exception e){
				e.printStackTrace();
			}
			
		} catch (IOException e ) {
			e.printStackTrace();
		}

		while (!crawlComplete) {

			if (!collectedLinks.iterator().hasNext()) {
				// if there are no more collected links, it's over!
				crawlComplete = true;
			} else if (masterContactSet.size() >= extractionCap || pagesHit >= pageHitCap) {
				// if we've met the extraction or page hit cap, it's over!
				crawlComplete = true;
			} else {
				// if there's more, visit the next one
				visitUrl(collectedLinks.iterator().next());
			}
			try {
				Thread.sleep((int) Math.random() * 300 + 1600 + (int) Math.random() * 300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Main.class -- CRAWL COMPLETE in " + ((System.nanoTime() - initialTime) / 1000000000 / 60)
				+ "m -- now writing to file");
	}
	
	static class ShutdownThread extends Thread {
		public void run() {
			//ensure that exit stats are calculated and printWriter closed at end
			System.out.println("Trigger shutdown hook");
			String startTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
			printWriter.println();
			printWriter.println();
			printWriter.println("END ".concat(startTime));
			printWriter.println();
			printWriter.print(
					"Contacts: ".concat(String.valueOf(masterContactSet.size()) + "/" + extractionCap + " -- Queries: ")
							.concat(String.valueOf(pagesHit) + "/" + pageHitCap + " -- Urls Visited: "
									+ String.valueOf(visitedLinks.size() + "\n")));
			printWriter.print("Full-Search-Keywords: " + String.join("_", Keymaster.topKeywords(masterKeywordMap)));
			
			printWriter.close();
		}
	}

	private static void setupArgs(String[] args) {
		if (args.length == 0) {
			originUrl = "http://www.nytimes.com";
		} else if (args.length > 0) {
			if (!(args[0] == null)) {
				// if the user supplied url correctly, set the first arg as origin URL
				originUrl = args[0];
			}
			if (!(args[1] == null)) {
				// if user supplied a extraction cap, set it now
				extractionCap = Integer.valueOf(args[1]);
			}
			if (!(args[2] == null)) {
				// if user supplied a page hit cap, set it here
				pageHitCap = Integer.valueOf(args[2]);
			}
		}
	}

	// CRAWLER METHODS!
	private static void visitUrl(String url) {
		// handle all the logic of visiting a single URL here, including
		// extracting links/info
		pagesHit++;
		visitedLinks.add(url);
		cleanCollectedLinks();
		// before attempting to hit the page, add link to visited and clean
		try {
			System.out.println(
					"Query#" + pagesHit + " Contacts: " + masterContactSet.size() + " , currently at address: " + url);

			driver.get(url);
			// try to visit the URL, catch if there is a Timeout Exception

			//trigger this method no matter what to prevent js alert from ending run?
			handleJavascriptAlert(driver);

			String theHtml = driver.getPageSource();
			String theBody = driver.findElement(By.tagName("body")).getText();

			// pull out the keywords
			mapKeywords(theBody);

			// pull out the emails
			pullContacts(theBody, url);

			// extract links into the right sets, clean to make sure
			if (theHtml != null) {
				pullLinks(theHtml);
			}

			// clean tracks and wait (THIS IS UNTESTED)
			driver.manage().deleteAllCookies();
			return;

		} catch (Exception e) {
			// timeout exception possible, so catch and consider URL visited,
			// kill browser and spawn a new instance
			System.out.println(url + " took too long to load or failed to handle JS popup!");
			driver.close();
			initiateWebdriver();
		}
	}

	private static void pullContacts(String theBody, String currentUrl) {
		// create a hashset from .purify function of page
		HashSet<String> tempSetEmail = RegexUtils.findEmails(theBody, originUrl);
		HashSet<PersonObject> tempSetPersonObject = RegexUtils.findNames(theBody);

		masterSetPersonObjects.addAll(tempSetPersonObject); // add all to global holder, but we'll
											// see if it's necessary
		if (tempSetEmail.size() > 0) {
			for (String emailItem : tempSetEmail) {
				// for each email collected on this page			
				if (!emailInMasterContactSet(emailItem)) {
					// if not already collected, get ready to make a ContactObject
					PersonObject thisPersonObject = null;
					String[] keywordArray = Keymaster.topKeywords(currentPageKeywordMap);
					
					if (tempSetPersonObject.size() > 0) {
						// if ContactObjects were discovered, try associating them
						for (PersonObject po : tempSetPersonObject) {
							// check name/email matching
							if (Utils.emailMatchesPersonObject(emailItem, po)) {
								// add NTO with email into Contact object
								thisPersonObject = po;
							}

						}
					} 
					
					masterContactSet.add(new ContactObject(emailItem, thisPersonObject, keywordArray, currentUrl));
					try {
						if (emailItem != null){
							printWriter.println(emailItem);
						}
						if (thisPersonObject != null){
							printWriter.print(", " + thisPersonObject.printFull());
						}
						if (keywordArray != null){
							printWriter.print(", " + String.join("_", keywordArray));
						}
						printWriter.println();
						bufferedWriter.flush();
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void pullLinks(String htmlPage) {
		// this method pulls links from a page, if they haven't been visited,
		// add into unvisited ArrayList<URL>

		// call sister method to pull relative links
		pullRelativeLinks(htmlPage);

		// test link pull with selenium
		Document doc = Jsoup.parse(htmlPage);
		Elements links = doc.select("a[href]");

		for (Element link : links) {

			String possibleUrl = link.absUrl("href");

			if (!possibleUrl.equals("") && !detectHoneypot(link)) {
				// if the link attr isn't empty, and honeypot not detected, make
				// a URL
				URL theUrl = NetworkUtils.makeURL(possibleUrl, originUrl);

				if (RegexUtils.urlDomainNameMatch(originUrl, theUrl.toString())) {
					// if the string version of url is within the same domain as
					// original query
					if (!visitedLinks.contains(theUrl.toString())
							&& !RegexUtils.unwantedUrlDestination(theUrl.toString())) {
						// If the link isn't contained within the visitedLinks
						// set and isn't a file
						// System.out.println("Adding Undiscovered URL: " +
						// theUrl.toString());
						collectedLinks.add(theUrl.toString());
					}
				}
			}

		}
	}

	private static void pullRelativeLinks(String htmlPage) {
		// this method pulls RELATIVE links from a page, if they haven't been
		// visited, add into unvisited ArrayList<URL>

		Document doc = Jsoup.parse(htmlPage);
		Elements links = doc.select("a[href]");

		for (Element link : links) {
			// System.out.println(link.toString());
			String regex = "(href=\"?\'?)([a-zA-Z0-9/\\-_\\.&'%]+)\"?\'?";
			// regex to find the content of the href attribute
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(link.toString());

			if (matcher.find() && !detectHoneypot(link)) {
				if (!matcher.group(2).startsWith("http") && !RegexUtils.unwantedUrlDestination(matcher.group(2))) {
					// if the relative link doesn't start with redirect, mailto,
					// javascript, or absolute http link
					String relativeUrl = matcher.group(2);

					if (!relativeUrl.startsWith("/")) {
						relativeUrl = "/".concat(relativeUrl);
						// make sure there's a slash in the relative Url before
						// concatting to origin!
//						System.out.println("relativeUrl did not start with a slash! Fixed to: " + relativeUrl);
					}

					String absLink;
					// absolute link stored as this String

					if (!relativeUrl.startsWith("//")) {
						absLink = originUrl.concat(relativeUrl);
					} else {
						// this relativeUrl is a protocol-relative URL, so build
						// using original protocol
						absLink = NetworkUtils.makeURL(originUrl, originUrl).getProtocol().concat(":")
								.concat(relativeUrl);
						System.out.println("Main.pullRel discovered protocol-relative URL: " + absLink);
					}
					// System.out.println("Main.PullRel found URL at " +
					// absLink);
					if (!visitedLinks.contains(absLink)) {
						// if the Url from the relative link is deemed good and
						// is not null, add into collectedLinks
						try {
							String refinedUrl = NetworkUtils.makeURL(absLink, originUrl).toString();
							if (refinedUrl != null) {
								collectedLinks.add(refinedUrl);
							}
						} catch (NullPointerException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
	}

	private static void cleanCollectedLinks() {
		// iterator to go over and clean out collectedLinks HashSet
		for (Iterator<String> itr = visitedLinks.iterator(); itr.hasNext();) {
			String thisURL = (String) itr.next();
			if (urlInLinkedList(NetworkUtils.makeURL(thisURL, originUrl), collectedLinks)) {
				try {
					collectedLinks.remove(thisURL);
				} catch (NullPointerException e) {
					System.out.println("MA.cleanCollected threw NullPointerException on " + thisURL);
					e.printStackTrace();
				}
			}
			if (RegexUtils.unwantedUrlDestination(thisURL)) {
				// test if link ends in filename, if so delete
				try {
					collectedLinks.remove(thisURL);
				} catch (NullPointerException e) {
					System.out.println("MA.cleanCollected threw NullPointerException on " + thisURL);
					e.printStackTrace();
				}
			}
		}

	}

	private static void mapKeywords(String input) {
		// pull the keywords from a page to put into page and master HashMaps
		String hostName = NetworkUtils.getHostName(originUrl);
		currentPageKeywordMap = Keymaster
				.generateKeywordMap(RegexUtils.cleanText(input, hostName, true, true, true, true, false));
		masterKeywordMap.putAll(currentPageKeywordMap);
		return;
	}

	private static boolean urlInLinkedList(URL url, LinkedList<String> list) {
		// checks if the URL is in a provided HashSet with an improved for loop
		boolean returnBoolean = false;

		for (String item : list) {
			if (NetworkUtils.urlHostPathMatch(NetworkUtils.makeURL(item, originUrl), url)) {
				// Log.v("DLAsync.urlInHashSet", " just found " + url.toString()
				// + " in " + set.toString());
				returnBoolean = true;
			}
		}
		return returnBoolean;
	}

	private static boolean emailInMasterContactSet(String email) {
		// check if email was already stored in the master contact hash to
		// prevent duplicates, even if origins are different, emails are primary identifier of contact objects
		boolean flag = false;
		for (ContactObject co : masterContactSet) {
			// System.out.println("comparing current email -- " + email + "--
			// with master contact set email -- " + contact.get(0) );
			if (co.mEmail.equals(email)) {
				// System.out.println("emailInContactSet comparison: " +
				// contact.get(0) + " and " + email + " returns: " +
				// (contact.get(0) == email));
				flag = true;
			}
		}
		return flag;
	}
	// END OF CRAWLER METHODS!!

	public static void initiateWebdriver() {

		// set chrome to ignore loading images and css passing in special
		// capability
		HashMap<String, Object> images = new HashMap<String, Object>();
		images.put("images", 2);

		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values", images);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		// ignore alert() if it appears
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
		dc.setCapability(ChromeOptions.CAPABILITY, options);
		// set up WebDriver and link to the binary
		//TODO: Package such that the driver will be assumed to be in the same directory as initialization path
		File file = new File(System.getProperty("user.dir") + "/chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		// set it as a ChromeDriver, set it to the binary
		driver = new ChromeDriver(dc);

		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		// set the timeout to X seconds
	}


	public static void handleJavascriptAlert(WebDriver driver) {
		// wait and intercept JS alert if it occurs on a page
		try {
			// time interval as second arg of constructor is in seconds
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// exception means no popup, do nothing
		}
		;
	}

	public static boolean detectHoneypot(Element element) {
		// detect whether an element is set to display:none, these shouldn't be crawled
		String idOfElement = null;
		String classOfElement = null;

		try {
			idOfElement = element.attributes().get("id");
			classOfElement = element.attributes().get("class");
			// System.out.println("element: " + element.toString() + " id: " +
			// idOfElement + " class: " + classOfElement);

			if (!idOfElement.equals(null) && !idOfElement.isEmpty()) {
				// if the id of element exists and is styled as display:none,
				// could be a trap so return true
				if (driver.findElement(By.id(idOfElement)).getCssValue("display").equals("none")) {
					//element is no good
					
					return true;
				}
			}

			if (!classOfElement.equals(null) && !classOfElement.isEmpty()) {
				// if the class of element exists and is styled as display:none,
				// could be a trap so return true
				if (driver.findElement(By.className(classOfElement)).getCssValue("display").equals("none")) {
					System.out.println("Main.detectHoneypot - display:none link or trap detected");
					return true;
				}
			}

			if (element.attr("style").contains("none") && element.attr("style").contains("display")) {
				System.out.println("found inline style that hides the damn thing: " + element.attr("style"));
				System.out.println("Main.detectHoneypot - display:none link or trap detected");
				return true;
			}

		} catch (Exception e) {
			// do nothing
		}

		return false;
	}

}