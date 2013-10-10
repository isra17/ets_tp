/*
 * rcs_heating_controller.c
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#include "rcs_heating_controller.h"
#include "rcs_temperature.h"
#include "rcs_state.h"
#include "rcs_waitstate.h"
#include "rcs_heating_profile.h"
#include "rcs_simulator.h"

void rcs_heating_controller_update(cook_mode_k mode, int time) {
	//Read temperature
	float current_temp = rcs_temperature_poll();
	if(current_temp < 0) {
		return;
	} else if(current_temp > MAX_SAFE_TEMP) {
		rcs_state_t* reset_state = rcs_waitstate_new();
		rcs_set_current_state(reset_state);
		return;
	}

	//Calc required power
	const rcs_profile_t* profile = rcs_profile_get(mode);
	float power = rcs_profile_calc_profile_power(profile, current_temp, time);
	power = power <= RC_HEAT_POWER? power: RC_HEAT_POWER;

	//Control heater
	rcs_temperature_set_heater(power);

	#ifdef __SIMULATOR
	RCS_DEBUG("Temp at %f (%d)", current_temp, time);
	#endif
}

void rcs_heating_controller_heat() {
	//Read temperature
	float current_temp = rcs_temperature_poll();
	if(current_temp < 0) {
		return;
	} else if(current_temp > MAX_SAFE_TEMP) {
		rcs_state_t* reset_state = rcs_waitstate_new();
		rcs_set_current_state(reset_state);
		return;
	}

	rcs_profile_calc_power(HEAT_TEMP - current_temp, HEAT_TIME_STEP);
}
