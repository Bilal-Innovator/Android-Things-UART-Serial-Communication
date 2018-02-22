package serial.bilal.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.UartDevice;

import java.io.UnsupportedEncodingException;

import serial.bilal.com.uartserial.OnSerialDataReceived;
import serial.bilal.com.uartserial.UARTService;

public class MainActivity extends Activity implements OnSerialDataReceived {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final UARTService uartService = new UARTService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Constructor parameters
         uartService.Initialize(
         OnSerialDataReceived Listener,
         Baud Rate,
         Data Bit,
         Stop Bit,
         Data Chunk Size);
         **/

        //Initialize the UARTSerial Library
        uartService.Initialize(this,
                115200,
                8,
                1,
                512);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uartService.close();
    }

    @Override
    public void onDataReceived(UartDevice uartDevice, byte[] buffer) {
        try {
            String converted = new String(buffer, "UTF-8");
            Log.d(TAG, "onDataReceived() returned: " + converted);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
