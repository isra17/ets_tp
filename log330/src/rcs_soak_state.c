/*
 * rcs_soak_state.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include "rcs_system.h"
#include "rcs_soak_state.h"
#include "rcs_cook_state.h"
#include "rcs_simulator.h"

rcs_state_t* rcs_soak_state_new() {
	rcs_state_t* state = rcs_state_new();

	state->run = &rcs_soak_state_run;
	state->on_timeout = &rcs_soak_state_on_timeout;

	state->timeout = SOAK_TIME;

	return state;
}

void rcs_soak_state_run(rcs_state_t* state) {

}

void rcs_soak_state_on_timeout(rcs_state_t* state) {
	RCS_DEBUG("Soaking finished");
	rcs_state_t* next_state = rcs_cook_state_new();
	rcs_set_current_state(next_state);
}

