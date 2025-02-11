package ca.etsmtl.log430.lab2.component;

import java.util.Observable;

import ca.etsmtl.log430.common.ui.Displays;
import ca.etsmtl.log430.lab2.CommonData;

/**
 * Upon notification, lists the resources that were read into the vector stored
 * in the CommonData class.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.5, 2013-Oct-06
 */

/*
 * Modification Log **********************************************************
 * v1.5, R. Champagne, 2013-Oct-06 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-14 - Various refactorings for new lab.
 * 
 * v1.2, R. Champagne, 2011-Feb-24 - Various refactorings, conversion of
 * comments to javadoc format.
 * 
 * v1.1, R. Champagne, 2002-Jun-19 - Adapted for use at ETS.
 * 
 * v1.0, A.J. Lattanze, 12/29/99 - Original version.
 * ***************************************************************************
 */

public class ListResources extends Communication {

	public ListResources(Integer registrationNumber, String componentName) {
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

		if (registrationNumber.compareTo((Integer) notificationNumber) == 0) {
			Displays display = new Displays();
			display.displayResourceList(CommonData.theListOfResources
					.getListOfResources());
		}
	}
}