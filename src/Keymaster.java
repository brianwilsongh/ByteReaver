
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class Keymaster {

	private static int wordCount = 0;

	public static HashMap<String, Integer> generateKeywordMap(String str) {
		HashMap<String, Integer> keywordMap = new HashMap<>();
		String[] wordsArray = str.split("\\s+");

		for (String word : wordsArray) {
			// it's a word, thus increment the total word counter
			wordCount++;
			String stringLowerCase = word.toLowerCase();
			if (keywordMap.containsKey(stringLowerCase)) {
				int count = keywordMap.get(stringLowerCase) + 1;
				keywordMap.remove(stringLowerCase);
				keywordMap.put(stringLowerCase, count);
			} else {
				keywordMap.put(stringLowerCase, 1);
			}
		}

//		System.out.println("Abathur: map before sort is " + keywordMap.toString());

		return keywordMap;
		// return an ArrayList of String arrays, each array containing ["word",
		// "frequency"]
	}

	private static ArrayList<String[]> convertMapToSortedArrayList(HashMap<String, Integer> unsortedMap) {
		// turn HashMap of keyword:frequency entries to a reverse order
		// ArrayList of string arrays in ["k", "v"] form
		// start with List
		List<HashMap.Entry<String, Integer>> entryList = new ArrayList<>(unsortedMap.entrySet());
//		System.out.println("Abathur.sortMap made list " + entryList.toString());

		// sort the list with Collections and a comaprator
		// compares keys reverse the normal order since we want descending
		Collections.sort(entryList, new Comparator<HashMap.Entry<String, Integer>>() {
			@Override
			public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		ArrayList<String[]> sortedKeywords = new ArrayList<>();
		for (HashMap.Entry<String, Integer> entry : entryList) {
			sortedKeywords.add(new String[] { entry.getKey().toString(), entry.getValue().toString() });
		}

		return sortedKeywords;
	}

	public static String[] topKeywords(HashMap<String, Integer> map) {
		ArrayList<String[]> list = convertMapToSortedArrayList(map);
		String[] topKeywords = new String[20];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			try {
				topKeywords[i] = list.get(i)[0];
			} catch (NullPointerException n) {
				//might get an NPE if there aren't even 'n' keywords
			}
		}
		
		return topKeywords;
	}

}