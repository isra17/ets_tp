/*
 * rcs_time.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <time.h>
#include "rcs_time.h"
#include "rcs_simulator.h"

#ifdef __SIMULATOR

unsigned int simulated_time = 0;

void rcs_simulator_time() {
	simulated_time += RCS_SIMULATOR_TIMESTEP;
}

unsigned int rcs_time_get_tick() {
	return simulated_time;
}

#else

unsigned int rcs_time_get_tick() {
	return time(0);
}

#endif


