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

import mypackage.*;


public class ClassDetails2 {
	Clazz classrep; 
	List<Clazz> ChildClasses= new ArrayList<Clazz>(); 
	List<Clazz> ParentClasses=new ArrayList<Clazz>(); 
	List<Interface> Interfaces=new ArrayList<Interface>(); 
	List<ClassField2> ClassFields=new ArrayList<ClassField2>(); ; 
	HashMap<Requirement, ClassTrace2> ClassTraces= new HashMap<Requirement, ClassTrace2> ();
	
	
	
	HashMap<Integer, ClassDetails2> ClassDetailsHashMap= new HashMap<Integer, ClassDetails2>(); 

	
	
	

	public Clazz getClassrep() {
		return classrep;
	}
	public void setClassrep(Clazz classrep) {
		this.classrep = classrep;
	}
	public List<Clazz> getChildClasses() {
		return ChildClasses;
	}
	public void setChildClasses(List<Clazz> childClasses) {
		ChildClasses = childClasses;
	}
	public List<Clazz> getParentClasses() {
		return ParentClasses;
	}
	public void setParentClasses(List<Clazz> parentClasses) {
		ParentClasses = parentClasses;
	}
	public List<Interface> getInterfaces() {
		return Interfaces;
	}
	public void setInterfaces(List<Interface> interfaces) {
		Interfaces = interfaces;
	}
	public List<ClassField2> getClassFields() {
		return ClassFields;
	}
	public void setClassFields(List<ClassField2> classFields) {
		ClassFields = classFields;
	}
	public HashMap<Requirement, ClassTrace2> getClassTraces() {
		return ClassTraces;
	}
	public void setClassTraces(HashMap<Requirement, ClassTrace2> classTraces) {
		ClassTraces = classTraces;
	}
	
	
	public ClassDetails2(Clazz classrep, List<Clazz> childClasses,
			List<Clazz> parentClasses, List<Interface> interfaces, List<ClassField2> classFields,
			HashMap<Requirement, ClassTrace2> classTraces, HashMap<Integer, ClassDetails2> classDetailsHashMap) {
		super();
		this.classrep = classrep;
		ChildClasses = childClasses;
		ParentClasses = parentClasses;
		Interfaces = interfaces;
		ClassFields = classFields;
		ClassTraces = classTraces;
		ClassDetailsHashMap = classDetailsHashMap;
	}
	public ClassDetails2() {
		// TODO Auto-generated constructor stub
	}
	public  HashMap<Integer, ClassDetails2> ReadClassesRepresentations(Connection conn) throws SQLException {
		// Rule: if method A calls method B and method A implements requirement X, then I can just assume that method B implements requirement X as well 
		// Retrieving the calleeid
		DatabaseReading2 db = new DatabaseReading2(); 
		ClassDetails2 classdet= new ClassDetails2(); 
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
		 ResultSet myresults = st.executeQuery("SELECT classes.* from classes where id='"+ index +"'"); 
		 while(myresults.next()) {
			 	 classdet= new ClassDetails2(); 
			     String id = myresults.getString("id"); 			
				 String classname = myresults.getString("classname"); 
				
				 Clazz classrep= new Clazz(id, classname); 
				 classdet.setClassrep(classrep);
				 
				 this.ChildClasses= new ArrayList<Clazz>(); 
				 ResultSet superclasses=st.executeQuery("select superclasses.* from superclasses where superclassid='" + classrep.ID+"'"); 
				 while(superclasses.next()) {
					 Clazz childclass= new Clazz(); 
					 childclass.setClassid(superclasses.getString("ownerclassid"));
					 childclass.setClassname(superclasses.getString("childclassname"));
					 ChildClasses.add(childclass); 
					 classdet.setChildClasses(ChildClasses);
				 }
				 this.ParentClasses= new ArrayList<Clazz>(); 
				  superclasses=st.executeQuery("select superclasses.* from superclasses where ownerclassid='" + classrep.ID+"'"); 
				 while(superclasses.next()) {
				 Clazz superclass= new Clazz(); 
				 superclass.setClassid(superclasses.getString("superclassid"));
				 superclass.setClassname(superclasses.getString("superclassname"));
				 ParentClasses.add(superclass); 
				 classdet.setParentClasses(ParentClasses);
				 }
				 this.Interfaces= new ArrayList<Interface>(); 
				 ResultSet interfaces=st.executeQuery("select interfaces.* from interfaces where ownerclassid='" + classrep.ID+"'"); 
				 while(interfaces.next()) {
					 Interface myinterface=new Interface(); 
					 Clazz interfaceclass= new Clazz(); 
					 interfaceclass.setClassid(interfaces.getString("interfaceclassid"));
					 interfaceclass.setClassname(interfaces.getString("interfacename"));
					 myinterface.setInterfaceClass(interfaceclass);
					 
					 Clazz ownerclass= new Clazz(); 
					 ownerclass.setClassid(interfaces.getString("ownerclassid"));
					 ownerclass.setClassname(interfaces.getString("classname"));
					 myinterface.setImplementation(ownerclass);
					 Interfaces.add(myinterface); 
					 classdet.setInterfaces(Interfaces);
					
				 }
				 this.ClassFields= new ArrayList<ClassField2>(); 
				 ResultSet classfields=st.executeQuery("select fieldclasses.* from fieldclasses where ownerclassid='" + classrep.ID+"'"); 
				 while(classfields.next()) {
					 ClassField2 classfield= new ClassField2(); 
					 Clazz fieldtype= new Clazz(); 
					 
					 classfield.setFieldName(classfields.getString("fieldname"));
					 classfield.setFieldName(classfields.getString("fieldname"));
					 fieldtype.setClassid(classfields.getString("fieldtypeclassid"));
					 fieldtype.setClassname(classfields.getString("fieldtype"));
					 classfield.setFieldType(fieldtype);
					 
					 Clazz ownerclass= new Clazz(); 
					 ownerclass.setClassid(classfields.getString("ownerclassid"));
					 ownerclass.setClassname(classfields.getString("classname"));
					 classfield.setOwnerClass(ownerclass);
					 ClassFields.add(classfield); 
					 classdet.setClassFields(ClassFields);
				
					
				 }
				 this.ClassTraces= new HashMap<Requirement, ClassTrace2> ();
				 ResultSet classtraces = st.executeQuery("SELECT tracesclasses.* from tracesclasses where classid ='"+classrep.ID+"'"); 
					//populateTables(classtraces, conn);
					while(classtraces.next()) {
						ClassTrace2 classtrace= new ClassTrace2();
						//classtrace.setID(classtraces.getString("id"));
						classtrace.settrace(classtraces.getString("goldfinal"));
						classtrace.setTraceFinal(classtraces.getString("goldfinal"));

						classtrace.setSubject(classtraces.getString("subject"));
						Requirement r= new Requirement();
						r.setID(classtraces.getString("requirementid"));
						r.setRequirementName(classtraces.getString("requirement"));
						classtrace.setRequirement(r);

						Clazz myclass= new Clazz();
						myclass.setClassname(classtraces.getString("classname"));
						myclass.setClassid(classtraces.getString("classid"));
						classtrace.setMyclass(myclass);
						ClassTraces.put(r, classtrace);
						classdet.setClassTraces(ClassTraces);
					}
				 
				 ClassDetailsHashMap.put(index, classdet); 
				 index=index+1; 
				 myresults = st.executeQuery("SELECT classes.* from classes where id='"+ index +"'"); 
				 
		 }
		
		 
		 
		 
		
		 return ClassDetailsHashMap; 
	}
	
	
	
	
	
}
