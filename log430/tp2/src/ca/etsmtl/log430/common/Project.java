package ca.etsmtl.log430.common;

/** This class defines the Project object for the system.
* 
* @author A.J. Lattanze, CMU
* @version 1.7, 2013-Sep-13
*/

/*
* Modification Log **********************************************************
* v1.8, I. Hallé, 2014-Fev-05 - Link project to their assigned resources
* 
* v1.7, R. Champagne, 2013-Sep-13 - Various refactorings for new lab.
* 
* v1.6, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
* 
* v1.5, R. Champagne, 2012-May-31 - Various refactorings for new lab.
* 
* v1.4, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
* 
* v1.3, R. Champagne, 2011-Feb-02 - Various refactorings, conversion of
* comments to javadoc format.
* 
* v1.2, R. Champagne, 2002-May-21 - Adapted for use at ETS.
* 
* v1.1, G.A.Lewis, 01/25/2001 - Bug in second constructor. Removed null
* assignment to id after being assigned a value.
* 
* v1.0, A.J. Lattanze, 12/29/99 - Original version.
* ***************************************************************************
*/

public class Project {

	/**
	 * Project ID
	 */
	private String id;

	/**
	 * Project name.
	 */
	private String name;

	/**
	 * Project start date.
	 */
	private String startDate;

	/**
	 * Project end date.
	 */
	private String endDate;

	/**
	 * Project priority
	 */
	private String priority;
	
	/**
	 * List of resources assigned to the project
	 */
	private ResourceList alreadyAssignedResources = new ResourceList();

	/**
	 * List of resources assigned to the project
	 */
	private ResourceList resourcesAssigned = new ResourceList();

	public Project() {
		this(null);
	}

	public Project(String id) {
		this.setID(id);
	}

	/**
	 * Assign a resource to a project.
	 * 
	 * @param resource
	 */
	public void assignResource(Resource resource) throws Exception {
		if(resource.canTakeProject(this)){
			resourcesAssigned.addResource(resource);
		}	
		else{
			throw new Exception("Resource overloaded. During the project period, " + 
                    "percentage of time occupied by the resource can't exceed 100%");
		}
	}

	public void setID(String projectID) {
		this.id = projectID;
	}

	public String getID() {
		return id;
	}

	public void setProjectName(String time) {
		this.name = time;
	}

	public String getProjectName() {
		return name;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public void setPreviouslyAssignedResources(ResourceList resourcesAlreadyAssigned){
		this.alreadyAssignedResources = resourcesAlreadyAssigned;
	}
	
	public ResourceList getPreviouslyAssignedResources(){
		return alreadyAssignedResources;
	}

	public void setResourcesAssigned(ResourceList resourcesAssigned) {
		this.resourcesAssigned = resourcesAssigned;
	}

	public ResourceList getResourcesAssigned() {
		return resourcesAssigned;
	}

	/**
	 * Return a list of all the assigned resources of a project (Both from 
	 * this session and previous session)
	 */
	public ResourceList getAllAssignedResources() {
		ResourceList allResources = new ResourceList();
		Resource current = null;
		resourcesAssigned.goToFrontOfList();
		while((current = resourcesAssigned.getNextResource()) != null) {
			allResources.addResource(current);
		}
		
		alreadyAssignedResources.goToFrontOfList();
		while((current = alreadyAssignedResources.getNextResource()) != null) {
			allResources.addResource(current);
		}
		
		return allResources;
	}

} // Project class