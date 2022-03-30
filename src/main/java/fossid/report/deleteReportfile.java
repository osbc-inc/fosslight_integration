package fossid.report;

import java.io.File;

import integration.values.excelValues;

public class deleteReportfile {
	
	excelValues excelVal = excelValues.getInstance();
	
	public void deleteReportfile() {	
		
		File file = new File(excelVal.getfilepath()); 
		
		if(file.exists() ) {
			
			if(file.delete()) {
				System.out.println();
				System.out.println("The FossID report file is deleted");				
				System.out.println("- FossID report file: " + excelVal.getfilepath());
			} else { 			
				System.out.println();
				System.out.println("FAILED: Deleting the FossID report file is failed");
				System.out.println("- FossID report file: " + excelVal.getfilepath());				
			} 
			
		} else {
			System.out.println();
			System.out.println("ERROR: The FossID report file is not exist"); 
			System.out.println("- FossID report file: " + excelVal.getfilepath());			
			System.exit(1);
		}		
		
		System.out.println();
		System.out.println("The Project data migration from FossID to FOSSLight is finished");
		System.exit(1);
	}

}
