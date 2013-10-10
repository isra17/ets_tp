/*
 * rcs_cook_state.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include "rcs_system.h"
#include "rcs_cook_state.h"
#include "rcs_output.h"
#include "rcs_alarm_state.h"
#include "rcs_heating_profile.h"
#include "rcs_time.h"
#include "rcs_heating_controller.h"
#include "rcs_simulator.h"
#include "rcs_input.h"

rcs_state_t* rcs_cook_state_new() {
	rcs_state_t* state = rcs_state_new();

	state->run = &rcs_cook_state_run;
	state->exit = &rcs_cook_state_exit;
	state->on_timeout = &rcs_cook_state_on_timeout;

	cook_mode_k mode = rcs_get_selected_mode();
	const rcs_profile_t* profile = rcs_profile_get(mode);
	state->timeout = rcs_profile_duration(profile);

	return state;
}

void rcs_cook_state_run(rcs_state_t* state) {
	RCS_SIMULATE(rcs_simulator_cooking_input_prompt());

	rcs_output_set_cooking_display(1);

	int time = rcs_time_get_tick() - state->start_time;
	cook_mode_k mode = rcs_get_selected_mode();
	rcs_heating_controller_update(mode, time);
}

void rcs_cook_state_exit(rcs_state_t* state) {
	rcs_output_set_cooking_display(0);
	RCS_DEBUG("Finished cooking");
}

void rcs_cook_state_on_timeout(rcs_state_t* state) {
	rcs_state_t* next_state = rcs_alarm_state_new();
	rcs_set_current_state(next_state);
}

