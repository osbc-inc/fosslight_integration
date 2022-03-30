package fossid.getdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import integration.values.identifiedFilesValues;
import integration.values.loginValues;

public class getComponentsInfo {
	
	public void getCompoentinfo(String componentName, String componentVersion, String copyright) {
		
		identifiedFilesValues idValues = identifiedFilesValues.getInstance();		
		loginValues lvalues = loginValues.getInstance();		
		
		JSONObject dataObject = new JSONObject();
	    dataObject.put("username", lvalues.getFIDuserName());
	    dataObject.put("key", lvalues.getFIDapikey());
	    dataObject.put("component_name", componentName);
	    dataObject.put("component_version", componentVersion);
		
		JSONObject rootObject = new JSONObject();
	    rootObject.put("group", "components");
	    rootObject.put("action", "get_information");
		rootObject.put("data", dataObject);
		
		
		HttpPost httpPost = new HttpPost(lvalues.getFIDserverUri());
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);	
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + httpClientResponse.getStatusLine().getStatusCode());
			}	
								
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
			JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj1 = (JSONObject) jsonParser.parse(result.toString());	        
	        JSONObject jsonObj2 = (JSONObject) jsonParser.parse(jsonObj1.get("data").toString());
	        
	        if(!(jsonObj2.get("url") == null)) {
	        		idValues.setcomponentHompage(jsonObj2.get("url").toString());	        		
	        } else if(!(jsonObj2.get("supplier_url") == null)) {
    	       		idValues.setcomponentHompage(jsonObj2.get("supplier_url").toString());	        			    				
	        } else if(!(jsonObj2.get("community_url") == null)) {     				
	        		idValues.setcomponentHompage(jsonObj2.get("community_url").toString());    				
			} else {
	        		idValues.setcomponentHompage("");
			}	
	        
	        if(jsonObj2.get("download_url") == null) {
	        	idValues.setcomponentDownload("");
	        } else {
	        	idValues.setcomponentDownload(jsonObj2.get("download_url").toString());
	        }	        	
	        
	        if(copyright.equals("1")) {
	        	// set copyright
            	if(jsonObj2.get("copyright") == null) {
	        		idValues.setCopyright("");
	        	} else {
	        		idValues.setCopyright(jsonObj2.get("copyright").toString());
	        	}
	        }
	        
	        httpClient.close();
	        			
		} catch (Exception e) {
			e.printStackTrace();
	
		}
	}

}
