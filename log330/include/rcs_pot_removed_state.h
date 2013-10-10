/*
 * rcs_pot_removed_state.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_POT_REMOVED_STATE_H_INCLUDED__
#define __RCS_POT_REMOVED_STATE_H_INCLUDED__

#include "rcs_state.h"

typedef struct rcs_pot_removed_state_t {
	rcs_state_t base;
	rcs_state_t* state_before;
} rcs_pot_removed_state_t;

rcs_state_t* rcs_pot_removed_state_new();
void rcs_pot_removed_state_run(rcs_state_t* state);

#endif /* __RCS_POT_REMOVED_STATE_H_INCLUDED__ */
