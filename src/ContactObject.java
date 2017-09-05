
public class ContactObject {
	String mEmail;
	PersonObject mPersonObject;
	String[] mKeywords;
	String mOrigin;
	
	public ContactObject(String email, PersonObject personObject, String[] keywords, String origin){
		this.mEmail = email;
		this.mPersonObject = personObject;
		this.mKeywords = keywords;
		this.mOrigin = origin;
	}
	
	
}
