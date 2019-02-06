package ALGO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import mypackage.*;
public class MethodList extends ArrayList<Method>{

	
	public OwnerClassList OwnerClasses= new OwnerClassList(); 

	




	
	

	
	public OwnerClassList getOwnerClasses(Requirement requirement) {
		OwnerClasses= new OwnerClassList(); 
		for(Method method: this) {
			Clazz clazz = DatabaseInput.classTraceHashMap.get(requirement.ID+"-"+method.Owner.ID).myclass; 
			if(!OwnerClasses.contains(clazz)){
				OwnerClasses.add(clazz); 
			}
			
		}
//		System.out.println("=========================> OwnerClasses "+OwnerClasses);
		OwnerClasses=RemoveDuplicateClasses(OwnerClasses); 
		
		return OwnerClasses;
	}


	public static OwnerClassList RemoveDuplicateClasses(OwnerClassList OwnerClassList) {
		// TODO Auto-generated method stub
		OwnerClassList OwnerClassList2= new OwnerClassList(); 
	
	for(Clazz ownerclass: OwnerClassList) {
		if(!OwnerClassList2.contains(ownerclass)) {
			OwnerClassList2.add(ownerclass); 
		}
	}
	return OwnerClassList2; 
		
	}

	public void setOwnerClasses(OwnerClassList ownerClasses) {
		OwnerClasses = ownerClasses;
	}




	public boolean AtLeast1T(Requirement requirement,  HashMap<java.lang.String, MethodTrace> methodtraces2HashMap) throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
		for (Method method : this) {
			String RequirementID = (String) requirement.ID; 
			String MethodID = method.ID; 
			String key = (String) (RequirementID + "-" + MethodID);
			if (methodtraces2HashMap.get(key) == null) throw new Exception();
				if (methodtraces2HashMap.get(key).getPrediction().equals("T")) 
					return true; 

			
		}
		}
		return false;
		
		
		
		
		

	}
	
	
	
	
	public boolean AtLeast1N(Requirement requirement,  HashMap<java.lang.String, MethodTrace> methodtraces2HashMap) throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
			for (Method method : this) {
				String RequirementID = (String) requirement.ID; 
				String MethodID = method.ID; 
				String key = (String) (RequirementID + "-" + MethodID);
				if (methodtraces2HashMap.get(key) == null) throw new Exception();
					if (methodtraces2HashMap.get(key).getPrediction().equals("N")) 
						return true; 

				
			}
		}
		

		return false;
		
		
		
		
		

	}
	
	
	
	
	
	
	
	public boolean AllNs(Requirement requirement,  HashMap<java.lang.String, MethodTrace> methodtraces2HashMap) throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
			for (Method method : this) {
				String RequirementID = (String) requirement.ID;
				String MethodID = method.ID; 
				String key = (String) (RequirementID + "-" + MethodID);
				if (methodtraces2HashMap.get(key) == null) throw new Exception();
					if (!methodtraces2HashMap.get(key).getPrediction().equals("N")) return false; 

				
			}
			return true;
		}
		
		return false; 
		

		
	}
	
	
	
	
	public boolean AllTs(Requirement requirement, HashMap<java.lang.String, MethodTrace> methodtraces2HashMap) throws Exception {
		// TODO Auto-generated method stub
		if(!this.isEmpty()) {
			for (Method method : this) {

				MethodTrace trace = methodtraces2HashMap.get(requirement.ID + "-" + method.ID);
				if (trace == null) 	throw new Exception();
				if (!trace.getPrediction().equals("T")) return false; 
			}
			return true;
		}
		
		return false; 
		

		
	}
	

	
	
	
	
	
	
	





	public MethodList AddAll(MethodList MethodList) {
		
		this.addAll(MethodList); 
//		for(Method meth: MethodList) {
//			this.add(meth); 
//		}
		MethodList NewMethodList = removeDuplicatesMethods(this); 
		return NewMethodList; 
	}


	 // Function to remove duplicates from an ArrayList 
    public static MethodList removeDuplicatesMethods(MethodList list) 
    { 
  
        // Create a new ArrayList 
    	MethodList newList = new MethodList(); 
  
        // Traverse through the first list 
        for (Method element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 










	
























	

	
}