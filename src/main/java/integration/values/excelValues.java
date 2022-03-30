package integration.values;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelValues {
	private static excelValues values = new excelValues();
	
	private XSSFSheet sheet0;
	XSSFWorkbook wb = new XSSFWorkbook();;
	
	private String filepath;
	
	private excelValues() {
	}

	public static excelValues getInstance() {
		return values;
	}
	
	public XSSFWorkbook getWB(){
		return wb;
	}	
	
	public XSSFSheet getSheet0(){
		return sheet0;
	}
	
	public String getfilepath() {
		return filepath;
	}
	
	public void setfilepath(String filepath) {
		this.filepath = filepath;
	}
}