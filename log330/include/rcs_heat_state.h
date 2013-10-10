/*
 * rcs_heat_state.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef RCS_HEAT_STATE_H_
#define RCS_HEAT_STATE_H_

#include "rcs_state.h"

#define HEAT_TIME 60*60*4 //4 hours

rcs_state_t* rcs_heat_state_new();
void rcs_heat_state_run(rcs_state_t* state);
void rcs_heat_state_on_timeout(rcs_state_t* state);
void rcs_heat_state_exit(rcs_state_t* state);

#endif /* RCS_HEAT_STATE_H_ */
