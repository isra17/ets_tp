package ca.etsmtl.log430.lab1;

import ca.etsmtl.log430.lab1.buisness.Project;
import ca.etsmtl.log430.lab1.buisness.ProjectReader;
import ca.etsmtl.log430.lab1.buisness.Resource;
import ca.etsmtl.log430.lab1.buisness.ResourceReader;
import ca.etsmtl.log430.lab1.presentation.Displays;

public class TestModifications {
	
	public static void main(String args[]){
		
		Project project = null; // A project object
		Resource resource = null; // A resource object
		
		// Instantiates a display object
		Displays display = new Displays();
		
		//projects.txt resources.txt
		ProjectReader projectList = new ProjectReader("test/projects.txt");
		ResourceReader resourceList = new ResourceReader("test/resources.txt", projectList.getListOfProjects());
		
		if ((projectList.getListOfProjects() == null)
				|| (resourceList.getListOfResources() == null)) {
			System.out
					.println("\n\n *** The projects list and/or the resources"
							+ " list was not initialized ***");
			
			System.exit(1);
		} // if
		
		
		System.out.println("Listes des ressources:");
		display.displayResourceList(resourceList.getListOfResources());
		System.out.println();
		
		System.out.println("Listes des projets:");
		display.displayProjectList(projectList.getListOfProjects());
		System.out.println();
		
		System.out.println("Listes des projets assign�s avant l'ex�cution � la ressource R001:");
		display.displayProjectsInitiallyAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		
		System.out.println("Listes des projets assign�s durant l'ex�cution � la ressource R001:");
		display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		
		System.out.println();
		
		System.out.println("Ensemble des r�les du project P004");
		project = projectList.getListOfProjects().findProjectByID("P004");
		resource = resourceList.getListOfResources().findResourceByID("R006");
		display.displayRoleList(project);
		
		System.out.println();
		
		System.out.println("Assignation de R006 a P004");
		try {
			project.assignResource(resource);
			resource.assignProject(project);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		System.out.println("Ensemble des r�les du project P004");
		display.displayRoleList(project);
		
		project = projectList.getListOfProjects().findProjectByID("P005");
		resource = resourceList.getListOfResources().findResourceByID("R001");
		
		System.out.println();
		System.out.println();
		
		try {
			System.out.println("Tentative d'assigner le projet P005 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		System.out.println();
		
		project = projectList.getListOfProjects().findProjectByID("P004");
		try {
			System.out.println("\nAssignation du projet P004 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		System.out.println();
		
		project = projectList.getListOfProjects().findProjectByID("P006");
		try {
			System.out.println("\nTentative d'assigner le projet P006 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		System.out.println();
		
		project = projectList.getListOfProjects().findProjectByID("P007");
		try {
			System.out.println("\nTentative d'assigner le projet P007 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
		System.out.println();
			
		try {
			project = projectList.getListOfProjects().findProjectByID("P008");
			System.out.println("\nTentative d'assigner le projet P008 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
			
			display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
			System.out.println();
			
			project = projectList.getListOfProjects().findProjectByID("P009");
			System.out.println("\nTentative d'assigner le projet P009 � la ressource R001");
			project.assignResource(resource);
			resource.assignProject(project);
			
			display.displayProjectsAssignedToResource(resourceList.getListOfResources().findResourceByID("R001"));
			
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		
		
			
		
		
	}
	
	
}
