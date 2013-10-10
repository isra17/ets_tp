/*
 * rcs_simulator.h
 *
 *  Created on: 2013-03-25
 *      Author: isra
 */

#ifndef __RCS_SIMULATOR_H_INCLUDED__
#define __RCS_SIMULATOR_H_INCLUDED__

#include "rcs_system.h"
#include "stdio.h"

#define RCS_SIMULATOR_TIMESTEP 30

#ifdef __SIMULATOR
#define RCS_SIMULATE(fn) fn
#define RCS_DEBUG(format, ...) printf("[Debug] " format "\n", ##__VA_ARGS__)
#define RCS_INFO(format, ...) printf("[INFO] " format "\n", ##__VA_ARGS__)
#else
#define RCS_SIMULATE(fn)
#define RCS_DEBUG(format, ...)
#define RCS_INFO(format, ...)
#endif

#endif /* __RCS_SIMULATOR_H_INCLUDED__ */
