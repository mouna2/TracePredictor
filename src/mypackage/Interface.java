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

public class Interface {
	String ID; 
	public Clazz InterfaceClass; 
	public Clazz Implementation;
	 HashMap<String, List<Interface>> ClassRepresentationHashMapOwnerClass= new HashMap<String, List<Interface>>(); 

	 HashMap<String, Interface> ClassRepresentationHashMap= new HashMap<String, Interface>(); 
	
	public Interface(String iD, Clazz interfaceClass, Clazz implementation) {
		super();
		ID = iD;
		InterfaceClass = interfaceClass;
		Implementation = implementation;
	}
	public Interface() {
		// TODO Auto-generated constructor stub
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public Clazz getInterfaceClass() {
		return InterfaceClass;
	}
	public void setInterfaceClass(Clazz interfaceClass) {
		InterfaceClass = interfaceClass;
	}
	public Clazz getImplementation() {
		return Implementation;
	}
	public void setImplementation(Clazz implementation) {
		Implementation = implementation;
	}
	@Override
	public String toString() {
		return "Interface2 [ID=" + ID + ", InterfaceClass=" + InterfaceClass + ", Implementation=" + Implementation + "]";
	}
	 
	
	public  HashMap<String, List<Interface>> ReadInterfacesRepresentations(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
	
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
			 	Interface myinterface= new Interface();
			 	Clazz InterfaceClass= new Clazz();
			     String interfaceclassid = myresults.getString("interfaceclassid"); 			
				 String interfacename = myresults.getString("interfacename"); 
				 InterfaceClass.setClassid(interfaceclassid);
				 InterfaceClass.setClassname(interfacename);
				 
				 Clazz Implementation= new Clazz();
				 String ownerclassid = myresults.getString("ownerclassid"); 			
				 String classname = myresults.getString("classname"); 
				 Implementation.setClassid(ownerclassid);
				 Implementation.setClassname(classname);
				 
				 myinterface.setInterfaceClass(InterfaceClass);
				 myinterface.setImplementation(Implementation);
				 
				 String key=ownerclassid;
				 if(ClassRepresentationHashMapOwnerClass.get(key)!=null) {
					 List<Interface> myinterfaceslist= ClassRepresentationHashMapOwnerClass.get(key); 
					 myinterfaceslist.add(myinterface); 
					 ClassRepresentationHashMapOwnerClass.put(key, myinterfaceslist); 
				 }else {
					 List<Interface> myinterfaceslist= new ArrayList<Interface>(); 
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
	
	

	
	
	
	public  HashMap<String, Interface> ReadInterfacesRepresentationsAlreadyImpl(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
	
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
			 	Interface myinterface= new Interface();
			 	Clazz InterfaceClass= new Clazz();
			     String interfaceclassid = myresults.getString("interfaceclassid"); 			
				 String interfacename = myresults.getString("interfacename"); 
				 InterfaceClass.setClassid(interfaceclassid);
				 InterfaceClass.setClassname(interfacename);
				 
				 Clazz Implementation= new Clazz();
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
