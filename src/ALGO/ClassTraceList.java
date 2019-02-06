package ALGO;

import java.util.ArrayList;
import java.util.HashMap;

import mypackage.Method;
import mypackage.MethodTrace;
import mypackage.Requirement;

public class ClassTraceList extends ArrayList<String>{

	
	
	public boolean AllNs() throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
			for (	String ClassTrace : this) {
				
				if (ClassTrace == null) throw new Exception();
					if (!ClassTrace.equals("N")) return false; 

				
			}
			return true;
		}
		
		return false; 
		

		
	}
	
	public boolean AllTs() throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
			for (	String ClassTrace : this) {
				
				if (ClassTrace == null) throw new Exception();
					if (!ClassTrace.equals("T")) return false; 

				
			}
			return true;
		}
		
		return false; 
		

		
	}
}
