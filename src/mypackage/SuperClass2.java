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

public class SuperClass2 {
	String ID; 
	public Clazz SuperClass; 
	public Clazz ChildClass;
	 List<SuperClass2> SupeClassList= new ArrayList<SuperClass2>(); 
	 HashMap<String, List<SuperClass2>> superclasshashmap= new HashMap<String, List<SuperClass2>>(); 
	
	 
	
	


	public SuperClass2(String iD, Clazz superClass, Clazz ownerClass,
			List<SuperClass2> SupeClassList) {
		super();
		ID = iD;
		SuperClass = superClass;
		ChildClass = ownerClass;
		SupeClassList = SupeClassList;
	}



	public SuperClass2() {
		// TODO Auto-generated constructor stub
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public Clazz getSuperClass() {
		return SuperClass;
	}



	public void setSuperClass(Clazz superClass) {
		SuperClass = superClass;
	}



	public Clazz getChildClass() {
		return ChildClass;
	}



	public void setChildClass(Clazz ownerClass) {
		ChildClass = ownerClass;
	}



	public List<SuperClass2> getSupeClassHashMap() {
		return SupeClassList;
	}



	public void setSupeClassHashMap(List<SuperClass2> SupeClassList) {
		SupeClassList = SupeClassList;
	}



	public  HashMap<String, List<SuperClass2>> ReadSuperClasses(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
	
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from superclasses"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		 ResultSet myresults = st.executeQuery("SELECT superclasses.* from superclasses "); 
		 while(myresults.next()) {
			 	SuperClass2 mysuperclass= new SuperClass2();
			 	Clazz superclass= new Clazz();
			     String superclassid = myresults.getString("superclassid"); 			
				 String superclassname = myresults.getString("superclassname"); 
				 superclass.setClassid(superclassid);
				 superclass.setClassname(superclassname);
				 
				 Clazz OwnerClass= new Clazz();
				 String ownerclassid = myresults.getString("ownerclassid"); 			
				 String childclassname = myresults.getString("childclassname"); 
				 OwnerClass.setClassid(ownerclassid);
				 OwnerClass.setClassname(childclassname);
				 
				 mysuperclass.setSuperClass(superclass);
				 mysuperclass.setChildClass(OwnerClass);
				 
				 String key=ownerclassid;
				 if(superclasshashmap.get(key)!=null) {
					 List<SuperClass2> mysuperclasses= superclasshashmap.get(key); 
					 mysuperclasses.add(mysuperclass); 
					 superclasshashmap.put(key, mysuperclasses); 
				 }else {
					 List<SuperClass2> mysuperclasses= new ArrayList<SuperClass2>(); 
					 mysuperclasses.add(mysuperclass); 
					 superclasshashmap.put(key, mysuperclasses); 
				 }
				
				 SupeClassList.add(mysuperclass);  
				 System.out.println("index 5 "+index);
				 index++; 
		 }
		
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return superclasshashmap; 
	}
	
	
	

	
}
