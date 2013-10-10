/*
 * rcs_output.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include <stdio.h>
#include "rcs_output.h"

void rcs_output_set_alarm(char state) {
	printf("alarm state %d\n", (int)state);
}

void rcs_output_set_heating_display(char state) {
	printf("heating display state %d\n", (int)state);
}

void rcs_output_set_cooking_display(char state) {
	printf("cooking state %d\n", (int)state);
}
