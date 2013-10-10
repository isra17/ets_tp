#include <stdio.h>

#include "rcs_system.h"
#include "rcs_state.h"
#include "rcs_waitstate.h"
#include "rcs_input.h"
#include "rcs_pot_removed_state.h"
#include "rcs_simulator.h"
#include "rcs_time.h"

rcs_state_t* current_state = NULL;
cook_mode_k selected_mode = NONE;
char in_pot_removed_state = 0;

int main()
{
	rcs_init();
	return rcs_run();
}

void rcs_init() {
	current_state = rcs_waitstate_new();
}

int rcs_run(void)
{
	while(current_state) {
		RCS_SIMULATE(rcs_simulator_time());

		rcs_input_t input = rcs_input_peek();
		if(!input.pot_inserted && !in_pot_removed_state) {
			in_pot_removed_state = 1;
			current_state = rcs_pot_removed_state_new(current_state);
		} else if(in_pot_removed_state) {
			in_pot_removed_state = 0;
		}

		rcs_state_run(current_state);
	}

	return 0;
}

void rcs_set_selected_mode(cook_mode_k mode) {
	selected_mode = mode;
}

cook_mode_k rcs_get_selected_mode() {
	return selected_mode;
}

void rcs_set_current_state(rcs_state_t* state) {
	rcs_state_exit(current_state);
	current_state = state;
}
