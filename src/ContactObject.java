
public class ContactObject {
	String mEmail;
	NameTitleObject mNto;
	String[] mKeywords;
	String mOrigin;
	
	public ContactObject(String email, NameTitleObject nto, String[] keywords, String origin){
		this.mEmail = email;
		this.mNto = nto;
		this.mKeywords = keywords;
		this.mOrigin = origin;
	}
	
	
}
