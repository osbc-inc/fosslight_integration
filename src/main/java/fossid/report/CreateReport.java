package fossid.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import integration.values.excelValues;
import integration.values.projectValues;

public class CreateReport {	
	
	projectValues pvalues = projectValues.getInstance();
	excelValues excelVal = excelValues.getInstance();
	
	public void writeReport(String filepath)  {
		
		CreateSheet0 sheet0 = new CreateSheet0();		
		sheet0.writeSheet();
		
		closeExcel(filepath);
	}
	
	public void closeExcel(String filepath){
		
		String date = new DateTime().toString(DateTimeFormat.forPattern("yyyyMMdd_HHmmss"));		
				
		try {
			
			Properties props = new Properties();
			InputStream is = getClass().getResourceAsStream("/config.properties");
			props.load(is);			
			String configFilepath = props.getProperty("fossid.filepath");
			
			if(filepath.equals("")) {
				
				if(!(configFilepath.equals(""))) {
					String end = configFilepath.substring(configFilepath.length() - 1, configFilepath.length());
					
					if(end.equals("/") || end.equals("\\")) {			  		
					  } else {
							if(isWindows()) {
								configFilepath = configFilepath + "\\";
							} else if (isMac() || isSolaris() || isUnix()){					
								configFilepath = configFilepath + "/";
							}
					  }	
					
					FileOutputStream output = new FileOutputStream(configFilepath + date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
					excelVal.setfilepath(configFilepath + date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
					
					excelVal.getWB().write(output);
					output.close();
					excelVal.getWB().close();
					
				} else {
					FileOutputStream output = new FileOutputStream(date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
					excelVal.setfilepath(date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
					
					excelVal.getWB().write(output);
					output.close();
					excelVal.getWB().close();
				}
				
			
			} else {
				
				String end = filepath.substring(filepath.length() - 1, filepath.length());
				
				if(end.equals("/") || end.equals("\\")) {			  		
				  } else {
						if(isWindows()) {
							filepath = filepath + "\\";
						} else if (isMac() || isSolaris() || isUnix()){					
							filepath = filepath + "/";
						}
				  }
				
				FileOutputStream output = new FileOutputStream(filepath + date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
				excelVal.setfilepath(filepath + date + "_"+ pvalues.getFIDprojectName() + "_" + pvalues.getFIDscanName() + "_Report.xlsx");
				
				excelVal.getWB().write(output);
				output.close();
				excelVal.getWB().close();
			}
			
			System.out.println("- FossID report file: " + excelVal.getfilepath());
			System.out.println();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
	        return (OS.indexOf("win") >= 0);
	}
	  
	public static boolean isMac() {	  
	        return (OS.indexOf("mac") >= 0);	  
	}
	  
	public static boolean isUnix() {
	         return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	  
	public static boolean isSolaris() {	 
        return (OS.indexOf("sunos") >= 0);	  
    }
    
}