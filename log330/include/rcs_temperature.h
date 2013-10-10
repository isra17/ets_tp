/*
 * rcs_temperature.h
 *
 *  Created on: 2013-03-14
 *      Author: isra
 */

#ifndef __RCS_TEMPERATURE_H_INCLUDED__
#define __RCS_TEMPERATURE_H_INCLUDED__

#define T_RANGE_MIN 20.f
#define T_RANGE_MAX 300.f

#define F_TO_C(F) (F - 32) * 5/9

float rcs_temperature_poll();
void rcs_temperature_set_heater(float power /*Watt hour*/);

#endif /* __RCS_TEMPERATURE_H_INCLUDED__ */
