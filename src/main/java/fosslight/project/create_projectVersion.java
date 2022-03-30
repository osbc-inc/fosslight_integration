package fosslight.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import integration.values.loginValues;
import integration.values.projectValues;

public class create_projectVersion {
	
	public void createProjectversion() {
		
		loginValues lvalues = loginValues.getInstance();		
		projectValues pvalues = projectValues.getInstance();
		
		String address = lvalues.getFLTserverUri() + "/api/v1/create_project?";		
		String output;
        
		try {
			if(pvalues.getFLTcodeosType() == null) {
				address = address + "prjName=" + pvalues.getFLTprojectName() + "&" + "prjVersion=" + pvalues.getFLTprojectVersion() + "&" + "osType=" + "100";
			} else {
				address = address + "prjName=" + pvalues.getFLTprojectName() + "&" + "prjVersion=" + pvalues.getFLTprojectVersion() + "&" + "osType=" + pvalues.getFLTcodeosType().toString();
			}
			System.out.println(address);			
			 
			URL url = new URL(address);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("_token", lvalues.getFLTtoken());
			
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
    		    pvalues.setFLTprojectId(jsonObj2.get("prjId").toString());
            } else {
            	System.err.println("ERROR: create_projectVersion class" + jsonObj1.get("msg").toString());
            	System.exit(1);
            }

            System.out.println();
            System.out.println("Project" + pvalues.getFLTprojectName() + " / Version: " + pvalues.getFLTprojectName() + " / Id: " + pvalues.getFLTprojectId() + " is created");            
            
		} catch (Exception e) {
			e.printStackTrace();

		}		
	}
	
}

