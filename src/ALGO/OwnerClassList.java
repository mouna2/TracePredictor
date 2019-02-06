package ALGO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Chess.LogInfo;
import mypackage.Clazz;
import mypackage.Method;
import mypackage.MethodTrace;
import mypackage.Requirement;

public class OwnerClassList extends ArrayList<Clazz>{
	
	
	
	public boolean AtLeast1T(Requirement requirement) throws Exception {
	// TODO Auto-generated method stub
	if(!this.isEmpty()) {
	for (Clazz clazz : this) {
		String RequirementID = (String) requirement.ID; 
		String classID = clazz.ID; 
		String key = (String) (RequirementID + "-" + classID);
		if (DatabaseInput.OwnerTraceHashMap.get(key) == null) throw new Exception();
			if (DatabaseInput.OwnerTraceHashMap.get(key).equals("T")) 
				return true; 

		
	}
	}
	return false;
	
	
	
	
	

}




public boolean AtLeast1N(Requirement requirement) throws Exception {
	// TODO Auto-generated method stub
	if(!this.isEmpty()) {
		for (Clazz clazz : this) {
			String RequirementID = (String) requirement.ID; 
			String MethodID = clazz.ID; 
			String key = (String) (RequirementID + "-" + MethodID);
			if (DatabaseInput.OwnerTraceHashMap.get(key) == null) throw new Exception();
				if (DatabaseInput.OwnerTraceHashMap.get(key).equals("N")) 
					return true; 

			
		}
	}
	

	return false;
	
	
	
	
	

}







public boolean AllNs(Requirement requirement) throws Exception {
	// TODO Auto-generated method stub
	if(!this.isEmpty()) {
		for (Clazz clazz : this) {
			String RequirementID = (String) requirement.ID;
			String MethodID = clazz.ID; 
			String key = (String) (RequirementID + "-" + MethodID);
			if (DatabaseInput.OwnerTraceHashMap.get(key) == null) throw new Exception();
				if (!DatabaseInput.OwnerTraceHashMap.get(key).equals("N")) return false; 

			
		}
		return true;
	}
	
	return false; 
	

	
}




public boolean AllTs(Requirement requirement) throws Exception {
	// TODO Auto-generated method stub
	if(!this.isEmpty()) {
		for (Clazz clazz : this) {

			String trace = DatabaseInput.OwnerTraceHashMap.get(requirement.ID + "-" + clazz.ID);
			if (trace == null) 	throw new Exception();
			if (!trace.equals("T")) return false; 
		}
		return true;
	}
	
	return false; 
	

	
}














public OwnerClassList AddAll(OwnerClassList OwnerClassList) {
	
	this.addAll(OwnerClassList); 
//	for(Method meth: MethodList) {
//		this.add(meth); 
//	}
	OwnerClassList NewOwnerList = removeDuplicatesClasses(this); 
	return NewOwnerList; 
}


 // Function to remove duplicates from an ArrayList 
public static OwnerClassList removeDuplicatesClasses(OwnerClassList list) 
{ 

    // Create a new ArrayList 
	OwnerClassList newList = new OwnerClassList(); 

    // Traverse through the first list 
    for (Clazz element : list) { 

        // If this element is not present in newList 
        // then add it 
        if (!newList.contains(element)) { 

            newList.add(element); 
        } 
    } 

    // return the new list 
    return newList; 
}




@Override
public String toString() {
	String res=""; 
	for(Clazz c: this) {
		res=res+c.toString(); 
	}
return res; 
} 

}
