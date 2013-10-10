/*
 * rcs_alarm_state.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_ALARM_STATE_H_INCLUDED__
#define __RCS_ALARM_STATE_H_INCLUDED__

#include "rcs_state.h"

#define ALARM_TIME 3 //3 sec

rcs_state_t* rcs_alarm_state_new();
void rcs_alarm_state_run(rcs_state_t* state);
void rcs_alarm_state_on_timeout(rcs_state_t* state);
void rcs_alarm_state_exit(rcs_state_t* state);

#endif /* RCS_ALARM_STATE_H_ */
