
public class NameTitleObject {
	String mFirstName;
	String mMiddleInitial;
	String mLastName;
	String mDegree;
	String mTitle;

	public NameTitleObject(String firstName, String middleInitial, String lastName, String degree, String title) {
		this.mFirstName = firstName;
		this.mMiddleInitial = middleInitial;
		this.mLastName = lastName;
		this.mDegree = degree;
		this.mTitle = title;
	}

	public String printFull() {
		StringBuilder sb = new StringBuilder();
		sb.append(mFirstName);
		sb.append(" ");

		sb.append(mMiddleInitial);
		if (mMiddleInitial != "") {
			sb.append(" ");
		}

		sb.append(mLastName);
		
//		if (mDegree != "") {
//			sb.append(" (");
//			sb.append(mDegree);
//			sb.append(") ");
//		}
		
		if (mTitle != null && mTitle != "") {
			sb.append(" - ");
			sb.append(mTitle);
		}

		return sb.toString();
	}
}
