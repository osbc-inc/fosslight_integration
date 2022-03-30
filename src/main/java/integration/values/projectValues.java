package integration.values;

import java.util.ArrayList;

public class projectValues {	
	
		private static projectValues values = new projectValues();

		public projectValues() {
		}

		public static projectValues getInstance() {
			return values;
		}

		private static String fid_projectid;	
		private static String fid_projectname;
		private static String fid_scanid;
		private static String fid_scanname;
		
		private static String flt_projectName;
		private static String flt_projectVersion;
		private static ArrayList<String> flit_projectVersionlist = new ArrayList<String>();
		private static String flt_projectid;
		private static String flt_codeosType;
		
		public String getFIDprojectId() {
			return fid_projectid;
		}

		public void setFIDprojectId(String fid_projectid) {
			this.fid_projectid = fid_projectid;
		}

		public String getFIDprojectName() {
			return fid_projectname;
		}

		public void setFIDprojectName(String fid_projectname) {
			this.fid_projectname = fid_projectname;
		}
		
		public String getFIDscanId() {
			return fid_scanid;
		}

		public void setFIDscanId(String fid_scanid) {
			this.fid_scanid = fid_scanid;
		}

		public void setFIDscanName(String fid_scanname) {
			this.fid_scanname = fid_scanname;
		}
		
		public String getFIDscanName() {
			return fid_scanname;
		}		

		public void setFLTprojectName(String flt_projectName) {
			this.flt_projectName = flt_projectName;
		}
		
		public String getFLTprojectName() {
			return flt_projectName;
		}
		
		public void setFLTprojectVersion(String flt_projectVerion) {
			this.flt_projectVersion = flt_projectVerion;
		}
				
		public String getFLTprojectVersion() {
			return flt_projectVersion;
		}

		public void setFLTprojectVersionList(String projectVersionlist) {
			this.flit_projectVersionlist.add(projectVersionlist);
		}
		
		public ArrayList<String> getFLTprojectVersionList() {
			return flit_projectVersionlist;
		}
		
		public String getFLTprojectId() {
			return flt_projectid;
		}

		public void setFLTprojectId(String flt_projectid) {
			this.flt_projectid = flt_projectid;
		}
		
		public String getFLTcodeosType() {
			return flt_codeosType;
		}

		public void setFLTcodeosType(String flt_codeosType) {
			this.flt_codeosType = flt_codeosType;
		}
		
}
