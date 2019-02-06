package mypackage;

public class RequirementClass {
	String requirementID; 
	String ClassID;
	public String getRequirementID() {
		return requirementID;
	}
	public void setRequirementID(String requirementID) {
		this.requirementID = requirementID;
	}
	public String getClassID() {
		return ClassID;
	}
	public void setClassID(String classID) {
		ClassID = classID;
	}
	@Override
	public String toString() {
		return "RequirementClass [requirementID=" + requirementID + ", ClassID=" + ClassID + "]";
	}
	public RequirementClass(String requirementID, String classID) {
		super();
		this.requirementID = requirementID;
		ClassID = classID;
	} 
	
	
}
