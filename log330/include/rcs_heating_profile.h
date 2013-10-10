/*
 * rcs_heating_profile.h
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#ifndef __RCS_HEATING_PROFILE_H_INCLUDED__
#define __RCS_HEATING_PROFILE_H_INCLUDED__

#include "rcs_system.h"

typedef struct rcs_profile_entry_t {
	int time;
	float temp;
} rcs_profile_entry_t;

typedef struct rcs_profile_t {
	int data_size;
	const rcs_profile_entry_t* data;
} rcs_profile_t;

const rcs_profile_t* rcs_profile_get(cook_mode_k mode);
int rcs_profile_duration(const rcs_profile_t* profile);
float rcs_profile_calc_power(float dT, int dt);
float rcs_profile_calc_profile_power(
		const rcs_profile_t* profile,
		float current_temp,
		int time);

#endif /* __RCS_HEATING_PROFILE_H_INCLUDED__ */
