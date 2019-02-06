package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ALGO.MethodList;

public class Clazz {

	public String ID; 
	public String classname;
	public String DeveloperGold=new String(); 
	public String SubjectGold; 
	public List<Clazz> Children= new ArrayList<Clazz>(); 
	public List<Clazz> Parents= new ArrayList<Clazz>();  
	public List<Clazz> Interfaces= new ArrayList<Clazz>();  
	public List<Clazz> Implementations= new ArrayList<Clazz>(); 
	public MethodList methods = new MethodList(); 
	 HashMap<Integer, Clazz> ClassRepresentationHashMap= new HashMap<Integer, Clazz>(); 

	
	public void setDeveloperGold( String developerGold) {
		DeveloperGold = developerGold;
	}


	@Override
	public String toString() {
//		return "Clazz [ID=" + ID + ", classname=" + classname + ", DeveloperGold=" + DeveloperGold+"]";
		return "Clazz [ID=" + ID + ", classname=" + classname +"]";
	}


	public Clazz() {
		super();
	}


	public String getDeveloperGold() {
		return DeveloperGold;
	}


	


	public String getSubjectGold() {
		return SubjectGold;
	}


	public void setSubjectGold(String subjectGold) {
		SubjectGold = subjectGold;
	}


	public String getGold() {
		return DeveloperGold;
	}


	


	public Clazz(String classid, String classname) {
		super();
		this.ID = classid;
		this.classname = classname;
	}


	public String getClassid() {
		return ID;
	}


	public void setClassid(String classid) {
		this.ID = classid;
	}


	public String getClassname() {
		return classname;
	}


	

	public void setClassname(String classname) {
		this.classname = classname;
	} 
	public  HashMap<Integer, Clazz> ReadClassesRepresentations(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
	
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
		 ResultSet myresults = st.executeQuery("SELECT classes.* from classes "); 
		 while(myresults.next()) {
			     String id = myresults.getString("id"); 			
				 String classname = myresults.getString("classname"); 
				
				 Clazz classdetails= new Clazz(id, classname); 
				 ClassRepresentationHashMap.put(index, classdetails); 
				 index++; 
		 }
		 Set<Integer> keys = ClassRepresentationHashMap.keySet();
			Map<Integer, Clazz> resultFieldClasses = ClassRepresentationHashMap.entrySet().stream()
			                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
			                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
			                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return ClassRepresentationHashMap; 
	}


	


	
	
}
