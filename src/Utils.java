
public final class Utils {

	public static boolean emailMatchesPersonObject(String email, PersonObject personObject) {
		// this is the LOCAL version of this method, can make a more strict
		// global version for matching between webpages in future?
		String username = email.split("@")[0].toLowerCase();

		if (username.matches(personObject.mFirstName.toLowerCase() + "\\.?" + personObject.mLastName.toLowerCase())
				|| username.matches(personObject.mFirstName.toLowerCase().charAt(0) + "\\.?" + personObject.mLastName.toLowerCase())) {
			// if username matches johndoe/john.doe@email.com or
			// jdoe/j.doe@email.com format
			return true;
		}

		if (username.matches(personObject.mFirstName.toLowerCase()) || username.matches(personObject.mLastName.toLowerCase())) {
			// if username matches john@email.com or doe@email.com
			return true;
		}

		if (username
				.matches(personObject.mFirstName.toLowerCase() + "\\.?" + personObject.mMiddleInitial.toLowerCase() + "\\.?"
						+ personObject.mLastName.toLowerCase())
				|| username.matches(personObject.mFirstName.toLowerCase() + "\\.?" + personObject.mMiddleInitial.toLowerCase())) {
			// if username matches johntdoe/john.t.doe@email or johnt/john.t@email.com 
			return true;
		}

		return false;
	}

	public static boolean wordInFilter(String word, String[] filter) {
		// check if String is in array of Strings (filter), check for plurals as
		// well
		String theWord = word.toLowerCase();
		boolean flag = false;
		for (String item : filter) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}
		return flag;
	}

	public static boolean wordInAnyFilter(String word) {
		// TODO: make this enitre implementation less sloppy
		// check if String is in array of Strings (filter), check for plurals as
		// well
		String theWord = word.toLowerCase();
		boolean flag = false;

		for (String item : Filters.commonStrings) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}

		for (String item : Filters.dayStrings) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}

		for (String item : Filters.dayStrings) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}

		for (String item : Filters.geographyStrings) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}


		for (String item : Filters.internetCommon) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}

		for (String item : Filters.monthStrings) {
			if (theWord.equals(item) || theWord.equals(item.concat("s"))) {
				flag = true;
			}
		}

		return flag;
	}
}