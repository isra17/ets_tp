#include <asf.h>
#include <avr32/io.h>

#define ADC_INSTANCE                       (&AVR32_ADC)
#define TC_INSTANCE                        (&AVR32_TC)
#define USART_INSTANCE                     (&AVR32_USART0)
#define GPIO_INSTANCE                      (&AVR32_GPIO)

#define TRUE 1
#define FALSE 0

#define TC_ADC_CHANNEL 0
#define TC_LED_CHANNEL 1

#define TC_LED_FREQ    10

#define SUCH_FASTNESS

#ifdef SUCH_FASTNESS
#  define TC_ADC_FREQ    20000
#  define USART_BAUDRATE 921600
#else
#  define TC_ADC_FREQ    4000
#  define USART_BAUDRATE 57600
#endif

#define FPBA           FOSC0

// Configuration du peripherique TC
static const tc_waveform_opt_t ADC_WAVEFORM_OPT =
{
	.channel  = TC_ADC_CHANNEL,                        // Channel selection.

	.bswtrg   = TC_EVT_EFFECT_NOOP,                // Software trigger effect on TIOB.
	.beevt    = TC_EVT_EFFECT_NOOP,                // External event effect on TIOB.
	.bcpc     = TC_EVT_EFFECT_NOOP,                // RC compare effect on TIOB.
	.bcpb     = TC_EVT_EFFECT_NOOP,                // RB compare effect on TIOB.

	.aswtrg   = TC_EVT_EFFECT_NOOP,                // Software trigger effect on TIOA.
	.aeevt    = TC_EVT_EFFECT_NOOP,                // External event effect on TIOA.
	.acpc     = TC_EVT_EFFECT_NOOP,                // RC compare effect on TIOA: toggle.
	.acpa     = TC_EVT_EFFECT_NOOP,                // RA compare effect on TIOA: toggle
	.wavsel   = TC_WAVEFORM_SEL_UP_MODE_RC_TRIGGER,// Waveform selection: Up mode with automatic trigger(reset) on RC compare.
	.enetrg   = FALSE,                             // External event trigger enable.
	.eevt     = 0,                                 // External event selection.
	.eevtedg  = TC_SEL_NO_EDGE,                    // External event edge selection.
	.cpcdis   = FALSE,                             // Counter disable when RC compare.
	.cpcstop  = FALSE,                             // Counter clock stopped with RC compare.

	.burst    = FALSE,                             // Burst signal selection.
	.clki     = FALSE,                             // Clock inversion.
	.tcclks   = TC_CLOCK_SOURCE_TC4                // Internal source clock 3, connected to fPBA / 8.
};

// Configuration du peripherique TC
static const tc_waveform_opt_t LED_WAVEFORM_OPT =
{
	.channel  = TC_LED_CHANNEL,                        // Channel selection.

	.bswtrg   = TC_EVT_EFFECT_NOOP,                // Software trigger effect on TIOB.
	.beevt    = TC_EVT_EFFECT_NOOP,                // External event effect on TIOB.
	.bcpc     = TC_EVT_EFFECT_NOOP,                // RC compare effect on TIOB.
	.bcpb     = TC_EVT_EFFECT_NOOP,                // RB compare effect on TIOB.

	.aswtrg   = TC_EVT_EFFECT_NOOP,                // Software trigger effect on TIOA.
	.aeevt    = TC_EVT_EFFECT_NOOP,                // External event effect on TIOA.
	.acpc     = TC_EVT_EFFECT_NOOP,                // RC compare effect on TIOA: toggle.
	.acpa     = TC_EVT_EFFECT_NOOP,                // RA compare effect on TIOA: toggle
	.wavsel   = TC_WAVEFORM_SEL_UP_MODE_RC_TRIGGER,// Waveform selection: Up mode with automatic trigger(reset) on RC compare.
	.enetrg   = FALSE,                             // External event trigger enable.
	.eevt     = 0,                                 // External event selection.
	.eevtedg  = TC_SEL_NO_EDGE,                    // External event edge selection.
	.cpcdis   = FALSE,                             // Counter disable when RC compare.
	.cpcstop  = FALSE,                             // Counter clock stopped with RC compare.

	.burst    = FALSE,                             // Burst signal selection.
	.clki     = FALSE,                             // Clock inversion.
	.tcclks   = TC_CLOCK_SOURCE_TC4                // Internal source clock 3, connected to fPBA / 8.
};


static const tc_interrupt_t TC_INTERRUPT =
{
	.etrgs = 0,
	.ldrbs = 0,
	.ldras = 0,
	.cpcs  = 1,
	.cpbs  = 0,
	.cpas  = 0,
	.lovrs = 0,
	.covfs = 0
};

static uint32_t current_sampling_rate = TC_ADC_FREQ / 2;

#define TOGGLE_LED(led) GPIO_INSTANCE->port[ led##_GPIO/32 ].ovrt = 1 << (led##_GPIO%32)
#define SET_LED(led) GPIO_INSTANCE->port[led##_GPIO/32 ].ovrc = 1 << (led##_GPIO%32)
#define CLEAR_LED(led) GPIO_INSTANCE->port[led##_GPIO/32 ].ovrs = 1 << (led##_GPIO%32)

__attribute__((__interrupt__)) static void adc_irq( void ) {
	if (USART_INSTANCE->csr & AVR32_USART_CSR_TXRDY_MASK) {
		SET_LED(LED3);
	}
	
	USART_INSTANCE->ier = AVR32_USART_IER_TXRDY_MASK;
}

__attribute__((__interrupt__)) static void adc_timer_irq( void ) {	
	adc_start(ADC_INSTANCE);
	tc_read_sr(TC_INSTANCE, TC_ADC_CHANNEL);
}


__attribute__((__interrupt__)) static void led_timer_irq( void ) {
	TOGGLE_LED(LED0);
	
	if (TC_INSTANCE->channel[TC_ADC_CHANNEL].imr & AVR32_TC_IMR1_CPCS_MASK) {
		TOGGLE_LED(LED1);
	}
	
	tc_read_sr(TC_INSTANCE, TC_LED_CHANNEL);
}

__attribute__((__interrupt__)) static void usart_irq( void ) {
	if (USART_INSTANCE->csr & (AVR32_USART_CSR_RXRDY_MASK)) {
		uint8_t rdata = USART_INSTANCE->rhr;
		if (rdata == 's') {
			TC_INSTANCE->channel[TC_ADC_CHANNEL].ier = AVR32_TC_IER0_CPCS_MASK;
		} else if (rdata == 'x') {
			TC_INSTANCE->channel[TC_ADC_CHANNEL].idr = AVR32_TC_IDR0_CPCS_MASK;
		}
	} else {
		uint32_t cdr = 0;
		uint32_t sr = ADC_INSTANCE->sr;
		
		if (sr & AVR32_ADC_SR_EOC1_MASK) {
			cdr = (ADC_INSTANCE->cdr1 >> 2) & 0xfe;
		} else {
			cdr = (ADC_INSTANCE->cdr2 >> 2) | 0x1;
		}
		
		if (sr & (AVR32_ADC_SR_OVRE1_MASK | AVR32_ADC_SR_OVRE2_MASK)) {
			SET_LED(LED2);
		}
		
		USART_INSTANCE->thr = cdr & AVR32_USART_THR_TXCHR_MASK;
		USART_INSTANCE->idr = AVR32_USART_IDR_TXRDY_MASK;
	}
}

__attribute__((__interrupt__)) static void gpio_irq( void ) {
	if (current_sampling_rate == TC_ADC_FREQ) {
		current_sampling_rate /= 2;
	} else {
		current_sampling_rate *= 2;
	}
	
	TC_INSTANCE->channel[TC_ADC_CHANNEL].rc = ((FPBA / 32) / current_sampling_rate) & AVR32_TC_RC_MASK;
	GPIO_INSTANCE->port[2].ifrc = 1<<24;
}

static void adc_init( void ) {
	INTC_register_interrupt(&adc_irq, AVR32_ADC_IRQ, AVR32_INTC_INT0);
	
	adc_configure(ADC_INSTANCE);
	
	adc_enable(ADC_INSTANCE, 1);
	adc_enable(ADC_INSTANCE, 2);
	
	ADC_INSTANCE->ier = AVR32_ADC_IER_EOC1_MASK | AVR32_ADC_IER_EOC2_MASK;
}

static void tc_init( void ) {
	
	INTC_register_interrupt(&adc_timer_irq, AVR32_TC_IRQ0, AVR32_INTC_INT0);
	INTC_register_interrupt(&led_timer_irq, AVR32_TC_IRQ1, AVR32_INTC_INT1);
	
	tc_init_waveform(TC_INSTANCE, &ADC_WAVEFORM_OPT);
	tc_write_rc(TC_INSTANCE, TC_ADC_CHANNEL, (FPBA / 32) / current_sampling_rate);
	tc_configure_interrupts(TC_INSTANCE, TC_ADC_CHANNEL, &TC_INTERRUPT);
	
	tc_init_waveform(TC_INSTANCE, &LED_WAVEFORM_OPT);
	tc_write_rc(TC_INSTANCE, TC_LED_CHANNEL, (FPBA / 32) / TC_LED_FREQ);
	tc_configure_interrupts(TC_INSTANCE, TC_LED_CHANNEL, &TC_INTERRUPT);
	
	tc_start(TC_INSTANCE, TC_ADC_CHANNEL);
	tc_start(TC_INSTANCE, TC_LED_CHANNEL);
}

static void usart_init( void ) {
	static const gpio_map_t USART_GPIO_MAP =
	{
		{AVR32_USART0_RXD_0_0_PIN, AVR32_USART0_RXD_0_0_FUNCTION},
		{AVR32_USART0_TXD_0_0_PIN, AVR32_USART0_TXD_0_0_FUNCTION}
	};
	
	static const usart_options_t USART_OPTION = {
		.baudrate = USART_BAUDRATE,
		.channelmode = USART_NORMAL_CHMODE,
		.charlength = 8,
		.paritytype = USART_NO_PARITY,
		.stopbits = USART_1_STOPBIT
	};
	
	INTC_register_interrupt(&usart_irq, AVR32_USART0_IRQ, AVR32_INTC_INT0);
	
	gpio_enable_module(USART_GPIO_MAP, sizeof(USART_GPIO_MAP) / sizeof(USART_GPIO_MAP[0]));
	usart_init_rs232(USART_INSTANCE, &USART_OPTION, FPBA);
	
	USART_INSTANCE->ier = AVR32_USART_IER_RXRDY_MASK;
}

static void gpio_init( void ) {
	INTC_register_interrupt(&gpio_irq, (AVR32_GPIO_IRQ_0+88/8), AVR32_INTC_INT1);
	
	GPIO_INSTANCE->port[2].gfers = 1 << (24 & 0x1F);
	
	GPIO_INSTANCE->port[2].imr0s = 1 << (24 & 0x1F);
	GPIO_INSTANCE->port[2].imr1c = 1 << (24 & 0x1F);
	
	GPIO_INSTANCE->port[2].iers = 1 << (24 & 0x1F);
	
	LED_Display(0);
}

int main(void)
{	
	pcl_switch_to_osc(PCL_OSC0, FOSC0, OSC0_STARTUP);

	INTC_init_interrupts();
	
	adc_init();
	tc_init();
	usart_init();
	gpio_init();
	
	cpu_irq_enable();

	while(TRUE) {}
}