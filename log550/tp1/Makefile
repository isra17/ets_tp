CC=avr32-gcc
OBJCOPY=avr32-objcopy
CFLAGS=-Wall -D BOARD=EVK1100 -D __AVR32_UC3A0512__ -ffunction-sections
MPART = -mpart=uc3a0512
TARGET = at32uc3a0512
INCLUDE= \
	-I. 										\
	-I/home/isra/opt/atmel-headers-6.1.3.1475 	\
	-I./asf/avr32/boards 						\
	-I./asf/avr32/boards/evk1100 				\
	-I./asf/avr32/drivers/gpio 					\
	-I./asf/avr32/drivers/intc 					\
	-I./asf/avr32/utils 						\
	-I./asf/avr32/utils/preprocessor 			\
	-I./asf/common/boards 						\
	-I./asf/common/utils 						\
	-I./asf 									\
	-I./asf/avr32/drivers/adc 					\
	-I./asf/avr32/drivers/tc 					\
	-I./asf/avr32/drivers/usart 				\
	-I./asf/avr32/drivers/flashc 				\
	-I./asf/avr32/drivers/pm 					\
	-I./asf/avr32/

SRC= \
	./asf/avr32/boards/evk1100/led.c 			\
	./asf/avr32/drivers/adc/adc.c 				\
	./asf/avr32/drivers/intc/intc.c 			\
	./asf/avr32/drivers/tc/tc.c 				\
	./asf/avr32/drivers/gpio/gpio.c 			\
	./asf/avr32/drivers/usart/usart.c 			\
	./asf/avr32/drivers/pm/pm.c 				\
	./asf/avr32/drivers/pm/pm_conf_clocks.c 	\
	./asf/avr32/drivers/pm/power_clocks_lib.c 	\
	./asf/avr32/drivers/flashc/flashc.c

ASSRC = \
	./asf/avr32/drivers/intc/exception.S

OBJ=$(SRC:.c=.o) $(ASSRC:.S=.o)
all: main.hex

%.elf: %.o trampoline.o $(OBJ)
	$(CC) -Wl,-e,_trampoline $(MPART) $^ -o $@

%.o: %.S
	$(CC) -x assembler-with-cpp $(INCLUDE) $(MPART) -c $^ -o $@

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDE) $(MPART) -c $^ -o $@

%.hex: %.elf
	$(OBJCOPY) -O ihex -R .eeprom -R .fuse -R .lock -R .signature $^ $@

clean:
	rm *.hex *.elf *.o $(OBJ) *.map 2>/dev/null || true

test:
	dfu-programmer $(TARGET) get bootloader-version

.PHONY: %.up
%.up: %.hex
	dfu-programmer $(TARGET) erase
	dfu-programmer $(TARGET) flash --suppress-bootloader-mem $^
	-dfu-programmer $(TARGET) start

