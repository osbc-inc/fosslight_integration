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

public class getMarkedAsIdentifiedFiles {

	
	public void getData(String copyright) {	
		
		getmAsIDfiles(copyright);
		
	}
	
	private void getmAsIDfiles(String copyright) {

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
        rootObject.put("action", "get_marked_as_identified_files");
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

            int componentCount = 0;
            String comment = "";
            
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
            	
            	if(!(tempObj.get("components") == null)) {
            		
            		JSONObject jsonObj3 = (JSONObject) tempObj.get("components");
                    Iterator componentsIter = jsonObj3.keySet().iterator();
                    
                    while(componentsIter.hasNext()) {
                    	componentCount = 0;

                    	// set key value to key
                    	String compoKey = (String) componentsIter.next();
                    	// get values from key
                    	JSONObject compoObj = (JSONObject) jsonObj3.get(compoKey);

                    	// set file_path
                      	if(tempObj.get("file_path") == null) {
                       		idValues.setfilepath("");
                       	} else {
                       		idValues.setfilepath(tempObj.get("file_path").toString());            		
                       	}
                            	
                       	// set component_name
                       	if(compoObj.get("component_name") == null) {
                       		idValues.setcomponenetName("");
                       		componentName = "";
                        } else {
                        	idValues.setcomponenetName(compoObj.get("component_name").toString());
                            componentName = compoObj.get("component_name").toString();
                       	}
                            	
                       	//set component_version
                       	if(compoObj.get("component_version") == null) {
                       		idValues.setcomponentVersion("");
                       		componentVersion = "";
                       	} else {
                       		idValues.setcomponentVersion(compoObj.get("component_version").toString());
                       		componentVersion = compoObj.get("component_version").toString();
                       	}
                        
                       	// set copyright
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
                       	
                       	// set component_license_id
                       	if(compoObj.get("component_license_identifier") == null) {
                       		idValues.setcomponentLicenseId("");
                       	} else {
                       		idValues.setcomponentLicenseId(compoObj.get("component_license_identifier").toString());            		
                       	}
                       	
                        // set comment
                       	comment = "";                        
                        if(!(tempObj.get("comments") == null)) {
                            JSONObject jsonObj4 = (JSONObject) tempObj.get("comments");
                            Iterator commentsIter = jsonObj4.keySet().iterator();
                            
                            while(commentsIter.hasNext()) {
                            	
                            	// set key value to key
                            	String commentVal = (String) commentsIter.next();
                            	// get values from key
                            	JSONObject commentObj = (JSONObject) jsonObj4.get(commentVal);                        	
                            	
                            	if(comment.equals("")){                            		
                            		if(commentsIter.hasNext()) {
                            			comment =  "- " + commentObj.get("comment").toString();
                            		} else {
                                		comment =  commentObj.get("comment").toString();
                            		}                            		
                            	} else {                            		
                                    comment =  comment + "\n" +
                            		               "- " + commentObj.get("comment").toString();
                            		
                            	}
                            }

                            idValues.setcommnet(comment);   
                        } else {
                        	idValues.setcommnet("");
                        }                   
                       	
                       	if(componentCount >= 1) {

                        	// set file_path
                          	if(tempObj.get("file_path") == null) {
                           		idValues.setfilepath("");
                           	} else {
                           		idValues.setfilepath(tempObj.get("file_path").toString());            		
                           	}
                                	
                           	// set component_name
                           	if(compoObj.get("component_name") == null) {
                           		idValues.setcomponenetName("");
                           		componentName = "";
                            } else {
                            	idValues.setcomponenetName(compoObj.get("component_name").toString());
                                componentName = compoObj.get("component_name").toString();
                           	}
                                	
                           	// set component_version
                           	if(compoObj.get("component_version") == null) {
                           		idValues.setcomponentVersion("");
                           		componentVersion = "";
                           	} else {
                           		idValues.setcomponentVersion(compoObj.get("component_version").toString());
                           		componentVersion = compoObj.get("component_version").toString();
                           	}
                            
                           	// set copyright
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
                           	
                           	// set component_license_id
                           	if(compoObj.get("component_license_identifier") == null) {
                           		idValues.setcomponentLicenseId("");
                           	} else {
                           		idValues.setcomponentLicenseId(compoObj.get("component_license_identifier").toString());            		
                           	} 
                           	
                           	// set comment
                            idValues.setcommnet(comment);                         	               
                       	}
                       	
                    	componentCount++;
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
