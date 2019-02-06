package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodTrace2Original {
	
	public Method MethodRepresentation; 
	public Requirement Requirement; 
	public Clazz ClassRepresentation; 
	public String gold; 
	public String subject;
	public String goldprediction; 
	public String goldpredictionCaller; 
	public String goldpredictionCallee; 
	public String gold2; 
	public String SubjectT; 
	public String SubjectN; 
	List<Method> callersList= new ArrayList<Method>(); 
	List<Method> calleesList= new ArrayList<Method>(); 
	List<Method> callersListExecuted= new ArrayList<Method>(); 
	List<Method> calleesListExecuted= new ArrayList<Method>(); 
	
	
	public String getGoldfinal() {
		return gold2;
	}

	public void setGold2(String gold2) {
		this.goldfinal = gold2;
	}

	public String getGoldpredictionCaller() {
		return goldpredictionCaller;
	}

	public void setGoldpredictionCaller(String goldpredictionCaller) {
		this.goldpredictionCaller = goldpredictionCaller;
	}

	public String getGoldpredictionCallee() {
		return goldpredictionCallee;
	}

	public void setGoldpredictionCallee(String goldpredictionCallee) {
		this.goldpredictionCallee = goldpredictionCallee;
	}

	public List<Method> getCallersList() {
		return callersList;
	}

	public String getSubjectT() {
		return SubjectT;
	}

	public void setSubjectT(String subjectT) {
		SubjectT = subjectT;
	}

	public String getSubjectN() {
		return SubjectN;
	}

	public void setSubjectN(String subjectN) {
		SubjectN = subjectN;
	}

	public void setCallersList(List<Method> callersList) {
		this.callersList = callersList;
	}

	public List<Method> getCalleesList() {
		return calleesList;
	}

	public void setCalleesList(List<Method> calleesList) {
		this.calleesList = calleesList;
	}

	public List<Method> getCallersListExecuted() {
		return callersListExecuted;
	}

	public void setCallersListExecuted(List<Method> callersListExecuted) {
		this.callersListExecuted = callersListExecuted;
	}

	public List<Method> getCalleesListExecuted() {
		return calleesListExecuted;
	}

	public void setCalleesListExecuted(List<Method> calleesListExecuted) {
		this.calleesListExecuted = calleesListExecuted;
	}

	public ArrayList<MethodTrace2> getMethodtraces() {
		return methodtraces;
	}

	public void setMethodtraces(ArrayList<MethodTrace2> methodtraces) {
		this.methodtraces = methodtraces;
	}

	public HashMap<Integer, MethodTrace2Original> getMethodtraceHashMap() {
		return methodtraceHashMap;
	}

	public void setMethodtraceHashMap(HashMap<Integer, MethodTrace2Original> methodtraceHashMap) {
		this.methodtraceHashMap = methodtraceHashMap;
	}

	public ArrayList<MethodTrace2> methodtraces; 
	
	public String getGoldprediction() {
		return goldprediction;
	}

	public void setGoldprediction(String goldprediction) {
		this.goldprediction = goldprediction;
	}

	HashMap<Integer, MethodTrace2Original> methodtraceHashMap= new HashMap<Integer, MethodTrace2Original> (); 
	
	public MethodTrace2Original() {
		super();
	}

	public Method getMethodRepresentation() {
		return MethodRepresentation;
	}

	public void setMethodRepresentation(Method methodRepresentation) {
		MethodRepresentation = methodRepresentation;
	}

	public Requirement getRequirement() {
		return Requirement;
	}

	public void setRequirement(Requirement requirement) {
		Requirement = requirement;
	}

	public Clazz getClassRepresentation() {
		return ClassRepresentation;
	}

	public void setClassRepresentation(Clazz classRepresentation) {
		ClassRepresentation = classRepresentation;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public MethodTrace2Original(Method methodRepresentation, Requirement requirement,
			Clazz classRepresentation, String gold, String subject) {
		super();
		MethodRepresentation = methodRepresentation;
		Requirement = requirement;
		ClassRepresentation = classRepresentation;
		this.gold = gold;
		this.subject = subject;
	}
	
	public  HashMap<Integer, MethodTrace2Original> ReadClassesRepresentations(Connection conn) throws SQLException {
		DatabaseReading2 db = new DatabaseReading2(); 
		ClassDetails2 classdet= new ClassDetails2(); 
		//CLASSESHASHMAP
		
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from classes"); 
		
	
		int index=1; 
	
		//END OF TEST 
			 ResultSet myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
			 while(myresults.next() ) {
				 MethodTrace2Original mytrace= new MethodTrace2Original(); 
				 RequirementGold RequirementGold = new RequirementGold(); 
				 Requirement requirement = new Requirement(); 
				 requirement.setID(myresults.getString("requirementid"));
				 requirement.setRequirementName(myresults.getString("requirement"));
				 mytrace.setRequirement(requirement);
				 
				 Clazz classrep = new Clazz(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 
				 Method methodrep= new Method(); 
				 methodrep.setMethodid(myresults.getString("methodid"));
				// methodrep.setMethodname(myresults.getString("method"));
				 String fullmethodname= myresults.getString("fullmethod"); 
				 methodrep.setMethodname(myresults.getString("methodname"));
				 methodrep.setFullmethodname(myresults.getString("fullmethod"));
				 mytrace.setMethodRepresentation(methodrep);
				 
				 mytrace.setClassRepresentation(classrep);
				 
				 List<Clazz> interfaceclassreps= new ArrayList<Clazz>(); 
				 ResultSet myinterfaces=st2.executeQuery("select interfaces.* from interfaces where ownerclassid='" + mytrace.getClassRepresentation().ID+"'"); 
				 while(myinterfaces.next()) {
					 Clazz myclassrepinterface= new Clazz(); 
					 myclassrepinterface.setClassid(myinterfaces.getString("interfaceclassid"));
					 myclassrepinterface.setClassname(myinterfaces.getString("interfacename"));
					 interfaceclassreps.add(myclassrepinterface); 
				 }
				
				 
				 mytrace.setGold(myresults.getString("gold"));
				 
				 mytrace.setSubject(myresults.getString("subject"));
				 
				 mytrace.setGold2(myresults.getString("gold2"));
				 String id= mytrace.getMethodRepresentation().ID; 
				 String tracename= mytrace.getMethodRepresentation().fullmethodname; 
				 ResultSet callers=st.executeQuery("select methodcalls.* from methodcalls where fullcallee='" + tracename+"'"); 
				 this.callersList= new  ArrayList<Method>(); 
				 while(callers.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(callers.getString("callermethodid"));
					 meth.setMethodname(callers.getString("callername"));
					
					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
					 while(myclass.next()) {
						 Clazz myclassrep= new Clazz(); 
						 myclassrep.setClassid(myclass.getString("classid"));
						 myclassrep.setClassname(myclass.getString("classname"));
						 meth.setClassrep(myclassrep);
					 }
					
					 this.callersList.add(meth); 					 
					
				 }
			
				 
				 
				 
				 
				 
				 
				 mytrace.setCallersList(this.callersList);
				 ResultSet callees=st.executeQuery("select methodcalls.* from methodcalls where fullcaller='" + tracename+"'"); 
				 this.calleesList= new  ArrayList<Method>(); 
				 while(callees.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(callees.getString("calleemethodid"));
					 meth.setMethodname(callees.getString("calleename"));
					 
					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
					 while(myclass.next()) {
						 Clazz myclassrep= new Clazz(); 
						 myclassrep.setClassid(myclass.getString("classid"));
						 myclassrep.setClassname(myclass.getString("classname"));
						 meth.setClassrep(myclassrep); 
					 }
					
					 
					 
				//	 meth.setRequirementsGold(requirementsGold);
					 this.calleesList.add(meth); 					 
					
				 }
				 
				 System.out.println("TRACENAME======="+ tracename);
				// ResultSet callersExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where calleemethodid='" + id+"'"); 
				 ResultSet callersExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where fullcallee='" + tracename+"'"); 
				 this.callersListExecuted= new  ArrayList<Method>(); 
				 while(callersExecuted.next()) {
					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 

					 Method meth= new Method(); 	
					 meth.setMethodid(callersExecuted.getString("callermethodid"));
					 meth.setMethodname(callersExecuted.getString("callername"));
					 
					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
					 while(myclass.next()) {
						 Clazz myclassrep= new Clazz(); 
						 myclassrep.setClassid(myclass.getString("classid"));
						 myclassrep.setClassname(myclass.getString("classname"));
						 meth.setClassrep(myclassrep); 
					 }
					
					 
					 //meth.setRequirementsGold(requirementsGold);
					 this.callersListExecuted.add(meth); 					 
				 }
				 mytrace.setCallersListExecuted(this.callersListExecuted);

				 
				 
				 
				 
				 
				 
//				 for(ClassRepresentation2 inter: interfaceclassreps) {
//					 if(fullmethodname!="") {
//						 System.out.println("FULL METHOD NAME: "+ fullmethodname);
//							
//							String myclass=  mytrace.ClassRepresentation.classname; 
//							 fullmethodname=  mytrace.MethodRepresentation.fullmethodname; 
//							String shortmethodname=  mytrace.MethodRepresentation.methodname; 
//							System.out.println("FULL METHOD NAME: "+ fullmethodname);
//							 System.out.println("MYCLASS: "+ myclass);
//						//	String shortmethodname=fullmethodname.substring(myclass.length(), fullmethodname.length()); 
//							 String interfacename= inter.classname+shortmethodname; 
//							 
//							  callers=st.executeQuery("select methodcalls.* from methodcalls where fullcallee='" + interfacename+"'"); 
//							// this.callersList= new  ArrayList<Method2Representation>(); 
//							 while(callers.next()) {
//
//								 Method2Representation meth= new Method2Representation(); 	
//								 meth.setMethodid(callers.getString("callermethodid"));
//								 meth.setMethodname(callers.getString("callername"));
//								
//								 ResultSet myclass2=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//								 while(myclass2.next()) {
//									 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//									 myclassrep.setClassid(myclass2.getString("classid"));
//									 myclassrep.setClassname(myclass2.getString("classname"));
//									 meth.setClassrep(myclassrep);
//								 }
//								
//								 this.callersList.add(meth); 					 
//					 }
//					
//							 
//							 System.out.println("FULL METHOD NAME: "+ fullmethodname);
//								
//								
//							 System.out.println("TRACENAME======="+ tracename);
//								  callees=st.executeQuery("select methodcalls.* from methodcalls where fullcaller='" + interfacename+"'"); 
//								// this.callersList= new  ArrayList<Method2Representation>(); 
//								 while(callees.next()) {
//
//									 Method2Representation meth= new Method2Representation(); 	
//									 meth.setMethodid(callees.getString("callermethodid"));
//									 meth.setMethodname(callees.getString("callername"));
//									
//									 ResultSet myclass2=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//									 while(myclass2.next()) {
//										 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//										 myclassrep.setClassid(myclass2.getString("classid"));
//										 myclassrep.setClassname(myclass2.getString("classname"));
//										 meth.setClassrep(myclassrep);
//									 }
//									
//									 this.calleesList.add(meth); 					 
//						 }	 
//							 
//							 
//						
//					 }
//				 }
				 mytrace.setCallersList(this.callersList);

				 mytrace.setCalleesList(this.calleesList);
				 
				 
				 
				 
			//	 ResultSet calleesExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where callermethodid='" + id+"'"); 
				 ResultSet calleesExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where fullcaller='" + tracename+"'"); 

				 this.calleesListExecuted= new  ArrayList<Method>(); 
				 while(calleesExecuted.next()) {
//					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 
//					 ResultSet methodtraces=st2.executeQuery("select traces.* from traces where methodid='" + id+"'"); 
//					 while(methodtraces.next()) {
//						 
//						 requirement= new Requirement2(); 
//						  RequirementGold= new RequirementGold(); 
//						 requirement.setID(methodtraces.getString("requirementid"));
//						 requirement.setRequirementName(methodtraces.getString("requirement"));
//						 RequirementGold.setRequirement(requirement);
//						 RequirementGold.setGold(methodtraces.getString("gold"));
//						 requirementsGold.add(RequirementGold); 
//					 }
					 Method meth= new Method(); 	
					 meth.setMethodid(calleesExecuted.getString("calleemethodid"));
					 meth.setMethodname(calleesExecuted.getString("calleename"));
					 
					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
					 while(myclass.next()) {
						 Clazz myclassrep= new Clazz(); 
						 myclassrep.setClassid(myclass.getString("classid"));
						 myclassrep.setClassname(myclass.getString("classname"));
						 meth.setClassrep(myclassrep); 
					 }
				
					 
					// meth.setRequirementsGold(requirementsGold);
					 this.calleesListExecuted.add(meth); 					 
					
				 }
				 mytrace.setCalleesListExecuted(this.calleesListExecuted);
				 
				 methodtraceHashMap.put(index, mytrace); 
				 index++; 
			//	 MethodTrace2 methtrace= new MethodTrace2(); 
				// System.out.println("my trace tostring: "+mytrace.toString());
				
				 myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
			
			 }
		

		 
		return methodtraceHashMap;
	}
	
	public List<MethodTrace2> getElement(List<MethodTrace2> methodtraces2, String ID, String goldpred, String goldprediction2, String RequirementID) {
		for(MethodTrace2 methodtrace: methodtraces2) {
			if(methodtrace.getMethodRepresentation().ID.equals(ID) && methodtrace.Requirement.ID.equals(RequirementID)) {
				if(goldprediction2.equals("goldpredictionCallee")){
					methodtrace.setGoldpredictionCallee(goldpred);
				}
				else if(goldprediction2.equals("goldpredictionCaller")) {
					methodtrace.setGoldpredictionCaller(goldpred);
				}
				
			}
		}
		
		return methodtraces2; 
		
	}

	public String toString(MethodTrace2 methtr) {
		String mycaller = ""; 
		String mycallee = ""; 
		String mycallerexecuted = ""; 
		String mycalleeexecuted = ""; 
		String requicallee = ""; 
		String requicaller = ""; 
		String requicalleeexecuted = ""; 
		String requicallerexecuted = ""; 
		String st= "MethodTrace2 [MethodRepresentation=" + MethodRepresentation.toString() 
		
		+ ", Requirement=" + methtr.Requirement.toString()
			+ ", ClassRepresentation=" + methtr.ClassRepresentation.toString() + ", gold=" + methtr.gold + ", subject=" + methtr.subject 
				+ ", goldpredictionCaller=" + methtr.goldpredictionCaller+ ", goldpredictionCallee=" + methtr.goldpredictionCallee ; 
		for(Method caller: methtr.callersList) {
		 mycaller=	mycaller+caller.getMethodid() +" "+caller.getMethodname(); 
		for(RequirementGold req: caller.requirementsGold) {
			 requicaller= requicaller+ " "+ req.Requirement.ID+ "  "+ req.Requirement.RequirementName; 
		}
		}
		
		for(Method callee: methtr.calleesList) {
			 mycallee=	mycallee+callee.getMethodid() +" "+callee.getMethodname(); 
			for(RequirementGold req: callee.requirementsGold) {
				 requicallee= requicallee+ " "+ req.Requirement.ID+ "  "+ req.Requirement.RequirementName; 
			}
			}
		
		for(Method callerexecuted: methtr.callersListExecuted) {
			 mycallerexecuted=	mycallerexecuted+callerexecuted.getMethodid() +" "+callerexecuted.getMethodname(); 
			for(RequirementGold req: callerexecuted.requirementsGold) {
				 requicallerexecuted=requicallerexecuted+ " "+ req.Requirement.ID+ "  "+ req.Requirement.RequirementName; 
			}
			}
			
			for(Method calleeexecuted: methtr.calleesListExecuted) {
				 mycalleeexecuted=	mycalleeexecuted+calleeexecuted.getMethodid() +" "+calleeexecuted.getMethodname(); 
				for(RequirementGold req: calleeexecuted.requirementsGold) {
					 requicalleeexecuted=requicalleeexecuted+ " "+ req.Requirement.ID+ "  "+ req.Requirement.RequirementName; 
				}
				}
		return st+"   CALLER: "+mycaller +"  "+requicaller+"CALLEE: "+mycallee+"   "+requicallee+"CALLER EXECUTED :"+mycallerexecuted+ "  " +requicallerexecuted+"CALLEE EXCUTED: "+mycalleeexecuted+"  "+requicalleeexecuted; 
			
	}
	
	
	
	
	
}