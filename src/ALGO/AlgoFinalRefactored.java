package ALGO;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.table.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.maven.model.Model;


import Chess.LogInfo;
import Chess.PredictionEvaluation;

import mypackage.Children2;
import mypackage.ClassField2;
import mypackage.ClassTrace2;
import mypackage.ColumnGroup;
import mypackage.Method;
import mypackage.MethodTrace;


public class AlgoFinalRefactored extends JFrame {

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException
	 *             If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(command); // This will throw a SQLException if it fails
			return true;
		} finally {

			// This will run whether we throw an exception or not
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	ResultSet rs = null;
	// Connect to MySQL

	PredictionEvaluation TotalPattern = new PredictionEvaluation();
	PredictionEvaluation RemainingPattern = new PredictionEvaluation();
	PredictionEvaluation NonOwnerClassPattern = new PredictionEvaluation();

	
	
	
	
	static List<MethodTrace> methodtraces = new ArrayList<MethodTrace>();
	HashMap<String, List<String>> classMethodsHashMap = new HashMap<String, List<String>>();
	public static HashMap<String, MethodTrace> methodtraces2HashMap = new HashMap<String, MethodTrace>();
	LinkedHashMap<String, ClassTrace2> methodtracesRequirementClass = new LinkedHashMap<String, ClassTrace2>();


	
	
	
	
	
	
	JTable table = new JTable();
	// File fout = new
	// File("C:\\Users\\mouna\\new_workspace\\SpoonProcessorFinal\\TableLog.txt");

	public final String userName = "root";
	public final String password = "123456";
	List<Method> CallerMethodListFinal = new ArrayList<Method>();
	List<Method> CalleeMethodListFinal = new ArrayList<Method>();

	public List<Method> getCallerMethodListFinal() {
		return CallerMethodListFinal;
	}

	public void setCallerMethodListFinal(List<Method> callerMethodListFinal) {
		CallerMethodListFinal = callerMethodListFinal;
	}

	public List<Method> getCalleeMethodListFinal() {
		return CalleeMethodListFinal;
	}

	public void setCalleeMethodListFinal(List<Method> calleeMethodListFinal) {
		CalleeMethodListFinal = calleeMethodListFinal;
	}

	public AlgoFinalRefactored(String ProgramName) throws Exception {

	
//		List<MethodTrace> methodtracesNew = InitializePredictionsHashMap2(methodtraces2);
		TracePredictionFunction( ProgramName);

	}

	

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/**
	 * @throws Exception **********************************************************************************************************************************************/

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void TracePredictionFunction( String ProgramName) throws Exception {
		// TODO Auto-generated method stub
		BufferedWriter bwfile1 =null; 
		BufferedWriter bwfile2 = null ; 
		BufferedWriter bwfile3 = null ; 

	if(ProgramName.equals("chess")) {
		File file1log = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\InterfacesNewRule.txt");
		FileOutputStream fosfila1 = new FileOutputStream(file1log);
		 bwfile1 = new BufferedWriter(new OutputStreamWriter(fosfila1));
		
		File file2log = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\LogFileUnderstandFP.txt");
		FileOutputStream fosfila2 = new FileOutputStream(file2log);
		 bwfile2 = new BufferedWriter(new OutputStreamWriter(fosfila2));
		 
			File file3log = new File("C:\\Users\\mouna\\ownCloud\\Share\\dumps\\LatestLogFiles\\LogFileUnderstandFN.txt");
			FileOutputStream fosfila3 = new FileOutputStream(file3log);
			 bwfile3 = new BufferedWriter(new OutputStreamWriter(fosfila3));
	}
		// TODO Auto-generated method stub
		LogInfo.CreateLogFiles(ProgramName);

		
		

	
		
		DatabaseReading DatabaseReading= new DatabaseReading(ProgramName); 
		
		methodtraces2HashMap = DatabaseReading.getMethodtracehashmap();
		methodtraces = DatabaseReading.getMethodtracesList(); 

		
		

		LinkedHashMap<String, LogInfo> LogInfoHashMap = new LinkedHashMap<String, LogInfo>();
		

		Collection<MethodTrace> MethodTracesHashmapValues = methodtraces2HashMap.values();


		
		
		PredictionValues TotalPredictionValues = new PredictionValues(); 
		PredictionValues RemainingpredictionValues = new PredictionValues(); 
		PredictionValues PredictionClassTraceBefore = new PredictionValues(); 
		PredictionValues PredictionClassTraceAfter = new PredictionValues(); 
		 PredictionValues OwnerClassPredictionValues = new PredictionValues(); 

		CountTracesClassesValues(PredictionClassTraceBefore, methodtraces2HashMap);
		LogInfo.InitializeLogInfoHashMap(LogInfoHashMap,MethodTracesHashmapValues, methodtraces2HashMap ); 

		LogInfo.bwTraceClass.write("BEFORE PATTERN 0 "+PredictionClassTraceBefore.toString());
		LogInfo.bwTraceClass.newLine();

		 
//		GenerateNewValuesInTracesClasses(methodtracesRequirementClass);
			// TODO Auto-generated method stub
			
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		// PATTERN 0
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//
//
//				
//				
//				MethodList children = methodtrace.Method.Children; 
//				MethodList parents = methodtrace.Method.Superclasses; 
//				MethodList implementations = methodtrace.Method.Implementations; 
//				MethodList interfaces = methodtrace.Method.Interfaces; 
//				ClassTraceList ChilrenGold= new ClassTraceList(); 
//				ClassTraceList ParentsGold= new ClassTraceList(); 
//				ClassTraceList ImplementationsGold= new ClassTraceList(); 
//				ClassTraceList InterfacesGold= new ClassTraceList(); 
//				String reqClass= methodtrace.Requirement.ID+"-"+methodtrace.Method.Owner.ID; 
//				for(Method child:children) {
//					String ChildGold = child.Owner.DeveloperGold; 
//					ChilrenGold.add(ChildGold); 
//				}
//				
//				
//				for(Method parent:parents) {
//					String ParentGold = parent.Owner.DeveloperGold; 
//					ParentsGold.add(ParentGold); 
//				}
//				for(Method myinterface:interfaces) {
//					String InterfaceGold = myinterface.Owner.DeveloperGold; 
//					InterfacesGold.add(InterfaceGold); 
//
//				}
//				for(Method implementation:implementations) {
//					String ImplementationGold = implementation.Owner.DeveloperGold; 
//					ImplementationsGold.add(ImplementationGold); 
//				}
//				
//				
//				
//				if(methodtrace.Method.Owner.DeveloperGold.equals("E") ) {
//					if(ImplementationsGold.AllTs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "T"); 
////						methodtrace.Method.Owner.DeveloperGold="T";
//					}else if(InterfacesGold.AllTs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "T"); 
//
////						methodtrace.Method.Owner.DeveloperGold="T";
//
//					}else if(ParentsGold.AllTs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "T"); 
//
////						methodtrace.Method.Owner.DeveloperGold="T";
//
//					}else if(ChilrenGold.AllTs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "T"); 
//
////						methodtrace.Method.Owner.DeveloperGold="T";
//
//					}
//					
//					
//					 if(ImplementationsGold.AllNs()) {
//							DatabaseInput.OwnerTraceHashMap.put(reqClass, "N"); 
//
////						methodtrace.Method.Owner.DeveloperGold="N";
//					}else if(InterfacesGold.AllNs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "N"); 
//
////						methodtrace.Method.Owner.DeveloperGold="N";
//
//					}else if(ParentsGold.AllNs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "N"); 
//
////						methodtrace.Method.Owner.DeveloperGold="N";
//
//					}else if(ChilrenGold.AllNs()) {
//						DatabaseInput.OwnerTraceHashMap.put(reqClass, "N"); 
//
////						methodtrace.Method.Owner.DeveloperGold="N";
//
//					}
//				}
//				
//			}
		
		CountTracesClassesValues(PredictionClassTraceAfter, methodtraces2HashMap);

		LogInfo.bwTraceClass.write("AFTER PATTERN 0 "+PredictionClassTraceAfter.toString());
		LogInfo.bwTraceClass.close();
	
		LogInfoHashMap=LogInfo.InitializeLogInfoHashMapTraceClassNewValue(LogInfoHashMap,MethodTracesHashmapValues, methodtraces2HashMap ); 

	
		MethodTracesHashmapValues = methodtraces2HashMap.values();
		LogInfoHashMap=InitializeHashMapWithPrecisionRecall(MethodTracesHashmapValues, LogInfoHashMap);
	
		

	
		
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//STEP 1
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// OWNER CLASS PATTERN
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
	int counter=0;
		for (MethodTrace methodtrace : MethodTracesHashmapValues) {

//			if *methodtrace.method.owner.getGold*()=N methodtrace.Method.setPrediction N
					
					
					
					String reqClass= methodtrace.Requirement.ID+"-"+methodtrace.Method.Owner.ID; 
					
		
//			if (methodtrace.Method.Owner.DeveloperGold != null) {
				
					if (DatabaseInput.OwnerTraceHashMap.get(reqClass) != null) {
				
//				if (methodtrace.Method.Owner.DeveloperGold.equals("T") )
					if (DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") )
				{
					methodtrace.SetPrediction(LogInfoHashMap, "E","E,Owner");

					//DO NOTHING 
					
				

				} 
//				else	if (methodtrace.Method.Owner.DeveloperGold.equals("N") )
					
					else if (DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("N") ) 
					{
					methodtrace.SetPrediction(LogInfoHashMap, "N","N,Owner");
					
					
				}

				else {
					methodtrace.SetPrediction(LogInfoHashMap, "E","E,Owner");

					//DO NOTHING 
				}
			

		
			
			
		}
		}
		System.out.println(counter);
		SetSubjectGoldDeveloperGoldEqualityFlag(methodtraces2HashMap, TotalPattern, LogInfoHashMap, ProgramName); 
			

			LogInfo.ComputePrecisionAndRecall(methodtraces2HashMap,TotalPattern, ProgramName, OwnerClassPredictionValues, LogInfoHashMap);
			
			LogInfo.updateResultsLog(TotalPattern, OwnerClassPredictionValues, ProgramName, "OWNER CLASS PRED", "owner class prediction values");
		
		
		

		int ITERATION = 0;
	//	MethodTracesHashmapValues=methodtraces2HashMap.values(); 

		
		
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//STEP 2
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
//		 LogHashMapRemaining = new LinkedHashMap<String, LogInfo>();
//		LogHashMapRemaining=InitializeHashMapWithPrecisionRecallRemaining(MethodTracesHashmapValues, LogHashMapRemaining, LogInfoHashMap); 
		MethodTracesHashmapValues=methodtraces2HashMap.values(); 

		while (MethodTrace.modified) {
			MethodTrace.modified = false;
			////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// PURE AND MIXED PATTERNS	PATTERN 1-2
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////

			//////////////////////////////////////////////////////////////////////////////////////////
		

			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// PURE PATTERNS
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//
//					
//					
////				System.out.println("PATTERN 1");
//
//				// PURE T PATTERN
//
////				if (    !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
////						&& methodtrace.Method.getCallees(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap)
////						&& methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap)					
////						
////
////				) {
////					methodtrace.SetPrediction(LogInfoHashMap, "T", "T,PureT");
////
////				
////
////				}
////				// PURE N PATTERN
////				else 
//					if ( !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
//						&& methodtrace.Method.getCallees(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap)
//						&& methodtrace.Method.getCallers(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap)
//
//				) {
//					methodtrace.SetPrediction(LogInfoHashMap,"N", "N,PureN");
//				
//
//				}
//
//				 
//				// PURE T LEAF PATTERN
////				 if (	methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
////						 && methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement,methodtraces2HashMap)
////						
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,PureTLeaf");
////
////				}
////				 
////				 
////				// PURE N LEAF PATTERN
////				 
////				 else 
//					 
//					 if (methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
//						 && methodtrace.Method.getCallers(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap)
////						 && methodtrace.Method.getCallers(methodtrace.Requirement)ofCallers.AllNs(methodtrace.Requirement, methodtraces2HashMap)
//
//				) {
//
//						methodtrace.SetPrediction(LogInfoHashMap,"N", "N,PureNLeaf");
//
//				}
//
//			
//				 if(((! methodtrace.Method.Children.isEmpty() || !methodtrace.Method.Implementations.isEmpty() 
//							|| !methodtrace.Method.Interfaces.isEmpty() || !methodtrace.Method.Superclasses.isEmpty() )
//							&& (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() || !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty())) && ProgramName.equals("chess")) {
//					 if(! methodtrace.Method.Children.isEmpty() ) {
//							bwfile1.write("CHILDREN PURE");
//							bwfile1.newLine();
//					 }else if(!methodtrace.Method.Implementations.isEmpty()) {
//						 bwfile1.write("IMPLEMENTATION PURE");
//							bwfile1.newLine();
//					 }
//					 else if(!methodtrace.Method.Interfaces.isEmpty()) {
//						 bwfile1.write("INTERFACE PURE");
//							bwfile1.newLine();
//					 }
//					 else if(!methodtrace.Method.Superclasses.isEmpty()) {
//						 bwfile1.write("SUPERCLASS PURE");
//							bwfile1.newLine();
//					 }
//						bwfile1.write("ITERATION "+ITERATION+"-------------methodtrace gold "+methodtrace.gold+"-------------"+methodtrace+"--------------");
//						bwfile1.newLine();
//
//						for(Method caller: methodtrace.Method.getCallers(methodtrace.Requirement)) {
//							bwfile1.write("PREDICTION "+methodtrace.prediction+" PURE PATTERN ---CALLERS  trace "+"     "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+caller.ID)); 
//							bwfile1.newLine();
//						}
//						for(Method caller: methodtrace.Method.getCallees(methodtrace.Requirement)) {
//							bwfile1.write("PREDICTION "+methodtrace.prediction+" PURE PATTERN ---CALLEES   trace"+ "    "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+caller.ID)); 
//							bwfile1.newLine();
//							
//						}
//						
//						bwfile1.write("Prediction "+methodtrace.prediction);
//						bwfile1.newLine();
//					}
//
//			}
//
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// MIXED PATTERNS
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				
//				
//				// MIXED T PATTERN
////				 if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
////					&& methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) 
////					&& methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) 
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,MixedT");
////
////				}
////				 
////				// MIXED N PATTERN
////				 else 
//					 if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
//						
//						&&( (methodtrace.Method.getCallees(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap) && methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap))
//						|| (methodtrace.Method.getCallers(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap) && methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)))
//						
//
//				) {
//					methodtrace.SetPrediction(LogInfoHashMap,"N", "N,MixedN");
//
//
//				}
//				
//
//				
//				 
//				// MIXED T LEAF PATTERN
////				 if (methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
////						 && methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) 
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,MixedTLeaf");
////
////
////				}
//				 //COMMENTED OUT BECAUSE THIS CASE NEVER HAPPENS 
//				 // MIXED N LEAF PATTERN
//				
////				 else if(methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
////						 && methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap) 
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"N", "N,MixedNLeaf");
////
////
////				}
//				 
//				 if(((! methodtrace.Method.Children.isEmpty() || !methodtrace.Method.Implementations.isEmpty() 
//							|| !methodtrace.Method.Interfaces.isEmpty() || !methodtrace.Method.Superclasses.isEmpty() )
//							&& (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty())) && ProgramName.equals("chess")) {
//					 if(! methodtrace.Method.Children.isEmpty() ) {
//							bwfile1.write("CHILDREN MIXED ");
//							bwfile1.newLine();
//					 }else if(!methodtrace.Method.Implementations.isEmpty()) {
//						 bwfile1.write("IMPLEMENTATION MIXED");
//							bwfile1.newLine();
//					 }
//					 else if(!methodtrace.Method.Interfaces.isEmpty()) {
//						 bwfile1.write("INTERFACE MIXED");
//							bwfile1.newLine();
//					 }
//					 else if(!methodtrace.Method.Superclasses.isEmpty()) {
//						 bwfile1.write("SUPERCLASS MIXED");
//							bwfile1.newLine();
//					 }		
//					 bwfile1.write("ITERATION "+ITERATION+"-------------methodtrace gold "+methodtrace.gold+"-------------"+methodtrace+"--------------");
//						bwfile1.newLine();
//
//						for(Method caller: methodtrace.Method.getCallers(methodtrace.Requirement)) {
//							bwfile1.write("PREDICTION "+methodtrace.prediction+" MIXED PATTERN --- CALLER trace  "+"   "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+caller.ID)); 
//							bwfile1.newLine();
//						}
//						for(Method caller: methodtrace.Method.getCallees(methodtrace.Requirement)) {
//							bwfile1.write("PREDICTION "+methodtrace.prediction+" MIXED PATTERN --- CALLEE trace  "+"   "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+caller.ID)); 
//							bwfile1.newLine();
//						}
//						bwfile1.write("Prediction "+methodtrace.prediction);
//						bwfile1.newLine();
//					}
//			}
			
			// PRINT

			
			
			
			
			
			
			
			
			
			
			
			
			
			
		

			
			// END PRINT
			
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// SUPERCLASSES , CHILDREN , INTERFACES , IMPLEMENTATIONS  PATTERN 3
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			
			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				String reqMethod= methodtrace.Requirement.ID+"-"+methodtrace.Method.ID; 
//				System.out.println(reqMethod); 
//				System.out.println("PATTERN 3");

//					if (
//							methodtrace.Method.Interfaces.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Implementations.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Superclasses.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Children.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							
//							)
//							
//						
//
//					{
//
//						
//						
//						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTInheritance");
//
//
//					}
//					else 
						if (
							
							methodtrace.Method.Interfaces.AllNs(methodtrace.Requirement,methodtraces2HashMap)
							||methodtrace.Method.Implementations.AllNs(methodtrace.Requirement,methodtraces2HashMap)
							||methodtrace.Method.Superclasses.AllNs(methodtrace.Requirement,methodtraces2HashMap)
							||methodtrace.Method.Children.AllNs(methodtrace.Requirement,methodtraces2HashMap)

					)

					{
						
						
						
						String reqClass= methodtrace.Requirement.ID+"-"+methodtrace.Method.Owner.ID; 
//					//	 DEBUGGING PURPOSES 
//						 if(methodtrace.getGold().equals("T") && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals(DatabaseInput.SubjectTraceHashMap.get(reqClass))&& methodtrace.getPrediction().trim().equals("E")) {
//								System.out.println("fullmethodname   "+methodtrace.Method.fullmethodname+" Requirement  "+methodtrace.Requirement.ID+" DeveloperGold "+methodtrace.getGold()+" Prediction "+methodtrace.getPrediction()+"   gold final  "+methodtrace.getGold());
//								if(methodtrace.Method.Interfaces.AllNs(methodtrace.Requirement,methodtraces2HashMap)){
//									System.out.println("interfaces");
//									System.out.println(methodtrace.Method.Interfaces);
//									System.out.println();
//								}
//								if(methodtrace.Method.Implementations.AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("implementations");
//									System.out.println(methodtrace.Method.Implementations);
//									System.out.println();
//
//								}
//								if(methodtrace.Method.getSuperclasses().AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("superclasses");
//									System.out.println(methodtrace.Method.getSuperclasses());
//									System.out.println();
//
//
//								}
//								if(methodtrace.Method.getChildren().AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("children");
//									System.out.println(methodtrace.Method.getChildren());
//									System.out.println();
//									
//									
//								}
//							}
//						 else 
//							 
//							 if(methodtrace.getGold().equals("T") && methodtrace.getPrediction().trim().equals("E")) {
//							 System.out.println("fullmethodname   "+methodtrace.Method.methodname+"classname   "+methodtrace.Method.Owner.ID+" Requirement  "+methodtrace.Requirement.ID+" DeveloperGold "+methodtrace.getGold()+" Prediction "+methodtrace.getPrediction()+"   gold final  "+methodtrace.getGold());
//								if(methodtrace.Method.Interfaces.AllNs(methodtrace.Requirement,methodtraces2HashMap)){
//									System.out.println("interfaces");
//									System.out.println(methodtrace.Method.Interfaces);
//									System.out.println();
//								}
//								if(methodtrace.Method.Implementations.AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("implementations");
//									System.out.println(methodtrace.Method.Implementations);
//									System.out.println();
//
//								}
//								if(methodtrace.Method.getSuperclasses().AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("superclasses");
//									System.out.println(methodtrace.Method.getSuperclasses());
//									System.out.println();
//
//
//								}
//								if(methodtrace.Method.getChildren().AllNs(methodtrace.Requirement,methodtraces2HashMap)) {
//									System.out.println("CHILDRENN");
//									System.out.println(methodtrace.Method.getChildren());
//									System.out.println();
//
//								}
//						 }
						 
						 
						 methodtrace.SetPrediction(LogInfoHashMap,"N", "N,AllNInheritance");
					
					}

			}
	
			
			
			
			
			
			
			
			

			
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
				

					
				
			
	

				
			
			

		
		
				

		
				
				

			
		
			
			
				
				
			
				

				
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// ALL CALLERS PATTERN 4
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
				

			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
				//System.out.println();
				

			
			
				
				
				// ALL T CALLERS 
//				 if (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
//					&& methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap) 
//					
//
//				) {
//						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTCallers");
//						
//				}
//				// ALL N CALLERS
//				 else 
					 if (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
							&& methodtrace.Method.getCallers(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap) 
							

						) {

						methodtrace.SetPrediction(LogInfoHashMap,"N", "N,AllNCallers");



				}
				

				
				 
					if((!methodtrace.Method.Interfaces.isEmpty() || 
							!methodtrace.Method.Superclasses.isEmpty()) && (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() )
							&& ProgramName.equals("chess")) {
						 if(! methodtrace.Method.Children.isEmpty() ) {
								bwfile1.write("CHILDREN all callers");
								bwfile1.newLine();
						 }else if(!methodtrace.Method.Implementations.isEmpty()) {
							 bwfile1.write("IMPLEMENTATION all callers");
								bwfile1.newLine();
						 }
						 else if(!methodtrace.Method.Interfaces.isEmpty()) {
							 bwfile1.write("INTERFACE all callers");
								bwfile1.newLine();
						 }
						 else if(!methodtrace.Method.Superclasses.isEmpty()) {
							 bwfile1.write("SUPERCLASS all callers");
								bwfile1.newLine();
						 }
						bwfile1.write("ITERATION "+ITERATION+"-------------"+methodtrace.gold+"-------------"+methodtrace+"--------------");
						bwfile1.newLine();

						for(Method caller: methodtrace.Method.getCallers(methodtrace.Requirement)) {
							bwfile1.write("PREDICTION "+methodtrace.prediction+" ALL CALLERS PATTERN --- CALLER trace  "+"   "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+caller.ID)); 
							bwfile1.newLine();
						}
					
						bwfile1.write("Prediction "+methodtrace.prediction);
						bwfile1.newLine();
					}
				
			}
				
			
			
			
			
				//////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////////////////
				//ALL CALLEES PATTERN 5
				//////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////////////////

			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
				
//				System.out.println("PATTERN 5");

				
				

			
				
				
				
				
				//ALL T CALLEES 
//				if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() 
//				&& methodtrace.Method.getCallees(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap) 
//				
//				
//				) {
//					methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTCallees");
//
//				
//				
//				}
//				//ALL N CALLEES
//				else 
					if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() 
				&& methodtrace.Method.getCallees(methodtrace.Requirement).AllNs(methodtrace.Requirement, methodtraces2HashMap) 
				
				
				) {
				
					methodtrace.SetPrediction(LogInfoHashMap,"N", "N,AllNCallees");

				
				
				}
				if((! methodtrace.Method.Children.isEmpty() || !methodtrace.Method.Implementations.isEmpty() )
						
						&& ( !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty()) && ProgramName.equals("chess")) {
					 if(! methodtrace.Method.Children.isEmpty() ) {
							bwfile1.write("CHILDREN all callees");
							bwfile1.newLine();
					 }else if(!methodtrace.Method.Implementations.isEmpty()) {
						 bwfile1.write("IMPLEMENTATION all callees");
							bwfile1.newLine();
					 }
					 else if(!methodtrace.Method.Interfaces.isEmpty()) {
						 bwfile1.write("INTERFACE all callees");
							bwfile1.newLine();
					 }
					 else if(!methodtrace.Method.Superclasses.isEmpty()) {
						 bwfile1.write("SUPERCLASS all callees");
							bwfile1.newLine();
					 }		
					bwfile1.write("ITERATION "+ITERATION+"-------------methodtrace gold "+methodtrace.gold+"-------------"+methodtrace+"--------------");
					bwfile1.newLine();

					for(Method callee: methodtrace.Method.getCallees(methodtrace.Requirement)) {
						bwfile1.write("PREDICTION "+methodtrace.prediction+" ALL CALLEES PATTERN --- CALLEE trace  "+"   "+methodtraces2HashMap.get(methodtrace.Requirement.ID+"-"+callee.ID)); 
						bwfile1.newLine();
					}
					
					bwfile1.write("Prediction "+methodtrace.prediction);
					bwfile1.newLine();
				}
				
				}	
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			// END PATTERNS 
			//////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			
			
			
			

			
			
			// PRINT
			SetLogFileIterations(MethodTracesHashmapValues, methodtraces, LogInfoHashMap, ITERATION); 
			

			// END PRINT
			ITERATION++;
			
		}
		
		System.out.println("ITERATION  "+ITERATION);
		System.out.println("ITERATION  "+ITERATION);

		
		//SET EVERYTHING ELSE TO T IF THE OWNER CLASS IS T 
//		for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//			List<String> OuterOwnerCallers= new ArrayList<String>(); 
//			List<String> OuterOwnerCallees= new ArrayList<String>(); 
//			
//			MethodList callers = methodtrace.Method.getCallers(methodtrace.Requirement); 
//			MethodList callees = methodtrace.Method.getCallees(methodtrace.Requirement); 
//			
//			for(Method caller: callers) {
//				String reqClass= methodtrace.Requirement.ID+"-"+caller.Owner.ID; 
//				if(!methodtrace.Method.Owner.ID.equals(caller.Owner.ID)) {
//					OuterOwnerCallers.add(DatabaseInput.OwnerTraceHashMap.get(reqClass)); 
//
//				}
//			}
//			
//			for(Method callee: callees) {
//				String reqClass= methodtrace.Requirement.ID+"-"+callee.Owner.ID; 
//				if(!methodtrace.Method.Owner.ID.equals(callee.Owner.ID)) {
//					OuterOwnerCallees.add(DatabaseInput.OwnerTraceHashMap.get(reqClass)); 
//					
//				}
//			}
//			String reqMethod = methodtrace.Requirement.ID + "-" + methodtrace.Method.ID;
//			String reqClass = methodtrace.Requirement.ID + "-" + methodtrace.Method.Owner.ID;
//
////			if( methodtrace.prediction.equals("E") && methodtrace.Method.Owner.DeveloperGold.equals("T") ) 
//			
//		if( methodtrace.prediction.equals("E") && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
////				&& (LogInfoHashMap.get(reqMethod).getOuterCallersPredictions().contains("T")
////				|| LogInfoHashMap.get(reqMethod).getOuterCalleesPredictions().contains("T"))
//				&& (OuterOwnerCallers.contains("T")
//				&& OuterOwnerCallees.contains("T"))
////				&& (!methodtrace.Method.Callers.AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)) 
////				||	 !methodtrace.Method.Callees.AtLeast1N(methodtrace.Requirement, methodtraces2HashMap) 
//				)
//			{
//				LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
//				methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining");
//		
//		LogInfoHashMap.put(reqMethod, loginfo); 
//			}
//
//
//			
//			
//		}
		

		
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		// STEP 3
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////
		//SET EVERYTHING ELSE TO T IF THE OWNER CLASS IS T 
				for (MethodTrace methodtrace : MethodTracesHashmapValues) {

					
					String reqMethod = methodtrace.Requirement.ID + "-" + methodtrace.Method.ID;
					String reqClass = methodtrace.Requirement.ID + "-" + methodtrace.Method.Owner.ID;
			
//					//PURE T 
//					if( methodtrace.prediction.equals("E") && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//
//							&& methodtrace.Method.getOuterCallers(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AllTs(methodtrace.Requirement)
//							&& methodtrace.Method.getOuterCallees(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AllTs(methodtrace.Requirement)
//							
//							&& !methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
//							&& !methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
//							
////							&& methodtrace.Method.Owner.methods.size()<30
//							
////							&& !methodtrace.Method.Owner.ID.equals("24")
//							)
//						{
//							LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
//							methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/PureT");
//					
//					LogInfoHashMap.put(reqMethod, loginfo); 
//						}
//					
//					
//					//PURE T LEAF 
//					 if( methodtrace.prediction.equals("E") && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//
//							&& methodtrace.Method.getOuterCallers(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AllTs(methodtrace.Requirement)
//							&& methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty()
//							
//							&& !methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
//							
////							&& methodtrace.Method.Owner.methods.size()<30
//
////							&& !methodtrace.Method.Owner.ID.equals("24")
//
//							)
//						{
//							LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
//							methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/PureTLeaf");
//					
//					LogInfoHashMap.put(reqMethod, loginfo); 
//						}
					
					
					
					
					
					
					
					
					
					
					
					//MIXED T 
					 if( methodtrace.prediction.equals("E") 
							

						&& methodtrace.Method.getOuterCallers(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement)
						&& methodtrace.Method.getOuterCallees(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement)
						
						&& !methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
						&& !methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
						 && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//						&& methodtrace.Method.Owner.methods.size()<30
	
//						&& !methodtrace.Method.Owner.ID.equals("24")

						)
					{
						LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/MixedT");
				
				LogInfoHashMap.put(reqMethod, loginfo); 
					}
					 //MIXED T LEAF 
					 	 if( methodtrace.prediction.equals("E") 
					 			

									&& methodtrace.Method.getOuterCallers(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement)
									&& methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty()
									
									&& !methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
									 && DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//									&& methodtrace.Method.Owner.methods.size()<30
		
//									&& !methodtrace.Method.Owner.ID.equals("24")

									)
								{
									LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
									methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/MixedTLeaf");
							
							LogInfoHashMap.put(reqMethod, loginfo); 
								}


						
						
						 //ALL CALLERS 
						 
						if (	
								 methodtrace.Method.getOuterCallers(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AllTs(methodtrace.Requirement)
								&& !methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap)
								&& DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//								&& methodtrace.Method.Owner.methods.size()<30

//								&& !methodtrace.Method.Owner.ID.equals("24")

								) {
							LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
							methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/AllCallersT");
					
					LogInfoHashMap.put(reqMethod, loginfo); 
						}
						//ALL CALLEES 
						if (	
								 methodtrace.Method.getOuterCallees(methodtrace.Requirement).getOwnerClasses(methodtrace.Requirement).AllTs(methodtrace.Requirement)
								&& !methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1N(methodtrace.Requirement, methodtraces2HashMap) 
								&& DatabaseInput.OwnerTraceHashMap.get(reqClass).equals("T") 
//								&& methodtrace.Method.Owner.methods.size()<30

//								&& !methodtrace.Method.Owner.ID.equals("24")

								) {
							LogInfo loginfo = LogInfoHashMap.get(reqMethod); 
							methodtrace.SetPrediction(LogInfoHashMap,"T", "T,Remaining/AllCalleesT");
					
					LogInfoHashMap.put(reqMethod, loginfo); 
						}
						
					
				}
				
		
		
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// STEP 4
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//		ITERATION=0; 
//		MethodTrace.modified=true; 
//		while (MethodTrace.modified) {
//			MethodTrace.modified = false;
//			////////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// PURE AND MIXED PATTERNS	PATTERN 1-2
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//
//			//////////////////////////////////////////////////////////////////////////////////////////
//		
//
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			/////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// PURE PATTERNS
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
////			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
////
////					
////					
//////				System.out.println("PATTERN 1");
////
////				// PURE T PATTERN
////
////				if (    !methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
////						&& methodtrace.Method.getCallees(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap)
////						&& methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap)					
////						
////
////				) {
////					methodtrace.SetPrediction(LogInfoHashMap, "T", "T,PureTStep4");
////
////				
////
////				}
////
////
////				 
////				// PURE T LEAF PATTERN
////				 if (	methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty()
////						 && methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement,methodtraces2HashMap)
////						
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,PureTLeafStep4");
////
////				}
////				 
////				 
////
////
////
////
////			}
//
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// MIXED PATTERNS
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				
//				
////				// MIXED T PATTERN
////				 if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
////					&& (methodtrace.Method.getCallees(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap) 
////					&& methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) )
////					||(methodtrace.Method.getCallees(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) 
////					&& methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap))
////
////				) {
////						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,MixedTStep4");
////
////				}
//				 
//
//
//				
//
//				
//				 
//				// MIXED T LEAF PATTERN
//				 if (methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() && !methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
//						 && methodtrace.Method.getCallers(methodtrace.Requirement).AtLeast1T(methodtrace.Requirement, methodtraces2HashMap) 
//
//				) {
//						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,MixedTLeafStep4");
//
//
//				}
//
//				
//
//			
//			}
//			
//			// PRINT
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
//			// END PRINT
//			
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			/////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// SUPERCLASSES , CHILDREN , INTERFACES , IMPLEMENTATIONS  PATTERN 3
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				String reqMethod= methodtrace.Requirement.ID+"-"+methodtrace.Method.ID; 
//				System.out.println(reqMethod); 
//				System.out.println("PATTERN 3");
//
//					if (
//							methodtrace.Method.Interfaces.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Implementations.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Superclasses.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							||methodtrace.Method.Children.AllTs(methodtrace.Requirement,methodtraces2HashMap)
//							
//							)
//							
//						
//
//					{
//
//						
//						
//						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTInheritanceStep4");
//
//
//					}
//
//
//			}
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
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			/////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
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
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// ALL CALLERS PATTERN 4
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//				
//
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				//System.out.println();
//				
//
//			
//			
//				
//				
//				// ALL T CALLERS 
//				 if (!methodtrace.Method.getCallers(methodtrace.Requirement).isEmpty() 
//					&& methodtrace.Method.getCallers(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap) 
//					
//
//				) {
//						methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTCallersStep4");
//						
//				}
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
//			
//			
//			
//				//////////////////////////////////////////////////////////////////////////////////////////
//				//////////////////////////////////////////////////////////////////////////////////////////
//				//ALL CALLEES PATTERN 5
//				//////////////////////////////////////////////////////////////////////////////////////////
//				//////////////////////////////////////////////////////////////////////////////////////////
//
//			for (MethodTrace methodtrace : MethodTracesHashmapValues) {
//				
////				System.out.println("PATTERN 5");
//
//				
//				
//
//			
//				
//				
//				
//				
//				//ALL T CALLEES 
//				if (!methodtrace.Method.getCallees(methodtrace.Requirement).isEmpty() 
//				&& methodtrace.Method.getCallees(methodtrace.Requirement).AllTs(methodtrace.Requirement, methodtraces2HashMap) 
//				
//				
//				) {
//					methodtrace.SetPrediction(LogInfoHashMap,"T", "T,AllTCalleesStep4");
//
//				
//				
//				}
//
//				
//				}	
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
//			// END PATTERNS 
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//////////////////////////////////////////////////////////////////////////////////////////
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
//			// PRINT
//			SetLogFileIterations(MethodTracesHashmapValues, methodtraces, LogInfoHashMap, ITERATION); 
//			
//
//			// END PRINT
//			ITERATION++;
//			
//		}
//		
		
	
		System.out.println(ITERATION);
		System.out.println("FINISJED");
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	
	
	

		if(ProgramName.equals("chess")) {
			bwfile2.close(); 
			bwfile1.close(); 
			bwfile3.close();
		}
	
		System.out.println("RemainingpredictionValues"+RemainingpredictionValues);
		System.out.println("OWNERRRRRRRRRR");
		LogInfo.ComputePrecisionAndRecall(methodtraces2HashMap, RemainingPattern, ProgramName, RemainingpredictionValues, LogInfoHashMap);
		System.out.println("RemainingpredictionValues"+TotalPattern);
		LogInfo.updateResultsLog(RemainingPattern, RemainingpredictionValues, ProgramName, "NON OWNER CLASS PRED", "non owner class prediction values");



		 TotalPredictionValues = new PredictionValues(); 
		 TotalPattern = new PredictionEvaluation();
		 RemainingpredictionValues = new PredictionValues(); 
		



		ResetAllTraceSetFlags(methodtraces2HashMap);
		 LogInfo.ComputePrecisionAndRecall(methodtraces2HashMap, TotalPattern, ProgramName, TotalPredictionValues, LogInfoHashMap);
			RemainingpredictionValues=SubstractPredictionValues(TotalPredictionValues, OwnerClassPredictionValues); 
			LogInfo.updateTableLog(ProgramName, MethodTracesHashmapValues, LogInfoHashMap);
		LogInfo.updateResultsLog(TotalPattern, TotalPredictionValues, ProgramName,"TOTAL  PREDICTION", "total prediction values");
		LogInfo.closeLogFile(); 
		
		

		
		LogInfo.CloseFiles(ProgramName); 
		
		
	}
	
	
	

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	public void SetLogFileIterations(Collection<MethodTrace> methodTracesHashmapValues, List<MethodTrace> methodtraces22, LinkedHashMap<String, LogInfo> LogInfoHashMap, int ITERATION ) {
		// TODO Auto-generated method stub
		for (MethodTrace methodtrace : methodTracesHashmapValues) {
			String ReqMethod = methodtrace.Requirement.ID + "-" + methodtrace.Method.ID;
			LogInfo LogInfo = LogInfoHashMap.get(ReqMethod);
			List<String> myits = LogInfo.getIterationValues();
			

			if (myits.size() == ITERATION + 1) {
				//System.out.println(myits.get(ITERATION));

			} else {
				myits.add(",");
				LogInfo.setIterationValues(myits);
			}

			for (String it : myits) {
				//System.out.println("it" + it + " it");
			}
			LogInfoHashMap.put(ReqMethod, LogInfo);
		}
	}

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	public PredictionValues SubstractPredictionValues(PredictionValues totalPredictionValues,
			PredictionValues ownerClassPredictionValues) {
			
		int totalT = totalPredictionValues.T; 
		int totalN=totalPredictionValues.N; 
		int totalE=totalPredictionValues.E;
		
		int ownerT=ownerClassPredictionValues.T; 
		int ownerN=ownerClassPredictionValues.N; 
		int ownerE=ownerClassPredictionValues.E; 

		
		int remainingT= totalT-ownerT; 
		int remainingN= totalN-ownerN;
		int remainingE= totalE;
		PredictionValues RemainingPredictionValues= new PredictionValues(); 
		RemainingPredictionValues.setT(remainingT);
		RemainingPredictionValues.setN(remainingN);
		RemainingPredictionValues.setE(totalE);
				return RemainingPredictionValues;
		// TODO Auto-generated method stub
		
	}

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	public void ResetAllTraceSetFlags(HashMap<String, MethodTrace> methodtraces2HashMap2) {
		// TODO Auto-generated method stub
		
		for(String key: methodtraces2HashMap2.keySet()) {
			MethodTrace MethodTrace = methodtraces2HashMap2.get(key); 
			MethodTrace.setTraceSet(false);
		}
		
	}

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	
	/************************************************************************************************************************************************/
	/**
	 * @param methodtraces2HashMap2 **********************************************************************************************************************************************/
	public void CountTracesClassesValues(PredictionValues PredictionClassTraceBefore,  HashMap<String, MethodTrace> methodtraces2HashMap2) {
		// TODO Auto-generated method stub
		HashMap<String, String> myhashmap= new HashMap<String, String> (); 
		
		for(String mykey: methodtraces2HashMap2.keySet()) {
			String reqClass= methodtraces2HashMap2.get(mykey).Requirement.ID+"-"+methodtraces2HashMap2.get(mykey).Method.Owner.ID; 
//			myhashmap.put(reqClass, methodtraces2HashMap2.get(mykey).Method.Owner.DeveloperGold);
			myhashmap.put(reqClass, DatabaseInput.OwnerTraceHashMap.get(reqClass));
		
		}
		
		
		
		
			for(String mykey: myhashmap.keySet()) {
				 String value = myhashmap.get(mykey); 
				
				PredictionClassTraceBefore.ComputePredictionValues(PredictionClassTraceBefore,value);
			}
		
		
		
		
	}
	
	
	

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	private LinkedHashMap<String, LogInfo> InitializeHashMapWithPrecisionRecall(
			Collection<MethodTrace> methodTracesHashmapValues, LinkedHashMap<String, LogInfo> logHashMapRemaining) {
		// TODO Auto-generated method stub
		
		for (MethodTrace methodtrace : methodTracesHashmapValues) {
			
			
			methodtrace.setPrediction("E");
			
		}
	
		return logHashMapRemaining;
	}

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/**
	 * @param methodtracesRequirementClass2 
	 * @return
	 * @throws Exception 
	 **********************************************************************************************************************************************/

	private void GenerateNewValuesInTracesClasses(
			LinkedHashMap<String, ClassTrace2> methodtracesRequirementClass2) throws Exception {
		// TODO Auto-generated method stub
		
		Collection<MethodTrace> MethodTracesHashmapValues = methodtraces2HashMap.values();

		for (MethodTrace methodtrace : MethodTracesHashmapValues) {


			// PATTERN 0
			
			MethodList children = methodtrace.Method.Children; 
			MethodList parents = methodtrace.Method.Superclasses; 
			MethodList implementations = methodtrace.Method.Implementations; 
			MethodList interfaces = methodtrace.Method.Interfaces; 
			ClassTraceList ChilrenGold= new ClassTraceList(); 
			ClassTraceList ParentsGold= new ClassTraceList(); 
			ClassTraceList ImplementationsGold= new ClassTraceList(); 
			ClassTraceList InterfacesGold= new ClassTraceList(); 
			for(Method child:children) {
				String ChildGold = child.Owner.DeveloperGold; 
				ChilrenGold.add(ChildGold); 
			}
			
			
			for(Method parent:parents) {
				String ParentGold = parent.Owner.DeveloperGold; 
				ParentsGold.add(ParentGold); 
			}
			for(Method myinterface:interfaces) {
				String InterfaceGold = myinterface.Owner.DeveloperGold; 
				InterfacesGold.add(InterfaceGold); 

			}
			for(Method implementation:implementations) {
				String ImplementationGold = implementation.Owner.DeveloperGold; 
				ImplementationsGold.add(ImplementationGold); 
			}
			
			
			
			if(methodtrace.Method.Owner.DeveloperGold.equals("E") ) {
				if(ImplementationsGold.AllTs()) {
					methodtrace.Method.Owner.DeveloperGold="T";
				}else if(InterfacesGold.AllTs()) {
					methodtrace.Method.Owner.DeveloperGold="T";

				}else if(ParentsGold.AllTs()) {
					methodtrace.Method.Owner.DeveloperGold="T";

				}else if(ChilrenGold.AllTs()) {
					methodtrace.Method.Owner.DeveloperGold="T";

				}
				
				
				 if(ImplementationsGold.AllNs()) {
					methodtrace.Method.Owner.DeveloperGold="N";
				}else if(InterfacesGold.AllNs()) {
					methodtrace.Method.Owner.DeveloperGold="N";

				}else if(ParentsGold.AllNs()) {
					methodtrace.Method.Owner.DeveloperGold="N";

				}else if(ChilrenGold.AllNs()) {
					methodtrace.Method.Owner.DeveloperGold="N";

				}
			}
			
		}
	}
	
	
	
	
	
	
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/**
	 * @param logInfoHashMap 
	 * @param programName **********************************************************************************************************************************************/
	public void SetSubjectGoldDeveloperGoldEqualityFlag(HashMap<String, MethodTrace> methodTraceHashMap,
			PredictionEvaluation nEWPATTERNMethodFields2, LinkedHashMap<String, LogInfo> logInfoHashMap, String programName) {
		for (String mykey : methodTraceHashMap.keySet()) {
			
			 LogInfo loginfo = logInfoHashMap.get(mykey);
			MethodTrace methodtraceValue = methodTraceHashMap.get(mykey); 
			if( programName.equals("gantt")||programName.equals("jhotdraw") ) {
				String reqClass= methodtraceValue.Requirement.ID+"-"+methodtraceValue.Method.Owner.ID; 

//				if (methodtraceValue.Method.Owner.DeveloperGold.equals(methodtraceValue.Method.Owner.SubjectGold) ) 
					if (DatabaseInput.OwnerTraceHashMap.get(reqClass).equals(DatabaseInput.SubjectTraceHashMap.get(reqClass)) )

				{
					loginfo.setSubjectDeveloperEqualityFlag(true);
					logInfoHashMap.put(mykey, loginfo); 
					
					
					System.out.println(mykey);
					MethodTrace methodtrace = methodTraceHashMap.get(mykey); 
					methodtrace.setSubjectDeveloperEqualityFlag(true);
					methodTraceHashMap.put(mykey, methodtrace); 
				}
			}
			

		}
		
	}
	
	
	
	
	
	

	
	
	

	


	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/**
	 * @throws Exception **********************************************************************************************************************************************/
	public static void main(String[] args) throws Exception {
		String ProgramName = "chess";
		AlgoFinalRefactored frame = new AlgoFinalRefactored(
				ProgramName);

		String ProgramName2 = "gantt";
			 frame = new AlgoFinalRefactored(ProgramName2);

		String ProgramName3 = "itrust";
			 frame = new AlgoFinalRefactored(ProgramName3);

		
		String ProgramName4 = "jhotdraw";
			frame = new AlgoFinalRefactored(ProgramName4);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}



}