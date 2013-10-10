/*
 * rcs_state.h
 *
 *  Created on: 2013-03-11
 *      Author: isra
 */

#ifndef __RCS_STATE_H_INCLUDED__
#define __RCS_STATE_H_INCLUDED__

typedef struct rcs_state_t {
	unsigned int timeout;
	unsigned int start_time;

	void (*run)(struct rcs_state_t*);
	void (*exit)(struct rcs_state_t*);
	void (*on_timeout)(struct rcs_state_t*);
} rcs_state_t;

rcs_state_t* rcs_state_new();
void rcs_state_destroy(rcs_state_t* state);
void rcs_state_run(rcs_state_t* state);
void rcs_state_exit(rcs_state_t* state);

#endif /* RCS_STATE_H_ */
