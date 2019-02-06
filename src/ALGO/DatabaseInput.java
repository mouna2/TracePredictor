package ALGO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import ALGO.MethodList;
import Chess.LogInfo;
import mypackage.ClassTrace2;
import mypackage.Clazz;
import mypackage.Method;
import mypackage.MethodTrace;
import mypackage.Requirement;
import spoon.pattern.internal.SubstitutionRequestProvider;

public class DatabaseInput {
	
	static HashMap<String, Method> MethodHashMap = new HashMap<String, Method>();

	static HashMap<String, Clazz> ClassHashMap = new HashMap<String, Clazz>();
	static HashMap<String, Requirement> RequirementHashMap = new HashMap<String, Requirement>();

	public static HashMap<String, ClassTrace2> classTraceHashMap = new HashMap<String, ClassTrace2>();
	static LinkedHashMap<String, MethodTrace> methodtraceHashMap = new LinkedHashMap<String, MethodTrace>();
	public static LinkedHashMap<String, String> OwnerTraceHashMap = new LinkedHashMap<String, String>();
	static LinkedHashMap<String, String> SubjectTraceHashMap = new LinkedHashMap<String, String>();



	




	
	
	
	

	
	
	
	
	
	
	



	


	public DatabaseInput() {
		super();
	}

	


///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
public static void CreateSuperclassesChildrenHashMap(Connection conn) throws SQLException {
		

		Statement st = conn.createStatement();
		
		
		ResultSet mysuperclasses = st.executeQuery("select superclasses.* from superclasses ");

		while (mysuperclasses.next()) {
			String parentid = mysuperclasses.getString("superclassid");
			String childid = mysuperclasses.getString("ownerclassid");
			
			
			mypackage.Clazz parent= ClassHashMap.get(parentid); 
			mypackage.Clazz child = ClassHashMap.get(childid); 
			
			parent.Children.add(child);
			child.Parents.add(parent);
							
		
		}

		

	
		
		
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

public static void CreateInterfacesImplementations(Connection conn) throws SQLException{
	

	Statement st = conn.createStatement();
	
	
	ResultSet myinterfaces = st.executeQuery("select interfaces.* from interfaces ");

	while (myinterfaces.next()) {
		String interfaceclassid = myinterfaces.getString("interfaceclassid");
		String ownerclassid = myinterfaces.getString("ownerclassid");
		
		
		mypackage.Clazz myinterface= ClassHashMap.get(interfaceclassid); 
		mypackage.Clazz implementation = ClassHashMap.get(ownerclassid); 
		
		myinterface.Implementations.add(implementation); 
		implementation.Interfaces.add(myinterface); 
						
	
	}

	


	
	
	
}



//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
public static void CreateMethodCallsHashMapCallersCallees(Connection conn) throws SQLException {
					Statement st = conn.createStatement();
					
					ResultSet methodcalls = st.executeQuery("select methodcalls.* from methodcalls ");
					
//					//comment out 
//					HashMap<String, MethodList> CalleeIndexedHashMap= new HashMap<String, MethodList>(); 
//					HashMap<String, MethodList> CallerIndexedHashMap= new HashMap<String, MethodList>(); 

					while (methodcalls.next()) {
	
						String callerid = methodcalls.getString("callermethodid");
						String calleeid = methodcalls.getString("calleemethodid");
						
						
						mypackage.Method callerMethod = MethodHashMap.get(callerid); 
						mypackage.Method calleeMethod = MethodHashMap.get(calleeid); 
						
						callerMethod.Callees.add(calleeMethod);
						calleeMethod.Callers.add(callerMethod);
						//comment out 				
//						CalleeIndexedHashMap.put(calleeid, calleeMethod.Callers); 
//						CallerIndexedHashMap.put(callerid, callerMethod.Callees); 
					} 


					
					//comment out 
					// CODE BELOW IS FOR COMPUTING CALLERS OF CALLERS 
//					for(Method method: MethodHashMap.values()) {
//						for(Method methodCaller: method.Callers) {
//							MethodList CallerofCallers = CalleeIndexedHashMap.get(methodCaller.ID); 
//							if(CallerofCallers!=null)
//							for(Method callerofcaller: CallerofCallers) {
//								method.CallersofCallers.add(callerofcaller); 
//								
//							}
//							
//						}
//						if(!method.CallersofCallers.isEmpty()) {
//							System.out.println(method.CallersofCallers);
//							System.out.println("yes");
//						}
//					}
//					// CODE BELOW IS FOR COMPUTING CALLEES OF CALLEES 
//					for(Method method: MethodHashMap.values()) {
//						for(Method methodCallee: method.Callees) {
//							MethodList CalleeofCallees = CallerIndexedHashMap.get(methodCallee.ID); 
//							if(CalleeofCallees!=null)
//							for(Method calleeofcallee: CalleeofCallees) {
//								method.CalleesofCallees.add(calleeofcallee); 
//								
//							}
//							
//						}
//						if(!method.CalleesofCallees.isEmpty()) {
//							System.out.println(method.CalleesofCallees);
//							System.out.println("yes");
//						}
//					}

}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void CreateMethodHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		
		ResultSet methods = st.executeQuery("select methods.* from methods ");
		while (methods.next()) {

			String methodid = methods.getString("id");
			String methodname = methods.getString("methodname");
			
			String classid = methods.getString("classid");
			Clazz clazz=ClassHashMap.get(classid); 
			

			Method method = new Method();

			method.setMethodid(methodid);
			method.setMethodname(methodname);
			
			method.setOwner(clazz);
			clazz.methods.add(method); 
			MethodHashMap.put(methodid, method);
		
		}
	for(Method method : MethodHashMap.values()) {
		Clazz clazz=method.Owner;
		
		if (clazz.Interfaces.size()> 0) {
			for(Clazz myinterfaceclass: clazz.Interfaces) {
				for(Method interfaceMethod: myinterfaceclass.methods) {
					if(interfaceMethod.methodname.equals(method.methodname)) {
						method.Interfaces.add(interfaceMethod); 
						interfaceMethod.Implementations.add(method); 
					}
				}
			}
		}
		if (clazz.Children.size()> 0) {
			for(Clazz child: clazz.Children) {
				for(Method ChildMethod: child.methods) {
					if(ChildMethod.methodname.equals(method.methodname)) {
						method.Children.add(ChildMethod); 
						ChildMethod.Superclasses.add(method); 
					}
				}
			}
		}
	}
		
	System.out.println("yes");
	}
		
		

		

		

	
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void CreateClassHashMap(Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		ResultSet classes = st.executeQuery("select classes.* from classes ");
		while (classes.next()) {

			String classid = classes.getString("id");
			String classname = classes.getString("classname");

			String key = classid;

			Clazz clazz = new Clazz();
			clazz.setClassid(classid);
			clazz.setClassname(classname);

			ClassHashMap.put(key, clazz);
			

		}

		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

public static void CreateRequirementsHashMap(Connection conn) throws SQLException {
				Statement st = conn.createStatement();
				
				ResultSet requirements = st.executeQuery("select requirements.* from requirements ");
				while (requirements.next()) {
				
				String requirementid = requirements.getString("id");
				String requirementname = requirements.getString("requirementname");
				
				Requirement requirement= new Requirement(); 
				
				requirement.setRequirementName(requirementname);
				requirement.setID(requirementid);
				RequirementHashMap.put(requirementid, requirement);


}


}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public  static void CreateClassTraces(Connection conn) throws SQLException {
		
		//CLASSESHASHMAP
		Statement st = conn.createStatement();
	
		
		
		 ResultSet myresults = st.executeQuery("SELECT tracesclasses.* from tracesclasses"); 
		 while(myresults.next()) {
		 ClassTrace2 myclasstrace= new ClassTrace2(); 
			 Requirement	requirement= RequirementHashMap.get(myresults.getString("requirementid")); 
			
			 Clazz clazz = ClassHashMap.get(myresults.getString("classid")); 
//			 clazz.setDeveloperGold(myresults.getString("goldfinal"));
			 clazz.setSubjectGold(myresults.getString("SubjectGold"));
			
			 myclasstrace.setMyclass(clazz);
			 myclasstrace.setRequirement(requirement);
			 myclasstrace.DeveloperGold=myresults.getString("goldfinal"); 
			 myclasstrace.SubjectGold=myresults.getString("SubjectGold"); 
			 classTraceHashMap.put(requirement.ID.trim()+"-"+clazz.ID.trim(), myclasstrace); 
			
			
		 }
	
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static LinkedHashMap<String, MethodTrace> ReadMethodTraces(Connection conn,
			HashMap<String, List<String>> classMethodsHashMap,
			HashMap<Method, HashMap<Requirement, String>> FinalMethodHashMapReqGolds) throws SQLException {


		
		CreateClassHashMap(conn);
		CreateSuperclassesChildrenHashMap(conn); 
		CreateInterfacesImplementations(conn);
		
		CreateMethodHashMap(conn);
		CreateMethodCallsHashMapCallersCallees(conn); 
		CreateRequirementsHashMap(conn); 

		CreateClassTraces(conn); 

		CreateMethodTraces(conn);
		
	

		return methodtraceHashMap;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void CreateMethodTraces(Connection conn) throws SQLException {
	// TODO Auto-generated method stub
		

		Statement st = conn.createStatement();

		ResultSet myresults = st.executeQuery("SELECT traces.* from traces");

		while (myresults.next()) {
			MethodTrace MethodTrace = new MethodTrace();
			Method method= new Method(); 
			Requirement requirement= new Requirement(); 
			 requirement=RequirementHashMap.get(myresults.getString("requirementid")); 
			 method = MethodHashMap.get(myresults.getString("methodid")); 
			
			MethodTrace.setMethod(method);
			MethodTrace.setRequirement(requirement);

			//checking whether the method is present in the superclasses
			

			MethodTrace.setGold(myresults.getString("goldfinal"));
			String reqMethod=MethodTrace.Requirement.ID+"-"+MethodTrace.Method.ID; 
			String reqClass=MethodTrace.Requirement.ID+"-"+MethodTrace.Method.Owner.ID;  

			MethodTrace.Method.Owner.DeveloperGold=classTraceHashMap.get(reqClass).DeveloperGold; 


			
//			 System.out.println(reqMethod+"-");
			
			 methodtraceHashMap.put(reqMethod, MethodTrace);
//			System.out.println("WE ARE IN THE LOOP "+methodtraceHashMap.get("1-1"));
//	        System.out.println("WE ARE IN THE LOOP "+methodtraceHashMap.get("1-1"));
	        
	        
	        OwnerTraceHashMap.put(reqClass, classTraceHashMap.get(reqClass).DeveloperGold); 
	        SubjectTraceHashMap.put(reqClass, classTraceHashMap.get(reqClass).SubjectGold); 

		}
		


}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////


	

}