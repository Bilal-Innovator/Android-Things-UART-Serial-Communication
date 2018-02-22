package serial.bilal.com.uartserial;

import android.os.Build;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_IMX6UL_PICO = "imx6ul_pico";
    private static final String DEVICE_IMX7D_PICO = "imx7d_pico";

    /**
     * Return the UART for loopback.
     */
    public static String getUartName() {
        switch (Build.DEVICE) {
            case DEVICE_RPI3:
                return "UART0";
            case DEVICE_IMX6UL_PICO:
                return "UART3";
            case DEVICE_IMX7D_PICO:
                return "UART6";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }
}
