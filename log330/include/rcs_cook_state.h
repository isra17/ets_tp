/*
 * rcs_cook_state.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_COOK_STATE_H_INCLUDED__
#define __RCS_COOK_STATE_H_INCLUDED__

#include "rcs_state.h"

rcs_state_t* rcs_cook_state_new();
void rcs_cook_state_run(rcs_state_t* state);
void rcs_cook_state_on_timeout(rcs_state_t* state);
void rcs_cook_state_exit(rcs_state_t* state);

#endif /* RCS_COOK_STATE_H_ */
