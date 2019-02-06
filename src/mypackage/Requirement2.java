package mypackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Requirement2 {
	public String ID; 
	public String RequirementName;
	public HashMap<String, Requirement2> RequirementsHashMap= new HashMap<String, Requirement2>(); 
	
	public Requirement2(String iD, String requirementName) {
		super();
		ID = iD;
		RequirementName = requirementName;
	}
	public Requirement2() {
		// TODO Auto-generated constructor stub
	}
	public String getID() {
		return ID;
	}
	public void setID(String string) {
		ID = string;
	}
	public String getRequirementName() {
		return RequirementName;
	}
	public void setRequirementName(String requirementName) {
		RequirementName = requirementName;
	}

	public  HashMap<String, Requirement2> ReadClassesRepresentations(Connection conn) throws SQLException {
		DatabaseReading2 db = new DatabaseReading2(); 
		
		//CLASSESHASHMAP
		String rowcount = null; 
		Statement st = conn.createStatement();
		ResultSet var  = st.executeQuery("select count(*) from requirements"); 
	while(var.next()){
		rowcount = var.getString("count(*)");
		}
	System.out.println("ROW COUNT::::::"+rowcount); 
	String index="1"; 
	ResultSet requirements = st.executeQuery("SELECT requirements.* from requirements"); 
	 String id=null;
	 String classname=null; 

		

		


				while(requirements.next() ){
					id = requirements.getString("id"); 
					
					String requirementname = requirements.getString("requirementname"); 








					Requirement2 requirement = new Requirement2(id, requirementname); 	
					RequirementsHashMap.put(id, requirement);  
						 

				}
				//fieldclasses.close();
		


	 Set<String> keys = RequirementsHashMap.keySet();
	Map<String, Requirement2> resultRequirements = RequirementsHashMap.entrySet().stream()
	                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

	 for(String key: keys){
            System.out.println("============================>Value of "+key+" is: "+ resultRequirements.get(key).RequirementName);
        }
	 return RequirementsHashMap; 
	}
	@Override
	public String toString() {
		return "Requirement2 [ID=" + ID + ", RequirementName=" + RequirementName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((RequirementName == null) ? 0 : RequirementName.hashCode());
		result = prime * result + ((RequirementsHashMap == null) ? 0 : RequirementsHashMap.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Requirement2 other = (Requirement2) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (RequirementName == null) {
			if (other.RequirementName != null)
				return false;
		} else if (!RequirementName.equals(other.RequirementName))
			return false;
		if (RequirementsHashMap == null) {
			if (other.RequirementsHashMap != null)
				return false;
		} else if (!RequirementsHashMap.equals(other.RequirementsHashMap))
			return false;
		return true;
	}
	
}
