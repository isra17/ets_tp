/*
 * rcs_heating_profile.c
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#include "rcs_heating_profile.h"

const rcs_profile_t profiles_map[] = {
	//Slow profile
	{
		10,
		(const rcs_profile_entry_t []) {
			{120, 18.f},
			{300, 25.f},
			{420, 40.f},
			{600, 52.f},
			{720, 68.f},
			{840, 85.f},
			{960, 105.f},
			{1080, 105.f},
			{1140, 90.f},
			{1200, 80.f}
		}
	},
	//Normal profile
	{
		11,
		(const rcs_profile_entry_t []) {
			{120, 18.f},
			{300, 40.f},
			{420, 58.f},
			{600, 75.f},
			{720, 85.f},
			{840, 98.f},
			{960, 118.f},
			{1080, 113.f},
			{1215, 102.f},
			{1230, 90.f},
			{1440, 80.f}
		}
	},
	//Fast profile
	{
		10,
		(const rcs_profile_entry_t []) {
			{120, 18.f},
			{240, 48.f},
			{420, 70.f},
			{600, 90.f},
			{720, 110.f},
			{840, 100.f},
			{960, 88.f},
			{1080, 85.f},
			{1140, 82.f},
			{1200, 80.f}
		}
	},
	//Minute mode
	{
		9,
		(const rcs_profile_entry_t []) {
			{60, 40.f},
			{150, 70.f},
			{210, 90.f},
			{270, 120.f},
			{330, 140.f},
			{420, 130.f},
			{480, 90.f},
			{540, 80.f},
			{600, 70.f}
		}
	}
};

const rcs_profile_t* rcs_profile_get(cook_mode_k mode) {
	if(mode == NONE) {
		return 0;
	}

	return &profiles_map[mode];
}

float rcs_profile_calc_profile_power(
		const rcs_profile_t* profile,
		float current_temp,
		int time)
{
	float power = 0;
	for(int i=0; i<profile->data_size; i++) {
		int entry_time = profile->data[i].time;
		if(time < entry_time) {
			float entry_temp = profile->data[i].temp;
			int dt = entry_time - time;
			float dT = entry_temp - current_temp;
			power = rcs_profile_calc_power(dT, dt);
			break;
		}
	}

	return power;
}

float rcs_profile_calc_power(float dT, int dt) {
	float q_needed = RC_VOLUME * RC_THERMAL_CAP * dT;
	float power = q_needed / dt;
	return power;
}

int rcs_profile_duration(const rcs_profile_t* profile) {
	if(profile){
		return profile->data[profile->data_size-1].time;
	}
	return 0;
}
