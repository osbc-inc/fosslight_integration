package integration.main;

import fossid.getdata.getIdentifiedFiles;
import fossid.getdata.getMarkedAsIdentifiedFiles;
import fossid.getdata.getProjectScanInfo;
import fossid.report.CreateReport;
import fossid.report.deleteReportfile;
import fosslight.project.check_projectVersion;
import fosslight.project.code_search;
import fosslight.project.create_projectVersion;
import fosslight.project.import_data;

public class main {

	public static void main(String[] args) {
		
		try {
			
			runwithArgu(args);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
		
	private static void runwithArgu(String[] args){

		String fid_protocol = "";
		String fid_address = "";
		String fid_userName = "";			
		String fid_apikey = "";
		String fid_projectName = "";		
		String fid_scanName = "";
		String fid_idresult = "0";
		String fid_copyright = "0";			
		String fid_filepath = "";
		String fid_deletefile = "1";
		
		String flt_protocol = "";
		String flt_address = "";					
		String flt_token = "";
		String flt_projectName = "";	
		String flt_projectVersion = "";
		String flt_ostype = "";	
		
		for(int i = 0; i < args.length; i++) {
			
			if(args[i].equals("-h") || args[i].equals("--h") || args[i].equals("--help")) {
				printInfo.usage();
				System.exit(1);
			}	
				
			if(args[i].equals("--fidprotocol")) {
				fid_protocol = args[i+1]; 
			}
			
			if(args[i].equals("--fidaddress")) {
				fid_address = args[i+1];
			}
				
			if(args[i].equals("--fidusername")) {
				fid_userName = args[i+1]; 
			}
				
			if(args[i].equals("--fidapikey")) {
				fid_apikey = args[i+1];
			}
				
			if(args[i].equals("--fidprojectname")) {
				fid_projectName = args[i+1]; 
			}
				
			if(args[i].equals("--fidscanname")) {
				fid_scanName = args[i+1];
			}
			
			if(args[i].equals("--fididresult")) {
				fid_idresult = args[i+1]; 
			}				
			
			if(args[i].equals("--fidcopyright")) {
				fid_copyright = args[i+1]; 
			}
			
			if(args[i].equals("--filepath")) {
				fid_filepath = args[i+1]; 
			}
			
			if(args[i].equals("--filedelete")) {
				fid_deletefile = args[i+1]; 
			}
			
			if(args[i].equals("--fltprotocol")) {
				flt_protocol = args[i+1]; 
			}
			
			if(args[i].equals("--fltaddress")) {
				flt_address = args[i+1];
			}							
				
			if(args[i].equals("--flttoken")) {
				flt_token = args[i+1];
			}
				
			if(args[i].equals("--fltprojectname")) {
				flt_projectName = args[i+1]; 
			}	
			
			if(args[i].equals("--fltprojectversion")) {
				flt_projectVersion = args[i+1]; 
			}	
			
			if(args[i].equals("--fltostype")) {
				flt_ostype = args[i+1]; 
			}	
			
			i++;
		}
			
		printInfo print = new printInfo();
		print.startFossid();
		
		setLoginInfo common = new setLoginInfo();
		common.setInfo(fid_protocol, fid_address, fid_userName, fid_apikey, flt_protocol, flt_address, flt_token);
		
		validateAuthentication fid_validation = new validateAuthentication();		
		fid_validation.fidValidateauthentication();
		
		getProjectScanInfo fid_prscInfo = new getProjectScanInfo();
		fid_prscInfo.getInfo(fid_projectName, fid_scanName);
		
		print.printFIDInfo();
		
		if(fid_idresult.equals("0")){
			getMarkedAsIdentifiedFiles fid_masidFiles = new getMarkedAsIdentifiedFiles();
			fid_masidFiles.getData(fid_copyright);			
		} else if(fid_idresult.equals("1")) {
			getIdentifiedFiles fid_idFiles = new getIdentifiedFiles();
			fid_idFiles.getData(fid_copyright);
		}
		
		CreateReport writeExcel = new CreateReport();
		writeExcel.writeReport(fid_filepath);
		
		print.startFosslight();
		
		check_projectVersion checkprojectVersion = new check_projectVersion();
		checkprojectVersion.check_projectversion(flt_projectName, flt_projectVersion);
		
		print.printFLTInfo();
		
		code_search codesearch = new code_search();
		if(!(flt_ostype.equals(""))) {
			codesearch.codesearch(flt_ostype);
		}
		
		create_projectVersion createprojectVersion = new create_projectVersion();
		createprojectVersion.createProjectversion();
		
		import_data importData = new import_data();
		importData.importData();
		
		deleteReportfile deleteFile = new deleteReportfile();
		if(fid_deletefile.equals("1")) {
			deleteFile.deleteReportfile();
		}
	}
}