package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Children2 {
	public Clazz SuperClass; 
	public Clazz OwnerClass;
	 List<Children2> ChildrenList= new ArrayList<Children2>(); 
	 HashMap<String, List<Children2>> ChildrenHashMap= new HashMap<String, List<Children2>>();
	public Clazz getSuperClass() {
		return SuperClass;
	}
	public void setSuperClass(Clazz superClass) {
		SuperClass = superClass;
	}
	public Clazz getOwnerClass() {
		return OwnerClass;
	}
	public void setOwnerClass(Clazz ownerClass) {
		OwnerClass = ownerClass;
	}
	public List<Children2> getChildrenList() {
		return ChildrenList;
	}
	public void setChildrenList(List<Children2> childrenList) {
		ChildrenList = childrenList;
	}
	public HashMap<String, List<Children2>> getChildrenHashMap() {
		return ChildrenHashMap;
	}
	public void setChildrenHashMap(HashMap<String, List<Children2>> childrenHashMap) {
		ChildrenHashMap = childrenHashMap;
	} 
	 
	 
	public  HashMap<String, List<Children2>> ReadChildren(Connection conn) throws SQLException {
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
			 	Children2 mysuperclass= new Children2();
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
				 mysuperclass.setOwnerClass(OwnerClass);
				 
				 String key=superclassid;
				 if(ChildrenHashMap.get(key)!=null) {
					 List<Children2> mysuperclasses= ChildrenHashMap.get(key); 
					 mysuperclasses.add(mysuperclass); 
					 ChildrenHashMap.put(key, mysuperclasses); 
				 }else {
					 List<Children2> mysuperclasses= new ArrayList<Children2>(); 
					 mysuperclasses.add(mysuperclass); 
					 ChildrenHashMap.put(key, mysuperclasses); 
				 }
				
				 System.out.println("index 5 "+index);
				 index++; 
		 }
		
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return ChildrenHashMap; 
	}
	
	
	 
	 
}
