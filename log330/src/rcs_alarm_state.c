/*
 * rcs_alarm_state.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include "rcs_alarm_state.h"
#include "rcs_heat_state.h"
#include "rcs_system.h"
#include "rcs_output.h"

rcs_state_t* rcs_alarm_state_new() {
	rcs_state_t* state = rcs_state_new();
	state->run = &rcs_alarm_state_run;
	state->exit = &rcs_alarm_state_exit;
	state->on_timeout = &rcs_alarm_state_on_timeout;

	state->timeout = ALARM_TIME;

	return state;
}

void rcs_alarm_state_run(rcs_state_t* state) {
	rcs_output_set_alarm(1);
}

void rcs_alarm_state_on_timeout(rcs_state_t* state) {
	rcs_state_t* next_state = rcs_heat_state_new();
	rcs_set_current_state(next_state);
}

void rcs_alarm_state_exit(rcs_state_t* state) {
	rcs_output_set_alarm(0);
}
