package ca.etsmtl.log430.lab1.buisness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class defines the Resource object for the system. Besides the basic
 * attributes, there are two lists maintained. alreadyAssignedProjectList is a
 * ProjectList object that maintains a list of projects that the resource was
 * already assigned to prior to this execution of the system.
 * projectsAssignedList is also a ProjectList object that maintains a list of
 * projects assigned to the resource durint the current execution or session.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.6, 2013-Sep-13
 */

/* Modification Log
 ****************************************************************************
 * v1.7, S. Lago,      2014-Jan-26 - Modification 3.
 *
 * v1.6, R. Champagne, 2013-Sep-13 - Various refactorings for new lab.
 * 
 * v1.5, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-May-31 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
 * 
 * v1.2, 2011-Feb-02, R. Champagne - Various refactorings, javadoc comments.
 *  
 * v1.1, 2002-May-21, R. Champagne - Adapted for use at ETS. 
 * 
 * v1.0, 12/29/99, A.J. Lattanze - Original version.

 ****************************************************************************/

public class Resource {

	/**
	 * Resource's last name
	 */
	private String lastName;
	
	/**
	 * Resource's first name
	 */
	private String firstName;
	
	/**
	 * Resource's identification number
	 */
	private String id;
	
	/**
	 * Resource role 
	 */
	private String role;

	/**
	 *  List of projects the resource is already allocated to
	 */
	private ProjectList alreadyAssignedProjectList = new ProjectList();

	/**
	 *  List of projects assigned to the resource in this session
	 */
	private ProjectList projectsAssignedList = new ProjectList();

	/**
	 * Assigns a project to a resource.
	 * 
	 * @param project
	 */
	public void assignProject(Project project) throws Exception {
		
		if(canTakeProject(project)){
			getProjectsAssigned().addProject(project);
		}
		else{
			throw new Exception("Resource overloaded. During the project period, " + 
                    "percentage of time occupied by the resource can't exceed 100%");
		}
			
	}
	
	public boolean canTakeProject(Project project){
		
		int occupiedPercent = 0;
		String tempPriority;
		boolean done;
		Project tempProject;
		SimpleDateFormat sdf;
		Date dateDebut;
		Date dateFin;
		Date tempDateDebut, tempDateFin;
		boolean result = false;
		
		try{
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			dateDebut = sdf.parse(project.getStartDate());
			dateFin = sdf.parse(project.getEndDate());
			
			getPreviouslyAssignedProjectList().goToFrontOfList();
			done = false;
			//Loop through projects assigned before execution.
			while (!done) {
				tempProject = getPreviouslyAssignedProjectList().getNextProject();
				if (tempProject == null) {
					done = true;
				} else {
					
					tempDateDebut = sdf.parse(tempProject.getStartDate());
					tempDateFin = sdf.parse(tempProject.getEndDate());
					
					//Check if time slots are in conflict.
					if((tempDateDebut.compareTo(dateDebut) >= 0 && tempDateDebut.compareTo(dateFin) <= 0) || 
							(tempDateFin.compareTo(dateDebut) >= 0 && tempDateFin.compareTo(dateFin) <= 0)){
						
						tempPriority = tempProject.getPriority().toUpperCase();
						//Add percentage of time occupied by the resource.
						if(tempPriority.equalsIgnoreCase("H"))
							occupiedPercent += 100;
						else if(tempPriority.equalsIgnoreCase("M"))
							occupiedPercent += 50;
						else if(tempPriority.equalsIgnoreCase("L"))
							occupiedPercent += 25;
						
					}
				} // if
			} // while
			
			getProjectsAssigned().goToFrontOfList();
			done = false;
			//Loop through projects assigned during execution.
			while (!done) {
				tempProject = getProjectsAssigned().getNextProject();
				if (tempProject == null) {
					done = true;
				} else {
					
					tempDateDebut = sdf.parse(tempProject.getStartDate());
					tempDateFin = sdf.parse(tempProject.getEndDate());
					
					//Check if time slots are in conflict.
					if((tempDateDebut.compareTo(dateDebut) >= 0 && tempDateDebut.compareTo(dateFin) <= 0) || 
							(tempDateFin.compareTo(dateDebut) >= 0 && tempDateFin.compareTo(dateFin) <= 0)){
						
						tempPriority = tempProject.getPriority().toUpperCase();
						//Add percentage of time occupied by the resource.
						if(tempPriority.equalsIgnoreCase("H"))
							occupiedPercent += 100;
						else if(tempPriority.equalsIgnoreCase("M"))
							occupiedPercent += 50;
						else if(tempPriority.equalsIgnoreCase("L"))
							occupiedPercent += 25;
					}
				} // if
			} // while
			
			tempPriority = project.getPriority().toUpperCase();
			//Add percentage of time occupied by the resource.
			if(tempPriority.equalsIgnoreCase("H"))
				occupiedPercent += 100;
			else if(tempPriority.equalsIgnoreCase("M"))
				occupiedPercent += 50;
			else if(tempPriority.equalsIgnoreCase("L"))
				occupiedPercent += 25;
			
			if(occupiedPercent > 100)
				result = false;
			else
				result = true;
			
		}
		catch(ParseException exc){
			System.out.println(exc.getMessage());
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
		
		return result;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setPreviouslyAssignedProjectList(ProjectList projectList) {
		this.alreadyAssignedProjectList = projectList;
	}

	public ProjectList getPreviouslyAssignedProjectList() {
		return alreadyAssignedProjectList;
	}

	public void setProjectsAssigned(ProjectList projectList) {
		this.projectsAssignedList = projectList;
	}

	public ProjectList getProjectsAssigned() {
		return projectsAssignedList;
	}

} // Resource class