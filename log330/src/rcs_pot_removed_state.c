/*
 * rcs_pot_removed_state.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <stdlib.h>
#include <memory.h>

#include "rcs_pot_removed_state.h"
#include "rcs_system.h"
#include "rcs_input.h"
#include "rcs_simulator.h"

rcs_state_t* rcs_pot_removed_state_new(rcs_state_t* state_before) {
	rcs_pot_removed_state_t* state = malloc(sizeof(rcs_pot_removed_state_t));
	memset(state, 0, sizeof(rcs_pot_removed_state_t));

	state->state_before = state_before;
	state->base.run = &rcs_pot_removed_state_run;

	return (rcs_state_t*)state;
}

void rcs_pot_removed_state_run(rcs_state_t* state) {
	RCS_SIMULATE(rcs_simulator_pot_removed_input_prompt());

	rcs_pot_removed_state_t* this = (rcs_pot_removed_state_t*)state;
	rcs_input_t input = rcs_input_peek();
	if(input.pot_inserted) {
		rcs_set_current_state(this->state_before);
	}
}
