package fosslight.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import integration.values.loginValues;
import integration.values.projectValues;

public class check_projectVersion {	
	
	static loginValues lvalues = loginValues.getInstance();		
	static projectValues pvalues = projectValues.getInstance();
	
	
	public void check_projectversion(String projectName, String projectVersion) {
		
		projectValues pvalues = projectValues.getInstance(); 

		setflt_projectVersion(projectName, projectVersion);
		flt_projectList();

		String fidprojectVersion = pvalues.getFLTprojectName() + "_" + pvalues.getFLTprojectVersion();
		
		if(pvalues.getFLTprojectVersionList().size() > 0) {
		
			for(int i = 0; i < pvalues.getFLTprojectVersionList().size(); i++) {
				if(fidprojectVersion.equals(pvalues.getFLTprojectVersionList().get(i))){
					System.out.println();
					System.err.println("ERROR: Please, check FossID Project + Scan / FOSSLight Project + Version");
					System.err.println("ERROR: FossID Project + Scan is already created in FOSSLight as Project + Version");
					System.exit(1);
				}
			}	
		}
		
		
	}

	private void flt_projectList() {
		
		String address = lvalues.getFLTserverUri() + "/api/v1/prj_search";
	    String output;
       
		try {
			URL url = new URL(address);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("_token", lvalues.getFLTtoken());
			conn.setRequestProperty("Content-Type","application/json");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		    StringBuffer result = new StringBuffer();
		    while ((output = in.readLine()) != null) {
		          result.append(output);
		    }
		    
		    in.close();
		    
		    JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj1 = (JSONObject) jsonParser.parse(result.toString());
                        
            if(jsonObj1.get("code").toString().equals("100")) {
            	JSONObject jsonObj2 = (JSONObject) jsonObj1.get("data");
                JSONArray dataArray = (JSONArray) jsonObj2.get("content");
                
                if(jsonObj2.get("content") != null || !(jsonObj2.get("content").equals(""))) {
                	
                	for(int i=0; i < dataArray.size(); i++) {
                        JSONObject tempObj = (JSONObject) dataArray.get(i);            
                        pvalues.setFLTprojectVersionList(tempObj.get("prjName").toString() + "_" + tempObj.get("prjVersion").toString());
                    }
                
                }
            } else {            	
            	System.err.println("ERROR: check_projectVersion class" + jsonObj1.get("msg").toString());
            	System.exit(1);
            }
            
		    
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	
	private void setflt_projectVersion(String projectName, String projectVersion) {
		
		try {
			
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream("/config.properties");
			props.load(is);
			
			// To change encoding to UTF-8
			String encoding = "";

			if(!(projectName.equals(""))) {
				
				pvalues.setFLTprojectName(projectName);
			
			} else {
				
				encoding = new String(props.getProperty("fosslight.project").getBytes("iso-8859-1"), "UTF-8");	
				
				if(encoding.equals("")) {
					pvalues.setFLTprojectName(pvalues.getFIDprojectName());
				} else {
					pvalues.setFLTprojectName(encoding);
				}
				
			}
			
			if(!(projectVersion.equals(""))) {
				pvalues.setFLTprojectVersion(projectVersion);
				
			} else {
				
				encoding = new String(props.getProperty("fosslight.version").getBytes("iso-8859-1"), "UTF-8");	
				
				if(encoding.equals("")) {
					pvalues.setFLTprojectVersion(pvalues.getFIDscanName());
				} else {
					pvalues.setFLTprojectVersion(encoding);
				}
			}

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
