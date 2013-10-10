/*
 * rice_cooker_system.h
 *
 *  Created on: 2013-03-11
 *      Author: isra
 */

#ifndef __RCS_SYSTEM_H_INCLUDED__
#define __RCS_SYSTEM_H_INCLUDED__

#include "rcs_state.h"

#define RC_VOLUME 2 //in Liter
#define RC_HEAT_POWER 990 //in Watt
#define RC_THERMAL_CAP 4200 //in J/m^3/K

typedef enum cook_mode_k {
	NONE=-1,
	SLOW=0,
	NORMAL,
	FAST,
	MINUTE
} cook_mode_k;

void rcs_init(void);
int rcs_run(void);
void rcs_set_selected_mode(cook_mode_k mode);
cook_mode_k rcs_get_selected_mode();
void rcs_set_current_state(rcs_state_t* new_state);

#endif /* RICE_COOKER_SYSTEM_H_ */
