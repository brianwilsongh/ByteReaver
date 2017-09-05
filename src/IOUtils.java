import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class IOUtils {

	public static void generateFile(String filename, HashSet<ContactObject> contactObjectSet,
			HashSet<String> visitedLinks, String infoString) {
		// first we will sort the HashSet
		ArrayList<ContactObject> contactList = new ArrayList<>();
		Iterator<ContactObject> itr = contactObjectSet.iterator();
		while (itr.hasNext()) {
			// add the HashSet items into an ArrayList for sorting/processing
			contactList.add((ContactObject) itr.next());
		}

		Collections.sort(contactList, new Comparator<ContactObject>() {
			@Override
			public int compare(ContactObject o1, ContactObject o2) {
				// TODO Auto-generated method stub
				return o1.mEmail.compareTo(o2.mEmail);
			}
		});

		StringBuilder builder = new StringBuilder();
		builder.append("ByteReaver 0.1 -- Extraction Timestamp (EDT) ")
				.append(LocalDateTime.now().toString().replace("T", " ")).append("\n").append(infoString).append("\n\n")
				.append("----------").append("\n").append("CONTACTS").append("\n").append("----------").append("\n\n");
		try {
			for (ContactObject item : contactList) {
				// add all the contact objects into the builder
				builder.append(item.mEmail);
				builder.append("  ,  ");
				if (item.mNto != null) {
					builder.append(item.mNto.printFull());
					builder.append("  ,  ");
				} else {
					builder.append("  ,  ");
				}
				
				
				builder.append("keywords:[");
				for (String kw : item.mKeywords){
					builder.append(kw + ";");
				}
				builder.append("]");
				
				builder.append("  ,  ");
				builder.append("origin:<");
				builder.append(item.mOrigin + ">");
				builder.append("\n");
			}

			builder.append("\n\n").append("----------").append("\n").append("VISITED").append("\n").append("----------")
					.append("\n\n");

			for (String url : visitedLinks) {
				builder.append(url);
				builder.append("\n");
			}

			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeFile(String filename, String content) {
		String ldt = LocalDateTime.now().toString().replace("T", " ");
		try {
			content = ldt + "\n" + content;
			// write into a file in the same directory
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
