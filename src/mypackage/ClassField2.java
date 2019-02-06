package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassField2 {
	
	String ID; 
	 String FieldName;
	 Clazz FieldType; 
	 Clazz OwnerClass;
	HashMap<String, ClassField2> myfieldclasses= new HashMap<String, ClassField2>(); 
	List<ClassField2> classFieldList= new ArrayList<ClassField2>(); 
	 HashMap<String, List<ClassField2>> ClassFieldHashMap= new HashMap<String, List<ClassField2>>(); 
	
	
	public HashMap<String, ClassField2> getMyfieldclasses() {
		return myfieldclasses;
	}

	public void setMyfieldclasses(HashMap<String, ClassField2> myfieldclasses) {
		this.myfieldclasses = myfieldclasses;
	}

	public ClassField2(String iD, String fieldName, Clazz fieldType, Clazz ownerClass) {
		super();
		ID = iD;
		FieldName = fieldName;
		FieldType = fieldType;
		OwnerClass = ownerClass;
	
	}

	public ClassField2() {
		// TODO Auto-generated constructor stub
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}

	public Clazz getFieldType() {
		return FieldType;
	}

	public void setFieldType(Clazz fieldType) {
		FieldType = fieldType;
	}

	public Clazz getOwnerClass() {
		return OwnerClass;
	}

	public void setOwnerClass(Clazz ownerClass) {
		OwnerClass = ownerClass;
	}

	@Override
	public String toString() {
		return "ClassField2 [ID=" + ID + ", FieldName=" + FieldName + ", FieldType=" + FieldType + ", OwnerClass="
				+ OwnerClass + "]";
	} 

	 
	 
	
	
	 
	 
	public  HashMap<String, List<ClassField2>> ReadClassFields(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
	
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		ResultSet var = st.executeQuery("select count(*) from fieldclasses"); 
		while(var.next()){
			rowcount = var.getString("count(*)");
		}
		System.out.println("ROW COUNT::::::"+rowcount); 
		int rowcountint= Integer.parseInt(rowcount); 
	
		int index=1; 
		 ResultSet myresults = st.executeQuery("SELECT fieldclasses.* from fieldclasses "); 
		 while(myresults.next()) {
			 	ClassField2 myclassfield = new ClassField2(); 
			 
			 	Clazz FieldType= new Clazz();
			     String fieldtypeclassid = myresults.getString("fieldtypeclassid"); 			
				 String fieldtype = myresults.getString("fieldtype"); 
				 FieldType.setClassid(fieldtypeclassid);
				 FieldType.setClassname(fieldtype);
				 
				 Clazz OwnerClass= new Clazz();
				 String ownerclassid = myresults.getString("ownerclassid"); 			
				 String classname = myresults.getString("classname"); 
				 OwnerClass.setClassid(ownerclassid);
				 OwnerClass.setClassname(classname);
				 
				 myclassfield.setFieldType(FieldType);
				 myclassfield.setOwnerClass(OwnerClass);
				 
				 String key=ownerclassid;
				 
				 if(ClassFieldHashMap.get(key)!=null) {
					 List<ClassField2> classfields= ClassFieldHashMap.get(key); 
					 classfields.add(myclassfield); 
					 ClassFieldHashMap.put(key, classfields); 
				 }else {
					 List<ClassField2> classfields= new ArrayList<ClassField2>(); 
					 classfields.add(myclassfield); 
					 ClassFieldHashMap.put(key, classfields); 
				 }
				
				 
				 myfieldclasses.put(key, myclassfield); 
				 index++; 
				 classFieldList.add(myclassfield); 
				 System.out.println("index 6 "+index);
		 }
		
			System.out.println("FIELD TYPE CLASSES");
			/* for(Integer key: keys){
		            System.out.println("Value of "+key+" is: "+ resultFieldClasses.get(key).classid+ "   "+resultFieldClasses.get(key).classname+ "   ");
		        }*/
		
		 return ClassFieldHashMap; 
	}
	 
	 
	
	 
	 
	 
}
