package integration.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import integration.values.loginValues;;

public class setLoginInfo {
	
	public void setInfo(String fid_protocol, String fid_address, String fid_userName, String fid_apikey,
			String flt_protocol, String flt_address, String flt_token) {
		
		setFIDLogininfo(fid_protocol, fid_address, fid_userName, fid_apikey);
		
		setFLTLogininfo(flt_protocol, flt_address, flt_token);
	}
	
	private void setFIDLogininfo(String fid_protocol, String fid_address, String fid_userName, String fid_apiKey) {
		
		loginValues fid_lvalues = loginValues.getInstance();		
		
		try {
			
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream("/config.properties");
			props.load(is);
			
			String fid_schema = "";
			String fid_url = "";

			if(fid_protocol.equals("")) {
				fid_schema = props.getProperty("fossid.schema");
			} else {
				fid_schema = fid_protocol;
			}
			
			if(fid_address.equals("")) {
				fid_url = props.getProperty("fossid.domain");
			} else {
				fid_url = fid_address;
			}
			
			if(fid_schema.equals("http")) {
				
				fid_lvalues.setFIDserverUri("http://" + fid_url);
				
				String fid_temp = fid_lvalues.getFIDserverUri();
				fid_temp = fid_temp.substring(fid_temp .length() - 1, fid_temp.length());
				
				if(fid_temp.endsWith("/")) {
					fid_lvalues.setFIDserverUri("http://" + fid_url + "api.php");
				} else {
					fid_lvalues.setFIDserverUri("http://" + fid_url + "/api.php");
				}
				
			} else if(fid_schema.equals("https")) {
				fid_lvalues.setFIDserverUri("https://" + fid_url);

				String fid_temp = fid_lvalues.getFIDserverUri();				
				fid_temp = fid_temp.substring(fid_temp .length() - 1, fid_temp.length());
				
				if(fid_temp.endsWith("/")) {
					fid_lvalues.setFIDserverUri("https://" + fid_url + "api.php");
				} else {
					fid_lvalues.setFIDserverUri("https://" + fid_url + "/api.php");
				}
			}
			
			String fid_username = "";
			String fid_apikey = "";
			
			if(fid_userName.equals("")) {
				fid_username = props.getProperty("fossid.username");
			} else {
				fid_username = fid_userName;
			}
			
			if(fid_apiKey.equals("")) {
				fid_apikey = props.getProperty("fossid.apikey");
			} else {
				fid_apikey = fid_apiKey;
			}
			
			fid_lvalues.setFIDuserName(fid_username);			
			fid_lvalues.setFIDapiKey(fid_apikey);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void setFLTLogininfo(String flt_protocol, String flt_address, String flt_token) {
		
		loginValues flt_lvalues = loginValues.getInstance();		
		
		try {
			
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream("/config.properties");
			props.load(is);
			
			String flt_schema = "";
			String flt_url = "";

			if(flt_protocol.equals("")) {
				flt_schema = props.getProperty("fosslight.schema");
			} else {
				flt_schema = flt_protocol;
			}
			
			if(flt_address.equals("")) {
				flt_url = props.getProperty("fosslight.domain");
			} else {
				flt_url = flt_address;
			}
			
			if(flt_schema.equals("http")) {
				flt_lvalues.setFLTserverUri("http://" + flt_url);
			} else if(flt_schema.equals("https")) {				
				flt_lvalues.setFLTserverUri("https://" + flt_url);
			}

			String fltToken = "";			
			
			if(flt_token.equals("")) {
				fltToken = props.getProperty("fosslight.token");
			} else {
				fltToken = flt_token;
			}
			
			flt_lvalues.setFLTtoken(fltToken);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	
	}

}
