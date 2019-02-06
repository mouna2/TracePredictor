package ALGO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import mypackage.Children2;
import mypackage.ClassDetails2;
import mypackage.ClassField2;
import mypackage.ClassTrace2;
import mypackage.Implementation2;
import mypackage.Interface;
import mypackage.Method;
import mypackage.MethodField2;
import mypackage.MethodTrace;
import mypackage.Parameter2;
import mypackage.Requirement;
import mypackage.SuperClass2;
import spoon.Launcher;
import spoon.SpoonAPI;

public class DatabaseReading {
	private final String userName = "root";
	private final String password = "123456";
	String ProgramName=""; 
	public static List<MethodTrace> methodtracesList = null;

	static LinkedHashMap<String, MethodTrace> methodtracehashmap = null; 
	static HashMap<String, List<String>> ClassMethodsHashMap= new HashMap<String, List<String>>(); 
	public static HashMap<Method, HashMap<Requirement, String>> FinalMethodHashMapReqGolds= new HashMap<Method, HashMap<Requirement, String>>() ; 
	public Connection getConnection(String programName) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("root", this.userName);
		connectionProps.put("123456", this.password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database"+programName, "root", "123456");
	

		// Interact with model

		return conn;
	}
	
	
	public DatabaseReading(String ProgramName) throws SQLException {
		this.ProgramName=ProgramName; 
		Connection conn=getConnection(ProgramName); 
		




		
		//SWITCHED TO MethodTraceSubjectTSubjectN
		
		LinkedHashMap<String, MethodTrace> methodtracehashmap = DatabaseInput.ReadMethodTraces(conn, ClassMethodsHashMap, FinalMethodHashMapReqGolds);
		List<MethodTrace> methodtraces = new ArrayList<MethodTrace>(methodtracehashmap.values());
		setMethodtracesList(methodtraces);
		setMethodtracehashmap(methodtracehashmap); 
		///////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
	}


	public static List<MethodTrace> getMethodtracesList() {
		return methodtracesList;
	}


	public static void setMethodtracesList(List<MethodTrace> methodtracesList) {
		DatabaseReading.methodtracesList = methodtracesList;
	}


	public String getProgramName() {
		return ProgramName;
	}


	public void setProgramName(String programName) {
		ProgramName = programName;
	}


	

	public static LinkedHashMap<String, MethodTrace> getMethodtracehashmap() {
		return methodtracehashmap;
	}


	public static void setMethodtracehashmap(LinkedHashMap<String, MethodTrace> methodtracehashmap) {
		DatabaseReading.methodtracehashmap = methodtracehashmap;
	}


	public static HashMap<String, List<String>> getClassMethodsHashMap() {
		return ClassMethodsHashMap;
	}


	public static void setClassMethodsHashMap(HashMap<String, List<String>> classMethodsHashMap) {
		ClassMethodsHashMap = classMethodsHashMap;
	}


	public static HashMap<Method, HashMap<Requirement, String>> getFinalMethodHashMapReqGolds() {
		return FinalMethodHashMapReqGolds;
	}


	public static void setFinalMethodHashMapReqGolds(
			HashMap<Method, HashMap<Requirement, String>> finalMethodHashMapReqGolds) {
		FinalMethodHashMapReqGolds = finalMethodHashMapReqGolds;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}
	
	
}
