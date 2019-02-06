package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ClassTrace2 {
	public String ID; 
	public	Requirement requirement; 
		public	Clazz myclass; 
		String trace; 
		String trace2; 
		String traceFinal; 
		String trace3; 
		String trace4; 
		String trace5; 
		public String DeveloperGold; 
		String trace6; 
		String subject;
		public String SubjectGold; 
		
		
		
	

	
		public String getGoldfinal() {
			return DeveloperGold;
		}

		public void setGoldFinal(String goldFinal) {
			this.DeveloperGold = goldFinal;
		}

		public String getTraceFinal() {
			return traceFinal;
		}

		public void setTraceFinal(String traceFinal) {
			this.traceFinal = traceFinal;
		}

		public String getTrace6() {
			return trace6;
		}

		public void setTrace6(String trace6) {
			this.trace6 = trace6;
		}

		public String getTrace5() {
			return trace5;
		}

		public void setTrace5(String trace5) {
			this.trace5 = trace5;
		}

		public String getTrace2() {
			return trace2;
		}

		public void setTrace2(String trace2) {
			this.trace2 = trace2;
		}

		public String getTrace3() {
			return trace3;
		}

		public void setTrace3(String trace3) {
			this.trace3 = trace3;
		}

		public String getTrace4() {
			return trace4;
		}

		public void setTrace4(String trace4) {
			this.trace4 = trace4;
		}

		public String getSubjectGold() {
			return SubjectGold;
		}

		public void setSubjectGold(String subjectGold) {
			SubjectGold = subjectGold;
		}

		HashMap<Integer, ClassTrace2> classtraceHashMap= new HashMap<Integer, ClassTrace2> (); 
		LinkedHashMap<String, ClassTrace2> classtraceHashMapRequirementClass= new LinkedHashMap<String, ClassTrace2> (); 
		
		public ClassTrace2(String iD, Requirement requirement, Clazz myclass, String trace, String subject) {
			super();
			ID = iD;
			this.requirement = requirement;
			this.myclass = myclass;
			this.trace = trace;
			this.subject = subject;
		}
		
		public ClassTrace2() {
			// TODO Auto-generated constructor stub
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public Requirement getRequirement() {
			return requirement;
		}
		public void setRequirement(Requirement requirement) {
			this.requirement = requirement;
		}
		public Clazz getMyclass() {
			return myclass;
		}
		public void setMyclass(Clazz myclass) {
			this.myclass = myclass;
		}
		public String gettrace() {
			return trace;
		}
		public void settrace(String trace) {
			this.trace = trace;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		
		
		public  HashMap<Integer, ClassTrace2> ReadClassesRepresentations(Connection conn) throws SQLException {
			DatabaseReading2 db = new DatabaseReading2(); 
			ClassDetails2 classdet= new ClassDetails2(); 
			//CLASSESHASHMAP
			String rowcount = null; 
			Statement st = conn.createStatement();
			ResultSet var = st.executeQuery("select count(*) from classes"); 
			while(var.next()){
				rowcount = var.getString("count(*)");
			}
			System.out.println("ROW COUNT::::::"+rowcount); 
			int rowcountint= Integer.parseInt(rowcount); 
		
			int index=1; 
			 ResultSet myresults = st.executeQuery("SELECT tracesclasses.* from tracesclasses where id='"+ index +"'"); 
			 while(myresults.next()) {
				 ClassTrace2 myclasstrace= new ClassTrace2(); 
				 Requirement requirement = new Requirement(); 
				 requirement.setID(myresults.getString("requirementid"));
				 requirement.setRequirementName(myresults.getString("requirement"));
				 myclasstrace.setRequirement(requirement);
				 
				 Clazz classrep = new Clazz(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 myclasstrace.setMyclass(classrep);
				 
				 myclasstrace.setTraceFinal(myresults.getString("goldfinal"));
				 myclasstrace.setSubjectGold(myresults.getString("SubjectGold"));
				 myclasstrace.setSubject(myresults.getString("subject"));
				 classtraceHashMap.put(index, myclasstrace); 
				 System.out.println("my classtrace toString: "+myclasstrace.toString()); 
				 index++; 
				 myresults = st.executeQuery("SELECT tracesclasses.* from tracesclasses where id='"+ index +"'"); 
			 }
			 
			return classtraceHashMap;
		}
		
		
		public  LinkedHashMap<String, ClassTrace2> ReadClassesRepresentationsRequirementClass(Connection conn) throws SQLException {
			DatabaseReading2 db = new DatabaseReading2(); 
			ClassDetails2 classdet= new ClassDetails2(); 
			//CLASSESHASHMAP
			String rowcount = null; 
			Statement st = conn.createStatement();
			ResultSet var = st.executeQuery("select count(*) from classes"); 
			while(var.next()){
				rowcount = var.getString("count(*)");
			}
			System.out.println("ROW COUNT::::::"+rowcount); 
			int rowcountint= Integer.parseInt(rowcount); 
		
			int index=1; 
			 ResultSet myresults = st.executeQuery("SELECT tracesclasses.* from tracesclasses "); 
			 while(myresults.next()) {
				 ClassTrace2 myclasstrace= new ClassTrace2(); 
				 Requirement requirement = new Requirement(); 
				 requirement.setID(myresults.getString("requirementid"));
				 requirement.setRequirementName(myresults.getString("requirement"));
				 myclasstrace.setRequirement(requirement);
				 
				 Clazz classrep = new Clazz(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 myclasstrace.setMyclass(classrep);
				 
				 myclasstrace.settrace(myresults.getString("SubjectGold"));
				 myclasstrace.setTrace2(myresults.getString("gold2"));
				 myclasstrace.setTraceFinal(myresults.getString("goldfinal"));
				 myclasstrace.setSubjectGold(myresults.getString("SubjectGold"));
				 myclasstrace.setGoldFinal(myresults.getString("goldfinal"));

//				 myclasstrace.setTrace3(myresults.getString("gold3"));
				
//				 myclasstrace.setTrace6(myresults.getString("gold6"));
				 myclasstrace.setSubject(myresults.getString("subject"));
				 //RequirementClass ReqClass= new RequirementClass(myclasstrace.getRequirement().ID, myclasstrace.getMyclass().classid); 
				String ReqClass= myclasstrace.getRequirement().ID+"-"+myclasstrace.getMyclass().ID; 
				 classtraceHashMapRequirementClass.put(ReqClass, myclasstrace); 
			//	 System.out.println("my classtrace toString: "+myclasstrace.toString()); 
				 index++; 
				
				 System.out.println("INDEX 4 "+index);
			 }
			 
			return classtraceHashMapRequirementClass;
		}
		
		
		

		public ClassTrace2 FindTrace(List<ClassTrace2> classtraces2, String ClassID, String RequirementID) {
			for(ClassTrace2 ct: classtraces2) {
				if(ct.myclass.getClassid().equals(ClassID) && ct.requirement.ID.equals(RequirementID)) {
					return ct; 
				}
			}
			return null;
			
		}
		
		public ClassTrace2 FindTrace2(LinkedHashMap<String, ClassTrace2> classesRequirementtraceshashmap, String ClassID, String RequirementID) {
			ClassTrace2 myclasstrace = classesRequirementtraceshashmap.get(RequirementID+"-"+ClassID); 
			return myclasstrace;
			
		}

		@Override
		public String toString() {
			return "ClassTrace2 [ID=" + ID + ", requirement=" + requirement + ", myclass=" + myclass + ", trace="
					+ trace + ", goldfinal=" + DeveloperGold + ", classtraceHashMap=" + classtraceHashMap
					+ ", classtraceHashMapRequirementClass=" + classtraceHashMapRequirementClass + "]";
		}

	
		
		
		
		
}
