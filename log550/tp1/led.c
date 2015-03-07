/*=============================================================================*/
/* demo1_led_basic.c                                                           */
/*                                                                             */
/* Roulement des LED1-LED2-LED3-LED4, avec un delai par boucle FOR a vide.     */
/* Demonstration inspiree de: gpio_peripheral_bus_example du FRAMEWORK AVR32.  */
/*=============================================================================*/
/*=============================================================================*/
/* Composant FRAMEWORK a ajouter:  (GPIO et INTC deja inclu par defaut)        */
/*     Aucun                                                                   */
/*=============================================================================*/

#include "asf.h"

/* Au reset, le microcontroleur opere sur un crystal interne a 115200Hz.
 * Il est possible de changer, en execution, pour le crystal OSC0 a 12Mhz.
 * grace a un service du Power Manager (PM)  */

int main(void)
{
  LED_Display(LED1);
  volatile int i=0;
  while (1)  // Boucle infinie
  {
    i++;
  }
}