/*
 * rcs_state.cpp
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <stdlib.h>
#include <assert.h>
#include "rcs_state.h"
#include "rcs_time.h"

rcs_state_t* rcs_state_new() {
	rcs_state_t* state = malloc(sizeof(rcs_state_t));
	state->start_time = 0;
	state->timeout = 0;
	state->run = NULL;
	state->exit = NULL;

	return state;
}

void rcs_state_destroy(rcs_state_t* state) {
	if(state) {
		free(state);
	}
}

void rcs_state_run(rcs_state_t* state) {
	assert(state != NULL);
	assert(state->run != NULL);

	if(!state->start_time) {
		state->start_time = rcs_time_get_tick();
	}

	if(state->timeout) {
		unsigned int currentTick = rcs_time_get_tick();
		if(state->start_time + state->timeout < currentTick && state->on_timeout) {
			state->on_timeout(state);
			return;
		}
	}

	state->run(state);
}


void rcs_state_exit(rcs_state_t* state) {
	assert(state != NULL);
	if(state->exit) {
		state->exit(state);
	}
	rcs_state_destroy(state);
}
