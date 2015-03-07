package ca.etsmtl.log430.lab2.component;

import java.util.Observable;

import ca.etsmtl.log430.common.Resource;
import ca.etsmtl.log430.common.ui.Displays;
import ca.etsmtl.log430.common.ui.Menus;
import ca.etsmtl.log430.lab2.CommonData;

/**
 * Upon notification, lists project initially assigned to a resource.
 * 
 * @author I. Hallé
 * @version 1.0, 2014-Fev-05
 */

/*
 * Modification Log **********************************************************
 * v1.0, I. Hallé, 02/05/14 - Original version.
 * ***************************************************************************
 */

public class ListProjectsInitialyAssignedToResource extends Communication {

	public ListProjectsInitialyAssignedToResource(Integer registrationNumber,
			String componentName) {
		super(registrationNumber, componentName);
	}


	/**
	 * The update() method is an abstract method that is called whenever the
	 * notifyObservers() method is called by the Observable class. First we
	 * check to see if the NotificationNumber is equal to this thread's
	 * RegistrationNumber. If it is, then we execute.
	 * 
	 * @see ca.etsmtl.log430.lab2.component.Communication#update(java.util.Observable,
	 *      java.lang.Object)
	 */
	public void update(Observable thing, Object notificationNumber) {
		Menus menu = new Menus();
		Displays display = new Displays();
		Resource myResource = new Resource();

		if (registrationNumber.compareTo((Integer) notificationNumber) == 0) {
			/*
			 * First we use a Displays object to list all of the resources. Then
			 * we ask the user to pick a resource using a Menus object.
			 */
			addToReceiverList("ListResourcesComponent");
			signalReceivers("ListResourcesComponent");
			myResource = menu.pickResource(CommonData.theListOfResources
					.getListOfResources());

			/*
			 * If the user selected an invalid resource, then a message is
			 * printed to the terminal.
			 */
			if (myResource != null) {
				display.displayProjectsInitiallyAssignedToResource(myResource);
			} else {
				System.out.println("\n\n *** Resource not found ***");
			}
		}
		removeFromReceiverList("ListResourcesComponent");
	}
}
