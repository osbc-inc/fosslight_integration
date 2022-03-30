package fossid.getdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import integration.values.identifiedFilesValues;
import integration.values.loginValues;
import integration.values.projectValues;

public class getIdentifiedFiles {
	
	public void getData(String copyright) {	
		
		getIDfiles(copyright);		
		
	}
	
	private void getIDfiles(String copyright) {

		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();
		identifiedFilesValues idValues = identifiedFilesValues.getInstance();
		getComponentsInfo coInfo = new getComponentsInfo();
		getFolderMatrics matrics = new getFolderMatrics();
	    
		String componentName = "";
		String componentVersion = "";
		
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", lvalues.getFIDuserName());
        dataObject.put("key", lvalues.getFIDapikey());
        dataObject.put("scan_code", pvalues.getFIDscanId());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "scans");
        rootObject.put("action", "get_identified_files");
		rootObject.put("data", dataObject);		
		
		HttpPost httpPost = new HttpPost(lvalues.getFIDserverUri());
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		try {
			
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				System.out.println("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
				System.exit(1);
			}					
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();		
			
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj1 = (JSONObject) jsonParser.parse(result.toString());
            JSONObject jsonObj2 = (JSONObject) jsonObj1.get("data");
            Iterator iter = jsonObj2.keySet().iterator();
                        
            int loopCount = 0;
            
            matrics.getMatrics();
            
            System.out.print("In progress to get identified results");

            while(iter.hasNext()) {
            	

            	if(loopCount%100 == 0) {
            		System.out.print(".");            		
            	}   
            	
            	if(idValues.gettotalIdcount() > 10000) {
            		if(loopCount%200 == 0) {
                		System.out.print(loopCount + "/"+ idValues.gettotalIdcount());            		
                	}
            	}          	
            	
            	loopCount++;
            	
            	// set key value to key
            	String key = (String) iter.next();         
            	// get values from key
            	JSONObject tempObj = (JSONObject) jsonObj2.get(key); 
            	
            	if(!(tempObj.get("component_name") == null)) {

          			//set file_path
                  	if(tempObj.get("file_path") == null) {
                   		idValues.setfilepath("");
                   	} else {
                   		idValues.setfilepath(tempObj.get("file_path").toString());            		
                   	}
                        	
                   	//set component_name
                   	if(tempObj.get("component_name") == null) {
                   		idValues.setcomponenetName("");
                   		componentName = "";
                    } else {
                    	idValues.setcomponenetName(tempObj.get("component_name").toString());
                        componentName = tempObj.get("component_name").toString();
                   	}
                        	
                   	//set component_version
                   	if(tempObj.get("component_version") == null) {
                   		idValues.setcomponentVersion("");
                   		componentVersion = "";
                   	} else {
                   		idValues.setcomponentVersion(tempObj.get("component_version").toString());
                   		componentVersion = tempObj.get("component_version").toString();
                   	}
                    
                   	if(copyright.equals("0")) {
                       	// set component_hompage,download
                       	coInfo.getCompoentinfo(componentName, componentVersion,copyright);
                        
                       	// set copyright
                    	if(tempObj.get("identification_copyright") == null) {
        	        		idValues.setCopyright("");
        	        	} else {
        	        		idValues.setCopyright(tempObj.get("identification_copyright").toString());
        	        	}	
                    	
                   	} else if(copyright.equals("1")){
                       	// set component_hompage,download,copyright
                       	coInfo.getCompoentinfo(componentName, componentVersion,copyright);
                   	}
                   	
                   	//set component_license_id
                   	if(tempObj.get("component_license_identifier") == null) {
                   		idValues.setcomponentLicenseId("");
                   	} else {
                   		idValues.setcomponentLicenseId(tempObj.get("component_license_identifier").toString());            		
                   	}
                       	
                   	// set comment
                   	if(tempObj.get("comment") == null) {
                   		idValues.setcommnet("");
                   	} else {
                   		idValues.setcommnet(tempObj.get("comment").toString());            		
                   	}
                }
            }
            
            System.out.println();            
            
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
}