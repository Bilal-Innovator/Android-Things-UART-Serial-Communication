package serial.bilal.com.uartserial;

import com.google.android.things.pio.UartDevice;

/**
 * Created by BLACK on 1/30/2018.
 */

public interface OnSerialDataReceived {
    void onDataReceived(UartDevice uartDevice, byte[] buffer);
}
