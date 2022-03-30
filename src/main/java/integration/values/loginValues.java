package integration.values;

public class loginValues {

	private static loginValues values = new loginValues();

	public loginValues() {
	}

	public static loginValues getInstance() {
		return values;
	}

	// FossID Login Values
	private String fid_serveruri;	
	private static String fid_username;
	private static String fid_apikey;
	
	public String getFIDserverUri() {
		return fid_serveruri;
	}

	public void setFIDserverUri(String fid_serveruri) {
		this.fid_serveruri = fid_serveruri;
	}
	
	public String getFIDuserName() {
		return fid_username;
	}

	public void setFIDuserName(String fid_username) {
		this.fid_username = fid_username;
	}
	
	public String getFIDapikey() {
		return fid_apikey;
	}

	public void setFIDapiKey(String fid_apikey) {
		this.fid_apikey = fid_apikey;
	}
	
	
	// Fosslight Login Values	
	private String flt_serveruri;		
	private static String flt_token;
	
	public String getFLTserverUri() {
		return flt_serveruri;
	}
	
	public void setFLTserverUri(String flt_serveruri) {
		this.flt_serveruri = flt_serveruri;
	}
	
	public String getFLTtoken() {
		return flt_token;
	}

	public void setFLTtoken(String flt_token) {
		this.flt_token = flt_token;
	}

	
	
}
