/*
 * rcs_waitstate.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <stdio.h>

#include "rcs_system.h"
#include "rcs_state.h"
#include "rcs_waitstate.h"
#include "rcs_input.h"
#include "rcs_mode_selected_state.h"
#include "rcs_simulator.h"

rcs_state_t* rcs_waitstate_new() {
	rcs_state_t* state = rcs_state_new();
	state->run = &rcs_waitstate_run;

	return state;
}

void rcs_waitstate_run(rcs_state_t* state) {
	RCS_SIMULATE(rcs_simulator_input_prompt());

	rcs_input_t input = rcs_input_peek();
	cook_mode_k mode = NONE;

	if(input.slow_mode) {
		mode = SLOW;
	} else if(input.normal_mode) {
		mode = NORMAL;
	} else if(input.fast_mode) {
		mode = FAST;
	} else if(input.minute_mode) {
		mode = MINUTE;
	}

	if(mode != NONE) {
		RCS_DEBUG("Mode %d selected", mode);
		rcs_state_t* next_state = rcs_mode_selected_state_new();
		rcs_set_selected_mode(mode);
		rcs_set_current_state(next_state);
	}
}
