/*
 * rcs_input.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef RCS_INPUT_H_
#define RCS_INPUT_H_

typedef struct rcs_input_t {
	int pot_inserted;
	int start;
	int soak;
	int slow_mode;
	int normal_mode;
	int fast_mode;
	int minute_mode;
} rcs_input_t;

rcs_input_t rcs_input_peek();

#ifdef __SIMULATOR
void rcs_simulator_input_prompt();
void rcs_simulator_cooking_input_prompt();
void rcs_simulator_pot_removed_input_prompt();
void rcs_simulator_input_reset();
#endif

#endif /* RCS_INPUT_H_ */
