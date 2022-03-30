package fossid.getdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import integration.values.loginValues;
import integration.values.projectValues;

public class getProjectScanInfo {
	
	public void getInfo(String fid_projectName, String fid_scanName) {
		
		try {
			projectValues pvalues = projectValues.getInstance();
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream("/config.properties");
			props.load(is);
			
			// To change encoding to UTF-8
			String encoding = "";
			
			if(fid_projectName.equals("")) {
				encoding = new String(props.getProperty("fossid.project").getBytes("iso-8859-1"), "UTF-8");			
				pvalues.setFIDprojectName(encoding);
			} else {
				pvalues.setFIDprojectName(fid_projectName);
			}
			
			if(fid_scanName.equals("")) {
				encoding = new String(props.getProperty("fossid.scan").getBytes("iso-8859-1"), "UTF-8");			
				pvalues.setFIDscanName(encoding);
			} else {
				pvalues.setFIDscanName(fid_scanName);
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getFIDprojectId();
		getFIDscanId();
	}
	
	private void getFIDprojectId() {

		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();
				
		// create json to call FOSSID project/list_projects api 		
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", lvalues.getFIDuserName());
        dataObject.put("key", lvalues.getFIDapikey());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "projects");
        rootObject.put("action", "list_projects");
		rootObject.put("data", dataObject);		
		
		HttpPost httpPost = new HttpPost(lvalues.getFIDserverUri());
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();			
		
		try {

			StringEntity entity = new StringEntity(rootObject.toString());
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
			
			//System.out.println("result: " + result.toString());
			
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());
            JSONArray dataArray = (JSONArray) jsonObj.get("data");
            
            ArrayList<String> projectList = new ArrayList<String>();
            //set projectid
            for(int i=0; i < dataArray.size(); i++) {
                JSONObject tempObj = (JSONObject) dataArray.get(i);                                
                if(tempObj.get("project_name").equals(pvalues.getFIDprojectName())) {
                	pvalues.setFIDprojectId(tempObj.get("project_code").toString());
                }            
                projectList.add(tempObj.get("project_name").toString());
            }
            
            if(!projectList.contains(pvalues.getFIDprojectName())){
            	System.out.println("Please, check the fossid.project in the config.properties file");
            }
            
            httpClient.close();
            
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
		
	private void getFIDscanId() {

		loginValues lvalues = loginValues.getInstance();
		projectValues pvalues = projectValues.getInstance();			
		
		JSONObject dataObject = new JSONObject();
        dataObject.put("username", lvalues.getFIDuserName());
        dataObject.put("key", lvalues.getFIDapikey());
        dataObject.put("project_code", pvalues.getFIDprojectId());
		
		JSONObject rootObject = new JSONObject();
        rootObject.put("group", "projects");
        rootObject.put("action", "get_all_scans");
		rootObject.put("data", dataObject);
		
		HttpPost httpPost = new HttpPost(lvalues.getFIDserverUri());
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();	
		
		try {			
			// TO set UTF-8 Entity
			StringEntity entity = new StringEntity(rootObject.toString(), "UTF-8");
			
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(entity);
			
			HttpResponse httpClientResponse = httpClient.execute(httpPost);	
			
			if (httpClientResponse.getStatusLine().getStatusCode() != 200) {
				
				
				System.out.println("Please, check " + pvalues.getFIDscanName() + " is mapped to " + pvalues.getFIDprojectName() + " or ");
				System.out.println("check " + lvalues.getFIDuserName() + " is assigned to " + pvalues.getFIDprojectName());				
				
				System.out.println("Failed : HTTP Error code : " + httpClientResponse.getStatusLine().getStatusCode());
				System.exit(1);
			}					
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(httpClientResponse.getEntity().getContent(), "utf-8"));
			String result = br.readLine();
			
			JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj1 = (JSONObject) jsonParser.parse(result.toString());
            
           	JSONArray dataArray = (JSONArray) jsonObj1.get("data");      	        
     	    JSONObject data = new JSONObject();
     	    
     	    for(int i=0; i < dataArray.size() ;i++) {
     	    	JSONObject tempObj = (JSONObject) dataArray.get(i);
          	    if(pvalues.getFIDscanName().equals(tempObj.get("name"))) {
          	       	pvalues.setFIDscanId(tempObj.get("code").toString());
          	       	break;
          	    }        	  
     	    }      	    
     	    
     	   if(pvalues.getFIDscanId() == null) {
     		  System.out.println();
     		  System.out.println("Please, check the fossid.scanname in the config.properties file or assign a scan to a Project on FossID");
     	   }     	 
     	   
     	   httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
}
