/*
 * rcs_waitstate.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_WAITSTATE_H_INCLUDED__
#define __RCS_WAITSTATE_H_INCLUDED__

#include "rcs_state.h"

rcs_state_t* rcs_waitstate_new();
void rcs_waitstate_run(rcs_state_t* state);
void rcs_waitstate_on_timeout(rcs_state_t* state);

#endif /* RCS_WAITSTATE_H_ */
