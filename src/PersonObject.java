
public class PersonObject {
	String mFirstName;
	String mMiddleInitial;
	String mLastName;

	public PersonObject(String firstName, String middleInitial, String lastName) {
		this.mFirstName = firstName;
		this.mMiddleInitial = middleInitial;
		this.mLastName = lastName;
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

		return sb.toString();
	}
}
