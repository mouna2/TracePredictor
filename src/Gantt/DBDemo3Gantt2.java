package Gantt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.pattern.FullLocationPatternConverter;

import Tables.methodcalls;
import Tables.methods;
import Tables.tracesmethods;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtTargetedExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.InterfaceFactory;
import spoon.reflect.factory.MethodFactory;
import spoon.reflect.path.CtPath;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.FieldAccessFilter;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * This class demonstrates how to connect to MySQL and run some basic commands.
 * 
 * In order to use this, you have to download the Connector/J driver and add
 * its .jar file to your build path.  You can find it here:
 * 
 * http://dev.mysql.com/downloads/connector/j/
 * 
 * You will see the following exception if it's not in your class path:
 * 
 * java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/
 * 
 * To add it to your class path:
 * 1. Right click on your project
 * 2. Go to Build Path -> Add External Archives...
 * 3. Select the file mysql-connector-java-5.1.24-bin.jar
 *    NOTE: If you have a different version of the .jar file, the name may be
 *    a little different.
 *    
 * The user name and password are both "root", which should be correct if you followed
 * the advice in the MySQL tutorial. If you want to use different credentials, you can
 * change them below. 
 * 
 * You will get the following exception if the credentials are wrong:
 * 
 * java.sql.SQLException: Access denied for user 'userName'@'localhost' (using password: YES)
 * 
 * You will instead get the following exception if MySQL isn't installed, isn't
 * running, or if your serverName or portNumber are wrong:
 * 
 * java.net.ConnectException: Connection refused
 */
public class DBDemo3Gantt2 {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */  
	
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "databasegantt";
	
	/** The name of the table we are testing with */
	private final String tableName = "classes";


	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("root", this.userName);
		connectionProps.put("123456", this.password);
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasegantt","root","123456");

		return conn;
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run() {
		ResultSet rs = null; 
		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		try {
			Statement st= conn.createStatement();
//			st.executeUpdate("DROP SCHEMA `databasegantt`"); 
//			
//			st.executeUpdate("CREATE DATABASE `databasegantt`"); 
//			st.executeUpdate("CREATE TABLE `databasegantt`.`classes` (\r\n" + 
//					"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//					"  `classname` LONGTEXT NULL,\r\n" + 
//					"  PRIMARY KEY (`id`),\r\n" + 
//					"  UNIQUE INDEX `id_UNIQUE` (`id` ASC));"); 
//			
//			
//
//		    
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`superclasses` (\r\n" + 
//		   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `superclassid` INT NULL,\r\n" + 
//		   		"  `superclassname` LONGTEXT NULL,\r\n" + 
//		   		"  `ownerclassid` INT NULL,\r\n" + 
//		   		"  `childclassname` LONGTEXT NULL,\r\n" + 
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  INDEX `superclassid_idx` (`superclassid` ASC),\r\n" + 
//		   		"  INDEX `ownerclassid_idx` (`ownerclassid` ASC),\r\n" + 
//		   		"  CONSTRAINT `superclassid`\r\n" + 
//		   		"    FOREIGN KEY (`superclassid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION,\r\n" + 
//		   		"  CONSTRAINT `ownerclassid`\r\n" + 
//		   		"    FOREIGN KEY (`ownerclassid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION);"); 
//		   
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`interfaces` (\r\n" + 
//		   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 	   	
//		   		"  `interfaceclassid` INT NULL,\r\n" + 
//		   		"  `interfacename` LONGTEXT NULL,\r\n" + 
//		   		"  `ownerclassid` INT NULL,\r\n" + 
//		   		"  `classname` LONGTEXT NULL,\r\n" +	   		
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC),\r\n" + 
//		   		"  INDEX `interfaceclassid_idx` (`interfaceclassid` ASC),\r\n" + 
//		   		"  INDEX `classid_idx` (`ownerclassid` ASC),\r\n" + 
//		   		"  CONSTRAINT `interfaceclassid`\r\n" + 
//		   		"    FOREIGN KEY (`interfaceclassid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION,\r\n" + 
//		   		"  CONSTRAINT `ownerclassid2`\r\n" + 
//		   		"    FOREIGN KEY (`ownerclassid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION);"); 
//		   
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`methods` (\r\n" + 
//		   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `methodname` LONGTEXT NULL,\r\n" + 
//		   		"  `methodnamerefined` LONGTEXT NULL,\r\n" + 
//		   		"  `methodabbreviation` LONGTEXT NULL,\r\n" + 
//		   		"  `fullmethod` LONGTEXT NULL,\r\n" + 
//		   		"  `classid` INT NULL,\r\n" + 
//		   		"  `classname` LONGTEXT NULL,\r\n" + 
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC),\r\n" + 
//		   		"  INDEX `classid_idx` (`classid` ASC),\r\n" + 
//		   		"  CONSTRAINT `classid2`\r\n" + 
//		   		"    FOREIGN KEY (`classid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION);"); 
//			
//			
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`parameters` (\r\n" + 
//		   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `parametername` VARCHAR(200) NULL,\r\n" + 
//		   		"  `parametertype` VARCHAR(200) NULL,\r\n" + 
//		   		"  `parameterclass` INT NULL,\r\n" + 
//		   		"  `classid` INT NULL,\r\n" + 
//		   		"  `classname` VARCHAR(200) NULL,\r\n" + 
//		   		"  `methodid` INT NULL,\r\n" + 
//		   		"  `methodname` LONGTEXT NULL,\r\n" + 
//		   		"  `isreturn` TINYINT NOT NULL,\r\n"+
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC),\r\n" + 
//		   		"  INDEX `classid_idx` (`classid` ASC),\r\n" + 
//		   		"  INDEX `methodid_idx` (`methodid` ASC),\r\n" + 
//		   		"  CONSTRAINT `classid8`\r\n" + 
//		   		"    FOREIGN KEY (`classid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION,\r\n" + 
//		   		"  CONSTRAINT `classid3`\r\n" + 
//		   		"    FOREIGN KEY (`classid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`classes` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION,\r\n" + 
//		   		"  CONSTRAINT `methodid`\r\n" + 
//		   		"    FOREIGN KEY (`methodid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`methods` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION"+   	
//		   		 ")"); 
//		   
//		   
//
//			   st.executeUpdate("CREATE TABLE `databasegantt`.`fieldclasses` (\r\n" + 
//				   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//				   		"  `fieldname` LONGTEXT NULL,\r\n" + 
//				   		"  `fieldtypeclassid` INT NULL,\r\n" + 
//				   		"  `fieldtype` LONGTEXT NULL,\r\n" + 
//				   		"  `ownerclassid` INT NULL,\r\n" + 
//				   		"  `classname` LONGTEXT NULL,\r\n" + 
//				   		"  PRIMARY KEY (`id`));"); 
//				   
//
//				   
//				   st.executeUpdate("CREATE TABLE `databasegantt`.`fieldmethods` (\r\n" + 
//				   		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//				   		"  `fieldaccess` VARCHAR(200) NULL,\r\n" + 
//				   		"  `fieldtypeclassid` INT NULL,\r\n" + 
//				   		"  `fieldtypeclassname` LONGTEXT NULL,\r\n" + 
//				   		"  `ownerclassname` VARCHAR(200) NULL,\r\n" + 
//				   		"  `ownerclassid` INT NULL,\r\n" + 
//				   		"  `ownermethodname` VARCHAR(400) NULL,\r\n" + 
//				   		"  `ownermethodid` INT NULL,\r\n" + 
//				   		"  `fieldclassownerclassid` LONGTEXT NULL,\r\n" + 
//				   		"  PRIMARY KEY (`id`));"); 
//		   
//
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`methodcalls` (\r\n" + 
//		   		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `callermethodid` INT NULL,\r\n" + 
//		   		"  `callername` LONGTEXT NULL,\r\n" + 
//		   		"  `callerclass` LONGTEXT NULL,\r\n" + 
//		   		"  `callerclassid` LONGTEXT NULL,\r\n" + 
//		   		"  `fullcaller` LONGTEXT NULL,\r\n" + 
//		   		"  `calleemethodid` INT NULL,\r\n" + 
//		   		"  `calleename` LONGTEXT NULL,\r\n" + 
//		   		"  `calleeclass` LONGTEXT NULL,\r\n" + 
//		   		"  `calleeclassid` LONGTEXT NULL,\r\n" + 
//		   		"  `fullcallee` LONGTEXT NULL,\r\n" + 
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC),\r\n" + 
//		   		"  INDEX `caller_idx` (`callermethodid` ASC),\r\n" + 
//		   		"  INDEX `callee_idx` (`calleemethodid` ASC),\r\n" + 
//		   		"  CONSTRAINT `methodcalledid`\r\n" + 
//		   		"    FOREIGN KEY (`callermethodid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`methods` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION,\r\n" + 
//		   		"  CONSTRAINT `callingmethodid`\r\n" + 
//		   		"    FOREIGN KEY (`calleemethodid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`methods` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION);"); 
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`methodcallsexecuted` (\r\n" + 
//			   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//			   		"  `callermethodid` LONGTEXT NULL,\r\n" + 
//			   		"  `callername` LONGTEXT NULL,\r\n" + 
//			   		"  `callerclass` LONGTEXT NULL,\r\n" + 
//			   		"  `fullcaller` LONGTEXT NULL,\r\n" + 
//			   		"  `calleemethodid` LONGTEXT NULL,\r\n" + 
//			   		"  `calleename` LONGTEXT NULL,\r\n" + 
//			   		"  `calleeclass` LONGTEXT NULL,\r\n" + 
//			   		"  `fullcallee` LONGTEXT NULL,\r\n" + 
//			   		"  PRIMARY KEY (`id`),\r\n" + 
//			   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC)); " ); 
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`traces` (\r\n" + 
//		   		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `requirement` LONGTEXT NULL,\r\n" + 
//		   		"  `requirementid` INT,\r\n" + 
//		   		"  `method` LONGTEXT NULL,\r\n" + 
//		   		"  `methodname` LONGTEXT NULL,\r\n" + 
//		   		"  `fullmethod` LONGTEXT NULL,\r\n" +
//		   		"  `methodid` INT NULL,\r\n" + 
//		   		"  `classname` LONGTEXT NULL,\r\n" + 
//		   		"  `classid` LONGTEXT NULL,\r\n" + 
//		   		"  `gold` LONGTEXT NULL,\r\n" + 
//		   		"  `subject` LONGTEXT NULL,\r\n" + 
//		   		"  `goldpredictioncallee` LONGTEXT NULL,\r\n" + 
//		   		"  `goldpredictioncaller` LONGTEXT NULL,\r\n" + 
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  INDEX `methodid_idx8` (`methodid` ASC),\r\n" + 
//		   		"  CONSTRAINT `methodid8`\r\n" + 
//		   		"    FOREIGN KEY (`methodid`)\r\n" + 
//		   		"    REFERENCES `databasegantt`.`methods` (`id`)\r\n" + 
//		   		"    ON DELETE NO ACTION\r\n" + 
//		   		"    ON UPDATE NO ACTION);\r\n" + 	
//		   		""); 
//		 
//		   
//		   st.executeUpdate("CREATE TABLE `databasegantt`.`requirements` (\r\n" + 
//		   		"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
//		   		"  `requirementname` LONGTEXT NULL,\r\n" + 
//		   		"  PRIMARY KEY (`id`),\r\n" + 
//		   		"  UNIQUE INDEX `id_UNIQUE` (`id` ASC));"); 
//			 st.executeUpdate("CREATE TABLE `databasegantt`.`tracesclasses` (\r\n" + 
//			 		"  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
//			 		"  `requirement` LONGTEXT NULL,\r\n" + 
//			 		"  `requirementid` INT NULL,\r\n" + 
//			 		"  `classname` LONGTEXT NULL,\r\n" + 
//			 		"  `classid` INT NULL,\r\n" + 
//			 		"  `gold` LONGTEXT NULL,\r\n" + 
//			 		"  `subject` LONGTEXT NULL,\r\n" + 
//			 		"  PRIMARY KEY (`id`),\r\n" + 
//			 		"  UNIQUE INDEX `idtracesclasses_UNIQUE` (`id` ASC));\r\n" + 
//			 		""); 
		   
		   try {
			Spoon();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		   
		   
		
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		
	}
	
	/**
	 * Connect to the DB and do some stuff
	 */
	public static void main(String[] args) {
		DBDemo3Gantt2 app = new DBDemo3Gantt2();
		app.run();
	}
	
	public void Spoon() throws SQLException, FileNotFoundException {
		DBDemo3Gantt2 dao = new DBDemo3Gantt2();
	Connection conn=getConnection();
	Statement st= conn.createStatement();
	
	
	
	
	Statement st2= conn.createStatement();
	Statement st3= conn.createStatement();
	Statement st4= conn.createStatement();
	Statement st5= conn.createStatement();
		SpoonAPI spoon = new Launcher();
    	spoon.addInputResource("C:\\Users\\\\mouna\\ownCloud\\Share\\ganttproject\\ganttprojectCopy");
    	spoon.getEnvironment().setAutoImports(true);
    	spoon.getEnvironment().setNoClasspath(true);
    	CtModel model = spoon.buildModel();
    	//List<String> classnames= new ArrayList<String>(); 
  
    	// Interact with model
    	Factory factory = spoon.getFactory();
    	ClassFactory classFactory = factory.Class();
    	MethodFactory methodFactory = factory.Method(); 
    	InterfaceFactory interfaceFactory = factory.Interface(); 
    	int i=1; 
        /*********************************************************************************************************************************************************************************/	
        /*********************************************************************************************************************************************************************************/	
        /*********************************************************************************************************************************************************************************/	  	
    
    	
    	
    	
    	
//  //  	BUILD CLASSES TABLE 
//    	for(CtType<?> clazz : classFactory.getAll()) {
//    		
//    	
//    		
//			Set<CtType<?>> nested = clazz.getNestedTypes();
//			
//				for(CtType<?> mynested: nested) {
//					System.out.println(mynested.getQualifiedName());
//					st.executeUpdate("INSERT INTO `classes`(`classname`) VALUES ('"+mynested.getQualifiedName()+"');");
//					
//					
//					
//					Set<CtType<?>> nested2 = mynested.getNestedTypes();
//					for(CtType<?> mynested2: nested2) {
//						System.out.println(mynested2.getQualifiedName());
//						st.executeUpdate("INSERT INTO `classes`(`classname`) VALUES ('"+mynested2.getQualifiedName()+"');");
//						
//						
//						}
//					}
//				
//			
//		
//			String FullClassName= clazz.getPackage()+"."+clazz.getSimpleName(); 
//			System.out.println(FullClassName);
//			st.executeUpdate("INSERT INTO `classes`(`classname`) VALUES ('"+FullClassName+"');");
//				
//   		
//    		
//    				
//    	
//   
//    		
//
//    	}
//    	
//    	
//    	
//    	 
//////    	/*********************************************************************************************************************************************************************************/	
//////        /*********************************************************************************************************************************************************************************/	
//////        /*********************************************************************************************************************************************************************************/
//    //	BUILD SUPERCLASSES TABLE 
//    	for(CtType<?> clazz : classFactory.getAll(true)) {
//    		String childclassQuery = null; 
//    		String superclassQuery = null;
//    		String superclassQueryName=null; 
//    		String childclassQueryName=null; 
//    		
//    		String FullClassName= clazz.getQualifiedName(); 
//    		//String superclass= clazz.getSuperclass().toString();
//    		
//			
//			//System.out.println("SUPERCLASS"+superclass +"SUBCLASS "+FullClassName);
////if(clazz.getSuperclass()!=null && clazz.getSuperclass().toString().contains(clazz.getPackage().toString()) ) {
//	if(clazz.getSuperclass()!=null  ) {
//		
//    			String superclass= clazz.getSuperclass().toString();
//    		//	System.out.println(i+"    HERE IS MY SUPERCLASS"+superclass+"AND HERE IS MY SUBCLASS  "+FullClassName);
//    		i++; 
//    
//    					ResultSet sClass = st.executeQuery("SELECT id from classes where classname='"+superclass+"'"); 
//    					while(sClass.next()){
//    						 superclassQuery= sClass.getString("id"); 
//    			//			System.out.println("superclass: "+superclassQuery);	
//    			   		   }
//
//    					ResultSet sClassName = st.executeQuery("SELECT classname from classes where classname='"+superclass+"'"); 
//    					while(sClassName.next()){
//    						 superclassQueryName= sClassName.getString("classname"); 
//    			//			System.out.println("superclass: "+superclassQuery);	
//    			   		   }		
//    					
//    					ResultSet cClass = st.executeQuery("SELECT id from classes where classname='"+FullClassName+"'"); 
//    					while(cClass.next()){
//    						 childclassQuery= cClass.getString("id"); 
//    			//			System.out.println("subclass: "+childclassQuery);	
//    			   		   }
//    					ResultSet cClassName = st.executeQuery("SELECT classname from classes where classname='"+FullClassName+"'"); 
//    					while(cClassName.next()){
//    						 childclassQueryName= cClassName.getString("classname"); 
//    			//			System.out.println("subclass: "+childclassQuery);	
//    			   		   }
//    					
//    			String result= "SELECT classname from classes where classname='"+FullClassName+"'"; 
//    			if(superclassQuery!=null)
//    			st.executeUpdate("INSERT INTO `superclasses`(`superclassid`, `superclassname`, `ownerclassid`, `childclassname`) VALUES ('"+superclassQuery +"','" +superclassQueryName+"','" +childclassQuery+"','" +childclassQueryName+"')");
//    			
//    		
//    		
//    		/*	st.executeUpdate("INSERT INTO `superclasses`(`superclass`, `childclass`) VALUES( "
//    					+"(("+ superclassQuery+")"
//    					+ ", ("+childclassQuery+")));" ); */
//        		//clazz.getSuperInterfaces();
//        		
//    		}
//    	}
//////////////    	/*********************************************************************************************************************************************************************************/	
//////////////        /*********************************************************************************************************************************************************************************/	
//////////////        /*********************************************************************************************************************************************************************************/	
////////////    	  	
//////////     	//BUILD INTERFACES TABLE 
//    	 
//
//    	List<String> mylist2 = new ArrayList<String>(); 
//    	for(CtType clazz : classFactory.getAll(true)) {
//    		
//    		if(clazz instanceof CtClass) {
//    			String myinterfaceclassid = null;
//        		String myinterfacename = null;
//        		String myclassid = null;
//        		String myclassname = null;
//        		
//    			String FullClassName= clazz.getQualifiedName(); 
//    			Set<CtTypeReference<?>> interfaces = clazz.getSuperInterfaces(); 
//
//    			for(CtTypeReference<?> inter: interfaces) {
//    			
//    					
//    				
//    		
//    					
//    				
//    					ResultSet interfacesnames = st.executeQuery("SELECT classname from classes where classname='"+inter+"'"); 
//    					while(interfacesnames.next()){
//    						myinterfacename= interfacesnames.getString("classname"); 
//    			   		   }
//    					
//    					ResultSet interfacesclasses = st.executeQuery("SELECT id from classes where classname='"+inter+"'"); 
//    					while(interfacesclasses.next()){
//    						myinterfaceclassid= interfacesclasses.getString("id"); 
//    			   		   }
//    					
//    					ResultSet classesnames= st.executeQuery("SELECT classname from classes where classname='"+FullClassName+"'"); 
//    					while(classesnames.next()){
//    						myclassname= classesnames.getString("classname"); 
//    			   		   }
//    					
//    					ResultSet interfacesname = st.executeQuery("SELECT id from classes where classname='"+FullClassName+"'"); 
//    					while(interfacesname.next()){
//    						myclassid= interfacesname.getString("id"); 
//    			   		   }
//    					String interface1= myinterfaceclassid+ myinterfacename;  
//    					String implementation1= myclassid+ myclassname; 
//    					
//    						System.out.println("INTERRRR "+inter.getQualifiedName());
//    						System.out.println("CLAZZZZ "+clazz.getQualifiedName());
//    					
//    		
//    		
//					
//					
//					if(myinterfaceclassid!=null && !mylist2.contains(interface1+implementation1) ) {
//		    			st.executeUpdate("INSERT INTO `interfaces`(`interfaceclassid`,`interfacename`,`ownerclassid`, `classname`) VALUES ('"+myinterfaceclassid +"','" +myinterfacename+"','" +myclassid+"','" +myclassname+"')");
//		    			mylist2.add(interface1+implementation1); 
//					}
//    			}
//
//					
//				
//					
//					
//					
//			
//				
//				
//				
//			}
//			
//    		
//       	List<String> mylist = new ArrayList<String>(); 
//
//     		if(clazz instanceof CtInterface) {
//    			String myinterfaceclassid = null;
//        		String myinterfacename = null;
//        		String myclassid = null;
//        		String myclassname = null;
//        		
//    			String FullClassName= clazz.getQualifiedName(); 
//    			Set<CtTypeReference<?>> interfaces = clazz.getSuperInterfaces(); 
//
//    			for(CtTypeReference<?> inter: interfaces) {
//    			
//    				ResultSet interfacesnames = st.executeQuery("SELECT classname from classes where classname='"+inter+"'"); 
//					while(interfacesnames.next()){
//						myinterfacename= interfacesnames.getString("classname"); 
//			   		   }
//					
//					ResultSet interfacesclasses = st.executeQuery("SELECT id from classes where classname='"+inter+"'"); 
//					while(interfacesclasses.next()){
//						myinterfaceclassid= interfacesclasses.getString("id"); 
//			   		   }
//					
//					ResultSet classesnames= st.executeQuery("SELECT classname from classes where classname='"+FullClassName+"'"); 
//					while(classesnames.next()){
//						myclassname= classesnames.getString("classname"); 
//			   		   }
//					
//					ResultSet interfacesname = st.executeQuery("SELECT id from classes where classname='"+FullClassName+"'"); 
//					while(interfacesname.next()){
//						myclassid= interfacesname.getString("id"); 
//			   		   }
//					String interface1= myinterfaceclassid+ myinterfacename;  
//					String implementation1= myclassid+ myclassname; 
//    				
//    		
//    					
//    				
//    					
//    					
//    						System.out.println("INTERRRR2 "+inter.getQualifiedName());
//    						System.out.println("CLAZZZZ2 "+clazz.getQualifiedName());
//    					
//    		
//    		
//					
//					
//					if(myinterfaceclassid!=null && !mylist.contains(interface1+implementation1) ) {
//		    			st.executeUpdate("INSERT INTO `superclasses`(`superclassid`, `superclassname`, `ownerclassid`, `childclassname`) VALUES ('"+myinterfaceclassid +"','" +myinterfacename+"','" +myclassid+"','" +myclassname+"')");
//		    			mylist.add(interface1+implementation1); 
//					}
//    			}
//
//					
//				
//					
//					
//					
//			
//				
//				
//				
//			}
//    		
//
//    	}
//
//
//////////////////////    	
//////////////////////    
//////////////////////    	
////////////////////////    	/*********************************************************************************************************************************************************************************/	
////////////////////////        /*********************************************************************************************************************************************************************************/	
////////////////////////        /*********************************************************************************************************************************************************************************/	  	
////////////////////////    	//BUILD METHODS TABLE 
//    	List<methods> mymethodlist = new ArrayList<methods>(); 
//    	for(CtType<?> clazz : classFactory.getAll(true)) {
//    		
//    	
//    		String myclassid = null;
//    		String myclassname = null;
//    		
//    		//ALTERNATIVE: Collection<CtMethod<?>> methods = clazz.getAllMethods(); 
//			Collection<CtMethod<?>> methods = clazz.getMethods(); 
//			String FullClassName= clazz.getQualifiedName(); 
//			
//			//System.out.println("count:   "+count);
//			//NEEDS TO BE CHANGED 
//		//	if(count==2) {
//			 List<CtConstructor> MyContructorlist = clazz.getElements(new TypeFilter<>(CtConstructor.class)); 
//			 for(CtConstructor<?> constructor: MyContructorlist) {
//				 
//				 	
//					String FullConstructorName=constructor.getSignature().toString(); 
//					
//					String methodabbreviation=FullConstructorName.substring(0, FullConstructorName.indexOf("(")); 
//					 methodabbreviation=FullClassName+".-init-"; 
//
//
//					//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//					//24 is the size of the string "net.sourceforge.ganttproject.javaChess."
//					int packagesize= "net.sourceforge.ganttproject.".length(); 
//						FullConstructorName=FullConstructorName.substring(packagesize, FullConstructorName.length()); 
//						FullConstructorName="-init-"+FullConstructorName.substring(FullConstructorName.lastIndexOf('('));  
//						
//						System.out.println(FullClassName);
//
//						ResultSet classesreferenced = st.executeQuery("SELECT * from classes where classname='"+FullClassName+"'"); 
//						while(classesreferenced.next()){
//							myclassid= classesreferenced.getString("id"); 
//							myclassname= classesreferenced.getString("classname"); 
//
//					//		System.out.println("class referenced: "+myclass);	
//				   		   }
//						
//						
//					
//							String FullMethodNameRefined=FullConstructorName.substring(0, FullConstructorName.indexOf("(")); 
//							//String FullMethodName=constructor.getSignature().toString(); 
//							String fullmeth= myclassname+"."+FullConstructorName; 
//							System.out.println(FullClassName);
//							methods meth= new methods(fullmeth, myclassid, myclassname); 
//							if(meth.contains(mymethodlist, meth)==false ) {
//							
//								
//							System.out.println(myclassname);
//							
//				    			st.executeUpdate("INSERT INTO `methods`(`methodname`, `methodnamerefined`, `methodabbreviation`, `fullmethod`,`classid`, `classname`) VALUES ('"+FullConstructorName+"','" +FullMethodNameRefined +"','" +methodabbreviation+"','" +fullmeth+"','" +myclassid+"','" +myclassname+"')");
//
//								
//				    			mymethodlist.add(meth); 
//							}
//						
//							 List<CtMethod> MyMethodsInsideCons = constructor.getElements(new TypeFilter<>(CtMethod.class)); 
//							 for(CtMethod mymethodInsideCons: MyMethodsInsideCons) {
//								
//								 String FullMethodName=mymethodInsideCons.getSignature().toString(); 
//									System.out.println("==============>"+mymethodInsideCons.getShortRepresentation().toString());
//									//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//								//	System.out.println(FullClassName);
//									String FullMethodNameRefinedInsideCons=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
//									String longmethInsideCons= clazz.getQualifiedName()+"."+FullMethodName; 
//									String methodabbreviationInsideCons=longmethInsideCons.substring(0, longmethInsideCons.indexOf("(")); 
//										ResultSet classesreferenced2 = st.executeQuery("SELECT * from classes where classname='"+FullClassName+"'"); 
//										while(classesreferenced2.next()){
//											myclassid= classesreferenced2.getString("id");
//											myclassname= classesreferenced2.getString("classname"); 
//
//									//		System.out.println("class referenced: "+myclass);	
//								   		   }
//										
//									
//									
//											String fullmeth2= myclassname+"."+FullMethodName; 
//											System.out.println(FullClassName);
//								 
//								 
//					    			st.executeUpdate("INSERT INTO `methods`(`methodname`, `methodnamerefined`, `methodabbreviation`, `fullmethod`,`classid`, `classname`) VALUES ('"+FullMethodName+"','" +FullMethodNameRefinedInsideCons +"','" +methodabbreviationInsideCons+"','" +fullmeth2+"','" +myclassid+"','" +myclassname+"')");
//
//							 }
//
//						}
//			 
//			 
//			 
//			for(CtMethod<?> method: methods) {
//				 
//				 
//				String FullMethodName=method.getSignature().toString(); 
//				System.out.println("==============>"+method.getShortRepresentation().toString());
//				//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//			//	System.out.println(FullClassName);
//				String FullMethodNameRefined=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
//				String longmeth= clazz.getQualifiedName()+"."+FullMethodName; 
//				String methodabbreviation=longmeth.substring(0, longmeth.indexOf("(")); 
//					ResultSet classesreferenced = st.executeQuery("SELECT id from classes where classname='"+FullClassName+"'"); 
//					while(classesreferenced.next()){
//						myclassid= classesreferenced.getString("id"); 
//				//		System.out.println("class referenced: "+myclass);	
//			   		   }
//					ResultSet classnames = st.executeQuery("SELECT classname from classes where classname='"+FullClassName+"'"); 
//					while(classnames.next()){
//						myclassname= classnames.getString("classname"); 
//				//		System.out.println("class referenced: "+myclass);	
//			   		   }
//					
//				
//				
//						String fullmeth= myclassname+"."+FullMethodName; 
//						System.out.println(FullClassName);
//						methods meth= new methods(FullMethodName, myclassid, myclassname); 
//						if(meth.contains(mymethodlist, meth)==false ) {
//							
//			    			st.executeUpdate("INSERT INTO `methods`(`methodname`,  `methodnamerefined`,`methodabbreviation`, `fullmethod`,`classid`, `classname`) VALUES ('"+FullMethodName +"','" +FullMethodNameRefined+"','" +methodabbreviation+"','" +longmeth+"','" +myclassid+"','" +myclassname+"')");
//
//							
//			    			mymethodlist.add(meth); 
//						}
//						
//						
//   	
//					}
//
//					
//				
//				
//			//}
//			
//			
//		
//			
//		
//			
//			
//			
//    	}
//    	
//    	
//    	
//    	for(CtType<?> myinterface : interfaceFactory.getAll(true)) {
//    		Collection<CtMethod<?>> methods = myinterface.getMethods(); 
//
//    		for(CtMethod<?> method: methods) {
//				 
//    			String myinterfaceid=null; 
//    			String myinterfacename=null; 
//				String FullMethodName=method.getSignature().toString(); 
//				System.out.println("==============>"+method.getShortRepresentation().toString());
//				//st.executeUpdate("INSERT INTO `fields`(`fieldname`) VALUES ('"+field+"');");
//			//	System.out.println(FullClassName);
//				String FullMethodNameRefined=FullMethodName.substring(0, FullMethodName.indexOf("(")); 
//				String longmeth= myinterface.getQualifiedName()+"."+FullMethodName; 
//				String methodabbreviation=longmeth.substring(0, longmeth.indexOf("(")); 
//				String inter=myinterface.getQualifiedName(); 
//				
//					ResultSet classesreferenced = st.executeQuery("SELECT classes.* from classes where classname='"+inter+"'"); 
//					System.out.println("INTER"+myinterface.getQualifiedName());
//					while(classesreferenced.next()){
//						myinterfaceid= classesreferenced.getString("id"); 
//						myinterfacename= classesreferenced.getString("classname"); 
//				//		System.out.println("class referenced: "+myclass);	
//			   		   }
//				
//					
//				
//				
//						String fullmeth= myinterfacename+"."+FullMethodName; 
//						System.out.println(fullmeth);
//						methods meth= new methods(FullMethodName, myinterfaceid, myinterfacename); 
//						if(meth.contains(mymethodlist, meth)==false ) {
//							
//			    			st.executeUpdate("INSERT INTO `methods`(`methodname`,  `methodnamerefined`,`methodabbreviation`, `fullmethod`,`classid`, `classname`) VALUES ('"+FullMethodName +"','" +FullMethodNameRefined+"','" +methodabbreviation+"','" +longmeth+"','" +myinterfaceid+"','" +myinterfacename+"')");
//
//							
//			    			mymethodlist.add(meth); 
//						}
//						
//						
//   	
//					}
//			
//		
//    	}
    	
    	
    	
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/   	
////////////////////BUILD METHODSCALLED TABLE
















        int counter=0; 


        String calleeDeclaringTypeName=null; 

        List<methodcalls> methodcallsList = new ArrayList<methodcalls>(); 
        for(CtType<?> clazz : classFactory.getAll(true)) {
        List<CtConstructor> constructorcallers = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
         for(CtConstructor<?> cons :constructorcallers) {
          	List<CtInvocation> MethodsInvokedByConstructors = cons.getElements(new TypeFilter<CtInvocation>(CtInvocation.class));
          	for(CtInvocation<?> consInvocation: MethodsInvokedByConstructors) {
          		String CalleeMethodID=null;  
          		String CALLEECLASSNAME=null;  
          		String CALLEECLASSID =null;  
          		String fullcalleeins=null;   
          		String CallerMethodIDcons=null; 
              	String CALLERCLASSNAMEcons=null; 
              	String CALLERCLASSIDcons=null; 
              	String fullcallerinscons=null; 
              	String fullcaller=null; 
              	String fullcallee=null; 
              	String InvokedMethodNamePackageFree=null;
              	String ConstructorNamePackageFree=null; 
              	
          		if(cons.getDeclaringType()!=null) {

          			
          		String constructorClassName=cons.getType().getQualifiedName();
          		String constructorName=cons.getSignature(); 
          		System.out.println("BEFORE constructorClassName====>"+constructorClassName);
          		System.out.println("BEFORE constructorName====>"+constructorName);
          		//System.out.println("CONSTRUCTOR NAME BEFORE INIT "+ constructorName);
          		
          		//System.out.println("CONS NAMEeeeeeee====>"+constructorName);	
          		System.out.println("CONSTRUCTOR NAME BEFORE INIT "+ constructorName);
          		constructorName=TransformConstructorIntoInit(constructorName); 
          		System.out.println("AFTER constructorClassName====>"+constructorClassName);	    		
          		System.out.println("AFTER constructorName====>"+constructorName);
          		System.out.println("\n");
          		fullcaller=constructorName; 
          		 ConstructorNamePackageFree=KeepOnlyMethodName(constructorName);
          		System.out.println("ConstructorNamePackageFree==ooooooooooooooooooooo==>"+ConstructorNamePackageFree);
          		System.out.println("constructorClassName==oooooooooooooooooooooooooo==>"+constructorClassName);	   
          		
          


          		
          		
          		ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+ConstructorNamePackageFree+"'"
          				+ "and methods.classname='"+constructorClassName+"'"); 
          		//while(callingmethodsrefined.next()){
          		if(callingmethodsrefined.next()) {
          			CallerMethodIDcons = callingmethodsrefined.getString("id"); 
          			CALLERCLASSNAMEcons = callingmethodsrefined.getString("classname"); 
          			CALLERCLASSIDcons = callingmethodsrefined.getString("classid"); 
          			 fullcallerinscons = callingmethodsrefined.getString("fullmethod"); 

          			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
          		}
          		System.out.println("CALLER CLASS NAME =======>>>>"+ CALLERCLASSNAMEcons);

          	}
          		
          		
          		if(consInvocation.getExecutable().getDeclaringType()!=null) {
          			
          	
          			
          			
          			String InvokedClassNameBEFORE = consInvocation.getExecutable().getDeclaringType().getQualifiedName().toString();
        	    		String InvokedMethodNameBEFORE=consInvocation.getExecutable().getSignature(); 
        	    		fullcallee=InvokedMethodNameBEFORE; 
        	    		System.out.println("BEFORE InvokedClassName====>"+InvokedClassNameBEFORE);
        	    		System.out.println("BEFORE InvokedMethodName====>"+InvokedMethodNameBEFORE);
//        	    		System.out.println("COOOOOOONS   "+cons.toString());
//        	    		System.out.println("CONSINVOCATION   "+consInvocation.toString());
        	    		String fullmeth= InvokedClassNameBEFORE+"."+InvokedMethodNameBEFORE; 
        	    	//	System.out.println("FULLMETH====>"+fullmeth);
        	    		System.out.println("\n"); 
        	    		//SUPER CONSTRUCTOR CALLS 
        	    		if(consInvocation instanceof CtConstructorCall ) {
        	    			InvokedMethodNameBEFORE=TransformConstructorIntoInit(InvokedMethodNameBEFORE); 
        	    			 InvokedMethodNamePackageFree=KeepOnlyMethodName(InvokedMethodNameBEFORE); 
        	    		}

        	    		
        	    		if(consInvocation instanceof CtSuperAccess  ) {
        	    			InvokedMethodNameBEFORE=TransformConstructorIntoInit(InvokedMethodNameBEFORE); 
        	    			 InvokedMethodNamePackageFree=KeepOnlyMethodName(InvokedMethodNameBEFORE); 
        	    			Set<CtTypeReference<?>> consss = consInvocation.getReferencedTypes() ; 
        	    			
        	    		}
        	    		 InvokedMethodNamePackageFree=KeepOnlyMethodName(InvokedMethodNameBEFORE); 
        	    	//	System.out.println("InvokedMethodNamePackageFree====>"+InvokedMethodNamePackageFree);
        	    		
        	    		
        	    		 fullmeth= InvokedClassNameBEFORE+"."+InvokedMethodNameBEFORE; 
        	    	//	System.out.println("FULLMETH====>"+fullmeth);
          					
        	    	//	System.out.println("InvokedClassName==oooooooooooooooooooooooo==>"+InvokedClassName);
        	    	//	System.out.println("InvokedMethodName==ooooooooooooooooooooo==>"+InvokedMethodName);
        	    		ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+InvokedMethodNamePackageFree+"'"
        	    				+ "and methods.classname='"+InvokedClassNameBEFORE+"'"); 
        	  
        	    		//while(callingmethodsrefined.next()){
        	    		if(callingmethodsrefined.next() && consInvocation.getParent(new TypeFilter<CtMethod>(CtMethod.class))==null) {
        	    			 CalleeMethodID = callingmethodsrefined.getString("id"); 
        	    			 CALLEECLASSNAME = callingmethodsrefined.getString("classname"); 
        	    			 CALLEECLASSID = callingmethodsrefined.getString("classid"); 
        	    			  fullcalleeins = callingmethodsrefined.getString("fullmethod"); 

        	    			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	    			 
        	    				
        	    		}
        	    		if(CalleeMethodID==null && CALLEECLASSNAME==null && CALLEECLASSID==null && consInvocation.getParent(new TypeFilter<CtMethod>(CtMethod.class))==null) {
        	    			ResultSet callingmethodsrefined2 = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+InvokedMethodNameBEFORE+"'"
          	    				+ "and methods.classname='"+InvokedClassNameBEFORE+"'"); 
          	  
          	    		//while(callingmethodsrefined.next()){
          	    		if(callingmethodsrefined2.next()) {
          	    			 CalleeMethodID = callingmethodsrefined2.getString("id"); 
          	    			 CALLEECLASSNAME = callingmethodsrefined2.getString("classname"); 
          	    			 CALLEECLASSID = callingmethodsrefined2.getString("classid"); 
          	    			  fullcalleeins = callingmethodsrefined2.getString("fullmethod"); 

          	    			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
          	    			 
          	    				
          	    		}
        	    		}
        	    
        	    		
        	    		if(CalleeMethodID==null && CALLEECLASSNAME==null && CALLEECLASSID==null && consInvocation.getParent(new TypeFilter<CtMethod>(CtMethod.class))==null) {
        	    			String fullmethod=InvokedClassNameBEFORE+"."+InvokedMethodNameBEFORE; 
        	    			ResultSet callingmethodsrefined2 = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+fullmethod+"'"); 
        	    			
        	    				//while(callingmethodsrefined.next()){
        	      	    		if(callingmethodsrefined2.next()) {
        	      	    			 CalleeMethodID = callingmethodsrefined2.getString("id"); 
        	      	    			 CALLEECLASSNAME = callingmethodsrefined2.getString("classname"); 
        	      	    			 CALLEECLASSID = callingmethodsrefined2.getString("classid"); 
        	      	    			  fullcalleeins = callingmethodsrefined2.getString("fullmethod"); 

        	      	    			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	      	    			 
        	      	    				
        	      	    		
        	    	    		}
        	    			}
          	    	
        	    		
        	    		
        	    		
        	    		
          		}
          		
          		methodcalls methodcall = new methodcalls(CalleeMethodID, fullcaller, CALLEECLASSNAME, CALLEECLASSID, CallerMethodIDcons, fullcallee, CALLERCLASSNAMEcons); 
          		//System.out.println(methodcall.toString()); 
          		if( methodcall.contains(methodcallsList, methodcall)==false && CallerMethodIDcons!=null && CalleeMethodID!=null) {
          			String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodIDcons +"','" +ConstructorNamePackageFree+"','" +CALLERCLASSNAMEcons+"','" +CALLERCLASSIDcons+"','" +fullcallerinscons+"','" +CalleeMethodID+"','" +InvokedMethodNamePackageFree+"','" +CALLEECLASSNAME+"','" +CALLEECLASSID+"','" +fullcalleeins+"')";
          			
          			st.executeUpdate(statement);
          			methodcallsList.add(methodcall); 
          		}
          	}
        	   
          	
          	
          	
          	
          	
          	List<CtConstructorCall> ConstructorsCalledByConstructors = cons.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
          	for(CtConstructorCall<?> consInvocation: ConstructorsCalledByConstructors) {
          		String CalleeMethodID=null;  
          		String CALLEECLASSNAME=null;  
          		String CALLEECLASSID =null;  
          		String fullcalleeins=null;   
          		String CallerMethodIDcons=null; 
              	String CALLERCLASSNAMEcons=null; 
              	String CALLERCLASSIDcons=null; 
              	String fullcallerinscons=null; 
              	String fullcaller=null; 
              	String fullcallee=null; 
              	String InvokedMethodNamePackageFree=null;
              	String ConstructorNamePackageFree=null; 
              	
          		if(cons.getDeclaringType()!=null) {
//            		String constructorClassName = cons.getExecutable().getDeclaringType().getQualifiedName().toString();
//          		String constructorName=cons.getExecutable().getSignature(); 
          		String constructorClassName=cons.getType().getQualifiedName();
          		String constructorName=cons.getSignature(); 
          		System.out.println("BEFORE constructorClassName====>"+constructorClassName);
          		System.out.println("BEFORE constructorName====>"+constructorName);
          		//System.out.println("CONSTRUCTOR NAME BEFORE INIT "+ constructorName);
          		
          		//System.out.println("CONS NAMEeeeeeee====>"+constructorName);	
          		System.out.println("CONSTRUCTOR NAME BEFORE INIT "+ constructorName);
          		constructorName=TransformConstructorIntoInit(constructorName); 
          		//System.out.println("constructorClassName====>"+constructorClassName);	    		
          		//System.out.println("constructorName====>"+constructorName);
          		System.out.println("\n");
          		fullcaller=constructorName; 
          		 ConstructorNamePackageFree=KeepOnlyMethodName(constructorName);
          		System.out.println("ConstructorNamePackageFree==ooooooooooooooooooooo==>"+ConstructorNamePackageFree);
          		System.out.println("constructorClassName==oooooooooooooooooooooooooo==>"+constructorClassName);	   
          		
          		
          		ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+ConstructorNamePackageFree+"'"
          				+ "and methods.classname='"+constructorClassName+"'"); 
          		//while(callingmethodsrefined.next()){
          		if(callingmethodsrefined.next()) {
          			CallerMethodIDcons = callingmethodsrefined.getString("id"); 
          			CALLERCLASSNAMEcons = callingmethodsrefined.getString("classname"); 
          			CALLERCLASSIDcons = callingmethodsrefined.getString("classid"); 
          			 fullcallerinscons = callingmethodsrefined.getString("fullmethod"); 

          			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
          		}
          		}
          		
          		
          		if(consInvocation.getExecutable().getDeclaringType()!=null) {
          			String InvokedClassNameBEFORE = consInvocation.getExecutable().getDeclaringType().getQualifiedName().toString();
        	    		String InvokedMethodNameBEFORE=consInvocation.getExecutable().getSignature(); 
        	    		fullcallee=InvokedMethodNameBEFORE; 
        	    		System.out.println("BEFORE InvokedClassName====>"+InvokedClassNameBEFORE);
        	    		System.out.println("BEFORE InvokedMethodName====>"+InvokedMethodNameBEFORE);
        	    		
        	    	//	System.out.println("InvokedClassName====>"+InvokedClassName);
        	    	//	System.out.println("InvokedMethodName====>"+InvokedMethodName);
        	    		String fullmeth= InvokedClassNameBEFORE+"."+InvokedMethodNameBEFORE; 
        	    	//	System.out.println("FULLMETH====>"+fullmeth);
        	    		System.out.println("\n");
        	    		if(consInvocation instanceof CtConstructorCall) {
        	    			InvokedMethodNameBEFORE=TransformConstructorIntoInit(InvokedMethodNameBEFORE); 
        	    			 InvokedMethodNamePackageFree=KeepOnlyMethodName(InvokedMethodNameBEFORE); 
        	    		}
        	    		 InvokedMethodNamePackageFree=KeepOnlyMethodName(InvokedMethodNameBEFORE); 
        	    	//	System.out.println("InvokedMethodNamePackageFree====>"+InvokedMethodNamePackageFree);
        	    		
        	    		
        	    		 fullmeth= InvokedClassNameBEFORE+"."+InvokedMethodNameBEFORE; 
        	    	//	System.out.println("FULLMETH====>"+fullmeth);
          					
        	    	//	System.out.println("InvokedClassName==oooooooooooooooooooooooo==>"+InvokedClassName);
        	    	//	System.out.println("InvokedMethodName==ooooooooooooooooooooo==>"+InvokedMethodName);
        	    		ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+InvokedMethodNamePackageFree+"'"
        	    				+ "and methods.classname='"+InvokedClassNameBEFORE+"'"); 
        	  
        	    		//while(callingmethodsrefined.next()){
        	    		if(callingmethodsrefined.next()) {
        	    			 CalleeMethodID = callingmethodsrefined.getString("id"); 
        	    			 CALLEECLASSNAME = callingmethodsrefined.getString("classname"); 
        	    			 CALLEECLASSID = callingmethodsrefined.getString("classid"); 
        	    			  fullcalleeins = callingmethodsrefined.getString("fullmethod"); 

        	    			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	    			 
        	    				
        	    		}
        	    		if(CalleeMethodID==null && CALLEECLASSNAME==null && CALLEECLASSID==null ) {
        	    			ResultSet callingmethodsrefined2 = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+InvokedMethodNameBEFORE+"'"
          	    				+ "and methods.classname='"+InvokedClassNameBEFORE+"'"); 
          	  
          	    		//while(callingmethodsrefined.next()){
          	    		if(callingmethodsrefined2.next()) {
          	    			 CalleeMethodID = callingmethodsrefined2.getString("id"); 
          	    			 CALLEECLASSNAME = callingmethodsrefined2.getString("classname"); 
          	    			 CALLEECLASSID = callingmethodsrefined2.getString("classid"); 
          	    			  fullcalleeins = callingmethodsrefined2.getString("fullmethod"); 

          	    			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
          	    			 
          	    				
          	    		}
        	    		}
        	    
        	    		
        	    		
          		}
          	
          		
          		
          		methodcalls methodcall = new methodcalls(CalleeMethodID, fullcaller, CALLEECLASSNAME, CALLEECLASSID, CallerMethodIDcons, fullcallee, CALLERCLASSNAMEcons); 
          		//System.out.println(methodcall.toString()); 
          		if( methodcall.contains(methodcallsList, methodcall)==false && CallerMethodIDcons!=null && CalleeMethodID!=null) {
          			String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodIDcons +"','" +ConstructorNamePackageFree+"','" +CALLERCLASSNAMEcons+"','" +CALLERCLASSIDcons+"','" +fullcallerinscons+"','" +CalleeMethodID+"','" +InvokedMethodNamePackageFree+"','" +CALLEECLASSNAME+"','" +CALLEECLASSID+"','" +fullcalleeins+"')";
          			
          			st.executeUpdate(statement);
          			methodcallsList.add(methodcall); 
          		}
          	}
        	   
        	   
        	   
         }
         
         
         
        for(CtMethod<?> method :clazz.getMethods()) {
        List<CtConstructorCall> ctNewClasses = method.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
       
        
        List<CtReturn> returnstatement = method.getElements(new TypeFilter<CtReturn>(CtReturn.class));
//        System.out.println("returnstatement.toString()  "+returnstatement.toString());
        for(CtReturn myret: returnstatement) {
            List<CtConstructorCall> constructorcallsWithinReturn = myret.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class));
          
            if(!constructorcallsWithinReturn.isEmpty()) {
            	if(constructorcallsWithinReturn.get(0)!=null) {
            		if(constructorcallsWithinReturn.get(0).getExecutable()!=null) {
            			 CtTypeReference type = constructorcallsWithinReturn.get(0).getExecutable().getType(); 
                         System.out.println(type);
                         
                       String params=  constructorcallsWithinReturn.get(0).getExecutable().toString().substring(constructorcallsWithinReturn.get(0).getExecutable().toString().indexOf("(")); 
                        String methodname=constructorcallsWithinReturn.get(0).getExecutable().toString().substring(0,constructorcallsWithinReturn.get(0).getExecutable().toString().indexOf("(")); 
                       String Fullcallee= methodname+".-init-"+params; 
                       
                       
                   
                	String FullCallerMeth=clazz.getQualifiedName()+"."+method.getSignature(); 
                	String	CallerMethodIDcons =null; 
                	String CALLERCLASSNAMEcons =null; 
                	String	CALLERCLASSIDcons =null; 
                	String fullcallerinscons =null; 
                   	ResultSet  caller = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+FullCallerMeth+"'"); 
                	//while(callingmethodsrefined.next()){
                	if(caller.next()) {
                			CallerMethodIDcons = caller.getString("id"); 
                		 CALLERCLASSNAMEcons = caller.getString("classname"); 
                			CALLERCLASSIDcons = caller.getString("classid"); 
                		 fullcallerinscons = caller.getString("fullmethod"); 

                		//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
                	}
                	
                	
                    ResultSet callee = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+Fullcallee+"'"); 
					//while(callingmethodsrefined.next()){
                   	if(callee.next()) {
                   		String CalleeMethodIDcons = callee.getString("id"); 
                   		String CALLEECLASSNAMEcons = callee.getString("classname"); 
                   		String CALLEECLASSIDcons = callee.getString("classid"); 
                   		String fullcalleeinscons = callee.getString("fullmethod"); 

                   		//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
                   		String constructorName="-init-"+params; 
                   		String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodIDcons +"','" +method.getSignature()+"','" +CALLERCLASSNAMEcons+"','" +CALLERCLASSIDcons+"','" +fullcallerinscons+"','" +CalleeMethodIDcons+"','" +constructorName+"','" +CALLEECLASSNAMEcons+"','" +CALLEECLASSIDcons+"','" +fullcalleeinscons+"')";
                   		
                   		st.executeUpdate(statement);
                   	
                   	}
                   
                   	
            		}
            	
            	}
               
            }
            
             
        }
         System.out.println("yes");
         
         
        for( CtConstructorCall myclass: ctNewClasses) {
        	//CONSTRUCTOR 
        	
        	String CallerMethodIDcons=null; 
        	String CALLERCLASSNAMEcons=null; 
        	String CALLERCLASSIDcons=null; 
        	
        	String CalleeMethodIDcons=null; 
        	String CALLEECLASSNAMEcons=null; 
        	String CALLEECLASSIDcons=null; 
        	String fullcallerinscons=null; 
        	String fullcalleeinscons=null; 
        	String constructorClassName=null; 
        	String callerclass=myclass.getExecutable().getDeclaringType().getQualifiedName(); 

        		constructorClassName= myclass.getExecutable().getDeclaringType().getQualifiedName(); 
        	
        		
        		
        	System.out.println("MYCLASS"+ clazz.getQualifiedName() +"."+method.getSignature()+"  METHOD"+ myclass.getExecutable().getSignature()+
        			"CLASSS    "+
        			myclass.getExecutable().getDeclaringType().getQualifiedName());
        	String classtype= myclass.getType().toString(); 
        	String FullCallerMeth=clazz.getQualifiedName()+"."+method.getSignature(); 
        	
        	String constructorName=myclass.getExecutable().getSignature(); 
        	System.out.println("CONSTRUCTOR AS CALLEE NAME "+ constructorName);
        	//String constructorClassName= myclass.getExecutable().getDeclaringType().getQualifiedName(); 
        	constructorName="-init-"+constructorName.substring(constructorName.indexOf("("), constructorName.length()); 
        	//System.out.println("CONSTRUCTOR NAME "+ constructorName);
        	System.out.println("CONSTRUCTOR AS CALLEE CLASS NAME"+ constructorClassName);
        	
        	//System.out.println("CONSTRUCTOR CLASS NAME"+ constructorClassName);
        	
//        	ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+constructorName+"'"
//        			+ "and methods.classname='"+constructorClassName+"'"); 
        	
        	ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+constructorName+"'"
        			+ "and methods.classname='"+classtype+"'"); 
        	//while(callingmethodsrefined.next()){
        	if(callingmethodsrefined.next()) {
        		CalleeMethodIDcons = callingmethodsrefined.getString("id"); 
        		CALLEECLASSNAMEcons = callingmethodsrefined.getString("classname"); 
        		CALLEECLASSIDcons = callingmethodsrefined.getString("classid"); 
        		 fullcalleeinscons = callingmethodsrefined.getString("fullmethod"); 

        		//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	}
        	if(CalleeMethodIDcons==null && myclass.getType().getQualifiedName().toString().contains("$")) {
        		String EditedConsClassName= myclass.getType().getQualifiedName().toString().substring(0, myclass.getType().getQualifiedName().toString().lastIndexOf("."))+"."+myclass.getType().getQualifiedName().toString().substring(myclass.getType().getQualifiedName().toString().lastIndexOf("$")+1, myclass.getType().getQualifiedName().toString().length()); 

        		 callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+constructorName+"'"
        				+ "and methods.classname='"+EditedConsClassName+"'"); 
        		//while(callingmethodsrefined.next()){
        		if(callingmethodsrefined.next()) {
        			CalleeMethodIDcons = callingmethodsrefined.getString("id"); 
        			CALLEECLASSNAMEcons = callingmethodsrefined.getString("classname"); 
        			CALLEECLASSIDcons = callingmethodsrefined.getString("classid"); 
        			 fullcalleeinscons = callingmethodsrefined.getString("fullmethod"); 

        			//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        		}
        		
        	}
        	
        callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+FullCallerMeth+"'"); 
        	//while(callingmethodsrefined.next()){
        	if(callingmethodsrefined.next()) {
        		CallerMethodIDcons = callingmethodsrefined.getString("id"); 
        		CALLERCLASSNAMEcons = callingmethodsrefined.getString("classname"); 
        		CALLERCLASSIDcons = callingmethodsrefined.getString("classid"); 
        		 fullcallerinscons = callingmethodsrefined.getString("fullmethod"); 

        		//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	}
        	
        	
        	//System.out.println("FULL CALLER INS CONS"+fullcallerinscons);
        	//System.out.println("FULL CALLEE INS CONS"+fullcalleeinscons);
        	methodcalls methodcall = new methodcalls(CalleeMethodIDcons, fullcalleeinscons, CALLEECLASSNAMEcons, CALLEECLASSIDcons, CallerMethodIDcons, fullcallerinscons, CALLERCLASSNAMEcons); 
        	//System.out.println(methodcall.toString()); 
        	if( methodcall.contains(methodcallsList, methodcall)==false && CallerMethodIDcons!=null && CalleeMethodIDcons!=null) {
        		String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodIDcons +"','" +method.getSignature()+"','" +CALLERCLASSNAMEcons+"','" +CALLERCLASSIDcons+"','" +fullcallerinscons+"','" +CalleeMethodIDcons+"','" +constructorName+"','" +CALLEECLASSNAMEcons+"','" +CALLEECLASSIDcons+"','" +fullcalleeinscons+"')";
        		
        		st.executeUpdate(statement);
        		methodcallsList.add(methodcall); 
        	}
        	
        	
//        		List args = (myclass.getExecutable().getArguments()); 
        	
//        	System.out.println("hEYYYYYY"+args.toString());
        	
        	
        	List list = myclass.getArguments();
        	
        	//System.out.println("LIST "+ list);
        	
        	for(Object elem: list) {
        		
        		if(elem instanceof CtInvocation) {
        			
        			 CtExecutableReference elemexec = ((CtInvocation) elem).getExecutable(); 
//        			System.out.println("ELEM"+elem);
//        			System.out.println("EXEC"+elemexec);
        			if(elemexec.getDeclaringType()!=null) {
        				String targetType=elemexec.getDeclaringType().getQualifiedName(); 	
        			}
        			
        			
        			
        			
        			
        			  CtExpression targ = ((CtInvocation) elem).getTarget(); 
        				if(targ instanceof CtInvocation) {
        					CtExecutableReference targex = ((CtInvocation) targ).getExecutable(); 
//        					System.out.println("TARG"+targex);
        					if(targex.getDeclaringType()!=null) {
        						String executableType=targex.getDeclaringType().getQualifiedName(); 

        					}
        					
        					
        					CtExpression targetoftarget = ((CtTargetedExpression) targ).getTarget(); 
        					while(!targetoftarget.toString().equals("") && targetoftarget instanceof CtInvocation==true ) {
        						
        						
//        						System.out.println("TARGET OF TARGET: "+targetoftarget);
        						if(targetoftarget instanceof CtInvocation<?> ) {
        							targetoftarget=((CtInvocation<?>) targetoftarget).getTarget(); 

        						}
        						else if(targetoftarget instanceof CtConstructorCall<?>) {
        							targetoftarget=((CtConstructorCall<?>) targetoftarget).getTarget(); 
        						}
        						else if(targetoftarget instanceof CtFieldAccess<?>) {
        							targetoftarget=((CtFieldAccess<?>) targetoftarget).getTarget(); 
        						}else if(targetoftarget instanceof CtField<?>) {
        							targetoftarget=((CtFieldAccess<?>) targetoftarget).getTarget(); 
        						}
        						
        						String targetoftargetType=targex.getDeclaringType().getQualifiedName(); 
        						
        					}
        				}
//        			if(elemtarg==null) {
//        				System.out.println("ELEM"+elem);
//        			}
//        			while(elemtarg!=null) {
//        				
//        				elemtarg = ((CtInvocation<?>) elemtarg).getTarget(); 
//        				System.out.println("ELEM TARG: "+elemtarg);
//        			}
        			
        		}else if(elem instanceof CtFieldAccess) {
        			//System.out.println("ELEMFILEDACCESS"+elem);
        		}
        	}
        	
        }


        String methname=method.getSimpleName(); 
        //System.out.println("CALLER METHOD=====>"+methname);
        // List<CtInvocation> methodcalls = Query.getElements(method, new TypeFilter<>(CtInvocation.class)); 
        List<CtInvocation> methodcalls = method.getElements(new TypeFilter<>(CtInvocation.class)); 
        for( CtInvocation invocation: methodcalls) {
        	String callingmethodid=null; 
        	String callingmethodsrefinedid=null; 
        	String callingmethodsrefinedname=null; 
        	String callingmethodclass=null; 
        	String calledmethodid=null; 
        	String calledmethodname=null; 
        	String calledmethodclass=null; 
        	String paramclassid=null; 
        	String CALLEEID=null; 
        	String CALLEECLASSNAME=null; 
        	String CALLEECLASSID=null; 
        	String CALLERCLASSID=null; 
        	String CallerMethodID=null; 
        	//CALLING METHOD ID 
        	String CALLEENAME= invocation.getExecutable().getSignature().toString(); 
        	CtExecutableReference<?> executableRef = invocation.getExecutable();
        	CtTypeReference<?> typeRef = executableRef.getDeclaringType();
        		
        	String CALLERCLASSNAME=clazz.getQualifiedName() ; 
        	String CallerMethod= method.getSignature(); 
        	//System.out.println("CALLER METHOD NAME: "+ CallerMethod);
        	//System.out.println("CALLER CLASS  NAME : "+ CALLERCLASSNAME);
        	ResultSet callingmethodsrefined3 = st.executeQuery("SELECT methods.id from methods where methods.methodname='"+CallerMethod+"'and methods.classname='"+CALLERCLASSNAME+"'"); 
        	//while(callingmethodsrefined.next()){
        	if(callingmethodsrefined3.next()) {
        		CallerMethodID = callingmethodsrefined3.getString("id"); 
        	//	System.out.println("CALLER METHOD ID: "+ CallerMethodID);
        	}
        	String fullcallerins=null; 
        	ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+CallerMethod+"'and methods.classname='"+CALLERCLASSNAME+"'"); 
        	//while(callingmethodsrefined.next()){
        	if(callingmethodsrefined.next()) {
        		CallerMethodID = callingmethodsrefined.getString("id"); 
        		CALLERCLASSNAME = callingmethodsrefined.getString("classname"); 
        		CALLERCLASSID = callingmethodsrefined.getString("classid"); 
        		 fullcallerins = callingmethodsrefined.getString("fullmethod"); 

        		//System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        	}
        	
        	
        	
        	
        	
//        	System.out.println("CALLEE METHOD NAME: "+ CALLEENAME);
        	if(typeRef!=null) {
        		String methodCalleeClassName=typeRef.getQualifiedName();
        	//	System.out.println("METHOD CALLEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE: "+methodCalleeClassName);
        		//ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.id from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodname='"+CalledMethodExecutable+"' and classes.classname='"+  ClassQualifiedName +"'"); 
        	
        		ResultSet callingmethodsrefined2 = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+CALLEENAME+"'and methods.classname='"+methodCalleeClassName+"'"); 
        		//while(callingmethodsrefined.next()){
        		if(callingmethodsrefined2.next()) {
        			CALLEECLASSNAME = callingmethodsrefined2.getString("classname"); 
        			CALLEECLASSID = callingmethodsrefined2.getString("classid"); 
        			CALLEEID = callingmethodsrefined2.getString("id"); 
        			
          		String fullcalleeins=null; 
        			 fullcalleeins = callingmethodsrefined2.getString("fullmethod"); 
//        			System.out.println("CALLEE METHOD ID: "+ CALLEEID);
        			//System.out.println("CALLEE CLASS NAME: "+ CALLEECLASSNAME);
        			
        			CALLEENAME= invocation.getExecutable().getSignature().toString(); 
        			String fullcaller= CALLERCLASSNAME+"."+CallerMethod; 
        			String fullcallee= CALLEECLASSNAME+"."+CALLEENAME; 
        			methodcalls methodcall= new methodcalls(CALLEEID, fullcalleeins, CALLEECLASSNAME, CALLEECLASSID, CallerMethodID, fullcallerins, CALLERCLASSNAME); 
        			//
        			//System.out.println("======>"+methodcall.toString()); 
        	//		System.out.println("FULL CALLER"+fullcallerins);
        	//		System.out.println("FULL CALLEE"+fullcalleeins);
        			if( methodcall.contains(methodcallsList, methodcall)==false && CallerMethodID!=null && CALLEEID!=null) {
        				
        				String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodID +"','" +CallerMethod+"','" +CALLERCLASSNAME+"','" +CALLERCLASSID+"','" +fullcallerins+"','" +CALLEEID+"','" +CALLEENAME+"','" +CALLEECLASSNAME+"','" +CALLEECLASSID+"','" +fullcalleeins+"')";
        				
        				st.executeUpdate(statement);
        				methodcallsList.add(methodcall); 
        			}
        		}
        	}
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	CtExpression<?> invocationTarget = invocation.getTarget(); 
        	
        	boolean  fieldaccesssflag=false; 
        	while(invocationTarget!=null ) {
        	//	String CALLEENAMETARGET= invocationTarget.toString(); 
        	//	System.out.println("TARGET: "+ CALLEENAMETARGET);
        		String NameCallee=null; 
        		if(invocationTarget instanceof CtInvocation<?>) {
        			//System.out.println("Invocation");
        			
        			List args = ((CtInvocation) invocationTarget).getArguments(); 
        			
        		//	System.out.println("hEYYYYYY"+args.toString());
        			for(Object elem: args) {
        			//	System.out.println("hEYYYYYY"+elem.toString());
        			}
        			
        			
        			
        			String calleeName = ((CtInvocation) invocationTarget).getExecutable().getSignature();
        		//	System.out.println("CALLEE NAME"+calleeName);
        		//	System.out.println(((CtInvocation) invocationTarget).getExecutable());
        			if((((CtInvocation) invocationTarget).getExecutable().getDeclaringType())!=null) {
        				 calleeDeclaringTypeName = ((CtInvocation) invocationTarget).getExecutable().getDeclaringType().getQualifiedName(); 
        		//		System.out.println("CALLEE type"+calleeDeclaringTypeName);
        			}
        			
        			List<CtParameter<?>> myparams = ((CtInvocation) invocationTarget).getExecutable().getParameters(); 
        			ResultSet callingmethodsrefined2 = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+calleeName+"'and methods.classname='"+calleeDeclaringTypeName+"'"); 
        			//while(callingmethodsrefined.next()){
        			 CALLEENAME= invocation.getExecutable().getSignature().toString(); 
        				
        				
        			if(callingmethodsrefined2.next()) {
        				NameCallee = callingmethodsrefined2.getString("methodname"); 
        				CALLEECLASSNAME = callingmethodsrefined2.getString("classname"); 
        				CALLEECLASSID = callingmethodsrefined2.getString("classid"); 
        				CALLEEID = callingmethodsrefined2.getString("id"); 
        				String fullcalleeins = callingmethodsrefined2.getString("fullmethod"); 
        				String fullcallee= CALLEECLASSNAME+"."+calleeName; 
        				String fullcaller= CALLERCLASSNAME+"."+CallerMethod; 
        			
        				System.out.println("CALLEE  NAME:  "+ NameCallee);
        				System.out.println("CALLEE CLASS NAME:  "+ CALLEECLASSNAME);
        				System.out.println("CALLEECLASSID:  "+ CALLEECLASSID);
        				System.out.println("CALLEEID:  "+ CALLEEID);
        				System.out.println("fullcalleeins:  "+ fullcalleeins);
        				System.out.println("fullcallee:  "+ fullcallee);
        				System.out.println("fullcaller:  "+ fullcaller);
        				System.out.println("\n");
        				methodcalls methodcall = new methodcalls(CALLEEID, fullcalleeins, CALLEECLASSNAME, CALLEECLASSID, CallerMethodID, fullcallerins, CALLERCLASSNAME); 
        				//System.out.println(methodcall.toString()); 
        				if( methodcall.contains(methodcallsList, methodcall)==false && CallerMethodID!=null && CALLEEID!=null) {
        					String statement = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`, `callerclassid`,`fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `calleeclassid`,  `fullcallee`) VALUES ('"+CallerMethodID +"','" +CallerMethod+"','" +CALLERCLASSNAME+"','" +CALLERCLASSID+"','" +fullcallerins+"','" +CALLEEID+"','" +NameCallee+"','" +CALLEECLASSNAME+"','" +CALLEECLASSID+"','" +fullcalleeins+"')";
        					
        					st.executeUpdate(statement);
        					methodcallsList.add(methodcall); 
        				}
        				
        		}
        		
        			invocationTarget=((CtInvocation<?>) invocationTarget).getTarget(); 
        	}	
        		else if(invocationTarget instanceof CtFieldAccess<?>) {
        		fieldaccesssflag=true; 
        		//System.out.println("Field Access");
        		invocationTarget=((CtFieldAccess<?>) invocationTarget).getTarget(); 
        	}else  {
        		
        		invocationTarget=null; 
        	}

        	}
        	
        	
        	

        	
        	

          
        	


        	
        }
        }





        }      




/////////////*********************************************************************************************************************************************************************************/	
/////////////*********************************************************************************************************************************************************************************/	
/////////////*********************************************************************************************************************************************************************************/   
//////////////CREATE REQUIREMENTS TABLE 
////////////
//file = new File("C:\\Users\\mouna\\new_workspace\\SpoonProcessorFinal\\java\\GanttFiles\\RequirementsGantt.txt");
//fileReader = new FileReader(file);
//bufferedReader = new BufferedReader(fileReader);
//stringBuffer = new StringBuffer();
//
//
//try {
//
//
//while ((line = bufferedReader.readLine()) != null) {
//System.out.println(line);
//
//
//
//
//
//String statement = "INSERT INTO `requirements`(`requirementname`) VALUES ('"+line+"')";		
//st.executeUpdate(statement);
//
//
//
//}
//
//
//
//
//}
//
//catch (IOException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//}
//////////////
///////////////*********************************************************************************************************************************************************************************/	
///////////////*********************************************************************************************************************************************************************************/	
///////////////*********************************************************************************************************************************************************************************/   
////
////////////////CREATE TRACES TABLE 
////////////
//
//HashMap<String, String> RequirementIDNameHashMap=new HashMap<String, String> (); 
//RequirementIDNameHashMap.put("1", "01: Create Tasks"); 
//RequirementIDNameHashMap.put("2", "02: Delete Tasks"); 
//RequirementIDNameHashMap.put("3", "03: Maintain Task Properties"); 
//RequirementIDNameHashMap.put("4", "04: Add/Remove Tasks as Subtasks"); 
//RequirementIDNameHashMap.put("5", "05: Handle Milestones"); 
//RequirementIDNameHashMap.put("6", "06: Create Resources (person)"); 
//RequirementIDNameHashMap.put("7", "07: Delete Resources (person)"); 
//RequirementIDNameHashMap.put("8", "08: Maintain Resource Properties"); 
//RequirementIDNameHashMap.put("9", "09: Add/Remove Task Links"); 
//RequirementIDNameHashMap.put("10", "10: Add/Remove Resources to Tasks Dependencies"); 
//RequirementIDNameHashMap.put("11", "11: Change Task Begin/End Times manually with user changes"); 
//RequirementIDNameHashMap.put("12", "12: Change Task Begin/End Times automatically with dependency changes"); 
//RequirementIDNameHashMap.put("13", "13: Change Task Begin/End Times automatically with subtask changes"); 
//RequirementIDNameHashMap.put("14", "14: Prevent Circular Dependencies"); 
//RequirementIDNameHashMap.put("15", "15: Show Critical Path"); 
//RequirementIDNameHashMap.put("16", "16: Add/Remove Holidays and Vacation Days"); 
//RequirementIDNameHashMap.put("17", "17: Show Resource Utilization (underused or overused person)"); 
//RequirementIDNameHashMap.put("18", "XXX: General Purpose Code"); 
//
//ResultSet mymeths = st2.executeQuery("SELECT methods.* from methods"); 
//while(mymeths.next()){
//String methodid = mymeths.getString("id"); 
//String method = mymeths.getString("methodabbreviation"); 
//String methodname = mymeths.getString("methodname"); 
//String fullmethod = mymeths.getString("fullmethod"); 
//
//String classname = mymeths.getString("classname"); 
//String classid = mymeths.getString("classid"); 
//
//
//
//for(String key: RequirementIDNameHashMap.keySet()) {
//tracesmethods tr= new tracesmethods(key, methodid,  classid); 
//
//String statement = "INSERT INTO `traces`(`requirement`, `requirementid`, `method`, `methodname`, `fullmethod`,  `methodid`,`classname`, `classid`) VALUES ('"+RequirementIDNameHashMap.get(tr.getRequirementid())+"','" +tr.getRequirementid()+"','" +method+"','" +methodname+"','" +fullmethod+"','" +methodid+"','"+classname +"','" +classid+"')";		
//st.executeUpdate(statement);
//
//}
//
//
//}
////
////
////
////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
///////////////////*********************************************************************************************************************************************************************************/   
//////
//////////////////CREATE TRACES CLASSES TABLE 
//////////////
////
////
//HashMap <String, String > RequirementClassHashMap= new HashMap <String, String > (); 
//
//String classname=""; 
//String classid=""; 
//String requirementname=""; 
//String requirementid="";
//ResultSet Traces = st.executeQuery("SELECT classes.* from classes "); 
//while(Traces.next()){
//classname = Traces.getString("classname"); 
//classid = Traces.getString("id"); 
//for(String keyreq: RequirementIDNameHashMap.keySet()) {
//	String key= keyreq+"-"+classid; 
//	String val= keyreq+"-"+RequirementIDNameHashMap.get(keyreq)+"-"+classid+"-"+classname; 
//
//	RequirementClassHashMap.put(key, val); 
//}
//
//
//
//
//
//}
//
//for(Entry<String, String> entry :RequirementClassHashMap.entrySet()) {
//String myvalue = entry.getValue(); 
//String[] myvalues = myvalue.split("-"); 
//String statement8= "INSERT INTO `tracesclasses`(`requirement`, `requirementid`,  `classname`, `classid`) VALUES ('"+myvalues[1]+"','" +myvalues[0]+"','"  +myvalues[3]+"','" +myvalues[2]+"')";	
//st2.executeUpdate(statement8);
//}









































//////////////////////////      	/*********************************************************************************************************************************************************************************/	
//////////////////////////        /*********************************************************************************************************************************************************************************/	
//////////////////////////        /*********************************************************************************************************************************************************************************/
////////////////////////    	
//////////////////////    	
////////    	//PARAMETERS
//    	   List<String> paramlist= new ArrayList<String>();   	
//    	   for(CtType<?> clazz : classFactory.getAll(true)) {
//    	       		
//    	       		System.out.println(clazz.getSimpleName());
//    	       		System.out.println(clazz.getPackage());
//    	       		String fullname= clazz.getPackage()+""+clazz.getQualifiedName(); 
//    	       		String MethodReferenced=null; 
//    	       		String MethodName=null; 
//    	       		String parameter=null; 
//    	       	    String ClassName=null; 
//    	       	    String classid=null; 
//    	       		String parameterclass=null; 
//    	       		String paramclassid=null; 
//    	       				
//    	       		 //for(CtField<?> field : clazz.getFields()) {
//    	       				for(CtMethod<?> method :clazz.getMethods()) {
//    	       	    			List<CtParameter<?>> params = method.getParameters(); 
//    	       				
//    	       	    			
//    	       	    			
//    	       	    		
//    	       	    	
//    	       	    			for( CtParameter<?> myparam :params) {
//    	       	    				String paramInfo=""; 
//    	       	    				boolean flag2=false; 
//    	       	    				String fullmethod=clazz.getQualifiedName()+"."+method.getSignature().toString(); 
//    	       	    				ResultSet classnames = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+fullmethod+"'"); 
//    	       	    				
//    	   	    					while(classnames.next()){
//    	   	    						 ClassName =classnames.getString("classname"); 
//    	   	    						 classid =classnames.getString("classid"); 
//    	   	    						MethodReferenced =classnames.getString("id"); 
//    	   	    			   		   }
//    	   	    					
////    	   	    					ResultSet classids = st.executeQuery("SELECT classes.id from classes INNER JOIN methods ON classes.id=methods.classid where methods.methodname='"+method.getSignature().toString()+"' "); 
////    	       	    				
////    	   	    					while(classids.next()){
////    	   	    						 classid =classids.getString("id"); 
////    	   	    					
////    	   	    			   		   }
//    	   	    					
////    	       	    					ResultSet methods = st.executeQuery("SELECT methods.id from methods INNER JOIN classes ON classes.id=methods.classid where methods.methodname='"+method.getSignature().toString()+"' and classes.id='"+classid+"'"); 
////    	       	    				
////    	       	    					while(methods.next()){
////    	       	    						MethodReferenced =methods.getString("id"); 
////    	       	    					
////    	       	    			  
//    	       	    					ResultSet paramclassids = st.executeQuery("SELECT classes.id from classes where classes.classname='"+myparam.getType()+"'"); 
//    	           	    				
//    	       	    					while(paramclassids.next()){
//    	       	    						flag2=true; 
//    	       	    						paramclassid =paramclassids.getString("id"); 
//    	       	    					
//    	       	    			   		   }
//    	       	    			
//    	       	    				
//    	       	    					
//    	       	    					
//    	           		    			 paramInfo=myparam +"','" +myparam.getType() +"','"+paramclassid+"','"+classid +"','"+ClassName+"','" +MethodReferenced+"','" +method.getSignature().toString()+"','" +0; 
//
//    	       	    				//	if(field.toString().contains("java.awt")==false && field.toString().contains("javax")==false) {
//    	       	    					//	System.out.println("HERE IS A PARAMETER: "+ myparam);
//    	       	    						System.out.println("paramInfo  "+paramInfo);
//    	       	    						if(MethodReferenced==null) {
//    	       	    							System.out.println("HERE IS NULL PARAMETER: "+myparam+"method referenced======>"+MethodReferenced);
//    	       	    						}
//    	       	    						if(MethodReferenced!=null && flag2==true && paramlist.contains(paramInfo)==false) {
//    	           	    		    			st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+myparam +"','" +myparam.getType() +"','"+paramclassid+"','"+classid +"','"+ClassName+"','" +MethodReferenced+"','" +clazz.getQualifiedName()+"."+method.getSignature().toString()+"','" +0+"')");
//    	           	    		    			paramlist.add(paramInfo); 
//    	       	    						}
//
//    	       	    				//	}
//    	       	    				
//    	       	    				
//    	       	    			}
//    	       	    			
//    	       	    		
//    	       	    			/*List<CtStatement> bodystatements = methodbody.getStatements(); 
//    	       	    			//List<CtReturn> returnstatement = methodbody.getElements(new TypeFilter<>(CtReturn.class)); 
//    	       	    		
//    	       	    				List<CtReturn> returnstatement = methodbody.getElements(new TypeFilter<>(CtReturn.class)); 
//    	       	    				for(CtReturn ret: returnstatement) {
//    	       	    					System.out.println("HERE IS RETURN: "+ret.getReturnedExpression().getType());
//    	       	    					ret.getReturnedExpression().getType(); 
//    	       	    				
//    	       	    			}*/
//    	       	    			boolean flag=false; 
//    	       	    			CtTypeReference<?> MethodType = method.getType();  
//    	        	    		//	System.out.println("METHOD TYPE  "+ MethodType);
////    	        	    			ResultSet classnames = st.executeQuery("SELECT classes.classname from classes INNER JOIN methods ON classes.id=methods.classid where methods.methodname='"+method.getSignature().toString()+"' "); 
////    	   	    				
////    	       					while(classnames.next()){
////    	       						 ClassName =classnames.getString("classname"); 
////    	       					
////    	       			   		   }
////    	       					
////    	       					ResultSet classids = st.executeQuery("SELECT classes.id from classes INNER JOIN methods ON classes.id=methods.classid where methods.methodname='"+method.getSignature().toString()+"' "); 
////    	   	    				
////    	       					while(classids.next()){
////    	       						 classid =classids.getString("id"); 
////    	       					
////    	       			   		   }
////    	       					
////    	   	    					ResultSet methods = st.executeQuery("SELECT methods.id from methods INNER JOIN classes ON classes.id=methods.classid where methods.methodname='"+method.getSignature().toString()+"' and classes.id='"+classid+"'"); 
////    	   	    				
////    	   	    					while(methods.next()){
////    	   	    						MethodReferenced =methods.getString("id"); 
////    	   	    					
////    	   	    			   		   }
//    	       	    			
//	       	    				String fullmethod=clazz.getQualifiedName()+"."+method.getSignature().toString(); 
//
//    	   	    				
//    	       	    			ResultSet classnames = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+fullmethod+"'"); 
//	       	    				
//	   	    					while(classnames.next()){
//	   	    						 ClassName =classnames.getString("classname"); 
//	   	    						 classid =classnames.getString("classid"); 
//	   	    						MethodReferenced =classnames.getString("id"); 
//	   	    			   		   }
//	   	    					if(MethodType.toString().contains("<")==true) {
//	 	   	    					String methtype=MethodType.toString().substring(MethodType.toString().indexOf("<")+1, MethodType.toString().indexOf(">")); 
//	 	   	    					ResultSet parameterclasses = st.executeQuery("SELECT classes.id from classes where classes.classname='"+methtype+"'"); 
//		   		    				
//		   	    					while(parameterclasses.next()){
//		   	    						parameterclass =parameterclasses.getString("id"); 
//		   	    						flag=true; 
//		   	    					
//		   	    			   		   }
//	 	   	    					}
//    	   	    					ResultSet parameterclasses = st.executeQuery("SELECT classes.id from classes where classes.classname='"+MethodType+"'"); 
//    	   		    				
//    	   	    					while(parameterclasses.next()){
//    	   	    						parameterclass =parameterclasses.getString("id"); 
//    	   	    						flag=true; 
//    	   	    					
//    	   	    			   		   }
//    	       		    			String paramInfo= MethodType +"','" +MethodType+"','" +parameterclass +"','" +classid +"','"+ClassName+"','" +MethodReferenced+"','" +method.getSignature().toString()+"','" +1; 
//    	       		    			System.out.println("paramInfo  "+paramInfo);
//    	       	    			if(MethodReferenced!=null && flag==true && paramlist.contains(paramInfo)==false) {
//    	       		    			st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+MethodType +"','" +MethodType+"','" +parameterclass +"','" +classid +"','"+ClassName+"','" +MethodReferenced+"','" +clazz.getQualifiedName()+"."+method.getSignature().toString()+"','" +1+"')");
//    	       		    			paramlist.add(paramInfo);
//    	       	    			}
//
//    	       	    		
//    	       	    		}
//    	       		 //}
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    	       				
//    		       			List<CtConstructor> constructorcallers = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class));
//    		   	       	   for(CtConstructor<?> cons :constructorcallers) {	
//    		 	       	    			List<CtParameter<?>> params = cons.getParameters(); 
//    		 	       				
//    		 	       	    			
//    		 	       	    			
//    		 	       	    		
//    		 	       	    	
//    		 	       	    			for( CtParameter<?> myparam :params) {
//    		 	       	    				String paramInfo=""; 
//    		 	       	    				boolean flag2=false; 
//    		 	       	    				String meth=cons.getSignature().toString(); 
//    		 	       	    				meth="-init-"+meth.substring(meth.indexOf("("), meth.indexOf(")")+1); 
//    		 	       	    				String fullmethod=clazz.getQualifiedName()+"."+meth; 
//    		 	       	    				ResultSet classnames = st.executeQuery("SELECT methods.* from methods where methods.fullmethod='"+fullmethod+"'"); 
//    		 	       	    				
//    		 	   	    					while(classnames.next()){
//    		 	   	    						 ClassName =classnames.getString("classname"); 
//    		 	   	    						 classid =classnames.getString("classid"); 
//    		 	   	    						MethodReferenced =classnames.getString("id"); 
//    		 	   	    			   		   }
//    		 	   	    					
//
//    		 	       	    				
//    		 	       	    					ResultSet paramclassids = st.executeQuery("SELECT classes.id from classes where classes.classname='"+myparam.getType()+"'"); 
//    		 	           	    				
//    		 	       	    					while(paramclassids.next()){
//    		 	       	    						flag2=true; 
//    		 	       	    						paramclassid =paramclassids.getString("id"); 
//    		 	       	    					
//    		 	       	    			   		   }
//    		 	       	    			
//    		 	       	    				
//    		 	       	    					
//    		 	       	    					
//    		 	           		    			 paramInfo=myparam +"','" +myparam.getType() +"','"+paramclassid+"','"+classid +"','"+ClassName+"','" +MethodReferenced+"','" +cons.getSignature().toString()+"','" +0; 
//
//    		 	       	    				//	if(field.toString().contains("java.awt")==false && field.toString().contains("javax")==false) {
//    		 	       	    					//	System.out.println("HERE IS A PARAMETER: "+ myparam);
//    		 	       	    						System.out.println("paramInfo  "+paramInfo);
//    		 	       	    						if(MethodReferenced==null) {
//    		 	       	    							System.out.println("HERE IS NULL PARAMETER: "+myparam+"method referenced======>"+MethodReferenced);
//    		 	       	    						}
//    		 	       	    						if(MethodReferenced!=null && flag2==true && paramlist.contains(paramInfo)==false) {
//    		 	           	    		    			st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+myparam +"','" +myparam.getType() +"','"+paramclassid+"','"+classid +"','"+ClassName+"','" +MethodReferenced+"','" +clazz.getQualifiedName()+"."+fullmethod+"','" +0+"')");
//    		 	           	    		    			paramlist.add(paramInfo); 
//    		 	       	    						}
//
//    		 	       	    				//	}
//    		 	       	    				
//    		 	       	    				
//    		 	       	    			}
//    		 	       	    			
//    		 	       	    		
//
//    		 	       	    	
//
//    		 	       	    		
//    		 	       	    		
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       				
//    		   	       		 //}
//    		   	       	}
//    	       				
//    	       				
//    	       				
//    	       	}
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/
//////////////////	
///////////////////*********************************************************************************************************************************************************************************/
//////////////////
////////////////////BUILD FIELDS TABLE -- CLASSES
//for(CtType<?> clazz : classFactory.getAll(true)) {
//
//
//
//
////ALTERNATIVE: Collection<CtFieldReference<?>> fields = clazz.getAllFields(); 
//Collection<CtField<?>> fields = clazz.getFields(); 
//String FullClassName= clazz.getPackage()+"."+clazz.getSimpleName(); 
//
////ALTERNATIVE: 	for(CtFieldReference<?> field: fields) {	
//for(CtField<?> field: fields) {
//String myclassid = null;
//String myclassname=null; 
//String fieldid=null; 
//boolean flag=false; 
//
//
//if(clazz.getQualifiedName().contains("$")) {
//	String classnamedollarfree=RemoveDollar(clazz.getQualifiedName()); 
//	System.out.println(classnamedollarfree);
//}
//String classnamedollarfree=RemoveDollar(clazz.getQualifiedName()); 
//
//
// FullClassName= classnamedollarfree; 
// System.out.println(FullClassName);
//	ResultSet classesreferenced = st.executeQuery("SELECT * from classes where classname='"+FullClassName+"'"); 
//	while(classesreferenced.next()){
//		myclassid= classesreferenced.getString("id"); 
//		myclassname= classesreferenced.getString("classname"); 
//		   }
//
//	
//	ResultSet fieldids = st.executeQuery("SELECT * from classes where classname='"+field.getType()+"'"); 
//	while(fieldids.next()){
//		flag=true; 
//		fieldid= fieldids.getString("id"); 
////			System.out.println("class referenced: "+myclass);	
//		   }
//	
////	if(field.toString().contains("java.awt")==false && field.toString().contains("javax")==false) {
//	if(fieldid!=null && flag==true) {
//		st.executeUpdate("INSERT INTO `fieldclasses`(`fieldname`, `fieldtypeclassid`, `fieldtype`, `ownerclassid`,  `classname`) VALUES ('"+field.getSimpleName() +"','"+fieldid +"','"+field.getType() +"','" +myclassid+"','" +myclassname+"')");
//
//	}
//
////	}
//
//
//}
//
//
//}
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/	
///////////////////*********************************************************************************************************************************************************************************/   	
////////////////////BUILD FIELDS TABLE -- METHODS
////////////////
//for(CtType<?> clazz : classFactory.getAll(true)) {
//
//String FullClassName= clazz.getPackage()+"."+clazz.getSimpleName();
//List<fieldmethod> FieldMethodsList= new ArrayList<fieldmethod>(); 
//
//
//for(CtMethod<?> method :clazz.getMethods()) {
//List<CtFieldAccess> list = method.getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class)); 
//for(CtFieldAccess fieldaccess: list) {
//	
//	String fieldname=null; 
//	String Fieldid=null; 
//	String Methodid=null; 
//	String myclassname=null; 
//	String MethodName=null; 
//	String FieldName=null; 
//	String myclassid=null; 
//	String myclass=null; 
//	String fieldid=null; 
//	String fieldclassid=null; 
//	String fieldaccessTargetType=null;
//	System.out.println("FIELD ACCESS===============================  "+fieldaccess);
//	System.out.println("METHOD===============================  "+clazz.getQualifiedName()+"."+method.getSignature());
//
//	boolean flag=false; 
//	
//
//	
//	if(clazz.getQualifiedName().contains("$")) {
//		String classnamedollarfree=RemoveDollar(clazz.getQualifiedName()); 
//		System.out.println(classnamedollarfree);
//	}
//	String classnamedollarfree=RemoveDollar(clazz.getQualifiedName()); 
//
//
//	ResultSet classesreferenced = st.executeQuery("SELECT methods.* from methods where classname='"+classnamedollarfree
//	+ "'and methodname='"+method.getSignature()+"'"); 
//while(classesreferenced.next()){
// myclass = classesreferenced.getString("classname"); 
// myclassid = classesreferenced.getString("classid"); 
//  Methodid = classesreferenced.getString("id"); 
//  MethodName = classesreferenced.getString("methodname"); 
////	System.out.println("class referenced: "+myclass);	
//   }
//if(fieldaccess.getType()!=null) {
//fieldaccessTargetType= fieldaccess.getType().toString(); 
//fieldaccessTargetType= fieldaccessTargetType.replaceAll("(\\[])", ""); 
//}
//
//
//
//System.out.println("FIELD ACCESS TYPE  ================================================================  "+ fieldaccessTargetType);
//
//	ResultSet fieldids = st.executeQuery("SELECT id from classes where classname='"+fieldaccessTargetType+"'"); 
//	while(fieldids.next()){
//		
//		fieldid= fieldids.getString("id"); 
//		   }
//	
//
//
//	
//	//////////////////////////////////////////////////////////////////////////////
//	ResultSet myres = st.executeQuery("SELECT * from fieldclasses where fieldname='"+fieldaccess.toString()+"' and fieldtypeclassid='"+fieldid+"' and "
//			+ "fieldtype='"+fieldaccessTargetType+"' and ownerclassid='"+myclassid+"' and classname='"+myclass+"'"); 
//	while(myres.next()){
//		
//		fieldclassid= myres.getString("id"); 
//		System.out.println("FIELD TYPE CLASS ID ::::::::::::::::::::::::::::::::::::::::::"+fieldclassid);
//		   }	
//	
//	if(fieldclassid==null) {
//		fieldclassid="null"; 
//	}
//	////////////////////////////////////////////////////////////////////
//	
//	
//	
//	ResultSet	 myres2=null; 
//	if(fieldid!=null) {
//			 String query= "SELECT * from fieldmethods where fieldaccess='"+fieldaccess+"' and fieldtypeclassid='"+fieldid+"' and "
//				+ "fieldtypeclassname='"+fieldaccessTargetType+"' and ownerclassid='"+myclassid+"' and ownerclassname='"+myclass+"' and "
//						+ "fieldclassownerclassid='"+fieldclassid+"'"; 
//			 
//			 
//			myres2= st.executeQuery(query); 
//		
//	}
//	if(myres2!=null) {
//		if(!myres2.next()) {
//			if(fieldid!=null) {
//				st.executeUpdate("INSERT INTO `fieldmethods`(`fieldaccess`, `fieldtypeclassid`, `fieldtypeclassname`,  `ownerclassname`,  `ownerclassid`,  `ownermethodname`, `ownermethodid`, `fieldclassownerclassid`) VALUES ('"+fieldaccess.toString() +"','" +fieldid+"','" +fieldaccessTargetType+"','" +myclass+"','" +myclassid+"','" +MethodName+"','" +Methodid+"','" +fieldclassid+"')");
//
//			}
//		}
//	}
//
//	
//	
//	
//
//}
//
//List<CtAssignment> asslist = method.getElements(new TypeFilter<CtAssignment>(CtAssignment.class)); 
//
//for(CtAssignment ass: asslist) {
//
//	String fieldtypeclassid=null; 
//	String fieldaccess=null; 
//	
//	String fieldtypeclassname=null; 
//	 fieldaccess= ass.getAssigned().toString(); 
//	
//	 fieldtypeclassname= ass.getAssigned().getType().toString(); 
//	 fieldtypeclassname= fieldtypeclassname.replaceAll("(\\[])", ""); 
//
//	String ownermethodid=null; 
//	String ownerclassid=null; 
//	String fieldclassownerclassid="null"; 
//	 String ownerclassname=RemoveDollar(clazz.getQualifiedName()); 
//	 
//	String ownermethodname= method.getSignature(); 
//	System.out.println("yes");
//	
//	ResultSet fieldids = st.executeQuery("SELECT id from classes where classname='"+fieldtypeclassname+"'"); 
//	while(fieldids.next()){
//		
//		fieldtypeclassid= fieldids.getString("id"); 
//		   }
//	
//	
//	ResultSet assinfo = st.executeQuery("SELECT * from methods where methodname='"+ownermethodname+"' and classname='"+ownerclassname+"'"); 
//	while(assinfo.next()){
//		
//		ownermethodid= assinfo.getString("id"); 
//		ownerclassid= assinfo.getString("classid"); 
//		ownerclassname=assinfo.getString("classname");
//		ownermethodname=assinfo.getString("methodname");
//		   }
//	
//	
//	ResultSet myres = st.executeQuery("SELECT * from fieldclasses where fieldname='"+fieldaccess+"' and fieldtypeclassid='"+fieldtypeclassid+"' and "
//			+ "fieldtype='"+fieldtypeclassname+"' and ownerclassid='"+ownerclassid+"' and classname='"+ownerclassname+"'"); 
//	while(myres.next()){
//		
//		fieldclassownerclassid= myres.getString("id"); 
//		System.out.println("FIELD TYPE CLASS ID ::::::::::::::::::::::::::::::::::::::::::"+fieldclassownerclassid);
//		   }	
//	
//	if(fieldclassownerclassid==null) {
//		fieldclassownerclassid="null"; 
//	}
//	
//	
//	
//	System.out.println("fieldtypeclassname "+fieldtypeclassname);
//System.out.println("fieldaccess  "+fieldaccess+"  fieldtypeclassid  "+fieldtypeclassid+"  fieldtypeclassname "+fieldtypeclassname+" ownerclassid "+ownerclassid+
//		" ownerclassname "+ownerclassname+"   fieldclassownerclassid "+fieldclassownerclassid);	
//ResultSet	 myres2=null; 
//if(fieldtypeclassid!=null) {
//		 String query= "SELECT * from fieldmethods where fieldaccess='"+fieldaccess+"' and fieldtypeclassid='"+fieldtypeclassid+"' and "
//			+ "fieldtypeclassname='"+fieldtypeclassname+"' and ownerclassid='"+ownerclassid+"' and ownerclassname='"+ownerclassname+"' and "
//					+ "fieldclassownerclassid='"+fieldclassownerclassid+"'"; 
//		 
//		 
//		myres2= st.executeQuery(query); 
//	
//}
//
//	
//	
//	
//if(myres2!=null) {
//	if(!myres2.next()) {
//		if(fieldtypeclassid!=null) {
//			st.executeUpdate("INSERT INTO `fieldmethods`(`fieldaccess`, `fieldtypeclassid`, `fieldtypeclassname`,  `ownerclassname`,  `ownerclassid`,  `ownermethodname`, `ownermethodid`, `fieldclassownerclassid`) VALUES ('"+fieldaccess.toString() +"','" +fieldtypeclassid+"','" +fieldtypeclassname+"','" +ownerclassname+"','" +ownerclassid+"','" +ownermethodname+"','" +ownermethodid+"','" +fieldclassownerclassid+"')");
//
//		}
//	}
//}
//
//	
//	
//	
//	
//}
//
//
//
//List<CtVariableReference> varlist = method.getElements(new TypeFilter<CtVariableReference>(CtVariableReference.class)); 
//
//for(CtVariableReference ass: varlist) {
//
//	String fieldtypeclassid=null; 
//	String fieldaccess=null; 
//	
//	String fieldtypeclassname=null; 
//	 fieldaccess= ass.getSimpleName().toString(); 
//	if(ass.getType()!=null) {
//		 fieldtypeclassname= ass.getType().toString(); 
//		 fieldtypeclassname= fieldtypeclassname.replaceAll("(\\[])", ""); 
//
//	}
//	
//	String ownermethodid=null; 
//	String ownerclassid=null; 
//	String fieldclassownerclassid="null"; 
//	
//	 String ownerclassname=RemoveDollar(clazz.getQualifiedName()); 
//	 
//	String ownermethodname= method.getSignature(); 
//	System.out.println("yes");
//	
//	ResultSet fieldids = st.executeQuery("SELECT id from classes where classname='"+fieldtypeclassname+"'"); 
//	while(fieldids.next()){
//		
//		fieldtypeclassid= fieldids.getString("id"); 
//		   }
//	
//	
//	ResultSet assinfo = st.executeQuery("SELECT * from methods where methodname='"+ownermethodname+"' and classname='"+ownerclassname+"'"); 
//	while(assinfo.next()){
//		
//		ownermethodid= assinfo.getString("id"); 
//		ownerclassid= assinfo.getString("classid"); 
//		ownerclassname=assinfo.getString("classname");
//		ownermethodname=assinfo.getString("methodname");
//		   }
//	
//	
//	ResultSet myres = st.executeQuery("SELECT * from fieldclasses where fieldname='"+fieldaccess+"' and fieldtypeclassid='"+fieldtypeclassid+"' and "
//			+ "fieldtype='"+fieldtypeclassname+"' and ownerclassid='"+ownerclassid+"' and classname='"+ownerclassname+"'"); 
//	while(myres.next()){
//		
//		fieldclassownerclassid= myres.getString("id"); 
//		System.out.println("FIELD TYPE CLASS ID ::::::::::::::::::::::::::::::::::::::::::"+fieldclassownerclassid);
//		   }	
//	
//	if(fieldclassownerclassid==null) {
//		fieldclassownerclassid="null"; 
//	}
//	
//	
//	
//	System.out.println("fieldtypeclassname "+fieldtypeclassname);
//System.out.println("fieldaccess  "+fieldaccess+"  fieldtypeclassid  "+fieldtypeclassid+"  fieldtypeclassname "+fieldtypeclassname+" ownerclassid "+ownerclassid+
//		" ownerclassname "+ownerclassname+"   fieldclassownerclassid "+fieldclassownerclassid);	
//ResultSet	 myres2=null; 
//if(fieldtypeclassid!=null) {
//		 String query= "SELECT * from fieldmethods where fieldaccess='"+fieldaccess+"' and fieldtypeclassid='"+fieldtypeclassid+"' and "
//			+ "fieldtypeclassname='"+fieldtypeclassname+"' and ownerclassid='"+ownerclassid+"' and ownerclassname='"+ownerclassname+"' and "
//					+ "fieldclassownerclassid='"+fieldclassownerclassid+"'"; 
//		 
//		 
//		myres2= st.executeQuery(query); 
//	
//}
//
//	
//	
//	
//if(myres2!=null) {
//	if(!myres2.next()) {
//		if(fieldtypeclassid!=null) {
//			st.executeUpdate("INSERT INTO `fieldmethods`(`fieldaccess`, `fieldtypeclassid`, `fieldtypeclassname`,  `ownerclassname`,  `ownerclassid`,  `ownermethodname`, `ownermethodid`, `fieldclassownerclassid`) VALUES ('"+fieldaccess.toString() +"','" +fieldtypeclassid+"','" +fieldtypeclassname+"','" +ownerclassname+"','" +ownerclassid+"','" +ownermethodname+"','" +ownermethodid+"','" +fieldclassownerclassid+"')");
//
//		}
//	}
//}
//
//	
//	
//	
//	
//}
//
//
//
//}
//
//
//
//
////-----------------------------------------------------------------------------------------------------------------
//List<CtConstructor> constructorlist = clazz.getElements(new TypeFilter<CtConstructor>(CtConstructor.class)); 
//
//for(CtConstructor cons: constructorlist) {
//
//List<CtFieldAccess> list = cons.getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class)); 
//for(CtFieldAccess fieldaccess: list) {
//	
//	String fieldname=null; 
//	String Fieldid=null; 
//	String Methodid=null; 
//	String myclassname=null; 
//	String MethodName=null; 
//	String FieldName=null; 
//	String myclassid=null; 
//	String myclass=null; 
//	String fieldid=null; 
//	String fieldaccessTargetType=null;
//
//	System.out.println("FIELD ACCESS===============================  "+fieldaccess);
//	System.out.println("METHOD===============================  "+clazz.getQualifiedName()+"."+cons.getSignature());
//	
//	boolean flag=false; 
//	String consignature= TransformConstructorIntoInit(cons.getSignature()); 
//	if(clazz.getQualifiedName().contains("$")){
//		String constructorClassName=RemoveDollar(clazz.getQualifiedName()); 
//		System.out.println(constructorClassName);
//	}
//
//	String constructorClassName=RemoveDollar(clazz.getQualifiedName()); 
//
//
//	
//
//
//	ResultSet classesreferenced = st.executeQuery("SELECT methods.* from methods where classname='"+constructorClassName
//	+ "'and methodname='"+consignature+"'"); 
//while(classesreferenced.next()){
// myclass = classesreferenced.getString("classname"); 
// myclassid = classesreferenced.getString("classid"); 
//  Methodid = classesreferenced.getString("id"); 
//  MethodName = classesreferenced.getString("methodname"); 
////	System.out.println("class referenced: "+myclass);	
//   }
//if(fieldaccess.getType()!=null) {
//fieldaccessTargetType= fieldaccess.getType().toString(); 
//fieldaccessTargetType= fieldaccessTargetType.replaceAll("(\\[])", ""); 
//}
//
//System.out.println("FIELD ACCESS TYPE  ================================================================  "+ fieldaccessTargetType);
//
//ResultSet fieldids = st.executeQuery("SELECT id from classes where classname='"+fieldaccessTargetType+"'"); 
//while(fieldids.next()){
//
//fieldid= fieldids.getString("id"); 
//   }
//
//
//
//////////////////////////////////////////////////////////////////////////////
////if(fieldid==null) {
////if(fieldaccess.getTarget()!=null) {
////	 fieldaccessTargetType= fieldaccess.getTarget().getType().toString(); 
////	fieldaccessTargetType= fieldaccessTargetType.replaceAll("(\\[])", ""); 
////}else if(fieldaccess.getType()!=null){
////	fieldaccessTargetType= fieldaccess.getType().toString(); 
////}
////
//// fieldids = st.executeQuery("SELECT id from classes where classname='"+fieldaccessTargetType+"'"); 
////while(fieldids.next()){
////	
////	fieldid= fieldids.getString("id"); 
////	   }
////}
//
////////////////////////////////////////////////////////////////////////////////
//	
//	
//	fieldmethod myfield= new fieldmethod( myclassid, myclass, MethodName, Methodid); 
//
//	
//	//	if(myfield.contains(FieldMethodsList, myfield)==false  && Methodid!=null && fieldid!=null) {
//			if( Methodid!=null && fieldid!=null) {
//			st.executeUpdate("INSERT INTO `fieldmethods`(`fieldaccess`, `fieldtypeclassid`, `fieldtype`,  `classname`,  `ownerclassid`,  `methodname`, `ownermethodid`) VALUES ('"+fieldaccess.toString() +"','" +fieldaccessTargetType+"','" +fieldaccess.getType()+"','" +myclass+"','" +myclassid+"','" +MethodName+"','" +Methodid+"')");
//			FieldMethodsList.add(myfield); 
//		}
//	
//	
//	
//	//ALTERNATIVE: 
//	//st.executeUpdate("INSERT INTO `fieldmethods`(`fieldaccess`,  `classname`,  `classid`,  `methodname`, `methodid`) VALUES ('"+fieldaccess.toString() +"','" +myclassname+"','" +myclass+"','" +MethodName+"','" +Methodid+"')");
//}
//
//
//}
//
//
//}   	
		
/////////////////*********************************************************************************************************************************************************************************/	
/////////////////*********************************************************************************************************************************************************************************/	
/////////////////*********************************************************************************************************************************************************************************/   	
//////////////////BUILD METHODSCALLED EXECUTED TABLE
//////////////   counter=0; 
File file = new File("C:\\Users\\mouna\\new_workspace\\SpoonProcessorFinal\\java\\GanttFiles\\dataMethodCallsExecutedGantt2.txt");
FileReader fileReader = new FileReader(file);
BufferedReader bufferedReader = new BufferedReader(fileReader);
StringBuffer stringBuffer = new StringBuffer();
String line;
//try {
//	
//	List<methodcallsexecuted> methodcallsexecutedlist= new ArrayList<methodcallsexecuted>(); 
//
//	while ((line = bufferedReader.readLine()) != null) {
//		
//		
//		
//		String methodsCalling= line.substring(1, line.indexOf("---")); 	
//		String ClassFROM=methodsCalling.substring(0, methodsCalling.lastIndexOf("."));
//		String MethodFROM=methodsCalling.substring(methodsCalling.lastIndexOf(".")+1, methodsCalling.indexOf(")")+1);
//		String returnFROM= methodsCalling.substring(methodsCalling.lastIndexOf(")")+1, methodsCalling.length());
//		MethodFROM=MethodFROM.replace("/", "."); 
////		MethodFROM=MethodFROM.replace(";", ","); 
////		  int endIndex = MethodFROM.lastIndexOf(",");
////		    if (endIndex != -1)  
////		    {
////		    	MethodFROM = MethodFROM.substring(0, endIndex)+")"; // not forgot to put check if(endIndex != -1)
////		    }
//		//MethodFROM=MethodFROM.replace("Lde", "de"); 
//		MethodFROM=MethodFROM.replace("Ljava", "java"); 
//		//MethodFROM=MethodFROM.replace("-", ""); 
//		String methodsCalled=line.substring(line.lastIndexOf("---")+5, line.length()-1); 			
//		String ClassTO=methodsCalled.substring(0, methodsCalled.lastIndexOf("."));
//		String MethodTO=methodsCalled.substring(methodsCalled.lastIndexOf(".")+1, methodsCalled.indexOf(")")+1); 
//		String returnTO= methodsCalled.substring(methodsCalled.lastIndexOf(")")+1, methodsCalled.length());
//		MethodTO=MethodTO.replace("/", "."); 
//		MethodTO=MethodTO.replace(";", ","); 
//		
////		   endIndex = MethodTO.lastIndexOf(",");
////		    if (endIndex != -1)  
////		    {
////		    	MethodTO = MethodTO.substring(0, endIndex)+")"; // not forgot to put check if(endIndex != -1)
////		    }
//		//MethodTO=MethodTO.substring(0, MethodTO.lastIndexOf(",")-2)+")"; 
//		MethodTO=MethodTO.replace("Lde", "de"); 
//		MethodTO=MethodTO.replace("Ljava", "java"); 
//		//MethodTO=MethodTO.replace("-", "");
//		stringBuffer.append("\n");
//		/*stringBuffer2.append("(SELECT MethodsID from Methods \r\n" + 
//				"INNER JOIN Classes \r\n" + 
//				"ON Classes.ClassID=Methods.ClassID\r\n" + 
//				"where Methods.MethodName='"+MethodTO+"'  AND Classes.ClassName='"+ClassTO+"')),"); 
//		stringBuffer2.append("\n");*/
//		// 
//		//
//		
//		//System.out.println("CLASS FROM: "+ClassFROM+"        METHOD FROM       "+ MethodFROM+ "       CLASS TO       "+ ClassTO+"       Method To       "+MethodTO); 
//		MethodFROM=RewriteFullMethod(MethodFROM); 
//		MethodTO=RewriteFullMethod(MethodTO); 
//		String callingmethodid=null; 
//		String callingmethodsrefinedid=null; 
//		String callingmethodsrefinedname=null; 
//		String callingmethodclass=null; 
//		String calledmethodid=null; 
//		String calledmethodname=null; 
//		String calledmethodclass=null; 
//		String classFROMid=null; 
//		String classTOid=null; 
//		String ClassFROMName=null; 
//		 String ClassTOName=null; 
//		 String ParameterClassID=null; 
//		 String ClassFROMidParamater=null; 
//		 String ClassFROMNameParamater=null; 
//		//get rid of everything that comes after the $ sign 
//		
//				
//				
////		String MethodFROMTransformed= MethodFROM.substring(0, MethodFROM.indexOf("(")); 
////		String MethodTOTransformed= MethodTO.substring(0, MethodTO.indexOf("(")); 
//		//CALLING METHOD ID 
//		
//		if(ClassFROM.contains("$")) {
//			//ClassFROM=ClassFROM.substring(0, ClassFROM.indexOf("$")); 
//		//	System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassFROM);
//			ClassFROM=RewriteFullMethodCallExecutedRemoveDollars(ClassFROM); 
//
//			System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassFROM);
//
//		}
//		if(ClassTO.contains("$")) {
//			//ClassTO=ClassTO.substring(0, ClassTO.indexOf("$")); 
//		//	System.out.println("ClassTO:::::::::::::::::::: "+ ClassTO);
//
//			ClassTO=RewriteFullMethodCallExecutedRemoveDollars(ClassTO); 
//
//
//			System.out.println("ClassTO:::::::::::::::::::: "+ ClassTO);
//		}
////		if(MethodTOTransformed.equals("-clinit-")) {
////			MethodTOTransformed="-init-"; 
////		}
////		if(MethodFROMTransformed.equals("-clinit-")) {
////			MethodFROMTransformed="-init-"; 
////		}
//		MethodTO= MethodTO.replaceAll("-clinit-", "-init"); 
//		MethodFROM= MethodFROM.replaceAll("-clinit-", "-init"); 
//		 String regEx = "[A-Z]";
//	    Pattern pattern = Pattern.compile(regEx);
//	 
//	  
//	    Matcher matcher = pattern.matcher(MethodFROM);
//
//		
//
//		ClassFROM=ReplaceLnetLjava(ClassFROM); 
//		ClassFROM=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(ClassFROM);
//
//		ClassTO=ReplaceLnetLjava(ClassTO);
//		ClassTO=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(ClassTO);
//		
//		MethodFROM=ReplaceLnetLjava(MethodFROM);
//		MethodTO=ReplaceLnetLjava(MethodTO);
//		
//	 
//		MethodFROM=MethodFROM.replaceAll("\\(Z\\)", "\\(boolean\\)"); 
//		MethodFROM=MethodFROM.replaceAll("\\(I\\)", "\\(int\\)"); 
//		MethodFROM=MethodFROM.replaceAll("\\$\\(", "\\("); 
//		MethodFROM=MethodFROM.replaceAll("II", "int,int,"); 
//		MethodFROM=MethodFROM.replaceAll("ZZ", "boolean,boolean,"); 
//		MethodFROM=MethodFROM.replaceAll(",\\)", "\\)"); 
//		MethodFROM=MethodFROM.replaceAll(",I\\)", ",int\\)"); 
//		MethodFROM=MethodFROM.replaceAll("Cint", "char,int"); 
//		MethodFROM=MethodFROM.replaceAll("Ijava", "int,java"); 
//		MethodTO=MethodTO.replaceAll("\\(Z\\)", "\\(boolean\\)"); 
//		MethodTO=MethodTO.replaceAll("\\(I\\)", "\\(int\\)"); 
//		MethodTO=MethodTO.replaceAll("\\$\\(", "\\("); 
//		MethodTO=MethodTO.replaceAll("II", "int,int,"); 
//		MethodTO=MethodTO.replaceAll("ZZ", "boolean,boolean,"); 
//		MethodTO=MethodTO.replaceAll(",\\)", "\\)"); 
//		MethodTO=MethodTO.replaceAll(",I\\)", ",int\\)"); 
//		MethodTO=MethodTO.replaceAll("Cint", "char,int"); 
//		MethodTO=MethodTO.replaceAll("Ijava", "int,java"); 
//		MethodFROM=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(MethodFROM);
//		MethodTO=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(MethodTO);
//		
//		
//		   System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassFROM);
//			System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassTO);
//			System.out.println("CLASS FROM:::::::::::::::::::: "+ MethodFROM);
//			System.out.println("CLASS FROM:::::::::::::::::::: "+ MethodTO);
//			
//	//	counter ++; 
//		//CALLING METHOD ID 
//		ResultSet callingmethodsrefined = st.executeQuery("SELECT methods.* from methods where methods.methodname='"+MethodFROM+"' and methods.classname='"+ClassFROM+"'"); 
//		while(callingmethodsrefined.next()){
//			callingmethodsrefinedid = callingmethodsrefined.getString("id"); 
//			callingmethodsrefinedname = callingmethodsrefined.getString("methodname"); 
//		}
//		 
//		
//		
//		
//		//CALLING METHOD CLASS 
//		ResultSet callingmethodsclasses = st.executeQuery("SELECT classes.classname from classes where classes.classname ='"+ClassFROM+"'"); 
//		while(callingmethodsclasses.next()){
//			callingmethodclass = callingmethodsclasses.getString("classname"); 
//			   }
//		
//		MethodTO=MethodTO.replaceAll("Lantlr", "antlr"); 
//		MethodFROM=MethodFROM.replaceAll("Lantlr", "antlr"); 
//		//CALLED METHOD ID 
//		ResultSet calledmethodsids= st.executeQuery("SELECT methods.* from methods  where methods.methodname='"+MethodTO+"'and methods.classname='"+ClassTO+"'"); 
//		while(calledmethodsids.next()){
//			calledmethodid = calledmethodsids.getString("id"); 
//			calledmethodname = calledmethodsids.getString("methodname"); 
//			   }
//		 
//		//CALLED METHOD NAME 
////		ResultSet callemethodnames = st.executeQuery("SELECT methods.methodname from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodname='"+MethodTOTransformed+"'"); 
////		while(callemethodnames.next()){
////			calledmethodname = callemethodnames.getString("methodname"); 
////			   }
//		
//		
//		//CALLED METHOD CLASS 
//		ResultSet calledmethodclasses = st.executeQuery("SELECT classes.classname from classes where classes.classname ='"+ClassTO+"'"); 
//		while(calledmethodclasses.next()){
//			calledmethodclass = calledmethodclasses.getString("classname"); 
//			   }
//		
//		
//		
//		
//
//		
//		
//		
//		
//				
//				
//				
//				
//		
//		//System.out.println("CLASS FROM: "+ClassFROM+"        METHOD FROM       "+ MethodFROM+ "       CLASS TO       "+ ClassTO+"       Method To       "+MethodTO+"calling merthod refined id    "+ callingmethodsrefinedid+ "called method id    "+ calledmethodid); 
//
//		methodcallsexecuted mce= new methodcallsexecuted(callingmethodsrefinedid, MethodFROM, ClassFROM, calledmethodid, MethodTO, ClassTO); 
//		System.out.println(mce.toString()); 	
//		if(mce.contains(methodcallsexecutedlist, mce)==false) {
//			if(callingmethodsrefinedid!=null && calledmethodid!=null ) {
//				String fullcaller= ClassFROM+"."+MethodFROM; 
//				String fullcallee= ClassTO+"."+MethodTO; 
//				String FullMethodFROM= ClassFROM+"."+MethodFROM; 
//			    String FullMethodTO= ClassTO+"."+MethodTO; 
//			    fullcaller=RewriteFullMethod(FullMethodFROM); 
//			    fullcallee=RewriteFullMethod(FullMethodTO); 
//				String statement = "INSERT INTO `methodcallsexecuted`(`callermethodid`,  `callername`,  `callerclass`,  `fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`, `fullcallee`) VALUES ('"+callingmethodsrefinedid+"','" +MethodFROM+"','" +ClassFROM+"','"+fullcaller+"','"+calledmethodid +"','" +MethodTO+"','" +ClassTO+"','" +fullcallee +"')";		
//				st.executeUpdate(statement);
//				methodcallsexecutedlist.add(mce); 
//				
//				System.out.println("LINE INSERTED=======>"+ line);
//			}
//			else {
//				System.out.println("LINE THAT COULD NOT BE INSERTED=======>"+ line);
//				//if the methods table does not contain a method call that is obtained from parsing the log file, then I am inserting this row within the methods table
//				   //This is for METHOD FROM 
//				ClassFROM=ReplaceLnetLjava(ClassFROM); 
//				ClassFROM=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(ClassFROM);
//
//				ClassTO=ReplaceLnetLjava(ClassTO);
//				ClassTO=RewriteFullMethodCallExecutedRemoveDollarsPushDollar(ClassTO);
//				MethodFROM=ReplaceLnetLjava(MethodFROM);
//				MethodTO=ReplaceLnetLjava(MethodTO);
//				
//			  
//				MethodFROM=MethodFROM.replaceAll("\\(Z\\)", "\\(boolean\\)"); 
//				MethodFROM=MethodFROM.replaceAll("\\(I\\)", "\\(int\\)"); 
//				MethodFROM=MethodFROM.replaceAll("$\\(", "\\("); 
//				MethodFROM=MethodFROM.replaceAll("II", "int,int,"); 
//				MethodFROM=MethodFROM.replaceAll("ZZ", "boolean,boolean,"); 
//				MethodFROM=MethodFROM.replaceAll(",\\)", "\\)"); 
//				MethodFROM=MethodFROM.replaceAll(",I\\)", ",int\\)"); 
//				MethodFROM=MethodFROM.replaceAll("IZ", "int,boolean"); 
//				MethodFROM=MethodFROM.replaceAll("\\(J\\)", "\\(long\\)"); 
//				MethodFROM=MethodFROM.replaceAll(",J\\)", ",long\\)"); 
//				
//				
//				MethodTO=MethodTO.replaceAll("\\(Z\\)", "\\(boolean\\)"); 
//				MethodTO=MethodTO.replaceAll("\\(I\\)", "\\(int\\)"); 
//				MethodTO=MethodTO.replaceAll("$\\(", "\\("); 
//				MethodTO=MethodTO.replaceAll("II", "int,int,"); 
//				MethodTO=MethodTO.replaceAll("ZZ", "boolean,boolean,"); 
//				MethodTO=MethodTO.replaceAll(",\\)", "\\)"); 
//				MethodTO=MethodTO.replaceAll(",I\\)", ",int\\)"); 
//				MethodTO=MethodTO.replaceAll("IZ", "int,boolean"); 
//				MethodTO=MethodTO.replaceAll("\\(J\\)", "\\(long\\)"); 
//				MethodTO=MethodTO.replaceAll(",J\\)", ",long\\)"); 
//				
//				
//				  System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassFROM);
//					System.out.println("CLASS FROM:::::::::::::::::::: "+ ClassTO);
//					System.out.println("CLASS FROM:::::::::::::::::::: "+ MethodFROM);
//					System.out.println("CLASS FROM:::::::::::::::::::: "+ MethodTO);
//				//calculate class id FROM 
//					ResultSet classidsFROM = st.executeQuery("SELECT classes.id from classes where classes.classname ='"+ClassFROM+"'"); 
//					while(classidsFROM.next()){
//						classFROMid = classidsFROM.getString("id"); 
//						   }
//					
//					//calculate class classname FROM 
//					ResultSet classnamesFROM = st.executeQuery("SELECT classes.classname from classes where classes.classname ='"+ClassFROM+"'"); 
//					while(classnamesFROM.next()){
//						ClassFROMName = classnamesFROM.getString("classname"); 
//						   }
//					
//					
//					//calculate class classname FROM 
//					ResultSet paramclassids = st.executeQuery("SELECT classes.* from classes where classes.id ='"+returnFROM+"'"); 
//					while(paramclassids.next()){
//						ClassFROMidParamater = paramclassids.getString("id"); 
//						ClassFROMNameParamater = paramclassids.getString("classname"); 
//						   }
//					
//					
//				//	String MethodFROMRefined= MethodFROMTransformed.substring(0, MethodFROMTransformed.indexOf("(")); 
//					String MethodFROMRefined= MethodFROM; 
//					String MethodFROMAbbreviation = ClassFROM+"."+MethodFROM; 
//					if(callingmethodsrefinedid==null && classFROMid!=null) {
//						String fullmeth=ClassFROM+"."+MethodFROM; 
//						fullmeth=RewriteFullMethod(fullmeth); 
//						MethodFROM=ReplaceLnetLjava(MethodFROM);
//						MethodFROMRefined=ReplaceLnetLjava(MethodFROMRefined);
//						MethodFROMAbbreviation=	ReplaceLnetLjava(MethodFROMAbbreviation);
//						fullmeth=ReplaceLnetLjava(fullmeth);
//						ClassFROM=ReplaceLnetLjava(ClassFROM);
//						st.executeUpdate("INSERT INTO `methods`(`methodname`,  `methodnamerefined`,`methodabbreviation`, `fullmethod`, `classid`, `classname`) VALUES ('"+MethodFROM +"','" +MethodFROMRefined+"','" +MethodFROMAbbreviation+"','" +fullmeth+"','" +classFROMid+"','" +ClassFROM+"')");
//		    		
//						//RECALCULATION PHASE: CALLING METHOD ID 
//						 callingmethodsrefined = st.executeQuery("SELECT methods.id from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodname='"+MethodFROM+"' and classes.classname='"+ClassFROM+"'"); 
//						while(callingmethodsrefined.next()){
//							callingmethodsrefinedid = callingmethodsrefined.getString("id"); 
//					
//						}
//						
//						String par= transformstring(returnFROM); 
//						 regEx = "\\(([A-Z])\\)";
//					     pattern = Pattern.compile(regEx);
//					     matcher = pattern.matcher(par);
//					    while (matcher.find()) {
//					    	par=par.replaceAll("Z", "boolean"); 
//					    	par=par.replaceAll("B", "byte"); 
//					    	par=par.replaceAll("I", "int"); 
//					    	par=par.replaceAll("J", "long"); 
//					    	par=par.replaceAll("S", "short"); 
//					    }
//						regEx = "\\(([A-Z][A-Z]+)\\)";
//					     pattern = Pattern.compile(regEx);
//					     matcher = pattern.matcher(par);
//					    while (matcher.find()) {
//					    
//					    	par=par.replaceAll("Z", "boolean,"); 
//					    	par=par.replaceAll("B", "byte,"); 
//					    	par=par.replaceAll("I", "int,"); 
//					    	par=par.replaceAll("J", "long,"); 
//					    	par=par.replaceAll("S", "short,"); 
//					    	par=par.substring(0, par.lastIndexOf(",")); 
//					    	par=par+")"; 
//					    }
//					System.out.println("PARAM"+par);
//						 if(par.contains("net.sourceforge.ganttproject")) {//ignore the basic data types, only insert the parameters thaht have classes as data types 
//							 
//							 ResultSet ParameterClassIDs= st.executeQuery("SELECT classes.id from classes where classes.classname='"+par+"'"); 
//								while(ParameterClassIDs.next()){
//									 ParameterClassID = ParameterClassIDs.getString("id"); 
//									   }
//							 
//					//	System.out.println("COUNYER========> "+counter);	
//						if(ParameterClassID!=null)
//						st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+par +"','" +par +"','"+ParameterClassID+"','"+ClassFROMidParamater +"','"+ClassFROMNameParamater+"','" +callingmethodsrefinedid+"','" +MethodFROM+"','" +1+"')");
//						 }
//						String[] params = ExtractParams(MethodFROM); 
//						 //insert parameters that were retrieved from the log file 
//					//	counter++; 
//						//System.out.println("HERE IS THE LINE =======>"+line+ counter);
//						for(String p: params) {
//							 regEx = "\\(([A-Z])\\)";
//						     pattern = Pattern.compile(regEx);
//						     matcher = pattern.matcher(par);
//						    while (matcher.find()) {
//						    	p=p.replaceAll("Z", "boolean"); 
//						    	p=p.replaceAll("B", "byte"); 
//						    	p=p.replaceAll("I", "int"); 
//						    	p=p.replaceAll("J", "long"); 
//						    	p=p.replaceAll("S", "short"); 
//						    }
//							regEx = "\\(([A-Z][A-Z]+)\\)";
//						     pattern = Pattern.compile(regEx);
//						     matcher = pattern.matcher(par);
//						    while (matcher.find()) {
//						    
//						    	p=p.replaceAll("Z", "boolean,"); 
//						    	p=p.replaceAll("B", "byte,"); 
//						    	p=p.replaceAll("I", "int,"); 
//						    	p=p.replaceAll("J", "long,"); 
//						    	p=p.replaceAll("S", "short,"); 
//						    	p=p.substring(0, p.lastIndexOf(",")); 
//						    	p=p+")"; 
//						    }
//						
//							System.out.println("HERE IS A PARAM==================================================================>"+p); 
//							ResultSet ParameterClassIDs= st.executeQuery("SELECT classes.id from classes where classes.classname='"+p+"'"); 
//							while(ParameterClassIDs.next()){
//								 ParameterClassID = ParameterClassIDs.getString("id"); 
//								   }
//							
//							
//							if(p.contains("net.sourceforge.ganttproject") && p!=null && p.equals("")==false && classFROMid!=null && ParameterClassID!=null) {
//								st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+p +"','" +p +"','"+ParameterClassID+"','"+classFROMid +"','"+ClassFROMName+"','" +callingmethodsrefinedid+"','" +MethodFROM+"','" +0+"')");
//
//							}
//					}
//				
//				//		counter++; 
//					
//					
//					//METHOD TO 
//					//calculate class id TO 
//					ResultSet classidsTO = st.executeQuery("SELECT classes.id from classes where classes.classname ='"+ClassTO+"'"); 
//					while(classidsTO.next()){
//						classTOid = classidsTO.getString("id"); 
//						   }
//					
//					//String MethodTORefined= MethodTOTransformed.substring(0, MethodTOTransformed.indexOf("(")); 
//					String MethodTORefined= MethodTO;
//					String MethodTOAbbreviation = ClassTO+"."+MethodTORefined; 
//					String FullMethTO= RewriteFullMethod(MethodTOAbbreviation); 
//					if(calledmethodid==null  && classTOid!=null) {
//						MethodTO=ReplaceLnetLjava(MethodTO);
//						MethodTORefined=ReplaceLnetLjava(MethodTORefined);
//						MethodTOAbbreviation=ReplaceLnetLjava(MethodTOAbbreviation);
//						FullMethTO=ReplaceLnetLjava(FullMethTO);
//						ClassTO=ReplaceLnetLjava(ClassTO);
//						st.executeUpdate("INSERT INTO `methods`(`methodname`,  `methodnamerefined`,`methodabbreviation`,`fullmethod`, `classid`, `classname`) VALUES ('"+MethodTO +"','" +MethodTORefined+"','" +MethodTOAbbreviation+"','"+FullMethTO+"','" +classTOid+"','" +ClassTO+"')");
//
//						//RECALCULATION PHASE: CALLED METHOD ID 
//						 calledmethodsids= st.executeQuery("SELECT methods.id from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodname='"+MethodTO+"'and classes.classname='"+ClassTO+"'"); 
//						while(calledmethodsids.next()){
//							calledmethodid = calledmethodsids.getString("id"); 
//							   }
//						
//						
//						
//						//calculate class classname FROM 
//						ResultSet classnamesTO = st.executeQuery("SELECT classes.classname from classes where classes.classname ='"+ClassTO+"'"); 
//						
//						while(classnamesTO.next()){
//							ClassTOName = classnamesTO.getString("classname"); 
//							   }
//						 par= transformstring(returnTO); 
//						 //insert return value within the parameters table 
//						  ResultSet ParameterClassIDs = st.executeQuery("SELECT classes.id from classes where classes.classname='"+par+"'"); 
//							while(ParameterClassIDs.next()){
//								 ParameterClassID = ParameterClassIDs.getString("id"); 
//								   }
//						 if(par.contains("net.sourceforge.ganttproject") && ParameterClassID!=null) {//ignore the basic data types, only insert the parameters thaht have classes as data types 
//								st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+par +"','" +par +"','"+ParameterClassID+"','"+classTOid +"','"+ClassTOName+"','" +calledmethodid+"','" +MethodTO+"','" +1+"')");
//
//						 }
//						 
//						 //insert parameters that were retrieved from the log file 
//						 params = ExtractParams(MethodTO); 
//						for(String p: params) {
//							System.out.println("HERE IS A PARAM==================================================================>"+p); 
//							 ParameterClassIDs= st.executeQuery("SELECT classes.id from classes where classes.classname='"+p+"'"); 
//							while(ParameterClassIDs.next()){
//								 ParameterClassID = ParameterClassIDs.getString("id"); 
//								   }
//							
//							if(p.contains("net.sourceforge.ganttproject")&& p!=null && p.equals("")==false && classTOid!=null && ParameterClassID!=null) {
//								st.executeUpdate("INSERT INTO `parameters`(`parametername`, `parametertype`, `parameterclass`,`classid`, `classname`, `methodid`, `methodname`, `isreturn`) VALUES ('"+p +"','" +p +"','"+ParameterClassID+"','"+classTOid +"','"+ClassTOName+"','" +calledmethodid+"','" +MethodTO+"','" +0+"')");
//
//							}
//						}
//					
//					}
//				
//					
//					
//					
//					/*
//					//RECALCULATION PHASE: CALLING METHOD ID 
//					 callingmethodsrefined = st.executeQuery("SELECT methods.id from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodnamerefined='"+MethodFROMTransformed+"' and classes.classname='"+ClassFROM+"'"); 
//					while(callingmethodsrefined.next()){
//						callingmethodsrefinedid = callingmethodsrefined.getString("id"); 
//				
//					}
//					//RECALCULATION PHASE: CALLED METHOD ID 
//					 calledmethodsids= st.executeQuery("SELECT methods.id from methods INNER JOIN classes on methods.classname=classes.classname where methods.methodnamerefined='"+MethodTOTransformed+"'and classes.classname='"+ClassTO+"'"); 
//					while(calledmethodsids.next()){
//						calledmethodid = calledmethodsids.getString("id"); 
//						   }*/
//					
//					//insert into methodcallsexecuted table 
//					String fullcaller= ClassFROM+"."+MethodFROM; 
//					String fullcallee= ClassTO+"."+MethodTO; 
//					String FullMethodFROM= ClassFROM+"."+MethodFROM; 
//				    String FullMethodTO= ClassTO+"."+MethodTO; 
//				    fullcaller=RewriteFullMethod(FullMethodFROM); 
//				    fullcallee=RewriteFullMethod(FullMethodTO); 
//					String statement = "INSERT INTO `methodcallsexecuted`(`callermethodid`,  `callername`,  `callerclass`,  `fullcaller`,`calleemethodid`,  `calleename`, `calleeclass`,  `fullcallee`) VALUES ('"+callingmethodsrefinedid+"','" +MethodFROM+"','" +ClassFROM+"','"+fullcaller+"','" +calledmethodid +"','" +MethodTO+"','" +ClassTO+"','" +fullcallee +"')";		
//					st.executeUpdate(statement);
//					methodcallsexecutedlist.add(mce); 	
//					
//					
//				//insert into methodcalls table as well 
////					String statement2 = "INSERT INTO `methodcalls`(`callermethodid`,  `callername`,  `callerclass`,`calleemethodid`,  `calleename`, `calleeclass`) VALUES ('"+callingmethodsrefinedid+"','" +MethodFROM+"','" +ClassFROM+"','"+calledmethodid +"','" +MethodTO+"','" +ClassTO +"')";		
////					st.executeUpdate(statement2);
//				
//					
//					
//					
//			}
//		}
//			
//		
//		
//		
//		
//		
//		}	
//		
//	}
//} catch (IOException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
////////////
////////////
////////////
////////////
////////////
//////////////System.out.println("Contents of file:");
//////////////System.out.println(stringBuffer.toString());
////////////




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	






}
	
	
	
//	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String ReturnParams(String method) {
		// TODO Auto-generated method stub
		
		String Paramlist=method.substring(method.indexOf("("), method.indexOf(")")+1); 
		return Paramlist;
	}

	
	

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public String KeepOnlyMethodName(String constructor) {
	String params= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor=constructor.substring(0, constructor.indexOf("(")); 
	constructor=constructor.substring(constructor.lastIndexOf(".")+1, constructor.length()); 
	constructor=constructor+params; 

return constructor; 
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

public String TransformConstructorIntoInit(String constructor) {
	String params= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor= constructor.substring(0, constructor.indexOf("(")); 
	
	//String part2= constructor.substring(constructor.indexOf("("), constructor.length()); 
	constructor=constructor+".-init-"+params; 
	
	return constructor; 
}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String ParseLine(String line) {
		System.out.println(line);
		String[] linesplitted = line.split(","); 
		String method = linesplitted[1]; 
		String requirement = linesplitted[2]; 
		String gold = linesplitted[4]; 
		String subject = linesplitted[5]; 
		System.out.println("HERE IS THIS SHORT METHOD========>"+ method); 
		
		String shortmethod=method.substring(0, method.indexOf("("));
		String regex = "(.)*(\\d)(.)*";      
		Pattern pattern = Pattern.compile(regex);
		boolean containsNumber = pattern.matcher(shortmethod).matches();
		String[] firstpart;
		String FinalMethod = null;
		shortmethod=shortmethod.replaceAll("clinit", "init"); 
		if(shortmethod.contains("$") && shortmethod.matches(".*\\d+.*")) {
			 firstpart = shortmethod.split("\\$");
			String myfirstpart= firstpart[0]; 
			FinalMethod=myfirstpart; 
			if(StringUtils.isNumeric(firstpart[1])==false) {
				String[] secondpart = firstpart[1].split("\\d"); 
				System.out.println("my first part "+ myfirstpart+ "firstpart"+ firstpart[1]);
				
				String mysecondpart=secondpart[1]; 
				
				 FinalMethod=myfirstpart+mysecondpart; 
				System.out.println("FINAL RESULT:    "+FinalMethod);
			}
			
		}
		
		else if(shortmethod.contains("$") && containsNumber==false) {
			 firstpart = shortmethod.split("\\$");
			
			System.out.println("FINAL STRING:   "+firstpart[0]);
			firstpart[1]=firstpart[1].substring(firstpart[1].indexOf("."), firstpart[1].length()); 
			System.out.println("FINAL STRING:   "+firstpart[1]);
			 FinalMethod= firstpart[0]+firstpart[1]; 
			System.out.println("FINAL STRING:   "+FinalMethod);
		}
		else {
			FinalMethod=shortmethod; 
		}
		return FinalMethod; 
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public String transformstring(String s) {
		s=s.replace("/", "."); 
		s=s.replace(";", ","); 
		  int endIndex = s.lastIndexOf(",");
		    if (endIndex != -1)  
		    {
		    	s = s.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
		    }
		s=s.replace("Lde", "de"); 
		s=s.replace("Ljava", "java"); 
		return s; 
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String[] ExtractParams(String method) {
		String Paramlist=method.substring(method.indexOf("(")+1, method.indexOf(")")); 
		 String[] data = Paramlist.split(",");
		 return data; 
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

}
