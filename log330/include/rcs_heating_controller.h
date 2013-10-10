/*
 * heating_controller.h
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#ifndef __HEATING_CONTROLLER_H_INCLUDED__
#define __HEATING_CONTROLLER_H_INCLUDED__

#include "rcs_system.h"

#define MAX_SAFE_TEMP 160
#define HEAT_TEMP 75
#define HEAT_TIME_STEP 5

void rcs_heating_controller_update(cook_mode_k mode, int time);
void rcs_heating_controller_heat();

#endif /* __HEATING_CONTROLLER_H_INCLUDED__ */
