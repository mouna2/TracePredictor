package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Method2Details {
	public Method methodrep; 
	public ClassRepresentation2 OwnerClass; 
	public List<Method> callersList= new ArrayList<Method>(); 
	public List<Method> calleesList= new ArrayList<Method>(); 
	public List<Method> callersListExecuted= new ArrayList<Method>(); 
	public List<Method> calleesListExecuted= new ArrayList<Method>(); 
	public List<Parameter2> parametersList= new ArrayList<Parameter2>(); 
	public List<MethodField2> methodfieldsList= new ArrayList<MethodField2>();  
	public HashMap<String, MethodTrace2> methodtraces= new HashMap <String, MethodTrace2>();
	HashMap<String, List<MethodCalls>> MethodCallsHashMapCaller= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<MethodCalls>> MethodCallsHashMapCallee= new HashMap<String, List<MethodCalls>>(); 

	HashMap<String, List<MethodCalls>> MethodCallsEXECHashMapCaller= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<MethodCalls>> MethodCallsEXECHashMapCallee= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<Parameter2>> ParameterHashMap= new HashMap<String, List<Parameter2>>(); 
	HashMap<String, List<MethodField2>> MethodFieldsHashMap= new HashMap<String, List<MethodField2>>(); 
	HashMap<String, List<MethodTrace2>> MethodTrace2HashMap= new HashMap<String, List<MethodTrace2>>(); 
	HashMap<Integer, Method2Details> MethodDetailsHashMap= new HashMap<Integer, Method2Details>(); 
	LinkedHashMap<String, Method2Details> MethodDetailsLinkedHashMap= new LinkedHashMap<String, Method2Details>(); 
	MethodTraceOld methodtraceold= new MethodTraceOld(); 
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
	public Method getMethodrep() {
		return methodrep;
	}
	public void setMethodrep(Method methodrep) {
		this.methodrep = methodrep;
	}
	public ClassRepresentation2 getOwnerClass() {
		return OwnerClass;
	}
	public void setOwnerClass(ClassRepresentation2 ownerClass) {
		OwnerClass = ownerClass;
	}
	public List<Method> getCallers() {
		return callersList;
	}
	public void setCallers(List<Method> callers) {
		this.callersList = callers;
	}
	public List<Method> getCallees() {
		return calleesList;
	}
	public void setCallees(List<Method> callees) {
		this.calleesList = callees;
	}
	public List<Parameter2> getParameters() {
		return parametersList;
	}
	public void setParameters(List<Parameter2> parameters) {
		this.parametersList = parameters;
	}
	public List<MethodField2> getMethodfields() {
		return methodfieldsList;
	}
	public void setMethodfields(List<MethodField2> methodfields) {
		this.methodfieldsList = methodfields;
	}

	public HashMap<String, MethodTrace2> getMethodtraces() {
		return methodtraces;
	}
	public void setMethodtraces(HashMap<String, MethodTrace2> methodtraces) {
		this.methodtraces = methodtraces;
	}
	public Method2Details(Method methodrep, ClassRepresentation2 ownerClass,
			List<Method> callers, List<Method> callees, List<Parameter2> parameters,
			List<MethodField2> methodfields, HashMap<String, MethodTrace2> methodtraces) {
		super();
		this.methodrep = methodrep;
		OwnerClass = ownerClass;
		this.callersList = callers;
		this.calleesList = callees;
		this.parametersList = parameters;
		this.methodfieldsList = methodfields;
		this.methodtraces = methodtraces;
	} 
	
	
	public Method2Details(Method methodrep, ClassRepresentation2 ownerClass,
			List<Method> callersList, List<Method> calleesList,
			List<Method> callersListExecuted, List<Method> calleesListExecuted,
			List<Parameter2> parametersList, List<MethodField2> methodfieldsList,
			HashMap<String, MethodTrace2> methodtraces, HashMap<Integer, Method2Details> methodDetailsHashMap) {
		super();
		this.methodrep = methodrep;
		OwnerClass = ownerClass;
		this.callersList = callersList;
		this.calleesList = calleesList;
		this.callersListExecuted = callersListExecuted;
		this.calleesListExecuted = calleesListExecuted;
		this.parametersList = parametersList;
		this.methodfieldsList = methodfieldsList;
		this.methodtraces = methodtraces;
		MethodDetailsHashMap = methodDetailsHashMap;
	}
	public Method2Details() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public  LinkedHashMap<String, Method2Details> ReadClassesRepresentations2(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
//		MethodCallsEXECHashMapCallee=methodtraceold.CreateMethodCallsHashMapCalleeEXEC(conn); 
//		ParameterHashMap=methodtraceold.CreateParametersHashMap(conn); 
//		MethodFieldsHashMap=methodtraceold.CreateFieldtypeHashMap(conn); 
//		MethodTrace2HashMap=methodtraceold.CreateClassTraceHashMap(conn); 
//
//		MethodCallsEXECHashMapCaller=methodtraceold.CreateMethodCallsHashMapCallerEXEC(conn); 
		HashMap<String, Method2Details> MethodHashMap= new HashMap<String, Method2Details>(); 

		MethodHashMap=methodtraceold.CreateMethodHashMap(conn); 
//		MethodCallsHashMapCaller=methodtraceold.CreateMethodCallsHashMapCaller(conn); 
//		MethodCallsHashMapCallee=methodtraceold.CreateMethodCallsHashMapCallee(conn); 
		DatabaseReading2 db = new DatabaseReading2(); 
		Method2Details methoddet2= new Method2Details(); 
		HashMap<String, MethodTrace2> methodtraces= new HashMap <String, MethodTrace2>();
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from methods"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		
		
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		
		 ResultSet myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
		 System.out.println(index);
		 while(myresults.next() ) {
			 	methoddet2= new Method2Details(); 
			     String id = myresults.getString("id"); 			
				 String methodname = myresults.getString("methodname"); 
				
				 Method methodrep= new Method(id, methodname); 
				 methoddet2.setMethodrep(methodrep);
				 
				 
				 ClassRepresentation2 classrep= new ClassRepresentation2(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 methoddet2.setOwnerClass(classrep);
				 
				 
				 
				 List<MethodCalls> mycalleelist = MethodCallsHashMapCallee.get(id); 
				 List<Method> mycalleelistrep = new ArrayList<Method>(); 
				 if(mycalleelist!=null) {
				 for(MethodCalls mycallee: mycalleelist) {
					 
						 
					 
					 Method meth= new Method(); 	
					 meth.setMethodid(mycallee.Callee.methodid);
					 meth.setMethodname(mycallee.Callee.methodname);
					Method2Details val = MethodHashMap.get(meth.getMethodid()); 
					 meth.setClassrep(val.OwnerClass);
					 mycalleelistrep.add(meth); 
				 }
				 
				 methoddet2.setCallees(mycalleelistrep);
				 }
				 
				 List<MethodCalls> mycallerlist = MethodCallsHashMapCaller.get(id); 
				 List<Method> mycallerlistrep = new ArrayList<Method>(); 
				 if(mycallerlist!=null) {
					 for(MethodCalls mycaller: mycallerlist) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycaller.Caller.methodid);
						 meth.setMethodname(mycaller.Caller.methodname);
						Method2Details val = MethodHashMap.get(meth.getMethodid()); 
						 meth.setClassrep(val.OwnerClass);
						 mycallerlistrep.add(meth); 
					 }
					 methoddet2.setCallers(mycallerlistrep);
				 }
				
				 
				 
				 
				 
				 List<MethodCalls> mycallerlistexecuted = MethodCallsEXECHashMapCaller.get(id); 
				 List<Method> mycallerlistrepexecuted = new ArrayList<Method>(); 
				 if(mycallerlistexecuted!=null) {
					 for(MethodCalls mycaller: mycallerlistexecuted) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycaller.Caller.methodid);
						 meth.setMethodname(mycaller.Caller.methodname);
						Method2Details val = MethodHashMap.get(meth.getMethodid()); 
						if(val!=null) {
							meth.setClassrep(val.OwnerClass);
							 mycallerlistrepexecuted.add(meth); 
						}
						 
					 }
					 methoddet2.setCallersListExecuted(mycallerlistrepexecuted);
				 }
				
				 
				 
				 
				 List<MethodCalls> mycalleelistexecuted = MethodCallsEXECHashMapCallee.get(id); 
				 List<Method> mycalleelistrepexecuted = new ArrayList<Method>(); 
				 if(mycalleelistexecuted!=null) {
					 for(MethodCalls mycallee: mycalleelistexecuted) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycallee.Callee.methodid);
						 meth.setMethodname(mycallee.Callee.methodname);
						Method2Details val = MethodHashMap.get(meth.getMethodid()); 
						 meth.setClassrep(val.OwnerClass);
						 mycalleelistrepexecuted.add(meth); 
					 }
					 methoddet2.setCalleesListExecuted(mycalleelistrepexecuted);
					 
				 }
				 
				 
				 
				 
				
				
				 
				 
				 
				 
				 List<Parameter2> myparams = ParameterHashMap.get(id); 
				 List<Parameter2> myparamslist = new ArrayList<Parameter2>(); 
				 if(myparams!=null) {
					 for(Parameter2 param: myparams) {
						 
						 param.setParameterName(param.parameterName);
						 
						 ClassRepresentation2 ParamType= new ClassRepresentation2(); 
						
						 param.setParameterType(param.ParameterType);
						 
						 ClassRepresentation2 OwnerType= new ClassRepresentation2(); 
						
						 param.setOwnerClass(param.OwnerClass);
						 
						 param.setMethodname(param.methodname);
						 
						 param.setIsReturn(param.isReturn);
						 myparamslist.add(param); 
					 }
					 methoddet2.setParameters(myparamslist);
					 
				 }
				 
				 
				 
				 
				
				
				 
				 List<MethodField2> methodfields = MethodFieldsHashMap.get(id); 
				 List<MethodField2> methodfields2 = new ArrayList<MethodField2>(); 
				 if(methodfields!=null) {
					 for(MethodField2 methodfield: methodfields) {
						 

						 MethodField2 methfield= new MethodField2(); 	
						 methfield.setFieldName(methodfield.FieldName);
						 
						
						 methfield.setMethodFieldType(methodfield.MethodFieldType);
						 
						 methfield.setMethodFieldType(methodfield.OwnerClass);
						 
						
						 methodfields2.add(methfield); 
					 }
					 methoddet2.setMethodfields(methodfields2);
					 
				 }
				
				 
				
				 
				 
				 
				 List<MethodTrace2> methodtracesList = MethodTrace2HashMap.get(id); 
					
				 if(methodtracesList!=null) {
					 for(MethodTrace2 methodtrace: methodtracesList) {
						 

						 MethodTrace2 MethodTrace= new MethodTrace2();
							//classtrace.setID(classtraces.getString("id"));
							
							
						
							MethodTrace.setGold(methodtrace.getGold());
							System.out.println("GOLD: "+methodtrace.getGold());
							MethodTrace.setGoldFinal(methodtrace.getGoldfinal());
							MethodTrace.setGold4(methodtrace.getGold4());
							MethodTrace.setGold3(methodtrace.getGold3());
							MethodTrace.setSubject(methodtrace.getSubject());
							
							
							MethodTrace.setRequirement(methodtrace.Requirement);

							
							MethodTrace.setClassRepresentation(methodtrace.ClassRepresentation);
							
							
							MethodTrace.setMethod(methodtrace.Method);
							methodtraces.put(methodtrace.Requirement.ID, MethodTrace);
							methoddet2.setMethodtraces(methodtraces);
					 }
					 
				 }
				 
			
				
				 
				 MethodDetailsLinkedHashMap.put(id, methoddet2); 
		
				 index=index+1; 
				 myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
				 System.out.println("index 3 "+index);
				 
		 }
		
		 
		 
		 
		 
		 return MethodDetailsLinkedHashMap; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public  HashMap<Integer, Method2Details> ReadClassesRepresentations(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
		Method2Details methoddet2= new Method2Details(); 
		HashMap<String, MethodTrace2> methodtraces= new HashMap <String, MethodTrace2>();
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from methods"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		
		 ResultSet myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
		 while(myresults.next() ) {
			 	methoddet2= new Method2Details(); 
			     String id = myresults.getString("id"); 			
				 String methodname = myresults.getString("methodname"); 
				
				 Method methodrep= new Method(id, methodname); 
				 methoddet2.setMethodrep(methodrep);
				 
				 
				 ClassRepresentation2 classrep= new ClassRepresentation2(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 methoddet2.setOwnerClass(classrep);
				 
				 
				 
				 ResultSet callers=st.executeQuery("select methodcalls.* from methodcalls where calleemethodid='" + id+"'"); 
				 this.callersList= new  ArrayList<Method>(); 
				 while(callers.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(callers.getString("callermethodid"));
					 meth.setMethodname(callers.getString("callername"));
				//	 meth.setRequirementsGold(requirementsGold);
					 this.callersList.add(meth); 					 
					 methoddet2.setCallers(callersList);
				 }
				 
				 ResultSet callees=st.executeQuery("select methodcalls.* from methodcalls where callermethodid='" + id+"'"); 
				 this.calleesList= new  ArrayList<Method>(); 
				 while(callees.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(callees.getString("calleemethodid"));
					 meth.setMethodname(callees.getString("calleename"));
					// meth.setRequirementsGold(requirementsGold);
					 this.calleesList.add(meth); 					 
					 methoddet2.setCallees(calleesList);
				 }
				 
				 
				 ResultSet callersExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where calleemethodid='" + id+"'"); 
				 this.calleesListExecuted= new  ArrayList<Method>(); 
				 while(callersExecuted.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(callersExecuted.getString("callermethodid"));
					 meth.setMethodname(callersExecuted.getString("callername"));
					// meth.setRequirementsGold(requirementsGold);
					 this.calleesListExecuted.add(meth); 					 
					 methoddet2.setCallersListExecuted(calleesListExecuted);
				 }
				 
				 ResultSet calleesExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where callermethodid='" + id+"'"); 
				 this.callersListExecuted= new  ArrayList<Method>(); 
				 while(calleesExecuted.next()) {

					 Method meth= new Method(); 	
					 meth.setMethodid(calleesExecuted.getString("calleemethodid"));
					 meth.setMethodname(calleesExecuted.getString("calleename"));
				//	 meth.setRequirementsGold(requirementsGold);
					 this.callersListExecuted.add(meth); 					 
					 methoddet2.setCalleesListExecuted(callersListExecuted);
				 }
				 
				 ResultSet parameters=st.executeQuery("select parameters.* from parameters where methodid='" + id+"'"); 
				 this.parametersList= new ArrayList<Parameter2>(); 
				 while(parameters.next()) {
					 
					 Parameter2 param= new Parameter2(); 	
					 param.setParameterName(parameters.getString("parametername"));
					 
					 ClassRepresentation2 ParamType= new ClassRepresentation2(); 
					 ParamType.setClassid(parameters.getString("parameterclass"));
					 ParamType.setClassname(parameters.getString("parametertype"));
					 param.setParameterType(ParamType);
					 
					 ClassRepresentation2 OwnerType= new ClassRepresentation2(); 
					 OwnerType.setClassid(parameters.getString("classid"));
					 OwnerType.setClassname(parameters.getString("classname"));
					 param.setOwnerClass(OwnerType);
					 
					 param.setIsReturn(parameters.getString("isreturn"));
					 parametersList.add(param); 
					 methoddet2.setParameters(parametersList);
				 }
				 
				 
				 ResultSet methodFields=st.executeQuery("select fieldmethods.* from fieldmethods where ownermethodid='" + id+"'"); 
				 this.methodfieldsList= new ArrayList<MethodField2>(); 
				 while(methodFields.next()) {
					
					 MethodField2 methfield= new MethodField2(); 	
					 methfield.setFieldName(methodFields.getString("fieldaccess"));
					 
					 ClassRepresentation2 FieldType= new ClassRepresentation2(); 
					 FieldType.setClassid(methodFields.getString("fieldtypeclassid"));
					 FieldType.setClassname(methodFields.getString("fieldtypeclassname"));
					 methfield.setMethodFieldType(FieldType);
					 
					 ClassRepresentation2 OwnerType= new ClassRepresentation2(); 
					 OwnerType.setClassid(methodFields.getString("ownerclassid"));
					 OwnerType.setClassname(methodFields.getString("ownerclassname"));
					 methfield.setOwnerClass(OwnerType);
					 
					
					 methodfieldsList.add(methfield); 
					 methoddet2.setMethodfields(methodfieldsList);
				 }
				 
				 
				 ResultSet methodtracesres = st.executeQuery("SELECT traces.* from traces where methodid ='"+id+"'"); 
					//populateTables(classtraces, conn);
				 this.methodtraces= new  HashMap <String, MethodTrace2>();	
				 while(methodtracesres.next()) {
						
						MethodTrace2 MethodTrace= new MethodTrace2();
						//classtrace.setID(classtraces.getString("id"));
						MethodTrace.setGold(methodtracesres.getString("gold"));
						
						
						MethodTrace.setGoldFinal(methodtracesres.getString("goldfinal"));
						
						MethodTrace.setGold3(methodtracesres.getString("gold3")); 
						 
						MethodTrace.setGold4(methodtracesres.getString("gold4")); 
						MethodTrace.setSubject(methodtracesres.getString("subject"));
						
						Requirement2 requirement= new Requirement2();
						requirement.setID(methodtracesres.getString("requirementid"));
						requirement.setRequirementName(methodtracesres.getString("requirement"));
						MethodTrace.setRequirement(requirement);

						ClassRepresentation2 myclass= new ClassRepresentation2();
						myclass.setClassname(methodtracesres.getString("classname"));
						myclass.setClassid(methodtracesres.getString("classid"));
						MethodTrace.setClassRepresentation(myclass);
						
						Method methodrepres = new Method(); 
						methodrepres.setMethodid(methodtracesres.getString("methodid"));
						methodrepres.setMethodname(methodtracesres.getString("method"));
						MethodTrace.setMethod(methodrepres);
						methodtraces.put(requirement.getID(), MethodTrace);
						methoddet2.setMethodtraces(methodtraces);
						
						
					}
				
				 
				 MethodDetailsLinkedHashMap.put(id, methoddet2); 
				 MethodDetailsHashMap.put(index, methoddet2); 
				// System.out.println("METHOD DETAILS 2 tostring: "+methoddet2.toString());
				 index=index+1; 
				 myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
				 System.out.println(index);
		 }
		
		 
		 
		 
		 
		 return MethodDetailsHashMap; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	public  LinkedHashMap<String, Method2Details> ReadClassesRepresentations2(Connection conn) throws SQLException {
//		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
//		// Retrieving the calleeid
//		DatabaseReading2 db = new DatabaseReading2(); 
//		Method2Details methoddet2= new Method2Details(); 
//		//CLASSESHASHMAP
//		String rowcount = null; 
//		Statement st = conn.createStatement();
//		Statement st2 = conn.createStatement();
//		ResultSet var = st.executeQuery("select count(*) from methods"); 
//		while(var.next()){
//			rowcount = var.getString("count(*)");
//		}
//		System.out.println("ROW COUNT::::::"+rowcount); 
//		int rowcountint= Integer.parseInt(rowcount); 
//	
//		int index=1; 
//		
//		 ResultSet myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
//		 System.out.println(index);
//		 while(myresults.next() ) {
//			 	methoddet2= new Method2Details(); 
//			     String id = myresults.getString("id"); 			
//				 String methodname = myresults.getString("methodname"); 
//				
//				 Method2Representation methodrep= new Method2Representation(id, methodname); 
//				 methoddet2.setMethodrep(methodrep);
//				 
//				 
//				 ClassRepresentation2 classrep= new ClassRepresentation2(); 
//				 classrep.setClassid(myresults.getString("classid"));
//				 classrep.setClassname(myresults.getString("classname"));
//				 methoddet2.setOwnerClass(classrep);
//				 
//				 
//				 
//				 ResultSet callers=st.executeQuery("select methodcalls.* from methodcalls where calleemethodid='" + id+"'"); 
//				 this.callersList= new  ArrayList<Method2Representation>(); 
//				 while(callers.next()) {
//
//					 
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callers.getString("callermethodid"));
//					 meth.setMethodname(callers.getString("callername"));
//					// meth.setRequirementsGold(requirementsGold);
//					 this.callersList.add(meth); 					 
//					 
//				 }
//				 methoddet2.setCallers(callersList);
//				 ResultSet callees=st.executeQuery("select methodcalls.* from methodcalls where callermethodid='" + id+"'"); 
//				 this.calleesList= new  ArrayList<Method2Representation>(); 
//				 while(callees.next()) {
//
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callees.getString("calleemethodid"));
//					 meth.setMethodname(callees.getString("calleename"));
//					// meth.setRequirementsGold(requirementsGold);
//					 this.calleesList.add(meth); 					 
//					
//				 }
//				 methoddet2.setCallees(calleesList);
//				 
//				 ResultSet callersExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where calleemethodid='" + id+"'"); 
//				 this.calleesListExecuted= new  ArrayList<Method2Representation>(); 
//				 while(callersExecuted.next()) {
//
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callersExecuted.getString("callermethodid"));
//					 meth.setMethodname(callersExecuted.getString("callername"));
//				//	 meth.setRequirementsGold(requirementsGold);
//					 this.calleesListExecuted.add(meth); 					 
//					 
//				 }
//				 methoddet2.setCallersListExecuted(calleesListExecuted);
//				 
//				 ResultSet calleesExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where callermethodid='" + id+"'"); 
//				 this.callersListExecuted= new  ArrayList<Method2Representation>(); 
//				 while(calleesExecuted.next()) {
//
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(calleesExecuted.getString("calleemethodid"));
//					 meth.setMethodname(calleesExecuted.getString("calleename"));
//				//	 meth.setRequirementsGold(requirementsGold);
//					 this.callersListExecuted.add(meth); 					 
//					
//				 }
//				 methoddet2.setCalleesListExecuted(callersListExecuted);
//				 ResultSet parameters=st.executeQuery("select parameters.* from parameters where methodid='" + id+"'"); 
//				 this.parametersList= new ArrayList<Parameter2>(); 
//				 while(parameters.next()) {
//					 
//					 Parameter2 param= new Parameter2(); 	
//					 param.setParameterName(parameters.getString("parametername"));
//					 
//					 ClassRepresentation2 ParamType= new ClassRepresentation2(); 
//					 ParamType.setClassid(parameters.getString("parameterclass"));
//					 ParamType.setClassname(parameters.getString("parametertype"));
//					 param.setParameterType(ParamType);
//					 
//					 ClassRepresentation2 OwnerType= new ClassRepresentation2(); 
//					 OwnerType.setClassid(parameters.getString("classid"));
//					 OwnerType.setClassname(parameters.getString("classname"));
//					 param.setOwnerClass(OwnerType);
//					 
//					 param.setMethodname(parameters.getString("methodname"));
//					 
//					 param.setIsReturn(parameters.getString("isreturn"));
//					 parametersList.add(param); 
//					 methoddet2.setParameters(parametersList);
//				 }
//				 
//				 
//				 ResultSet methodFields=st.executeQuery("select fieldmethods.* from fieldmethods where ownermethodid='" + id+"'"); 
//				 this.methodfieldsList= new ArrayList<MethodField2>(); 
//				 while(methodFields.next()) {
//					
//					 MethodField2 methfield= new MethodField2(); 	
//					 methfield.setFieldName(methodFields.getString("fieldaccess"));
//					 
//					 ClassRepresentation2 FieldType= new ClassRepresentation2(); 
//					 FieldType.setClassid(methodFields.getString("fieldtypeclassid"));
//					 FieldType.setClassname(methodFields.getString("fieldtype"));
//					 methfield.setMethodFieldType(FieldType);
//					 
//					 ClassRepresentation2 OwnerType= new ClassRepresentation2(); 
//					 OwnerType.setClassid(methodFields.getString("ownerclassid"));
//					 OwnerType.setClassname(methodFields.getString("classname"));
//					 methfield.setOwnerClass(OwnerType);
//					 
//					
//					 methodfieldsList.add(methfield); 
//					 methoddet2.setMethodfields(methodfieldsList);
//				 }
//				 
//				 
//				 ResultSet methodtracesres = st.executeQuery("SELECT traces.* from traces where methodid ='"+id+"'"); 
//					//populateTables(classtraces, conn);
//				 this.methodtraces= new  HashMap <String, MethodTrace2>();	
//				 while(methodtracesres.next()) {
//						
//						MethodTrace2 MethodTrace= new MethodTrace2();
//						//classtrace.setID(classtraces.getString("id"));
//						
//						
//					
//						MethodTrace.setGold(methodtracesres.getString("gold"));
//					
//						MethodTrace.setGold2(methodtracesres.getString("gold2"));
//						MethodTrace.setGold4(methodtracesres.getString("gold4"));
//						MethodTrace.setGold3(methodtracesres.getString("gold3"));
//						MethodTrace.setSubject(methodtracesres.getString("subject"));
//						
//						Requirement2 requirement= new Requirement2();
//						requirement.setID(methodtracesres.getString("requirementid"));
//						requirement.setRequirementName(methodtracesres.getString("requirement"));
//						MethodTrace.setRequirement(requirement);
//
//						ClassRepresentation2 myclass= new ClassRepresentation2();
//						myclass.setClassname(methodtracesres.getString("classname"));
//						myclass.setClassid(methodtracesres.getString("classid"));
//						MethodTrace.setClassRepresentation(myclass);
//						
//						Method2Representation methodrepres = new Method2Representation(); 
//						methodrepres.setMethodid(methodtracesres.getString("methodid"));
//						methodrepres.setMethodname(methodtracesres.getString("method"));
//						MethodTrace.setMethodRepresentation(methodrepres);
//						methodtraces.put(requirement.getID(), MethodTrace);
//						methoddet2.setMethodtraces(methodtraces);
//						
//						
//					}
//				
//				 
//				 MethodDetailsLinkedHashMap.put(id, methoddet2); 
//		
//				 index=index+1; 
//				 myresults = st.executeQuery("SELECT methods.* from methods where id='"+ index +"'"); 
//				 System.out.println("index 3 "+index);
//				 
//		 }
//		
//		 
//		 
//		 
//		 
//		 return MethodDetailsLinkedHashMap; 
//	}
	
	
}
