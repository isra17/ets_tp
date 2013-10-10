/*
 * rcs_heat_state.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include "rcs_heat_state.h"
#include "rcs_waitstate.h"
#include "rcs_system.h"
#include "rcs_output.h"
#include "rcs_heating_controller.h"

rcs_state_t* rcs_heat_state_new() {
	rcs_state_t* state = rcs_state_new();

	state->run = &rcs_heat_state_run;
	state->exit = &rcs_heat_state_exit;
	state->on_timeout = &rcs_heat_state_on_timeout;

	state->timeout = HEAT_TIME;

	return state;
}

void rcs_heat_state_run(rcs_state_t* state) {
	rcs_output_set_heating_display(1);

	rcs_heating_controller_heat();
}

void rcs_heat_state_on_timeout(rcs_state_t* state) {
	rcs_state_t* next_state = rcs_waitstate_new();
	rcs_set_current_state(next_state);
}

void rcs_heat_state_exit(rcs_state_t* state) {
	rcs_output_set_heating_display(0);
	rcs_set_selected_mode(NONE);
}

