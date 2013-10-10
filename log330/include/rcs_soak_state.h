/*
 * rcs_soak_state.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_SOAK_STATE_H_INCLUDED__
#define __RCS_SOAK_STATE_H_INCLUDED__

#define SOAK_TIME 3600 //1 hour

rcs_state_t* rcs_soak_state_new();
void rcs_soak_state_run(rcs_state_t* state);
void rcs_soak_state_on_timeout(rcs_state_t* state);

#endif /* RCS_SOAK_STATE_H_ */
