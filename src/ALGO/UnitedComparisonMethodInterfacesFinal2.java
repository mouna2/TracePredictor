package ALGO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import java.util.Properties;
import java.util.Set;

import mypackage.Clazz;
import mypackage.Interface;
import mypackage.Method;
import mypackage.MethodTrace;
import mypackage.Requirement;
import mypackage.SuperClass2;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.InterfaceFactory;
import spoon.reflect.factory.MethodFactory;

public class UnitedComparisonMethodInterfacesFinal2 {
	 final static String userName = "root";

		/** The password for the MySQL account (or empty for anonymous) */
		  final static String password = "root";
	public UnitedComparisonMethodInterfacesFinal2() {

	}

	

	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	static Connection getConnection(String ProgramName) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		
		connectionProps.put("root", userName);
		connectionProps.put("123456", password);
		String connectionString="jdbc:mysql://localhost:3306/database"+ProgramName;
		conn = DriverManager.getConnection(connectionString,"root","123456");

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
	 * @param string 
	 * @param string 
	 * @throws IOException 
	 */
	public void run(Connection conn, String ProgramName, String ProgramNameLoweCase) throws IOException {
		
		
		/** The name of the MySQL account to use (or empty for anonymous) */
		 

		/** The name of the computer running MySQL */  
		
		 final String serverName = "localhost";

		/** The port of the MySQL server (default is 3306) */
		
		final int portNumber = 3306;

		/** The name of the database we are testing with (this default is installed with MySQL) */
		
		/** The name of the table we are testing with */
		 final String tableName = "classes";
		
		LinkedHashMap <String, List<MethodTrace>> ImplementationsTracesHashMap = new LinkedHashMap <String, List<MethodTrace>>(); 
		LinkedHashMap <String, List<String>> InterfacesImplementationsHashMap = new LinkedHashMap <String, List<String>>(); 
		LinkedHashMap <String, String> InterfacesTracesHashMap = new LinkedHashMap <String, String>(); 

		LinkedHashMap <String, List<MethodTrace>> SuperclassesChildrenTracesHashMap = new LinkedHashMap <String, List<MethodTrace>>(); 
		LinkedHashMap <String, String> SuperclassesTracesHashMap = new LinkedHashMap <String, String>(); 
		LinkedHashMap <String, List<SuperClass2>> SuperclassesChildrenHashMap = new LinkedHashMap <String, List<SuperClass2>>(); 
		
		

		
		ResultSet rs = null; 

		BufferedWriter bwfile5 = null ; 
		BufferedWriter bwfile2= null ; 
		if(ProgramName.equals("Chess")) {
			File fout1 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonSuperclassesChildrenChess_COUNTS.txt");
			FileOutputStream fos = new FileOutputStream(fout1);
			 bwfile2 = new BufferedWriter(new OutputStreamWriter(fos));
			File fout5 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonInterfacesImpChess_COUNTS.txt");
			FileOutputStream fos5 = new FileOutputStream(fout5);
			 bwfile5 = new BufferedWriter(new OutputStreamWriter(fos5));
		}
		if(ProgramName.equals("iTrust")) {
			File fout1 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonSuperclassesChildreniTrust_COUNTS.txt");
			FileOutputStream fos = new FileOutputStream(fout1);
			 bwfile2 = new BufferedWriter(new OutputStreamWriter(fos));
			File fout5 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonInterfacesImpiTrust_COUNTS.txt");
			FileOutputStream fos5 = new FileOutputStream(fout5);
			 bwfile5 = new BufferedWriter(new OutputStreamWriter(fos5));
		}
		if(ProgramName.equals("Gantt")) {
			File fout1 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonSuperclassesChildrenGantt_COUNTS.txt");
			FileOutputStream fos = new FileOutputStream(fout1);
			 bwfile2 = new BufferedWriter(new OutputStreamWriter(fos));
			File fout5 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonInterfacesImpGantt_COUNTS.txt");
			FileOutputStream fos5 = new FileOutputStream(fout5);
			 bwfile5 = new BufferedWriter(new OutputStreamWriter(fos5));
		}
		if(ProgramName.equals("JHotDraw")) {
			File fout1 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonSuperclassesChildrenJHotDraw_COUNTS.txt");
			FileOutputStream fos = new FileOutputStream(fout1);
			 bwfile2 = new BufferedWriter(new OutputStreamWriter(fos));
			File fout5 = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\ComparisonInterfacesImpJHotDraw_COUNTS.txt");
			FileOutputStream fos5 = new FileOutputStream(fout5);
			 bwfile5 = new BufferedWriter(new OutputStreamWriter(fos5));
		}
		
		
		
		
		
		
		

		// Create a table
		try {
			List<String> implementationList = new ArrayList<String>(); 
			List<String> superclassList = new ArrayList<String>(); 
			List<String> interfacelist=new ArrayList<String>(); 

			Statement st= conn.createStatement();
			String requirement=""; 
			String requirementid=""; 
			String methodname=""; 
			String methodid=""; 
			String fullmethod=""; 
			String classname=""; 
			String classid=""; 
			String goldfinal=""; 
			String interfaceclassid=""; 
			String interfacename=""; 
			String implementationclassid=""; 
			String implementationclassname=""; 
			String superclassid=""; 
			String superclassname=""; 
			String childclassid=""; 
			String childclassname=""; 
			String rowID=""; 
			LinkedHashMap <String, List<String>> ImplementationInterfaceTraceHashMap = new LinkedHashMap <String, List<String>>(); 

			ResultSet res2=st.executeQuery("SELECT * "+
					"			FROM "+ProgramNameLoweCase+".interfaces" ); 
			while(res2.next()) {
				
				interfaceclassid=res2.getString("interfaceclassid"); 
				interfacename=res2.getString("interfacename"); 
				implementationclassid=res2.getString("ownerclassid"); 
				implementationclassname=res2.getString("classname"); 
				
				
				
				
				
//				System.out.println("INTERFACE CLASS ID    "+ interfaceclassid);
				if(InterfacesImplementationsHashMap.get(interfaceclassid)!=null) {
					implementationList= InterfacesImplementationsHashMap.get(interfaceclassid); 
					implementationList.add(implementationclassid); 
					InterfacesImplementationsHashMap.put(interfaceclassid, implementationList); 
				}else {
					implementationList = new ArrayList<String>(); 
					implementationList.add(implementationclassid); 
					InterfacesImplementationsHashMap.put(interfaceclassid, implementationList); 
				}
				
				
				
				
				if(ImplementationInterfaceTraceHashMap.get(implementationclassid)!=null) {
					interfacelist= ImplementationInterfaceTraceHashMap.get(implementationclassid); 
					interfacelist.add(interfaceclassid); 
					ImplementationInterfaceTraceHashMap.put(implementationclassid, interfacelist); 
				}else {
					interfacelist = new ArrayList<String>(); 
					interfacelist.add(interfaceclassid); 
					ImplementationInterfaceTraceHashMap.put(implementationclassid, interfacelist); 
				}
				
				//implementationList.add(myinter); 
				
			}
			
			
			
			
			
			
			System.out.println("DONE");
			
			

			
			
			
			
			
			
			
			LinkedHashMap <String, String> InterfaceTraceHashmap = new LinkedHashMap <String, String>(); 
			LinkedHashMap <String, String> ImplementationTraceHashmap = new LinkedHashMap <String, String>(); 

			
			ResultSet res=st.executeQuery("SELECT *" + 
					"			FROM "+ProgramNameLoweCase+".traces" ); 
			while(res.next()) {
				
				requirementid=res.getString("requirementid"); 
				classid=res.getString("classid"); 
				classname=res.getString("classname"); 
				
				goldfinal=res.getString("goldfinal"); 
				methodname=res.getString("methodname"); 

				
				if(InterfacesImplementationsHashMap.get(classid)!=null) {
					InterfaceTraceHashmap.put(requirementid+"="+methodname+"="+classid, goldfinal); 
				}
				
				if(ImplementationInterfaceTraceHashMap.get(classid)!=null) {
					ImplementationTraceHashmap.put(requirementid+"="+methodname+"="+classid, goldfinal); 
				}
				
				
				
				
			
				
			
	

			}
			
			System.out.println("DONE 2");

			HashMap<String, List<String>> FinalList= new HashMap<String, List<String>>(); 

			for(String key: InterfaceTraceHashmap.keySet()) {
				String [] SplittedKeys=key.split("="); 
				String ReqID=SplittedKeys[0];
				String InterfaceMethodName=SplittedKeys[1]; 
				String interfaceID= SplittedKeys[2]; 	

//				System.out.println("************************INTERFACE******************************************");

				bwfile2.write(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"---- "+InterfaceTraceHashmap.get(key));
				
//				System.out.println(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"----"+InterfaceTraceHashmap.get(key));
//				System.out.println("*************************IMPLEMENTATIONS*****************************************");

				bwfile2.newLine();
				List<String> implist = InterfacesImplementationsHashMap.get(interfaceID); 
				List<String> mynewlist = new ArrayList<String>(); 
				FinalList.put(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"----"+InterfaceTraceHashmap.get(key), mynewlist); 
				
			}
			System.out.println("DONE 3");

			
			
			for(String key: InterfaceTraceHashmap.keySet()) {
						String [] SplittedKeys=key.split("="); 
						String ReqID=SplittedKeys[0];
						String InterfaceMethodName=SplittedKeys[1]; 
						String interfaceID= SplittedKeys[2]; 	

//						System.out.println("************************INTERFACE******************************************");

						bwfile2.write(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"---- "+InterfaceTraceHashmap.get(key));
						
//						System.out.println(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"----"+InterfaceTraceHashmap.get(key));
//						System.out.println("*************************IMPLEMENTATIONS*****************************************");

						bwfile2.newLine();
						List<String> implist = InterfacesImplementationsHashMap.get(interfaceID); 
						
						for(String imp: implist) {
							for(String key2: ImplementationTraceHashmap.keySet()) {
								String [] SplittedKeys2=key2.split("="); 
								String implementationID= SplittedKeys2[2]; 	
								String ImplementationMethodName=SplittedKeys2[1]; 
								String ReqIDImp=SplittedKeys2[0];
								
								
									if(imp.equals(implementationID) && ImplementationMethodName.equals(InterfaceMethodName)
											&& ReqIDImp.equals(ReqID)) {
										
										bwfile2.write(implementationID+"----"+ImplementationMethodName+"----"+ReqID+"---- "+ImplementationTraceHashmap.get(key2));
//										System.out.println(implementationID+"----"+ImplementationMethodName+"----"+ReqID+"---- "+ImplementationTraceHashmap.get(key2));
										List<String> myimplsit = FinalList.get(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"----"+InterfaceTraceHashmap.get(key)); 
										myimplsit.add(implementationID+"----"+ImplementationMethodName+"----"+ReqID+"---- "+ImplementationTraceHashmap.get(key2)); 
										FinalList.put(interfaceID+"----"+InterfaceMethodName+"----"+ReqID+"----"+InterfaceTraceHashmap.get(key), myimplsit);
										
										bwfile2.newLine();
										
										
										
										
										
										
										
									}
								
								
								
								
							}
						}
						
						
						
						
			}
			System.out.println("DONE 4");

			
			
			
			
			for(String s: FinalList.keySet()) {
				System.out.println("--------------------INTER-------------------");

				System.out.println(s);
				System.out.println("--------------------IMPS-------------------");
				for(String imp: FinalList.get(s)) {
					System.out.println(imp);
				}
			}
			
			
			
			
			
			
			
			
			
			
			

			
			
			
			System.out.println("finished");
			
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		bwfile2.close();

	}
	
	/**
	 * Connect to the DB and do some stuff
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		UnitedComparisonMethodInterfacesFinal2 app = new UnitedComparisonMethodInterfacesFinal2();
		
		// Connect to MySQL
				Connection conn = null;
				try {
					conn = getConnection("chess");
					System.out.println("Connected to database");
					
				} catch (SQLException e) {
					System.out.println("ERROR: Could not connect to the database");
					e.printStackTrace();
					return;
				}
		app.run(conn, "Chess", "databasechess");
		Spoon(); 
		
		conn.close();
		
		
		
		
//		 conn = null;
//		try {
//			conn = getConnection("gantt");
//			System.out.println("Connected to database");
//			
//		} catch (SQLException e) {
//			System.out.println("ERROR: Could not connect to the database");
//			e.printStackTrace();
//			return;
//		}
//		app.run(conn, "Gantt", "databasegantt");
//		Spoon(); 
//		conn.close();
//		
//		
//		 conn = null;
//			try {
//				conn = getConnection("itrust");
//				System.out.println("Connected to database");
//				
//			} catch (SQLException e) {
//				System.out.println("ERROR: Could not connect to the database");
//				e.printStackTrace();
//				return;
//			}
//			app.run(conn, "iTrust", "databaseitrust");
//			Spoon(); 
//			conn.close();
			
//			conn = null;
//			try {
//				conn = getConnection("jhotdraw");
//				System.out.println("Connected to database");
//				
//			} catch (SQLException e) {
//				System.out.println("ERROR: Could not connect to the database");
//				e.printStackTrace();
//				return;
//			}
//			app.run(conn, "JHotDraw", "databasejhotdraw");
//			Spoon(); 
//			conn.close();
	}
	
	

	static void Spoon() throws SQLException, IOException {
		
		
		
		
		
		
	}

	
}
