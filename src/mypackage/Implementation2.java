package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Implementation2 {
	public Clazz InterfaceClass; 
	public Clazz Implementation;
	
	 HashMap<String, List<Implementation2>> ImplementationHashMap= new HashMap<String, List<Implementation2>>(); 
	 
	 
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


	public HashMap<String, List<Implementation2>> getImplementationHashMap() {
		return ImplementationHashMap;
	}


	public void setImplementationHashMap(HashMap<String, List<Implementation2>> implementationHashMap) {
		ImplementationHashMap = implementationHashMap;
	}


		public  HashMap<String, List<Implementation2>> ReadImplementationsRepresentations(Connection conn) throws SQLException {
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
				 	Implementation2 myimplementation= new Implementation2();
				 	Clazz ImplementationClass= new Clazz();
				     String interfaceclassid = myresults.getString("interfaceclassid"); 			
					 String interfacename = myresults.getString("interfacename"); 
					 ImplementationClass.setClassid(interfaceclassid);
					 ImplementationClass.setClassname(interfacename);
					 
					 Clazz OwnerClass= new Clazz();
					 String ownerclassid = myresults.getString("ownerclassid"); 			
					 String classname = myresults.getString("classname"); 
					 OwnerClass.setClassid(ownerclassid);
					 OwnerClass.setClassname(classname);
					 
					 myimplementation.setImplementation(InterfaceClass);
					 myimplementation.setImplementation(OwnerClass);
					 
					 String key=interfaceclassid;
					 if(ImplementationHashMap.get(key)!=null) {
						 List<Implementation2> myimplementationlist= ImplementationHashMap.get(key); 
						 myimplementationlist.add(myimplementation); 
						 ImplementationHashMap.put(key, myimplementationlist); 
					 }else {
						 List<Implementation2> myimplementationlist= new ArrayList<Implementation2>(); 
						 myimplementationlist.add(myimplementation); 
						 ImplementationHashMap.put(key, myimplementationlist); 
					 }
					
					 System.out.println("index 5 "+index);
					 index++; 
			 }
			
				System.out.println("FIELD TYPE CLASSES");
				/* for(Integer key: keys){
			            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
			        }*/
			
			 return ImplementationHashMap; 
		}
}
