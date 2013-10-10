/*
 * rcs_time.h
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#ifndef __RCS_TIME_H_INCLUDED__
#define __RCS_TIME_H_INCLUDED__

unsigned int rcs_time_get_tick();

#ifdef __SIMULATOR
void rcs_simulator_time();
#endif

#endif /* RCS_TIME_H_ */
