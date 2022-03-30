package integration.main;

import integration.values.loginValues;
import integration.values.projectValues;

public class printInfo {
	
	static loginValues lvalues = loginValues.getInstance();		
	static projectValues pvalues = projectValues.getInstance();
	
	
	public static void usage() {
		System.out.println();
		System.out.println("== FOSSLight Integration Guide == ");
		System.out.println();
		System.out.println("These parameters overwrite the values of config.properties");
		System.out.println();
		System.out.println("e.g)");
		System.out.println("$ java -jar fosslight_integration.jar --fidprotocol https --fidaddress fossid.osbc.co.kr --fidusername byunghoon "
				+ "--fidapikey fidapikey --fidprojectname projectName --fidscanname scanName --fltprotocol https --fltaddress fosslight.osbc.co.kr --flttoken flttoken");
		System.out.println();
		System.out.println();
		System.out.println("Arguments");
		System.out.println();
		System.out.println("FossID Server Information");
		System.out.println("--fidprotocol: FossID web interface protocol");		
		System.out.println("--fidaddress: FossID address");
		System.out.println("--fidusername: FossID username");
		System.out.println("--fidapikey: FossID apikey");
		System.out.println();
		System.out.println("FossID Project/Scan Information");
		System.out.println("--fidprojectname: FossID project name");
		System.out.println("--fidscanname: FossID scan name");
		System.out.println();
		System.out.println("FossID Report Option");
		System.out.println("--fididresult: Choice a identification result, 0: Only marked as identified files, 1: Marked as Identified files & Pending files with assigned components");
		System.out.println("               (default: 0, 1)");
		System.out.println("--fidcopyright: Choice a copyright, 0: Identified by Shinobi, 1: Component copyright");
		System.out.println("                (default: 0, 1)");
		System.out.println("--filepath: Set FossID report file path, /some/path/to/be/saved");
		System.out.println("            (default: Saved in FOSSLight integration tool path)");
		System.out.println("--filedelete: Delete the FossID report file after importing the FossID report file into FOSSLight");
		System.out.println("              (0, default: 1)");
		System.out.println();
		System.out.println("FOSSLight Server Information");
		System.out.println("--fltprotocol: FOSSLight web interface protocol  ");		
		System.out.println("--fltaddress: FOSSLight address");
		System.out.println("--flttoken: FOSSLight token");
		System.out.println();
		System.out.println("FOSSLight Project/Scan Information");
		System.out.println("--fltprojectname: FOSSLight project name");
		System.out.println("                  (default: fossidProjectName)");
		System.out.println("--fltprojectversion: FOSSLight project version");
		System.out.println("                     (default: fossidScanName)");
		System.out.println("--fltostype: FOSSLight project OS type");
		System.out.println("             (defulat: Linux, Windows, MacOS, ETC)");
		System.out.println();
	}
	
	public static void startFossid() {
		System.out.println("Start FossID Process");
		System.out.println();
		System.out.println("******                                 *****    ****		");
		System.out.println("*                                        *      *   *		");
		System.out.println("*                                        *      *    *		");
		System.out.println("*                                        *      *     *		");
		System.out.println("******    ****     *****     *****       *      *     *		");
		System.out.println("*        *    *   *         *            *      *     *		");
		System.out.println("*        *    *    *****     *****       *      *    *		");
		System.out.println("*        *    *         *         *      *      *   *		");		
		System.out.println("*         ****     *****     *****     *****    ****		");		
	}
	
	
	public static void printFIDInfo() {
		
		System.out.println();
		System.out.println("===================================================================");
		System.out.println("FossID Server URL: " +  lvalues.getFIDserverUri());
		System.out.println("FossID UserName: " + lvalues.getFIDuserName());
		System.out.println("FossID ApiKey: " + "*******");
		System.out.println("FossID Project Name/Code: " + pvalues.getFIDprojectName() + " / " + pvalues.getFIDprojectId());
		System.out.println("FossID Scan Name/Code: " + pvalues.getFIDscanName() + " / " + pvalues.getFIDscanId());		
		System.out.println("===================================================================");
		System.out.println();
	}
	
	
	public static void endFossid() {
		
	}
	
	
	public static void startFosslight() {		
		System.out.println();
		System.out.println();
		System.out.println("Start Fosslight Process");
		System.out.println();
		System.out.println("******     ****       ****       *****       *****     *         *              *						");
		System.out.println("*         *    *     *    *     *           *          *                        *						");
		System.out.println("*        *      *   *      *   *           *           *         *              *						");
		System.out.println("*        *      *   *      *    *           *          *         *              *           *			");
		System.out.println("******   *      *   *      *     *****       *****     *         *     *****    ****     *******		");
		System.out.println("*        *      *   *      *          *           *    *         *    *     *   *   *       *			");
		System.out.println("*        *      *   *      *           *           *   *         *   *      *   *    *      *			");
		System.out.println("*         *    *     *    *           *           *    *         *    *     *   *    *      *			");		
		System.out.println("*          ****       ****       *****       *****     *******   *     ******   *    *       ***		");		
		System.out.println("                                                                            *							");
		System.out.println("                                                                       *    *							");
		System.out.println("                                                                        ****							");		
	}
	
	public static void printFLTInfo() {
		
		System.out.println();
		System.out.println("===================================================================");		
		System.out.println("Fosslight Server URL: " + lvalues.getFLTserverUri());		
		System.out.println("Fosslight Token: " + "*******");
		System.out.println("Fosslight Project Name/Version: " + pvalues.getFLTprojectName() + " / " + pvalues.getFLTprojectVersion());		
		System.out.println("===================================================================");
		System.out.println();
	}
	
	public static void endFosslight() {
		
	}

}
