package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodTraceSubjectTSubjectNOriginal {
	
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
	public String gold3; 
	public String gold4; 
	public String goldfinal; 

	List<Method> callersList= new ArrayList<Method>(); 
	List<Method> calleesList= new ArrayList<Method>(); 
	List<Method> callersListExecuted= new ArrayList<Method>(); 
	List<Method> calleesListExecuted= new ArrayList<Method>(); 
	HashMap<String, List<Interface>> InterfaceHashMapOwner= new HashMap<String, List<Interface>>(); 
	HashMap<String, List<Interface>> InterfaceHashMapInterface= new HashMap<String, List<Interface>>(); 
	HashMap<String, MethodDetails> MethodHashMap= new HashMap<String, MethodDetails>(); 
	HashMap<String, Clazz> ClassHashMap= new HashMap<String, Clazz>(); 
	HashMap<String, List<MethodCalls>> MethodCallsHashMapCaller= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<Parameter2>> ParameterHashMap= new HashMap<String, List<Parameter2>>(); 
	HashMap<String, List<MethodField2>> MethodFieldsHashMap= new HashMap<String, List<MethodField2>>(); 

	HashMap<String, List<MethodCalls>> MethodCallsHashMapCallee= new HashMap<String, List<MethodCalls>>(); 

	HashMap<String, List<MethodCalls>> MethodCallsEXECHashMapCaller= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<MethodCalls>> MethodCallsEXECHashMapCallee= new HashMap<String, List<MethodCalls>>(); 
	HashMap<String, List<MethodTrace2>> MethodTrace2HashMap= new HashMap<String, List<MethodTrace2>>(); 
	
	@Override
	public String toString() {
		return "MethodTraceSubjectTSubjectNOriginal [MethodRepresentation=" + MethodRepresentation + ", Requirement="
				+ Requirement + ", ClassRepresentation=" + ClassRepresentation + "]";
	}

	public String getGold3() {
		return gold3;
	}

	public void setGold3(String gold3) {
		this.gold3 = gold3;
	}

	public String getGold4() {
		return gold4;
	}

	public void setGold4(String gold4) {
		this.gold4 = gold4;
	}

	public String getGoldfinal() {
		return gold2;
	}

	public void setGold2(String godlfinal) {
		this.goldfinal = godlfinal;
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

	public ArrayList<MethodTraceSubjectTSubjectNOriginal> getMethodtraces() {
		return methodtraces;
	}

	public void setMethodtraces(ArrayList<MethodTraceSubjectTSubjectNOriginal> methodtraces) {
		this.methodtraces = methodtraces;
	}

	public HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> getMethodtraceHashMap() {
		return methodtraceHashMap;
	}

	public void setMethodtraceHashMap(HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> methodtraceHashMap) {
		this.methodtraceHashMap = methodtraceHashMap;
	}

	public ArrayList<MethodTraceSubjectTSubjectNOriginal> methodtraces; 
	
	public String getGoldprediction() {
		return goldprediction;
	}

	public void setGoldprediction(String goldprediction) {
		this.goldprediction = goldprediction;
	}

	HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> methodtraceHashMap= new HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> (); 
	
	public MethodTraceSubjectTSubjectNOriginal() {
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

	
	
	
	public HashMap<String, List<MethodTrace2>> CreateClassTraceHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from traces"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet traces=st.executeQuery("select traces.* from traces where id='" + index+"'"); 
		while(index<count) {
			
			List<MethodTrace2> mymethodtracelist= new ArrayList<MethodTrace2>(); 

			 while(traces.next()) {
				 MethodTrace2 MethodTrace= new MethodTrace2();
					//classtrace.setID(classtraces.getString("id"));
					
					
				
					MethodTrace.setGold(traces.getString("gold"));
				
					MethodTrace.setGoldFinal(traces.getString("goldfinal"));
					MethodTrace.setGold4(traces.getString("gold4"));
					MethodTrace.setGold3(traces.getString("gold3"));
					MethodTrace.setSubject(traces.getString("subject"));
					
					Requirement requirement= new Requirement();
					requirement.setID(traces.getString("requirementid"));
					requirement.setRequirementName(traces.getString("requirement"));
					MethodTrace.setRequirement(requirement);

					Clazz myclass= new Clazz();
					myclass.setClassname(traces.getString("classname"));
					myclass.setClassid(traces.getString("classid"));
					MethodTrace.setClassRepresentation(myclass);
					
					Method methodrepres = new Method(); 
					methodrepres.setMethodid(traces.getString("methodid"));
					methodrepres.setMethodname(traces.getString("method"));
					MethodTrace.setMethod(methodrepres);
					
					 if(MethodTrace2HashMap.get(methodrepres.getMethodid())!=null) {
						 mymethodtracelist= MethodTrace2HashMap.get(methodrepres.getMethodid()); 
					 }
					 
					
					 mymethodtracelist.add(MethodTrace); 
					 MethodTrace2HashMap.put(methodrepres.getMethodid(), mymethodtracelist); 
				
			 }
			 index++; 
			 traces=st.executeQuery("select traces.* from traces where id='" + index+"'");
		}
		
		
		return MethodTrace2HashMap;
		
	}
	
	
	
	
	
	public HashMap<String, List<Interface>> CreateOwnerHashMapInterface(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from interfaces"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet myinterfaces=st.executeQuery("select interfaces.* from interfaces where id='" + index+"'"); 
		while(index<count) {
			
			List<Interface> mylist= new ArrayList<Interface>(); 

			 while(myinterfaces.next()) {
				 Interface myinterface = new Interface(); 
				 Clazz myclassrepinterface= new Clazz(); 
				 Clazz ownerclass= new Clazz(); 
				 myclassrepinterface.setClassid(myinterfaces.getString("interfaceclassid"));
				 myclassrepinterface.setClassname(myinterfaces.getString("interfacename"));
				 
				 ownerclass.setClassid(myinterfaces.getString("ownerclassid"));
				 ownerclass.setClassname(myinterfaces.getString("classname"));
				 myinterface.setInterfaceClass(myclassrepinterface);
				 myinterface.setImplementation(ownerclass);
				 String key1 =myclassrepinterface.getClassid(); 
				 String key2= ownerclass.getClassid(); 
				 String key=key1+"-"+key2; 
				 mylist.add(myinterface); 
				 InterfaceHashMapOwner.put(key2, mylist); 
				
			 }
			 index++; 
			 myinterfaces = st.executeQuery("select interfaces.* from interfaces where id='" + index+"'"); 
		}
		
		
		return InterfaceHashMapOwner;
		
	}
	
	
	
	
	public HashMap<String, List<Interface>> CreateInterfaceHashMapInterface(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from interfaces"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet myinterfaces=st.executeQuery("select interfaces.* from interfaces where id='" + index+"'"); 

		while(index<count) {
			List<Interface> mylist= new ArrayList<Interface>(); 

			 while(myinterfaces.next()) {
				 Interface myinterface = new Interface(); 
				 Clazz myclassrepinterface= new Clazz(); 
				 Clazz ownerclass= new Clazz(); 
				 myclassrepinterface.setClassid(myinterfaces.getString("interfaceclassid"));
				 myclassrepinterface.setClassname(myinterfaces.getString("interfacename"));
				 
				 ownerclass.setClassid(myinterfaces.getString("ownerclassid"));
				 ownerclass.setClassname(myinterfaces.getString("classname"));
				 myinterface.setInterfaceClass(myclassrepinterface);
				 myinterface.setImplementation(ownerclass);
				 String key1 =myclassrepinterface.getClassid(); 
				 String key2= ownerclass.getClassid(); 
				 String key=key1+"-"+key2; 
				 mylist.add(myinterface); 
				 InterfaceHashMapInterface.put(key1, mylist); 
				
			 }
			 index++; 
			 myinterfaces = st.executeQuery("select interfaces.* from interfaces where id='" + index+"'"); 
		}
		
		
		return InterfaceHashMapInterface;
		
	}
	
	
	
	
	
	public HashMap<String, List<Parameter2>> CreateParametersHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from parameters"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet parameters=st.executeQuery("select parameters.* from parameters where id='" + index+"'"); 

		while(index<count) {
			 List<Parameter2> parametersList = new ArrayList<Parameter2>(); 
		 while(parameters.next()) {
			 Parameter2 param= new Parameter2(); 	
			 String parametername= parameters.getString("parametername"); 
			 String parametertype= parameters.getString("parametertype"); 
			 String parameterclass= parameters.getString("parameterclass"); 
			 String classid= parameters.getString("classid"); 
			 String classname= parameters.getString("classname"); 
			 String methodid= parameters.getString("methodid");
			 String methodname= parameters.getString("methodname");
			 String isreturn= parameters.getString("isreturn");
			 
			 param.setParameterName(parametername);
			 Clazz ParamType= new Clazz(); 
			 ParamType.setClassid(parameterclass);
			 ParamType.setClassname(parametertype);
			 param.setParameterType(ParamType);
			 
			 Clazz OwnerType= new Clazz(); 
			 OwnerType.setClassid(parameters.getString("classid"));
			 OwnerType.setClassname(parameters.getString("classname"));
			 param.setOwnerClass(OwnerType);
			 
			 param.setMethodname(parameters.getString("methodname"));
			 
			 param.setIsReturn(parameters.getString("isreturn"));
			
			 
			
			 if(ParameterHashMap.get(methodid)!=null) {
				 parametersList= ParameterHashMap.get(methodid); 
			 }
			 
			
			 parametersList.add(param); 
			 ParameterHashMap.put(methodid, parametersList); 
			
			 
			 
		 }
		 index++; 
		 parameters=st.executeQuery("select parameters.* from parameters where id='" + index+"'"); 
		}
		
		return ParameterHashMap;
		
		
	}
	
	
	
	
	
	
	
	public HashMap<String, List<MethodField2>> CreateFieldtypeHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from fieldmethods"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet methodFields=st.executeQuery("select fieldmethods.* from fieldmethods where id='" + index+"'"); 

		while(index<count) {
			 List<MethodField2> methodfieldsList = new ArrayList<MethodField2>(); 
		 while(methodFields.next()) {
			 MethodField2 methfield= new MethodField2(); 	
			 methfield.setFieldName(methodFields.getString("fieldaccess"));
			 
			 Clazz FieldType= new Clazz(); 
			 FieldType.setClassid(methodFields.getString("fieldtypeclassid"));
			 FieldType.setClassname(methodFields.getString("fieldtype"));
			 methfield.setMethodFieldType(FieldType);
			 
			 Clazz OwnerType= new Clazz(); 
			 OwnerType.setClassid(methodFields.getString("ownerclassid"));
			 OwnerType.setClassname(methodFields.getString("classname"));
			 methfield.setOwnerClass(OwnerType);
			 
			
			 String methid=methodFields.getString("ownermethodid"); 
			
			 if(MethodFieldsHashMap.get(methodFields.getString("ownermethodid"))!=null) {
				 methodfieldsList= MethodFieldsHashMap.get(methid); 
			 }
			 
			
			 methodfieldsList.add(methfield); 
			 MethodFieldsHashMap.put(methid, methodfieldsList); 
			
			
			 
			 
		 }
		 index++; 
		 methodFields=st.executeQuery("select fieldmethods.* from fieldmethods where id='" + index+"'"); 
		}
		
		return MethodFieldsHashMap;
		
		
	}
	
	
	
	
	
	
	public HashMap<String, List<MethodCalls>> CreateMethodCallsHashMapCaller(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from methodcalls"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet methodcalls=st.executeQuery("select methodcalls.* from methodcalls where id='" + index+"'"); 

		while(index<count) {
		 

		 while(methodcalls.next()) {
			 List<MethodCalls>mylist= new ArrayList<MethodCalls>(); 
			 String callerid= methodcalls.getString("callermethodid"); 
			 String calleeid= methodcalls.getString("calleemethodid"); 
			 String callername= methodcalls.getString("callername"); 
			 String calleename= methodcalls.getString("calleename"); 
			
			 
			 Method callerclass= new Method(); 
			 callerclass.setMethodid(callerid); 
			callerclass.setMethodname(callername); 
			 
			 Method calleeclass= new Method(); 
			 calleeclass.setMethodid(calleeid);
			 calleeclass.setMethodname(calleename);
			 
			 
			 MethodCalls methodcall= new MethodCalls(); 
			 methodcall.setCaller(callerclass);
			 methodcall.setCallee(calleeclass);
			 if(MethodCallsHashMapCaller.get(callerid)!=null) {
				 mylist= MethodCallsHashMapCaller.get(callerid); 
			 }
			 
			 String key = callerid+"-"+calleeid; 
			 mylist.add(methodcall); 
			 MethodCallsHashMapCaller.put(calleeid, mylist); 
			
			 
			 
		 }
		 index++; 
		 methodcalls=st.executeQuery("select methodcalls.* from methodcalls where id='" + index+"'"); 
		}
		
		return MethodCallsHashMapCaller;
		
		
	}
	
	
	
	public HashMap<String, List<MethodCalls>> CreateMethodCallsHashMapCallee(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from methodcalls"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet methodcalls=st.executeQuery("select methodcalls.* from methodcalls where id='" + index+"'"); 

		while(index<count) {
		 

		 while(methodcalls.next()) {
			 List<MethodCalls>mylist= new ArrayList<MethodCalls>(); 
			 String callerid= methodcalls.getString("callermethodid"); 
			 String calleeid= methodcalls.getString("calleemethodid"); 
			 String callername= methodcalls.getString("callername"); 
			 String calleename= methodcalls.getString("calleename"); 
			
			 
			 Method callerclass= new Method(); 
			 callerclass.setMethodid(callerid); 
			callerclass.setMethodname(callername); 
			 
			 Method calleeclass= new Method(); 
			 calleeclass.setMethodid(calleeid);
			 calleeclass.setMethodname(calleename);
			 
			 
			 MethodCalls methodcall= new MethodCalls(); 
			 methodcall.setCaller(callerclass);
			 methodcall.setCallee(calleeclass);
			 if(MethodCallsHashMapCallee.get(callerid)!=null) {
				 mylist= MethodCallsHashMapCallee.get(callerid); 
			 }
			 
			 String key = callerid+"-"+calleeid; 
			 mylist.add(methodcall); 
			 MethodCallsHashMapCallee.put(callerid, mylist); 
			
			 
			 
		 }
		 index++; 
		 methodcalls=st.executeQuery("select methodcalls.* from methodcalls where id='" + index+"'"); 
		}
		
		return MethodCallsHashMapCallee;
		
		
	}
	
	
	
	
	
	public HashMap<String, List<MethodCalls>> CreateMethodCallsHashMapCalleeEXEC(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from methodcallsexecuted"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet methodcalls=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where id='" + index+"'"); 

		while(index<count) {
		 

		 while(methodcalls.next()) {
			 List<MethodCalls>mylist= new ArrayList<MethodCalls>(); 
			 String callerid= methodcalls.getString("callermethodid"); 
			 String calleeid= methodcalls.getString("calleemethodid"); 
			 String callername= methodcalls.getString("callername"); 
			 String calleename= methodcalls.getString("calleename"); 
			
			 
			 Method callerclass= new Method(); 
			 callerclass.setMethodid(callerid); 
			callerclass.setMethodname(callername); 
			 
			 Method calleeclass= new Method(); 
			 calleeclass.setMethodid(calleeid);
			 calleeclass.setMethodname(calleename);
			 
			 
			 MethodCalls methodcall= new MethodCalls(); 
			 methodcall.setCaller(callerclass);
			 methodcall.setCallee(calleeclass);
			 if(MethodCallsEXECHashMapCallee.get(callerid)!=null) {
				 mylist= MethodCallsEXECHashMapCallee.get(callerid); 
			 }
			 
			 String key = callerid+"-"+calleeid; 
			 mylist.add(methodcall); 
			 MethodCallsEXECHashMapCallee.put(callerid, mylist); 
			
			 
			 
		 }
		 index++; 
		 methodcalls=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where id='" + index+"'"); 
		}
		
		return MethodCallsEXECHashMapCallee;
		
		
	}
	
	
	
	public HashMap<String, List<MethodCalls>> CreateMethodCallsHashMapCallerEXEC(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		int count=1; 
		ResultSet var = st.executeQuery("select count(*) from methodcallsexecuted"); 
		while(var.next()) {
			 count = var.getInt(1); 
		}
		ResultSet methodcalls=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where id='" + index+"'"); 

		while(index<count) {
		 

		 while(methodcalls.next()) {
			 List<MethodCalls>mylist= new ArrayList<MethodCalls>(); 
			 String callerid= methodcalls.getString("callermethodid"); 
			 String calleeid= methodcalls.getString("calleemethodid"); 
			 String callername= methodcalls.getString("callername"); 
			 String calleename= methodcalls.getString("calleename"); 
			
			 
			 Method callerclass= new Method(); 
			 callerclass.setMethodid(callerid); 
			callerclass.setMethodname(callername); 
			 
			 Method calleeclass= new Method(); 
			 calleeclass.setMethodid(calleeid);
			 calleeclass.setMethodname(calleename);
			 
			 
			 MethodCalls methodcall= new MethodCalls(); 
			 methodcall.setCaller(callerclass);
			 methodcall.setCallee(calleeclass);
			 if(MethodCallsEXECHashMapCaller.get(calleeid)!=null) {
				 mylist= MethodCallsEXECHashMapCaller.get(calleeid); 
			 }
			 
			 String key = callerid+"-"+calleeid; 
			 mylist.add(methodcall); 
			 MethodCallsEXECHashMapCaller.put(calleeid, mylist); 
			
			 
			 
		 }
		 index++; 
		 methodcalls=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where id='" + index+"'"); 
		}
		
		return MethodCallsEXECHashMapCaller;
		
		
	}
	public HashMap<String, MethodDetails> CreateMethodHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		
		
		ResultSet methods=st.executeQuery("select methods.* from methods where id='" + index+"'"); 
		 while(methods.next()) {
			
			 String methodid= methods.getString("id"); 
			 String methodname= methods.getString("methodname"); 
			
			 
			 String classid= methods.getString("classid"); 
			 String classname= methods.getString("classname"); 
			 
			 
			 Clazz classrep = new Clazz(); 
			 
			 classrep.setClassid(classid);
			 classrep.setClassname(classname);
			 
			 
			 Method methodrep= new Method(); 
			 String key = methodid; 
			 
			 methodrep.setMethodid(methodid);
			 methodrep.setMethodname(methodname);
			 MethodDetails methdetails = new MethodDetails(); 
			 methdetails.setOwnerClass(classrep);
			 methdetails.setMethodrep(methodrep);
			 
			 
			 
			 MethodHashMap.put(key, methdetails); 
			 index++; 
			 methods=st.executeQuery("select methods.* from methods where id='" + index+"'"); 
			 
		 }
		
		
		return MethodHashMap;
		
	}
	public HashMap<String, Clazz> CreateClassHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		int index=1; 
		
		
		ResultSet classes=st.executeQuery("select classes.* from classes where id='" + index+"'"); 
		 while(classes.next()) {
			
			 String classid= classes.getString("id"); 
			 String classname= classes.getString("classname"); 
			
			String key=classid; 
			 
		Clazz classrep = new Clazz(); 
		classrep.setClassid(classid);
		classrep.setClassname(classname);
		
			 ClassHashMap.put(key, classrep); 
			 index++; 
			 classes=st.executeQuery("select classes.* from classes where id='" + index+"'"); 
			 
		 }
		
		
		return ClassHashMap;
		
	}
	
	public MethodTraceSubjectTSubjectNOriginal(Method methodRepresentation, Requirement requirement,
			Clazz classRepresentation, String gold, String subject) {
		super();
		MethodRepresentation = methodRepresentation;
		Requirement = requirement;
		ClassRepresentation = classRepresentation;
		this.gold = gold;
		this.subject = subject;
	}
	
	
	
	
	
	
	
	
	
	
	public  HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> ReadClassesRepresentations(Connection conn) throws SQLException {
		DatabaseReading2 db = new DatabaseReading2(); 
		ClassDetails2 classdet= new ClassDetails2(); 
		//CLASSESHASHMAP
		
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		//ResultSet var = st.executeQuery("select count(*) from classes"); 
		
	
		int index=1; 
	
		//END OF TEST 
			ClassHashMap=CreateClassHashMap(conn); 
			InterfaceHashMapOwner = CreateOwnerHashMapInterface(conn);
			InterfaceHashMapInterface = CreateInterfaceHashMapInterface(conn); 
			MethodCallsEXECHashMapCallee=CreateMethodCallsHashMapCalleeEXEC(conn); 
			MethodCallsEXECHashMapCaller=CreateMethodCallsHashMapCallerEXEC(conn); 

			MethodHashMap=CreateMethodHashMap(conn); 
			MethodCallsHashMapCaller=CreateMethodCallsHashMapCaller(conn); 
			MethodCallsHashMapCallee=CreateMethodCallsHashMapCallee(conn); 

			
			 ResultSet myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
			
			 while(myresults.next() ) {
				 MethodTraceSubjectTSubjectNOriginal mytrace= new MethodTraceSubjectTSubjectNOriginal(); 
				 RequirementGold RequirementGold = new RequirementGold(); 
				 Requirement requirement = new Requirement(); 
				 requirement.setID(myresults.getString("requirementid"));
				 requirement.setRequirementName(myresults.getString("requirement"));
				 mytrace.setRequirement(requirement);
				 
				 Clazz classrep = new Clazz(); 
				 classrep.setClassid(myresults.getString("classid"));
				 classrep.setClassname(myresults.getString("classname"));
				 String fullmethodname= myresults.getString("fullmethod"); 

				 Method methodrep= new Method(); 
				 methodrep.setMethodid(myresults.getString("methodid"));
				 methodrep.setMethodname(myresults.getString("method"));
				 methodrep.setMethodname(myresults.getString("methodname"));
				 methodrep.setFullmethodname(myresults.getString("fullmethod"));

				 mytrace.setMethodRepresentation(methodrep);
				 
				 mytrace.setClassRepresentation(classrep);
				 
				 
				 List<Interface> myownerinterfaceList = InterfaceHashMapOwner.get(mytrace.getClassRepresentation().ID);
				 
				 List<Interface> myinterfacelist = InterfaceHashMapInterface.get(mytrace.getClassRepresentation().ID);
				 List<Clazz> interfaceclassreps= new ArrayList<Clazz>(); 
if(myinterfacelist!=null) {
	 for(Interface myinterface: myinterfacelist) {
		 Clazz myclassrepinterface= new Clazz(); 
		 myclassrepinterface.setClassid(myinterface.getInterfaceClass().ID);
		 myclassrepinterface.setClassname(myinterface.getInterfaceClass().classname);
		 interfaceclassreps.add(myclassrepinterface); 
	 }
	
}
				
				 
				 List<Clazz> interfaceclassrepsOwner= new ArrayList<Clazz>(); 

				 	if(myownerinterfaceList!=null) {
				 		 for(Interface myinterface: myownerinterfaceList) {
							 Clazz myclassrepinterface= new Clazz(); 
							 myclassrepinterface.setClassid(myinterface.getImplementation().ID);
							 myclassrepinterface.setClassname(myinterface.getImplementation().classname);
							 interfaceclassrepsOwner.add(myclassrepinterface); 
						 }
				 	}
				
				 
				 
				 
				 
				 
				
				 
				 
				 mytrace.setGold(myresults.getString("gold"));

				 mytrace.setGold2(myresults.getString("gold2"));
				 
				 mytrace.setGold3(myresults.getString("gold3"));

				 mytrace.setGold4(myresults.getString("gold4"));
				 
				 
				 mytrace.setSubject(myresults.getString("subject"));
				 
				
				 String id= mytrace.getMethodRepresentation().ID; 
				 
				 
				 
				 List<MethodCalls> mycalleelist = MethodCallsHashMapCallee.get(id); 
				 List<Method> mycalleelistrep = new ArrayList<Method>(); 
				 if(mycalleelist!=null) {
				 for(MethodCalls mycallee: mycalleelist) {
					 
						 
					 
					 Method meth= new Method(); 	
					 meth.setMethodid(mycallee.Callee.ID);
					 meth.setMethodname(mycallee.Callee.methodname);
					MethodDetails val = MethodHashMap.get(meth.getMethodid()); 
					 meth.setClassrep(val.OwnerClass);
					 mycalleelistrep.add(meth); 
				 }
				 
				 mytrace.setCalleesList(mycalleelistrep);
				 }
				 
				 List<MethodCalls> mycallerlist = MethodCallsHashMapCaller.get(id); 
				 List<Method> mycallerlistrep = new ArrayList<Method>(); 
				 if(mycallerlist!=null) {
					 for(MethodCalls mycaller: mycallerlist) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycaller.Caller.ID);
						 meth.setMethodname(mycaller.Caller.methodname);
						MethodDetails val = MethodHashMap.get(meth.getMethodid()); 
						 meth.setClassrep(val.OwnerClass);
						 mycallerlistrep.add(meth); 
					 }
					 mytrace.setCallersList(mycallerlistrep);
				 }
				
				 
				 
				 
				 
				 List<MethodCalls> mycallerlistexecuted = MethodCallsEXECHashMapCaller.get(id); 
				 List<Method> mycallerlistrepexecuted = new ArrayList<Method>(); 
				 if(mycallerlistexecuted!=null) {
					 for(MethodCalls mycaller: mycallerlistexecuted) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycaller.Caller.ID);
						 meth.setMethodname(mycaller.Caller.methodname);
						MethodDetails val = MethodHashMap.get(meth.getMethodid()); 
						 meth.setClassrep(val.OwnerClass);
						 mycallerlistrepexecuted.add(meth); 
					 }
					 mytrace.setCallersList(mycallerlistrepexecuted);
				 }
				
				 
				 
				 
				 List<MethodCalls> mycalleelistexecuted = MethodCallsEXECHashMapCallee.get(id); 
				 List<Method> mycalleelistrepexecuted = new ArrayList<Method>(); 
				 if(mycalleelistexecuted!=null) {
					 for(MethodCalls mycallee: mycalleelistexecuted) {
						 Method meth= new Method(); 	
						 meth.setMethodid(mycallee.Callee.ID);
						 meth.setMethodname(mycallee.Callee.methodname);
						MethodDetails val = MethodHashMap.get(meth.getMethodid()); 
						 meth.setClassrep(val.OwnerClass);
						 mycalleelistrepexecuted.add(meth); 
					 }
					 mytrace.setCalleesList(mycalleelistrepexecuted);
					 
				 }
				
				 
				 
			

				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				
				 
			//	 System.out.println("HEY");
				 methodtraceHashMap.put(index, mytrace); 
				 index++; 
			//	 MethodTraceSubjectTSubjectN33 methtrace= new MethodTraceSubjectTSubjectN33(); 
				// System.out.println("my trace tostring: "+mytrace.toString());
				
				 myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
				 System.out.println("index 1 "+index);
			
			 }
		

		 
		return methodtraceHashMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	public  HashMap<Integer, MethodTraceSubjectTSubjectNOriginal> ReadClassesRepresentations(Connection conn) throws SQLException {
//		DatabaseReading2 db = new DatabaseReading2(); 
//		ClassDetails2 classdet= new ClassDetails2(); 
//		//CLASSESHASHMAP
//		
//		Statement st = conn.createStatement();
//		Statement st2 = conn.createStatement();
//		ResultSet var = st.executeQuery("select count(*) from classes"); 
//		
//	
//		int index=1; 
//	
//		//END OF TEST 
//			 ResultSet myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
//			 while(myresults.next() ) {
//				 MethodTraceSubjectTSubjectNOriginal mytrace= new MethodTraceSubjectTSubjectNOriginal(); 
//				 RequirementGold RequirementGold = new RequirementGold(); 
//				 Requirement2 requirement = new Requirement2(); 
//				 requirement.setID(myresults.getString("requirementid"));
//				 requirement.setRequirementName(myresults.getString("requirement"));
//				 mytrace.setRequirement(requirement);
//				 
//				 ClassRepresentation2 classrep = new ClassRepresentation2(); 
//				 classrep.setClassid(myresults.getString("classid"));
//				 classrep.setClassname(myresults.getString("classname"));
//				 String fullmethodname= myresults.getString("fullmethod"); 
//
//				 Method2Representation methodrep= new Method2Representation(); 
//				 methodrep.setMethodid(myresults.getString("methodid"));
//				 methodrep.setMethodname(myresults.getString("method"));
//				 methodrep.setMethodname(myresults.getString("methodname"));
//				 methodrep.setFullmethodname(myresults.getString("fullmethod"));
//
//				 mytrace.setMethodRepresentation(methodrep);
//				 
//				 mytrace.setClassRepresentation(classrep);
//				 
//				 
//				 List<ClassRepresentation2> interfaceclassreps= new ArrayList<ClassRepresentation2>(); 
//				 ResultSet myinterfaces=st2.executeQuery("select interfaces.* from interfaces where ownerclassid='" + mytrace.getClassRepresentation().classid+"'"); 
//				 while(myinterfaces.next()) {
//					 ClassRepresentation2 myclassrepinterface= new ClassRepresentation2(); 
//					 myclassrepinterface.setClassid(myinterfaces.getString("interfaceclassid"));
//					 myclassrepinterface.setClassname(myinterfaces.getString("interfacename"));
//					 interfaceclassreps.add(myclassrepinterface); 
//				 }
//				
//				 
//				 myinterfaces=st2.executeQuery("select interfaces.* from interfaces where interfaceclassid='" + mytrace.getClassRepresentation().classid+"'"); 
//				 while(myinterfaces.next()) {
//					 ClassRepresentation2 myclassrepinterface= new ClassRepresentation2(); 
//					 myclassrepinterface.setClassid(myinterfaces.getString("ownerclassid"));
//					 myclassrepinterface.setClassname(myinterfaces.getString("classname"));
//					 interfaceclassreps.add(myclassrepinterface); 
//				 }
//				 
//				 
//				 mytrace.setGold(myresults.getString("gold"));
//
//				 mytrace.setGold3(myresults.getString("gold3"));
//				 
//				 mytrace.setGold4(myresults.getString("gold4"));
//				 
//				 mytrace.setSubject(myresults.getString("subject"));
//				 
//				 mytrace.setSubjectT(myresults.getString("SubjectT"));
//				 mytrace.setSubjectN(myresults.getString("SubjectN"));
//
//				 String id= mytrace.getMethodRepresentation().methodid; 
//				 ResultSet callers=st.executeQuery("select methodcalls.* from methodcalls where calleemethodid='" + id+"'"); 
//				 this.callersList= new  ArrayList<Method2Representation>(); 
//				 while(callers.next()) {
////					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 
////					 ResultSet methodtraces=st2.executeQuery("select traces.* from traces where methodid='" + id+"'"); 
////					 while(methodtraces.next()) {
////						 
////						  requirement= new Requirement2(); 
////						  RequirementGold= new RequirementGold(); 
////						 requirement.setID(methodtraces.getString("requirementid"));
////						 requirement.setRequirementName(methodtraces.getString("requirement"));
////						 RequirementGold.setRequirement(requirement);
////						 RequirementGold.setGold(methodtraces.getString("gold"));
////						 requirementsGold.add(RequirementGold); 
////					 }
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callers.getString("callermethodid"));
//					 meth.setMethodname(callers.getString("callername"));
//					
//					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//					 while(myclass.next()) {
//						 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//						 myclassrep.setClassid(myclass.getString("classid"));
//						 myclassrep.setClassname(myclass.getString("classname"));
//						 meth.setClassrep(myclassrep);
//					 }
//					
//				//	 meth.setRequirementsGold(requirementsGold);
//					 this.callersList.add(meth); 					 
//					 mytrace.setCallersList(this.callersList);
//				 }
//				 
//				 ResultSet callees=st.executeQuery("select methodcalls.* from methodcalls where callermethodid='" + id+"'"); 
//				 this.calleesList= new  ArrayList<Method2Representation>(); 
//				 while(callees.next()) {
////					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 
////					 ResultSet methodtraces=st2.executeQuery("select traces.* from traces where methodid='" + id+"'"); 
////					 while(methodtraces.next()) {
////						 
////						 requirement= new Requirement2(); 
////						  RequirementGold= new RequirementGold(); 
////						 requirement.setID(methodtraces.getString("requirementid"));
////						 requirement.setRequirementName(methodtraces.getString("requirement"));
////						 RequirementGold.setRequirement(requirement);
////						 RequirementGold.setGold(methodtraces.getString("gold"));
////						 requirementsGold.add(RequirementGold); 
////					 }
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callees.getString("calleemethodid"));
//					 meth.setMethodname(callees.getString("calleename"));
//					 
//					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//					 while(myclass.next()) {
//						 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//						 myclassrep.setClassid(myclass.getString("classid"));
//						 myclassrep.setClassname(myclass.getString("classname"));
//						 meth.setClassrep(myclassrep); 
//					 }
//					
//					 
//					 
//			//		 meth.setRequirementsGold(requirementsGold);
//					 this.calleesList.add(meth); 					 
//					 mytrace.setCalleesList(this.calleesList);
//				 }
//				 
//				 
//				 ResultSet callersExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where calleemethodid='" + id+"'"); 
//				 this.calleesListExecuted= new  ArrayList<Method2Representation>(); 
//				 while(callersExecuted.next()) {
////					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 
////					 ResultSet methodtraces=st2.executeQuery("select traces.* from traces where methodid='" + id+"'"); 
////					 while(methodtraces.next()) {
////						 
////						 requirement= new Requirement2(); 
////						  RequirementGold= new RequirementGold(); 
////						 requirement.setID(methodtraces.getString("requirementid"));
////						 requirement.setRequirementName(methodtraces.getString("requirement"));
////						 RequirementGold.setRequirement(requirement);
////						 RequirementGold.setGold(methodtraces.getString("gold"));
////						 requirementsGold.add(RequirementGold); 
////					 }
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(callersExecuted.getString("callermethodid"));
//					 meth.setMethodname(callersExecuted.getString("callername"));
//					 
//					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//					 while(myclass.next()) {
//						 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//						 myclassrep.setClassid(myclass.getString("classid"));
//						 myclassrep.setClassname(myclass.getString("classname"));
//						 meth.setClassrep(myclassrep); 
//					 }
//					
//					 
//				//	 meth.setRequirementsGold(requirementsGold);
//					 this.calleesListExecuted.add(meth); 					 
//					 mytrace.setCallersListExecuted(this.calleesListExecuted);
//				 }
//				 
//				 
//				 
////				 for(ClassRepresentation2 inter: interfaceclassreps) {
////					 if(fullmethodname!="") {
////						 System.out.println("FULL METHOD NAME: "+ fullmethodname);
////							
////							String myclass=  mytrace.ClassRepresentation.classname; 
////							 fullmethodname=  mytrace.MethodRepresentation.fullmethodname; 
////							String shortmethodname=  mytrace.MethodRepresentation.methodname; 
////							System.out.println("FULL METHOD NAME: "+ fullmethodname);
////							 System.out.println("MYCLASS: "+ myclass);
////						//	String shortmethodname=fullmethodname.substring(myclass.length(), fullmethodname.length()); 
////							 String interfacename= inter.classname+shortmethodname; 
////							 
////							  callers=st.executeQuery("select methodcalls.* from methodcalls where fullcallee='" + interfacename+"'"); 
////							// this.callersList= new  ArrayList<Method2Representation>(); 
////							 while(callers.next()) {
////
////								 Method2Representation meth= new Method2Representation(); 	
////								 meth.setMethodid(callers.getString("callermethodid"));
////								 meth.setMethodname(callers.getString("callername"));
////								
////								 ResultSet myclass2=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
////								 while(myclass2.next()) {
////									 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
////									 myclassrep.setClassid(myclass2.getString("classid"));
////									 myclassrep.setClassname(myclass2.getString("classname"));
////									 meth.setClassrep(myclassrep);
////								 }
////								
////								 this.callersList.add(meth); 					 
////					 }
////					
////							 
////							 System.out.println("FULL METHOD NAME: "+ fullmethodname);
////								
////							 String tracename= mytrace.getMethodRepresentation().fullmethodname; 
////
////							 System.out.println("TRACENAME======="+ tracename);
////								  callees=st.executeQuery("select methodcalls.* from methodcalls where fullcaller='" + interfacename+"'"); 
////								// this.callersList= new  ArrayList<Method2Representation>(); 
////								 while(callees.next()) {
////
////									 Method2Representation meth= new Method2Representation(); 	
////									 meth.setMethodid(callees.getString("callermethodid"));
////									 meth.setMethodname(callees.getString("callername"));
////									
////									 ResultSet myclass2=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
////									 while(myclass2.next()) {
////										 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
////										 myclassrep.setClassid(myclass2.getString("classid"));
////										 myclassrep.setClassname(myclass2.getString("classname"));
////										 meth.setClassrep(myclassrep);
////									 }
////									
////									 this.calleesList.add(meth); 					 
////						 }	 
////							 
////							 
////						
////					 }
////				 }
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 
//				 ResultSet calleesExecuted=st.executeQuery("select methodcallsexecuted.* from methodcallsexecuted where callermethodid='" + id+"'"); 
//				 this.callersListExecuted= new  ArrayList<Method2Representation>(); 
//				 while(calleesExecuted.next()) {
////					 List<RequirementGold> requirementsGold = new ArrayList<RequirementGold>(); 
////					 ResultSet methodtraces=st2.executeQuery("select traces.* from traces where methodid='" + id+"'"); 
////					 while(methodtraces.next()) {
////						 
////						 requirement= new Requirement2(); 
////						  RequirementGold= new RequirementGold(); 
////						 requirement.setID(methodtraces.getString("requirementid"));
////						 requirement.setRequirementName(methodtraces.getString("requirement"));
////						 RequirementGold.setRequirement(requirement);
////						 RequirementGold.setGold(methodtraces.getString("gold"));
////						 requirementsGold.add(RequirementGold); 
////					 }
//					 Method2Representation meth= new Method2Representation(); 	
//					 meth.setMethodid(calleesExecuted.getString("calleemethodid"));
//					 meth.setMethodname(calleesExecuted.getString("calleename"));
//					 
//					 ResultSet myclass=st2.executeQuery("select methods.* from methods where id='" + meth.getMethodid()+"'"); 
//					 while(myclass.next()) {
//						 ClassRepresentation2 myclassrep= new ClassRepresentation2(); 
//						 myclassrep.setClassid(myclass.getString("classid"));
//						 myclassrep.setClassname(myclass.getString("classname"));
//						 meth.setClassrep(myclassrep); 
//					 }
//				
//					 
//				//	 meth.setRequirementsGold(requirementsGold);
//					 this.callersListExecuted.add(meth); 					 
//					 mytrace.setCalleesListExecuted(this.callersListExecuted);
//				 }
//				 
//			//	 System.out.println("HEY");
//				 methodtraceHashMap.put(index, mytrace); 
//				 index++; 
//			//	 MethodTraceSubjectTSubjectN33 methtrace= new MethodTraceSubjectTSubjectN33(); 
//				// System.out.println("my trace tostring: "+mytrace.toString());
//				
//				 myresults = st.executeQuery("SELECT traces.* from traces where id='"+ index +"'"); 
//				 System.out.println("index 1 "+index);
//			
//			 }
//		
//
//		 
//		return methodtraceHashMap;
//	}
//	
//	public List<MethodTraceSubjectTSubjectNOriginal> getElement(List<MethodTraceSubjectTSubjectNOriginal> methodtraces2, String ID, String goldpred, String goldprediction2, String RequirementID) {
//		for(MethodTraceSubjectTSubjectNOriginal methodtrace: methodtraces2) {
//			if(methodtrace.getMethodRepresentation().methodid.equals(ID) && methodtrace.Requirement.ID.equals(RequirementID)) {
//				if(goldprediction2.equals("goldpredictionCallee")){
//					methodtrace.setGoldpredictionCallee(goldpred);
//				}
//				else if(goldprediction2.equals("goldpredictionCaller")) {
//					methodtrace.setGoldpredictionCaller(goldpred);
//				}
//				
//			}
//		}
//		
//		return methodtraces2; 
//		
//	}

	public String toString(MethodTraceSubjectTSubjectNOriginal methtr) {
		String mycaller = ""; 
		String mycallee = ""; 
		String mycallerexecuted = ""; 
		String mycalleeexecuted = ""; 
		String requicallee = ""; 
		String requicaller = ""; 
		String requicalleeexecuted = ""; 
		String requicallerexecuted = ""; 
		String st= "MethodTrace [MethodRepresentation=" + MethodRepresentation.toString() 
		
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