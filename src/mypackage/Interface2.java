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

public class Interface2 {
	String ID; 
	public ClassRepresentation2 InterfaceClass; 
	public ClassRepresentation2 Implementation;
	 HashMap<String, List<Interface2>> ClassRepresentationHashMapOwnerClass= new HashMap<String, List<Interface2>>(); 

	 HashMap<String, Interface2> ClassRepresentationHashMap= new HashMap<String, Interface2>(); 
	
	public Interface2(String iD, ClassRepresentation2 interfaceClass, ClassRepresentation2 implementation) {
		super();
		ID = iD;
		InterfaceClass = interfaceClass;
		Implementation = implementation;
	}
	public Interface2() {
		// TODO Auto-generated constructor stub
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public ClassRepresentation2 getInterfaceClass() {
		return InterfaceClass;
	}
	public void setInterfaceClass(ClassRepresentation2 interfaceClass) {
		InterfaceClass = interfaceClass;
	}
	public ClassRepresentation2 getImplementation() {
		return Implementation;
	}
	public void setImplementation(ClassRepresentation2 implementation) {
		Implementation = implementation;
	}
	@Override
	public String toString() {
		return "Interface2 [ID=" + ID + ", InterfaceClass=" + InterfaceClass + ", Implementation=" + Implementation + "]";
	}
	 
	
	public  HashMap<String, List<Interface2>> ReadInterfacesRepresentations(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
	
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from interfaces"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		 ResultSet myresults = st.executeQuery("SELECT interfaces.* from interfaces "); 
		 while(myresults.next()) {
			 	Interface2 myinterface= new Interface2();
			 	ClassRepresentation2 InterfaceClass= new ClassRepresentation2();
			     String interfaceclassid = myresults.getString("interfaceclassid"); 			
				 String interfacename = myresults.getString("interfacename"); 
				 InterfaceClass.setClassid(interfaceclassid);
				 InterfaceClass.setClassname(interfacename);
				 
				 ClassRepresentation2 Implementation= new ClassRepresentation2();
				 String ownerclassid = myresults.getString("ownerclassid"); 			
				 String classname = myresults.getString("classname"); 
				 Implementation.setClassid(ownerclassid);
				 Implementation.setClassname(classname);
				 
				 myinterface.setInterfaceClass(InterfaceClass);
				 myinterface.setImplementation(Implementation);
				 
				 String key=ownerclassid;
				 if(ClassRepresentationHashMapOwnerClass.get(key)!=null) {
					 List<Interface2> myinterfaceslist= ClassRepresentationHashMapOwnerClass.get(key); 
					 myinterfaceslist.add(myinterface); 
					 ClassRepresentationHashMapOwnerClass.put(key, myinterfaceslist); 
				 }else {
					 List<Interface2> myinterfaceslist= new ArrayList<Interface2>(); 
					 myinterfaceslist.add(myinterface); 
					 ClassRepresentationHashMapOwnerClass.put(key, myinterfaceslist); 
				 }
				
				 System.out.println("index 5 "+index);
				 index++; 
		 }
		
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return ClassRepresentationHashMapOwnerClass; 
	}
	
	

	
	
	
	public  HashMap<String, Interface2> ReadInterfacesRepresentationsAlreadyImpl(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
	
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from interfaces"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		 ResultSet myresults = st.executeQuery("SELECT interfaces.* from interfaces "); 
		 while(myresults.next()) {
			 	Interface2 myinterface= new Interface2();
			 	ClassRepresentation2 InterfaceClass= new ClassRepresentation2();
			     String interfaceclassid = myresults.getString("interfaceclassid"); 			
				 String interfacename = myresults.getString("interfacename"); 
				 InterfaceClass.setClassid(interfaceclassid);
				 InterfaceClass.setClassname(interfacename);
				 
				 ClassRepresentation2 Implementation= new ClassRepresentation2();
				 String ownerclassid = myresults.getString("ownerclassid"); 			
				 String classname = myresults.getString("classname"); 
				 Implementation.setClassid(ownerclassid);
				 Implementation.setClassname(classname);
				 
				 myinterface.setInterfaceClass(InterfaceClass);
				 myinterface.setImplementation(Implementation);
				 
				 String key=interfaceclassid+"-"+interfacename;
				 
				 System.out.println(myinterface.toString()); 
				 ClassRepresentationHashMap.put(key, myinterface); 
				 index++; 
				 System.out.println("index 6 "+index);
		 }
		
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return ClassRepresentationHashMap; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
}
