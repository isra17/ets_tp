/*
 * rcs_input.c
 *
 *  Created on: 2013-03-12
 *      Author: isra
 */

#include "rcs_input.h"
#include "stdio.h"

#ifdef __SIMULATOR
rcs_input_t simulated_input = {1,0};
#endif

rcs_input_t rcs_input_peek() {
	#ifdef __SIMULATOR
		return simulated_input;
	#else
		#error "RCS only support simulation"
	#endif
}

#ifdef __SIMULATOR

void rcs_simulator_input_prompt() {
	int choice = 0;
	do {
		printf("Press a button:\n");
		printf("1) Start cooking\n");
		printf("2) Start soaking\n");
		printf("3) Slow mode\n");
		printf("4) Normal mode\n");
		printf("5) Fast mode\n");
		printf("6) Minute mode\n");
		printf("Do an action:\n");
		printf("7) Remove pot\n");
		printf("8) Insert pot\n");
		printf("9) Nothing\n");
		scanf("%d", &choice);
	} while(choice < 1 || choice > 8);

	rcs_input_t choice_input = {0};
	choice_input.pot_inserted = simulated_input.pot_inserted;

	if(choice < 7) {
		((int*)&choice_input)[choice] = 1;
	} else if(choice < 9) {
		choice_input.pot_inserted = (choice == 8);
	}

	simulated_input = choice_input;
}

void rcs_simulator_cooking_input_prompt() {
	int choice = 0;
	do {
		printf("Do an action:\n");
		printf("1) Wait...\n");
		printf("2) Remove pot\n");
		scanf("%d", &choice);
	} while(choice < 1 || choice > 2);

	rcs_input_t choice_input = {0};
	choice_input.pot_inserted = simulated_input.pot_inserted;
	choice_input.pot_inserted = (choice != 2);

	simulated_input = choice_input;
}

void rcs_simulator_pot_removed_input_prompt() {
	int choice = 0;
	printf("Do an action:\n");
	printf("1) Put pot\n");
	scanf("%d", &choice);

	rcs_input_t choice_input = {0};
	choice_input.pot_inserted = simulated_input.pot_inserted;
	choice_input.pot_inserted = (choice == 1);

	simulated_input = choice_input;
}

void rcs_simulator_input_reset() {
	rcs_input_t choice_input = {0};
	choice_input.pot_inserted = simulated_input.pot_inserted;
	simulated_input = choice_input;
}

#endif
