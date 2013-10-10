/*
 * rcs_mode_selected_state_new.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <stdio.h>

#include "rcs_system.h"
#include "rcs_mode_selected_state.h"
#include "rcs_cook_state.h"
#include "rcs_soak_state.h"
#include "rcs_input.h"
#include "rcs_simulator.h"

rcs_state_t* rcs_mode_selected_state_new() {
	rcs_state_t* state = rcs_state_new();

	state->run = &rcs_mode_selected_state_run;

	return state;
}

void rcs_mode_selected_state_run(rcs_state_t* state) {
	RCS_SIMULATE(rcs_simulator_input_prompt());

	rcs_input_t input = rcs_input_peek();
	rcs_state_t* next_state = NULL;

	if(input.start) {
		RCS_DEBUG("Start cooking");
		next_state = rcs_cook_state_new();
	} else if(input.soak) {
		RCS_DEBUG("Start soaking");
		next_state = rcs_soak_state_new();
	}

	if(next_state) {
		rcs_set_current_state(next_state);
	}
}
