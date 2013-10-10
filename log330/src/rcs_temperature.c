/*
 * rcs_temperature.c
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#include "rcs_temperature.h"
#include "rcs_time.h"
#include "rcs_system.h"
#include "rcs_simulator.h"

#ifdef __SIMULATOR

float heater_temp = 18.f;

float rcs_temperature_poll() {
	return heater_temp;
}

void rcs_temperature_set_heater(float power /*Watt hour*/) {
	heater_temp = (power * RCS_SIMULATOR_TIMESTEP) / (RC_VOLUME * RC_THERMAL_CAP) + heater_temp;
}

#else

char temp_input = 0;

float rcs_temperature_poll() {
	return F_TO_C(((float)temp_input/255.f) * (T_RANGE_MAX - T_RANGE_MIN) + T_RANGE_MIN);
}

void rcs_temperature_set_heater(float power /*Watt hour*/) {
	//Set the heater power...
}

#endif
