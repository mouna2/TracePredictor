package ALGO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import mypackage.Method;
import mypackage.*;
import mypackage.Requirement;

public class Methods<String> extends ArrayList<String>{

	
//	MethodList<String> MethodList; 
	
	public Methods<String> ConvertToMethodList(List<String> inputString)
	{	this.clear();
		for(String st: inputString) {
			this.add(st); 
		}
		return this; 
	}
	
	
	
	public boolean AllCallersAreNWithAtLeast2N() {
		// TODO Auto-generated method stub
		
		int occurrences = Collections.frequency(this, "N");

		if(!this.contains("T")  && this.contains("N") && !this.contains("E") && occurrences>=2) {
			return true;
		}
		return false;
	}



	public boolean AllCallersAreTWithAtLeast2T() {
		// TODO Auto-generated method stub
	
		int occurrences = Collections.frequency(this, "T");

		if(!this.contains("N")  && this.contains("T") && !this.contains("E") && occurrences>=2) {
			return true;
		}
		return false;
	}

	
	
	
	public boolean AllCalleesAreT() {
		// TODO Auto-generated method stub
		
		if(!this.contains("N")  && this.contains("T") && !this.contains("E")) {
			return true;
		}
		return false;
	}
	
	public boolean AtLeast1T(Requirement requirement, DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, List<Method> calls) {
		// TODO Auto-generated method stub
	
		 

		List<String> PredictionList = new ArrayList<String>();
		for (Method call : calls) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) call.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) != null) {
				String predictionvalue = (String) methodtraces2HashMap.get(key).getPrediction();
				PredictionList.add(predictionvalue);
			}
		}
		if(PredictionList.contains("T") ) {
			return true;
		}
		return false;
	}
	public boolean AtLeast1N(DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, Requirement requirement, List<Method> calls) {
		// TODO Auto-generated method stub
	
		 

		List<String> PredictionList = new ArrayList<String>();
		for (Method callee : calls) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) callee.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) != null) {
				String predictionvalue = (String) methodtraces2HashMap.get(key).getPrediction();
				PredictionList.add(predictionvalue);
			}
		}
		if(PredictionList.contains("N") ) {
			return true;
		}
		return false;
	}
	
	public boolean AllCallersAreT() {
		// TODO Auto-generated method stub
		
		if(!this.contains("N")  && this.contains("T") && !this.contains("E")) {
			return true;
		}
		return false;
	}
	public boolean AllTs() {
		// TODO Auto-generated method stub
		
		if(!this.contains("N")  && this.contains("T") && !this.contains("E")) {
			return true;
		}
		return false;
	}
	
	
	public boolean AllCalleesAreN() {
		// TODO Auto-generated method stub
		
		if(!this.contains("T")  && this.contains("N") && !this.contains("E")) {
			return true;
		}
		return false;
	}
	
	
	public boolean AllNs(Requirement requirement, DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, List<Method> calls) {
		// TODO Auto-generated method stub
		
		List<String> PredictionList = new ArrayList<String>();
		for (Method callee : calls) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) callee.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) != null) {
				String predictionvalue = (String) methodtraces2HashMap.get(key).getPrediction();
				PredictionList.add(predictionvalue);
			}
		}

		if(!PredictionList.contains("T")  && PredictionList.contains("N") && !PredictionList.contains("E")) {
			return true;
		}
		return false;
	}
	
	
	public boolean AllCallersN(DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, Requirement requirement) {
		// TODO Auto-generated method stub
		List<Method> CalleesList = methodtrace.getCalleesList();
		List<Method> CallersList = methodtrace.getCallersList();
		 

		List<String> PredictionList = new ArrayList<String>();
		for (Method callee : CallersList) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) callee.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) != null) {
				String predictionvalue = (String) methodtraces2HashMap.get(key).getPrediction();
				PredictionList.add(predictionvalue);
			}
		}

		if(!PredictionList.contains("T")  && PredictionList.contains("N") && !PredictionList.contains("E")) {
			return true;
		}
		return false;
	}
	
	
	
	public boolean AllCalleesT(DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, Requirement requirement) {
		// TODO Auto-generated method stub
		List<Method> CalleesList = methodtrace.getCalleesList();
		List<Method> CallersList = methodtrace.getCallersList();
		 

		List<String> PredictionList = new ArrayList<String>();
		for (Method callee : CalleesList) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) callee.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) != null) {
				String predictionvalue = (String) methodtraces2HashMap.get(key).getPrediction();
				PredictionList.add(predictionvalue);
			}
		}

		if(PredictionList.contains("T")  && !PredictionList.contains("N") && !PredictionList.contains("E")) {
			return true;
		}
		return false;
	}
	
	
	public boolean AllTs(Requirement requirement, DatabaseInput methodtrace, HashMap<java.lang.String, DatabaseInput> methodtraces2HashMap, List<Method> calls) {
		// TODO Auto-generated method stub
		
		 
		for (Method callee : calls) {
			String RequirementID = (String) methodtrace.Requirement.ID;
			String MethodID = (String) callee.ID;
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) == null) {
				try {
					throw new Exception();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			if (methodtraces2HashMap.get(key).getPrediction()!="T") return false; 
		}

		return true;
	}
	public boolean AllCallersAreN() {
		// TODO Auto-generated method stub
		
		if(!this.contains("T")  && this.contains("N") && !this.contains("E")) {
			return true;
		}
		return false;
	}



	public boolean AllTs(boolean InterfacesFlag, List<String> interfaceTraceValues, DatabaseInput methodtrace) {
		// TODO Auto-generated method stub
		
		if(InterfacesFlag == true && interfaceTraceValues.get(0).equals("T")
				&& methodtrace.getPrediction().trim().equals("E")) {
			return true;
		}

		return false; 
	}
	
	

	
	
	
	
	
	
	public boolean AllNs(boolean InterfacesFlag, List<String> interfaceTraceValues, DatabaseInput methodtrace) {
		// TODO Auto-generated method stub
		
		if(InterfacesFlag == true && interfaceTraceValues.get(0).equals("N")
				&& methodtrace.getPrediction().trim().equals("E")) {
			return true;
		}

		return false; 
	}
	
	
	











	public java.lang.String[] split(java.lang.String string) {
		// TODO Auto-generated method stub
		java.lang.String[] splitted = string.split("-");
		return splitted;
	}



	









	
























	

	
}